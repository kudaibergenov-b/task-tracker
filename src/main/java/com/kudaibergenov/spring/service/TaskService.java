package com.kudaibergenov.spring.service;

import com.kudaibergenov.spring.dto.TaskRequest;
import com.kudaibergenov.spring.dto.TaskResponse;

import java.util.List;

public interface TaskService {

    TaskResponse create(TaskRequest request);

    TaskResponse update(Long id, TaskRequest request);

    TaskResponse getById(Long id);

    List<TaskResponse> getAll();

    void delete(Long id);
}