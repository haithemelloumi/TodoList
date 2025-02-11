package com.helloumi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helloumi.data.api.apc.ApcAPI
import com.helloumi.data.database.datasources.interfaces.TodoLDS
import com.helloumi.data.mappers.toEntity
import com.helloumi.data.mappers.toTodo
import com.helloumi.data.model.AddTodoResponse
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.domain.model.result.TodoResult
import com.helloumi.domain.repository.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val apcAPI: ApcAPI,
    private val todoLDS: TodoLDS,
) : TodoRepository {

    private val _todosLiveData = MutableLiveData<TodoResult>()
    val todosLiveData: LiveData<TodoResult> = _todosLiveData

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    override fun getTodos(): LiveData<TodoResult> {
        _todosLiveData.postValue(TodoResult.Loading) // Indiquer le chargement

        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call Api
                val response = apcAPI.getTodos().execute()

                if (response.isSuccessful && response.body() != null) {
                    val todos = response.body()!!.todos
                    _todosLiveData.postValue(TodoResult.Success(response.body()!!))

                    // Insert data to local datasource
                    todoLDS.insertTodos(todos.map { it.toEntity() })

                } else {
                    // if call api failed, get data from local datasource
                    getTodosFromLDS()
                }
            } catch (e: Exception) {
                // if call api failed, get data from local datasource
                getTodosFromLDS()
            }
        }

        return todosLiveData
    }

    override fun addTodo(todo: Todo): LiveData<Boolean> {
        insertTodoToLDS(todo)
        return insertTodoToRDS(todo)
    }

    //////////////////////////// INTERNAL METHODS ////////////////////////////

    // Gets list todo from local datasource
    private fun getTodosFromLDS() {
        CoroutineScope(Dispatchers.IO).launch {
            todoLDS.getTodos().collect { localTodos ->
                if (localTodos.isNotEmpty()) {
                    val todoResponse = TodoResponse(localTodos.map { it.toTodo() })
                    withContext(Dispatchers.Main) {
                        _todosLiveData.postValue(TodoResult.Success(todoResponse))
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        _todosLiveData.postValue(TodoResult.ServerUnavailable)
                    }
                }
            }
        }
    }

    // Insert list todo to to remote datasource
    private fun insertTodoToRDS(todo: Todo): LiveData<Boolean> {
        val call: Call<AddTodoResponse> = apcAPI.addTodo(todo)
        call.enqueue(object : Callback<AddTodoResponse> {
            override fun onFailure(call: Call<AddTodoResponse>, t: Throwable) {
                Log.e("API CALL ERROR", t.message ?: "api call error")
                _isAdded.postValue(false)
            }

            override fun onResponse(
                call: Call<AddTodoResponse>,
                response: Response<AddTodoResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.i("API CALL SUCCESS", response.body().toString())
                    _isAdded.postValue(true)
                } else {
                    Log.e("API CALL NULL", response.body().toString())
                    _isAdded.postValue(false)
                }
            }
        })
        return isAdded
    }

    // Insert todo to local datasource
    private fun insertTodoToLDS(todo: Todo) {
        CoroutineScope(Dispatchers.IO).launch {
            todoLDS.insertTodo(todo.toEntity())
        }
    }
}
