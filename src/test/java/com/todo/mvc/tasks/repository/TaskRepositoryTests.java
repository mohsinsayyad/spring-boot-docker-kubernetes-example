package com.todo.mvc.tasks.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.todo.mvc.tasks.entities.Task;

@DataJpaTest
public class TaskRepositoryTests {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testCustomMethod() {
        Task task1 = new Task(1L, "Task 1", true);
        Task task2 = new Task(2L, "Task 2", false);
        Task task3 = new Task(3L, "Task 3", true);
        taskRepository.save(task1);
        taskRepository.save(task2);
        taskRepository.save(task3);

        List<Task> tasks = taskRepository.findAll();

        assertEquals(3, tasks.size());
        assertEquals(tasks.get(0).getId(), 1);
        assertEquals(tasks.get(0).getTitle(), "Task 1");
        assertEquals(tasks.get(0).isCompleted(), true);

        assertEquals(tasks.get(1).getId(), 2);
        assertEquals(tasks.get(1).getTitle(), "Task 2");
        assertEquals(tasks.get(1).isCompleted(), false);
    }
}

