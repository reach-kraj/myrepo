package com.example.taskmanagementcqrs;

import com.example.taskmanagementcqrs.command.TaskCommand;
import com.example.taskmanagementcqrs.query.TaskReadRepository;
import com.example.taskmanagementcqrs.query.TaskView;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final TaskReadRepository readRepository;

    @Autowired
    public TaskController(TaskService taskService, TaskReadRepository readRepository) {
        this.taskService = taskService;
        this.readRepository = readRepository;
    }

    @PostMapping
    public ResponseEntity<String> createTask(@RequestBody TaskCommand command) {
        String taskId = taskService.createTask(command);
        return ResponseEntity.ok(taskId);
    }

    @GetMapping
    public ResponseEntity<List<TaskView>> getAllTasks() {
        return ResponseEntity.ok(readRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskView> getTaskById(@PathVariable String id) {
        TaskView task = readRepository.findById(id);
        return task != null ? ResponseEntity.ok(task) : ResponseEntity.notFound().build();
    }
}