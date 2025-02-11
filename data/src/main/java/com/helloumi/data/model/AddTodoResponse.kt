package com.helloumi.data.model

data class AddTodoResponse(
    val completed: Boolean,
    val id: Int,
    val todo: String,
    val userId: Int
)