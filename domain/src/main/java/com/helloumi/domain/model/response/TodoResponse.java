package com.helloumi.domain.model.response;

import java.util.List;

public class TodoResponse {

    private int limit;
    private int skip;
    private List<Todo> todos;
    private int total;

    public TodoResponse(List<Todo> todos) {
        this.todos = todos;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    public static class Todo {
        private boolean completed;
        private int id;
        private String todo;
        private int userId;

        public Todo(String todo) {
            this.todo = todo;
        }

        public Todo(boolean completed, String todo) {
            this.completed = completed;
            this.todo = todo;
        }

        public Todo(boolean completed, String todo, int userId) {
            this.completed = completed;
            this.todo = todo;
            this.userId = userId;
        }

        public Todo(boolean completed, int id, String todo, int userId) {
            this.completed = completed;
            this.id = id;
            this.todo = todo;
            this.userId = userId;
        }

        public int getId() {
            return id;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        public String getTodo() {
            return todo;
        }

        public int getUserId() {
            return userId;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setTodo(String todo) {
            this.todo = todo;
        }
    }
}
