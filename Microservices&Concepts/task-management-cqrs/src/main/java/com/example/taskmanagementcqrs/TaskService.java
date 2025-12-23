package com.example.taskmanagementcqrs;

import com.example.taskmanagementcqrs.command.TaskCommand;
import com.example.taskmanagementcqrs.command.TaskWriteRepository;
import com.example.taskmanagementcqrs.query.TaskReadRepository;
import com.example.taskmanagementcqrs.query.TaskView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {
    private final TaskWriteRepository writeRepository;
    private final TaskReadRepository readRepository;

    @Autowired
    public TaskService(TaskWriteRepository writeRepository, TaskReadRepository readRepository) {
        this.writeRepository = writeRepository;
        this.readRepository = readRepository;
    }

    public String createTask(TaskCommand command) {
        String id = writeRepository.save(command);
        TaskView view = new TaskView();
        view.setId(id); // Use the same ID
        view.setTitle(command.getTitle());
        view.setDescription(command.getDescription());
        readRepository.save(view); // Update read model
        return id;
    }
}