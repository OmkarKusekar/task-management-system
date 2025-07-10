package com.omkar.task_service.controller;

import com.omkar.task_service.model.Task;
import com.omkar.task_service.service.TaskService;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody Task task){
        Task savedTask=taskService.createTask(task);

        return ResponseEntity.ok(savedTask);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable Long id){
        Task task=taskService.getTask(id);
        return task !=null ? ResponseEntity.ok(task):ResponseEntity
                .notFound()
                .build();
    }



}
