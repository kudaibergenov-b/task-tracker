package com.kudaibergenov.spring.controller;

import com.kudaibergenov.spring.dto.TaskRequest;
import com.kudaibergenov.spring.dto.TaskResponse;
import com.kudaibergenov.spring.service.ExternalApiService;
import com.kudaibergenov.spring.service.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Task API", description = "Операции с задачами")
@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    private final ExternalApiService externalApiService;

    @PostMapping
    public TaskResponse create(@RequestBody @Valid TaskRequest request) {
        return taskService.create(request);
    }

    @GetMapping
    public List<TaskResponse> getAll() {
        return taskService.getAll();
    }

    @GetMapping("/{id}")
    public TaskResponse getById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PutMapping("/{id}")
    public TaskResponse update(@PathVariable Long id, @RequestBody @Valid TaskRequest request) {
        return taskService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        taskService.delete(id);
    }

    @GetMapping("/external")
    public ResponseEntity<String> callExternalApi() {
        return externalApiService.fetchAndLogObjects();
    }
}