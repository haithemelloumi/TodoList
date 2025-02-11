package com.helloumi.data.api.apc

import com.helloumi.data.model.AddTodoResponse
import retrofit2.http.GET
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.domain.model.response.TodoResponse.Todo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApcAPI {

    @GET("todos")
    fun getTodos(): Call<TodoResponse>

    @Headers("Content-Type: application/json")
    @POST("todos/add")
    fun addTodo(@Body todo: Todo): Call<AddTodoResponse>
}
