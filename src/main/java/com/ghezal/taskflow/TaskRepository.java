package com.ghezal.taskflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TaskRepository {
    private final Path storageFile;

    public TaskRepository(Path storageFile) {
        this.storageFile = storageFile;
    }

    public List<Task> load() throws IOException {
        if (!Files.exists(storageFile)) {
            return new ArrayList<>();
        }

        List<Task> tasks = new ArrayList<>();
        for (String line : Files.readAllLines(storageFile, StandardCharsets.UTF_8)) {
            if (line.isBlank()) continue;
            String[] parts = line.split("\\t", 3);
            if (parts.length != 3) continue;

            try {
                int id = Integer.parseInt(parts[0]);
                boolean completed = Boolean.parseBoolean(parts[1]);
                String title = new String(Base64.getDecoder().decode(parts[2]), StandardCharsets.UTF_8);
                tasks.add(new Task(id, title, completed));
            } catch (IllegalArgumentException ignored) {
                // Skip a damaged record instead of stopping the whole application.
            }
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        List<String> lines = tasks.stream()
                .map(task -> task.getId() + "\t" + task.isCompleted() + "\t"
                        + Base64.getEncoder().encodeToString(task.getTitle().getBytes(StandardCharsets.UTF_8)))
                .toList();
        Files.write(storageFile, lines, StandardCharsets.UTF_8);
    }
}
