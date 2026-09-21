package com.ghezal.taskflow;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    @TempDir
    Path tempDirectory;

    @Test
    void addsAndCompletesATask() throws IOException {
        TaskService service = createService();

        Task task = service.addTask("Prepare for coding interview");
        assertEquals(1, task.getId());
        assertFalse(task.isCompleted());

        assertTrue(service.completeTask(task.getId()));
        assertTrue(service.getAllTasks().get(0).isCompleted());
    }

    @Test
    void savesTasksBetweenSessions() throws IOException {
        Path file = tempDirectory.resolve("tasks.db");
        TaskService firstSession = new TaskService(new TaskRepository(file));
        firstSession.addTask("Learn Java collections");

        TaskService secondSession = new TaskService(new TaskRepository(file));
        assertEquals(1, secondSession.getAllTasks().size());
        assertEquals("Learn Java collections", secondSession.getAllTasks().get(0).getTitle());
    }

    @Test
    void rejectsAnEmptyTitle() throws IOException {
        TaskService service = createService();
        assertThrows(IllegalArgumentException.class, () -> service.addTask("   "));
    }

    private TaskService createService() throws IOException {
        return new TaskService(new TaskRepository(tempDirectory.resolve("tasks.db")));
    }
}
