package com.helloumi.data.local.datasource

import com.helloumi.data.database.datasources.interfaces.TodoLDS
import com.helloumi.data.datasource.TodoDao
import com.helloumi.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoLDSImpl @Inject constructor(
    private val dao: TodoDao
) : TodoLDS {
    override suspend fun insertTodos(todos: List<TodoEntity>) = dao.insertTodos(todos)
    override suspend fun insertTodo(todos: TodoEntity) = dao.insertTodo(todos)
    override fun getTodos(): Flow<List<TodoEntity>> = dao.getTodos()
}
