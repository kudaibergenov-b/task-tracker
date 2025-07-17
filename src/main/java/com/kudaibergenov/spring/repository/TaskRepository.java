package com.kudaibergenov.spring.repository;

import com.kudaibergenov.spring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}