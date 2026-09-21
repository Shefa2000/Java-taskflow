package com.ghezal.taskflow;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class TaskService {
    private final TaskRepository repository;
    private final List<Task> tasks;

    public TaskService(TaskRepository repository) throws IOException {
        this.repository = repository;
        this.tasks = new ArrayList<>(repository.load());
    }

    public Task addTask(String title) throws IOException {
        int nextId = tasks.stream().mapToInt(Task::getId).max().orElse(0) + 1;
        Task task = new Task(nextId, title, false);
        tasks.add(task);
        persist();
        return task;
    }

    public boolean completeTask(int id) throws IOException {
        Optional<Task> match = findById(id);
        if (match.isEmpty()) return false;
        match.get().markCompleted();
        persist();
        return true;
    }

    public boolean deleteTask(int id) throws IOException {
        boolean removed = tasks.removeIf(task -> task.getId() == id);
        if (removed) persist();
        return removed;
    }

    public List<Task> getAllTasks() {
        return Collections.unmodifiableList(tasks);
    }

    private Optional<Task> findById(int id) {
        return tasks.stream().filter(task -> task.getId() == id).findFirst();
    }

    private void persist() throws IOException {
        repository.save(tasks);
    }
}
