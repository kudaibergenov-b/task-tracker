package com.kudaibergenov.spring.service;

import com.kudaibergenov.spring.model.Task;
import com.kudaibergenov.spring.repository.TaskRepository;
import com.kudaibergenov.spring.service.impl.TaskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceImplTest {

    private TaskRepository taskRepository;

    private EmailService emailService;

    private TaskServiceImpl taskService;

    private Environment environment;

    @BeforeEach
    void setUp() {
        taskRepository = mock(TaskRepository.class);
        emailService = mock(EmailService.class);
        environment = mock(Environment.class);
        taskService = new TaskServiceImpl(taskRepository, emailService, environment);
    }

    @Test
    void create_ShouldSaveTaskAndSendEmail() {
        Task task = Task.builder().title("Test").description("Test desc").build();
        Task savedTask = Task.builder().id(1L).title("Test").description("Test desc").build();

        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        Task result = taskService.create(task);

        assertEquals(1L, result.getId());
        verify(emailService).sendTaskCreatedNotification(eq(environment.getProperty("spring.mail.username")),
                contains("Новая"), contains("Заголовок"));
    }

    @Test
    void update_ShouldUpdateExistingTask() {
        Task existing = Task.builder().id(1L).title("Old").description("Old").completed(false).build();
        Task newTask = Task.builder().title("New").description("New").completed(true).build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(taskRepository.save(any(Task.class))).thenReturn(existing);

        Task updated = taskService.update(1L, newTask);

        assertEquals("New", updated.getTitle());
        assertTrue(updated.isCompleted());
    }

    @Test
    void getById_ShouldReturnTask() {
        Task task = Task.builder().id(1L).title("T").build();
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task found = taskService.getById(1L);
        assertEquals("T", found.getTitle());
    }

    @Test
    void getAll_ShouldReturnListOfTasks() {
        when(taskRepository.findAll()).thenReturn(Arrays.asList(
                Task.builder().title("T1").build(),
                Task.builder().title("T2").build()
        ));

        List<Task> all = taskService.getAll();
        assertEquals(2, all.size());
    }

    @Test
    void delete_ShouldInvokeRepositoryDelete() {
        taskService.delete(1L);
        verify(taskRepository).deleteById(1L);
    }
}