package com.helloumi.data.database.datasources.interfaces

import com.helloumi.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoLDS {
    suspend fun insertTodo(todo: TodoEntity)
    suspend fun insertTodos(todos: List<TodoEntity>)
    fun getTodos(): Flow<List<TodoEntity>> // Change LiveData to Flow
}
