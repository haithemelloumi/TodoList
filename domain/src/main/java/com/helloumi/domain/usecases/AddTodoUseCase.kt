package com.helloumi.domain.usecases

import androidx.lifecycle.LiveData
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Adds Todo.
 */
class AddTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {

    /**
     * Executes use case.
     *
     * @return LiveData Boolean.
     */
    operator fun invoke(todo: Todo): LiveData<Boolean> = todoRepository.addTodo(todo)
}
