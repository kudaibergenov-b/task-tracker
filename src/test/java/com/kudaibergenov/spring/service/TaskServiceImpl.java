package com.kudaibergenov.spring.service;

import com.kudaibergenov.spring.dto.TaskRequest;
import com.kudaibergenov.spring.dto.TaskResponse;
import com.kudaibergenov.spring.mapper.TaskMapper;
import com.kudaibergenov.spring.model.Task;
import com.kudaibergenov.spring.repository.TaskRepository;
import com.kudaibergenov.spring.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;

    private EmailService emailService;

    private TaskMapper taskMapper;

    private Environment environment;

    private TaskServiceImpl taskService;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        emailService = mock(EmailService.class);
        environment = mock(Environment.class);
        taskMapper = mock(TaskMapper.class);
        taskService = new TaskServiceImpl(taskRepository, emailService, taskMapper, environment);
    }

    @Test
    void create_ShouldSaveTaskAndSendEmail() {
        TaskRequest request = new TaskRequest();
        request.setTitle("Test");
        request.setDescription("Test desc");
        request.setCompleted(false);

        Task taskToSave = Task.builder()
                .title("Test")
                .description("Test desc")
                .completed(false)
                .build();

        Task savedTask = Task.builder()
                .id(1L)
                .title("Test")
                .description("Test desc")
                .completed(false)
                .build();

        TaskResponse expectedResponse = TaskResponse.builder()
                .id(1L)
                .title("Test")
                .description("Test desc")
                .completed(false)
                .build();

        when(taskMapper.toEntity(request)).thenReturn(taskToSave);
        when(taskRepository.save(taskToSave)).thenReturn(savedTask);
        when(taskMapper.toResponse(savedTask)).thenReturn(expectedResponse);
        when(environment.getProperty("spring.mail.username")).thenReturn("test@example.com");

        TaskResponse result = taskService.create(request);

        assertEquals(expectedResponse, result);
        verify(emailService).sendTaskCreatedNotification(
                eq("test@example.com"),
                contains("Новая задача"),
                contains("Заголовок")
        );
    }

    @Test
    void update_ShouldUpdateExistingTask() {
        Task existing = Task.builder()
                .id(1L)
                .title("Old")
                .description("Old desc")
                .completed(false)
                .build();

        TaskRequest request = new TaskRequest();
        request.setTitle("New");
        request.setDescription("Updated desc");
        request.setCompleted(true);

        Task updated = Task.builder()
                .id(1L)
                .title("New")
                .description("Updated desc")
                .completed(true)
                .build();

        TaskResponse expected = TaskResponse.builder()
                .id(1L)
                .title("New")
                .description("Updated desc")
                .completed(true)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(existing)).thenReturn(updated);
        when(taskMapper.toResponse(updated)).thenReturn(expected);

        TaskResponse result = taskService.update(1L, request);

        assertEquals(expected, result);
    }

    @Test
    void getById_ShouldReturnTaskResponse() {
        Task task = Task.builder().id(1L).title("T").description("Test").completed(false).build();
        TaskResponse expected = TaskResponse.builder().id(1L).title("T").description("Test").completed(false).build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toResponse(task)).thenReturn(expected);

        TaskResponse result = taskService.getById(1L);

        assertEquals(expected, result);
    }

    @Test
    void getAll_ShouldReturnListOfTaskResponses() {
        List<Task> tasks = List.of(
                Task.builder().id(1L).title("T1").build(),
                Task.builder().id(2L).title("T2").build()
        );

        List<TaskResponse> responses = List.of(
                TaskResponse.builder().id(1L).title("T1").build(),
                TaskResponse.builder().id(2L).title("T2").build()
        );

        when(taskRepository.findAll()).thenReturn(tasks);
        when(taskMapper.toResponseList(tasks)).thenReturn(responses);

        List<TaskResponse> result = taskService.getAll();

        assertEquals(responses, result);
    }

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        taskService.delete(42L);
        verify(taskRepository).deleteById(42L);
    }
}