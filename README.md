# TaskFlow — Java Task Manager

TaskFlow is a small command-line task manager written in Java. I created it to practise object-oriented programming, collections, file handling, input validation, exception handling, and automated testing.

## Features

- Add a task
- List all tasks
- Mark a task as completed
- Delete a task
- Save tasks locally so they remain available after restarting the program
- Automated unit tests with JUnit 5

## Technologies

- Java 17
- Maven
- JUnit 5

## Project structure

- `Task` represents one task.
- `TaskService` contains the application logic.
- `TaskRepository` saves and loads tasks from a local file.
- `Main` provides the command-line menu.
- `TaskServiceTest` verifies the main behaviours.

## Run the application

Install Java 17 and Maven, then run:

```bash
mvn clean test
mvn exec:java
```

## What I learned

This project helped me practise separating data, business logic, persistence, and user interaction into different classes. I also learned how automated tests make it safer to change and improve an application.

## Future improvements

- Add due dates and priorities
- Search and filter tasks
- Replace local file storage with a database
- Build a web interface

## Author

Ghezal Shefa
