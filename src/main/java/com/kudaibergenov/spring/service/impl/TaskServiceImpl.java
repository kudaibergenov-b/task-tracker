package com.kudaibergenov.spring.service.impl;

import com.kudaibergenov.spring.model.Task;
import com.kudaibergenov.spring.repository.TaskRepository;
import com.kudaibergenov.spring.service.EmailService;
import com.kudaibergenov.spring.service.TaskService;
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

    private final Environment environment;

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task create(Task task) {
        Task saved = taskRepository.save(task);

        emailService.sendTaskCreatedNotification(
                environment.getProperty("spring.mail.username"),
                "Новая задача создана",
                "Заголовок: " + task.getTitle() + "\nОписание: " + task.getDescription()
        );

        return saved;
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public Task update(Long id, Task task) {
        Task existing = taskRepository.findById(id).orElseThrow();

        existing.setTitle(task.getTitle());
        existing.setDescription(task.getDescription());
        existing.setCompleted(task.isCompleted());

        return taskRepository.save(existing);
    }

    @Override
    public Task getById(Long id) {
        return taskRepository.findById(id).orElseThrow();
    }

    @Override
    @Cacheable("tasks")
    public List<Task> getAll() {
        return taskRepository.findAll();
    }

    @CacheEvict(value = "tasks", allEntries = true)
    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}