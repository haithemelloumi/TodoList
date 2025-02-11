package com.helloumi.domain.usecases

import androidx.lifecycle.LiveData
import com.helloumi.domain.model.result.TodoResult
import com.helloumi.domain.repository.TodoRepository
import javax.inject.Inject

/**
 * Gets TodoResult.
 */
class GetTodoUseCase @Inject constructor(private val todoRepository: TodoRepository) {

    /**
     * Executes use case.
     *
     * @return LiveData TodoResult.
     */
    operator fun invoke(): LiveData<TodoResult>  = todoRepository.getTodos()
}
