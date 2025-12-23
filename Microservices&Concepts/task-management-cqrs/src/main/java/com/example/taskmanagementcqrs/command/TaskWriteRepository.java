package com.example.taskmanagementcqrs.command;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class TaskWriteRepository {
    private final List<TaskCommand> tasks = new ArrayList<>();

    public String save(TaskCommand command) {
        String id = UUID.randomUUID().toString();
        tasks.add(command);
        return id;
    }
}