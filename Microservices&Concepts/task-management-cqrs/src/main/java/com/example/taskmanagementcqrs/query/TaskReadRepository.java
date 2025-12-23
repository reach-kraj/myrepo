package com.example.taskmanagementcqrs.query;

import com.example.taskmanagementcqrs.command.TaskCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TaskReadRepository {
    private final List<TaskView> tasks = new ArrayList<>();

    public void save(TaskView view) {
        tasks.add(view); // Save the TaskView directly
    }

    public List<TaskView> findAll() {
        return new ArrayList<>(tasks);
    }

    public TaskView findById(String id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}