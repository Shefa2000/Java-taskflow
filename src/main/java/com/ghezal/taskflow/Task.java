package com.ghezal.taskflow;

import java.util.Objects;

public class Task {
    private final int id;
    private final String title;
    private boolean completed;

    public Task(int id, String title, boolean completed) {
        if (id <= 0) {
            throw new IllegalArgumentException("Task ID must be positive.");
        }
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title cannot be empty.");
        }
        this.id = id;
        this.title = title.trim();
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        completed = true;
    }

    @Override
    public String toString() {
        return String.format("%d. [%s] %s", id, completed ? "x" : " ", title);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Task task)) return false;
        return id == task.id && completed == task.completed && title.equals(task.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, completed);
    }
}
