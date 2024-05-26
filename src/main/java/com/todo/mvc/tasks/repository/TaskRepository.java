package com.todo.mvc.tasks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.todo.mvc.tasks.entities.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
