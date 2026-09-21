package com.ghezal.taskflow;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            TaskService service = new TaskService(new TaskRepository(Path.of("tasks.db")));
            boolean running = true;

            System.out.println("Welcome to TaskFlow!");
            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> addTask(scanner, service);
                        case "2" -> listTasks(service);
                        case "3" -> completeTask(scanner, service);
                        case "4" -> deleteTask(scanner, service);
                        case "5" -> running = false;
                        default -> System.out.println("Please choose a number from 1 to 5.");
                    }
                } catch (IllegalArgumentException exception) {
                    System.out.println("Invalid input: " + exception.getMessage());
                } catch (IOException exception) {
                    System.out.println("Could not save the tasks: " + exception.getMessage());
                }
            }
            System.out.println("Goodbye!");
        } catch (IOException exception) {
            System.out.println("Could not start TaskFlow: " + exception.getMessage());
        }
    }

    private static void printMenu() {
        System.out.println("\n1. Add task");
        System.out.println("2. List tasks");
        System.out.println("3. Complete task");
        System.out.println("4. Delete task");
        System.out.println("5. Exit");
        System.out.print("Choose an option: ");
    }

    private static void addTask(Scanner scanner, TaskService service) throws IOException {
        System.out.print("Task title: ");
        Task task = service.addTask(scanner.nextLine());
        System.out.println("Added: " + task);
    }

    private static void listTasks(TaskService service) {
        if (service.getAllTasks().isEmpty()) {
            System.out.println("No tasks yet.");
            return;
        }
        service.getAllTasks().forEach(System.out::println);
    }

    private static void completeTask(Scanner scanner, TaskService service) throws IOException {
        System.out.print("Task ID to complete: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(service.completeTask(id) ? "Task completed." : "Task not found.");
    }

    private static void deleteTask(Scanner scanner, TaskService service) throws IOException {
        System.out.print("Task ID to delete: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.println(service.deleteTask(id) ? "Task deleted." : "Task not found.");
    }
}
