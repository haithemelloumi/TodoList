package com.helloumi.data.mappers

import com.helloumi.data.entity.TodoEntity
import com.helloumi.domain.model.response.TodoResponse.Todo


fun TodoEntity.toTodo(): Todo =
    Todo(
        completed,
        id,
        todo,
        userId
    )

fun Todo.toEntity(): TodoEntity =
    TodoEntity(
        id = id,
        completed = isCompleted,
        todo = todo,
        userId = userId
    )
