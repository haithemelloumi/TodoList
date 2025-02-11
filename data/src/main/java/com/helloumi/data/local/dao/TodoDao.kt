package com.helloumi.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.helloumi.data.entity.TodoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao : BaseDao<TodoEntity> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodos(todos: List<TodoEntity>)

    @Query("SELECT * FROM Todo")
    fun getTodos(): Flow<List<TodoEntity>> // Return Flow instead of LiveData
}
