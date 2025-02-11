package com.helloumi.ui.features.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.helloumi.domain.model.response.TodoResponse;
import com.helloumi.domain.model.result.TodoResult;
import com.helloumi.domain.usecases.AddTodoUseCase;
import com.helloumi.domain.usecases.GetTodoUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;


@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final MutableLiveData<TodoResult> todoResultMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isAddedMutableLiveData = new MutableLiveData<>();

    private final GetTodoUseCase getTodoUseCase;
    private final AddTodoUseCase addTodoUseCase;

    public LiveData<TodoResult> getTodoResultMutableLiveData() {
        return todoResultMutableLiveData;
    }

    public MutableLiveData<Boolean> getIsAddedMutableLiveData() {
        return isAddedMutableLiveData;
    }

    @Inject
    public HomeViewModel(
            GetTodoUseCase getTodoUseCase,
            AddTodoUseCase addTodoUseCase
    ) {
        this.getTodoUseCase = getTodoUseCase;
        this.addTodoUseCase = addTodoUseCase;
    }

    public void callGetTodos() {
        LiveData<TodoResult> liveData = getTodoUseCase.invoke();
        liveData.observeForever(todoResultMutableLiveData::setValue);
    }

    public void callAddTodo(TodoResponse.Todo todo) {
        LiveData<Boolean> liveData = addTodoUseCase.invoke(todo);
        liveData.observeForever(isAddedMutableLiveData::setValue);
    }

    public void addTodo(String todo) {
        // default user id 1
        TodoResponse.Todo todoToAdd = new TodoResponse.Todo(false, todo, 1);
        callAddTodo(todoToAdd);

        TodoResult currentTodoResult = todoResultMutableLiveData.getValue();

        if (currentTodoResult instanceof TodoResult.Success) {
            List<TodoResponse.Todo> existingTodos =
                    new ArrayList<>(((TodoResult.Success) currentTodoResult).getTodoResponse().getTodos());
            // Add new todo
            existingTodos.add(todoToAdd);
            // Create a new TodoResponse with updated todo list
            TodoResponse updatedResponse = new TodoResponse(existingTodos);
            // Update LiveData with new list
            todoResultMutableLiveData.setValue(new TodoResult.Success(updatedResponse));
        }
    }


    public void updateTodo(TodoResponse.Todo todo) {
        TodoResult currentTodoResult = todoResultMutableLiveData.getValue();

        if (currentTodoResult instanceof TodoResult.Success) {
            List<TodoResponse.Todo> updatedTodos =
                    new ArrayList<>(((TodoResult.Success) currentTodoResult).getTodoResponse().getTodos());

            for (TodoResponse.Todo t : updatedTodos) {
                if (t.getId() == todo.getId()) {
                    t.setCompleted(!t.isCompleted());
                    break;
                }
            }

            // Create a new TodoResponse with updated todo list
            TodoResponse updatedResponse = new TodoResponse(updatedTodos);
            // Update LiveData with new list
            todoResultMutableLiveData.setValue(new TodoResult.Success(updatedResponse));
        }
    }
}
