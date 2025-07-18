package com.kudaibergenov.spring.service.impl;

import com.kudaibergenov.spring.dto.TaskResponse;
import com.kudaibergenov.spring.dto.TaskRequest;
import com.kudaibergenov.spring.mapper.TaskMapper;
import com.kudaibergenov.spring.model.Task;
import com.kudaibergenov.spring.repository.TaskRepository;
import com.kudaibergenov.spring.service.EmailService;
import com.kudaibergenov.spring.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final EmailService emailService;

    private final TaskMapper taskMapper;

    private final Environment environment;

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskResponse create(TaskRequest request) {
        Task task = taskMapper.toEntity(request);
        Task saved = taskRepository.save(task);

        emailService.sendTaskCreatedNotification(
                environment.getProperty("spring.mail.username"),
                "Новая задача создана",
                "Заголовок: " + task.getTitle() + "\nОписание: " + task.getDescription()
        );

        return taskMapper.toResponse(saved);
    }

    @Override
    public TaskResponse getById(Long id) {
        return taskMapper.toResponse(taskRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Задача с id=" + id + " не найдена")
        ));
    }

    @Cacheable("tasks")
    @Override
    public List<TaskResponse> getAll() {
        return taskMapper.toResponseList(taskRepository.findAll());
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public TaskResponse update(Long id, TaskRequest request) {
        Task task = taskRepository.findById(id).orElseThrow();

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setCompleted(request.isCompleted());

        return taskMapper.toResponse(taskRepository.save(task));
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}