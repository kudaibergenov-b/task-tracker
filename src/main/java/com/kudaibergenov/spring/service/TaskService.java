package com.kudaibergenov.spring.service;

import com.kudaibergenov.spring.model.Task;

import java.util.List;

public interface TaskService {

    Task create(Task task);

    Task update(Long id, Task task);

    Task getById(Long id);

    List<Task> getAll();

    void delete(Long id);
}