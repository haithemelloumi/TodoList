package com.helloumi.ui.features.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.helloumi.domain.model.response.TodoResponse
import com.helloumi.domain.model.response.TodoResponse.Todo
import com.helloumi.domain.model.result.TodoResult
import com.helloumi.domain.usecases.AddTodoUseCase
import com.helloumi.domain.usecases.GetTodoUseCase
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Runs LiveData sync

    @Mock
    private lateinit var getTodoUseCase: GetTodoUseCase

    @Mock
    private lateinit var addTodoUseCase: AddTodoUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        homeViewModel = HomeViewModel(getTodoUseCase, addTodoUseCase)
    }

    @Test
    fun `callGetTodos should update todoResultMutableLiveData`() {
        // GIVEN
        val todoResult = TodoResult.Success(TodoResponse(listOf()))
        val todoResultLiveData = MutableLiveData<TodoResult>()
        todoResultLiveData.value = todoResult

        whenever(getTodoUseCase.invoke()).thenReturn(todoResultLiveData)

        val observer = mock<Observer<TodoResult>>()
        homeViewModel.todoResultMutableLiveData.observeForever(observer)

        // WHEN
        homeViewModel.callGetTodos()

        // THEN
        verify(observer).onChanged(todoResult)

        homeViewModel.todoResultMutableLiveData.removeObserver(observer)
    }

    @Test
    fun `callAddTodo should update isAddedLiveData`() {
        // GIVEN
        val isAddedLiveData = MutableLiveData<Boolean>()
        isAddedLiveData.value = true

        whenever(addTodoUseCase.invoke(any())).thenReturn(isAddedLiveData)

        val observer = mock<Observer<Boolean>>()
        homeViewModel.getIsAddedMutableLiveData().observeForever(observer)

        // WHEN
        homeViewModel.callAddTodo(Todo(false, "todo", 5))

        // THEN
        verify(observer).onChanged(true)

        homeViewModel.getIsAddedMutableLiveData().removeObserver(observer)
    }

    @Test
    fun `updateTodo should change isCompleted`() {
        // GIVEN
        val todo = Todo(false, "Test todo", 5)
        val todoResult = TodoResult.Success(TodoResponse(listOf(todo)))

        val todoResultLiveData = MutableLiveData<TodoResult>()
        todoResultLiveData.value = todoResult
        whenever(getTodoUseCase.invoke()).thenReturn(todoResultLiveData)

        homeViewModel.callGetTodos()
        val observer = mock<Observer<TodoResult>>()
        homeViewModel.todoResultMutableLiveData.observeForever(observer)

        // WHEN
        homeViewModel.updateTodo(todo)

        // THEN
        val updatedValue = homeViewModel.todoResultMutableLiveData.value
        assert(updatedValue is TodoResult.Success)
        assert((updatedValue as TodoResult.Success).todoResponse.todos[0].isCompleted == true)

        homeViewModel.todoResultMutableLiveData.removeObserver(observer)
    }
}
