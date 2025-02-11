package com.helloumi.domain.model.result

import com.helloumi.domain.model.response.TodoResponse

/**
 * todos result, from web service requests.
 */
sealed class TodoResult {

    /**
     * Loading result.
     */
    object Loading : TodoResult()

    /**
     * Success result.
     *
     * @property todoResponse downloaded todoResponse.
     */
    class Success(val todoResponse: TodoResponse) : TodoResult()

    /**
     * Unavailable server result.
     */
    object ServerUnavailable : TodoResult()
}
