package com.helloumi.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.helloumi.data.api.apc.ApcAPI
import com.helloumi.data.model.AddTodoResponse
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.domain.model.result.TodoResult
import com.helloumi.domain.repository.TodoRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(private val apcAPI: ApcAPI) :
    TodoRepository {

    private val _todosLiveData = MutableLiveData<TodoResult>()
    val todosLiveData: LiveData<TodoResult> = _todosLiveData

    private val _isAdded = MutableLiveData<Boolean>()
    val isAdded: LiveData<Boolean> = _isAdded

    override fun getTodos(): LiveData<TodoResult> {
        _todosLiveData.postValue(TodoResult.Loading) // Show loading state

        val call: Call<TodoResponse> = apcAPI.getTodos()
        call.enqueue(object : Callback<TodoResponse> {
            override fun onFailure(call: Call<TodoResponse>, t: Throwable) {
                Log.e("API CALL ERROR", t.message ?: "api call error")
                _todosLiveData.postValue(TodoResult.ServerUnavailable)
            }

            override fun onResponse(
                call: Call<TodoResponse>,
                response: Response<TodoResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    Log.i("API CALL Success", response.body().toString())
                    response.body()?.let {
                        _todosLiveData.postValue(TodoResult.Success(it))
                    }
                } else {
                    _todosLiveData.postValue(TodoResult.ServerUnavailable)
                    Log.w("API CALL NULL", response.body().toString())
                }
            }
        })

        return todosLiveData
    }

    override fun addTodo(todo: Todo): LiveData<Boolean> {

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
}
