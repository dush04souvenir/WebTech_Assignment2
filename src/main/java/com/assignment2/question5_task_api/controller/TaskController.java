package com.assignment2.question5_task_api.controller;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import com.assignment2.question5_task_api.model.Task;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private List<Task> tasks = new ArrayList<>();

    // Constructor to initialize some sample tasks
    public TaskController() {
        tasks.add(new Task(1L, "Study Java", "Learn Spring Boot basics",
                false, "HIGH", "2026-02-15"));
        tasks.add(new Task(2L, "Do Assignment", "Finish REST API assignment",
                false, "MEDIUM", "2026-02-20"));
        tasks.add(new Task(3L, "Buy groceries", "Milk, bread, eggs",
                true, "LOW", "2026-02-10"));
    }

    // GET all tasks
    @GetMapping
    public List<Task> getAllTasks() {
        return tasks;
    }

    // GET task by ID
    @GetMapping("/{taskId}")
    public Task getTaskById(@PathVariable Long taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    // GET tasks by completion status
    @GetMapping("/status")
    public List<Task> getTasksByStatus(@RequestParam boolean completed) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.isCompleted() == completed) {
                result.add(task);
            }
        }
        return result;
    }

    // 4️⃣ GET tasks by priority
    @GetMapping("/priority/{priority}")
    public List<Task> getTasksByPriority(@PathVariable String priority) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getPriority().equalsIgnoreCase(priority)) {
                result.add(task);
            }
        }
        return result;
    }

    // POST – create new task
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        tasks.add(task);
        return task;
    }

    // PUT – update task
    @PutMapping("/{taskId}")
    public Task updateTask(@PathVariable Long taskId,
            @RequestBody Task updatedTask) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                task.setTitle(updatedTask.getTitle());
                task.setDescription(updatedTask.getDescription());
                task.setPriority(updatedTask.getPriority());
                task.setDueDate(updatedTask.getDueDate());
                task.setCompleted(updatedTask.isCompleted());
                return task;
            }
        }
        return null;
    }

    // PATCH – mark task as completed
    @PatchMapping("/{taskId}/complete")
    public Task markTaskCompleted(@PathVariable Long taskId) {
        for (Task task : tasks) {
            if (task.getTaskId().equals(taskId)) {
                task.setCompleted(true);
                return task;
            }
        }
        return null;
    }

    // DELETE – remove task
    @DeleteMapping("/{taskId}")
    public String deleteTask(@PathVariable Long taskId) {
        tasks.removeIf(task -> task.getTaskId().equals(taskId));
        return "Task deleted successfully";
    }
}
