package com.kudaibergenov.spring.mapper;

import com.kudaibergenov.spring.dto.TaskRequest;
import com.kudaibergenov.spring.dto.TaskResponse;
import com.kudaibergenov.spring.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    Task toEntity(TaskRequest request);

    TaskResponse toResponse(Task task);

    List<TaskResponse> toResponseList(List<Task> tasks);
}