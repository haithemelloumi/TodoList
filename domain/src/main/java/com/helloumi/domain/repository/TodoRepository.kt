package com.helloumi.domain.repository

import androidx.lifecycle.LiveData
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.domain.model.result.TodoResult

interface TodoRepository {

    /**
     * Requests todo list.
     *
     * @return a TodoResult LiveData
     */
    fun getTodos(): LiveData<TodoResult>

    /**
     * Adds todo.
     *
     * @return a Boolean LiveData
     */
    fun addTodo(todo: Todo): LiveData<Boolean>
}
