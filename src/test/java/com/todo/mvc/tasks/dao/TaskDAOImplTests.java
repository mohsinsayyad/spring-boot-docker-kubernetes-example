package com.todo.mvc.tasks.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.todo.mvc.tasks.entities.Task;
import com.todo.mvc.tasks.repository.TaskRepository;

@SpringBootTest
public class TaskDAOImplTests {

	@Mock
	private TaskRepository taskRepository;

	@InjectMocks
	private TaskDAOImpl taskService;

	@Test
	public void testGetAllTasks() {

		// Mock data
		List<Task> mockTasks = List.of(new Task(1L, "Task 1", true), new Task(2L, "Task 2", false));

		// Mocking behavior of TaskRepository
		when(taskRepository.findAll()).thenReturn(mockTasks);

		// Calling the service method
		List<Task> tasks = taskService.getAllTasks();

		// Verifying behavior
		verify(taskRepository).findAll();

		// Assertions
		assertNotNull(tasks);
		assertEquals(tasks.size(), 2);
		assertSame(mockTasks, tasks, "All tasks are retrieved successfull from DB");
		assertEquals(tasks.get(1).isCompleted(), false, "Task incomplete");
		assertEquals(tasks.get(1).getId(), 2, "Task id matching");
		assertEquals(tasks.get(1).getTitle(), "Task 2", "Task title matching");
	}

	@Test
	public void testGetTaskById() {

		// Mock data
		Task mockTask = new Task(1L, "Task 1", true);

		// Mocking behavior of TaskRepository
		when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(mockTask));

		// Calling the service method
		Task task = taskService.getTaskById(1L);

		// Verifying behavior
		verify(taskRepository).findById(any(Long.class));

		// Assertions
		assertNotNull(task);
		assertSame(mockTask, task, "Task retrieved for given task id from DB");
		assertEquals(task.getId(), 1L, "Task id is matching");
		assertEquals(task.getTitle(), "Task 1", "Task name is matching");
		assertEquals(task.isCompleted(), true, "Task incomplete");
	}

	@Test
	public void testGetTaskByIdNotFound() {

		// Mocking behavior of TaskRepository
		when(taskRepository.findById(2L)).thenThrow(NoSuchElementException.class);

		assertThrows(NoSuchElementException.class, () -> {
			// Method call that you expect to throw NoSuchElementException
			taskService.getTaskById(2L);
		});
	}

	@Test
	public void testAddTask() {

		// Mock data
		Task mockTask = new Task(1L, "Task 1", true);

		// Mocking behavior of TaskRepository
		when(taskRepository.save(any(Task.class))).thenReturn(mockTask);

		// Calling the service method
		Task savedTask = taskService.addTask(mockTask);

		// Verifying behavior
		verify(taskRepository).save(any(Task.class));

		// Assertions
		assertNotNull(savedTask);
		assertEquals(savedTask.getId(), 1L, "Task id is matching");
		assertEquals(savedTask.getTitle(), "Task 1", "Task name is matching");
		assertEquals(savedTask.isCompleted(), true, "Task completed");
	}

	@Test
	public void testUpdateTask() {

		// Mock data
		Task mockTask = new Task(1L, "Task 1", true);

		// Mocking behavior of TaskRepository
		when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(mockTask));
		Task updateTask = new Task(1L, "Task 1", false);
		when(taskRepository.save(any(Task.class))).thenReturn(updateTask);

		// Calling the service method
		Task updatedTask = taskService.updateTask(1L, updateTask);

		// Verifying behavior
		verify(taskRepository).findById(any(Long.class));
		verify(taskRepository).save(any(Task.class));

		// Assertions
		assertNotNull(updatedTask);
		assertEquals(updatedTask.getId(), 1L, "Task id is matching");
		assertEquals(updatedTask.getTitle(), "Task 1", "Task name is matching");
		assertEquals(updatedTask.isCompleted(), false, "Task incomplete");
	}

	@Test
	public void testUpdateTaskNotFound() {

		// Mocking behavior of TaskRepository
		when(taskRepository.findById(2L)).thenThrow(NoSuchElementException.class);

		assertThrows(NoSuchElementException.class, () -> {
			Task updateTask = new Task(1L, "Task 1", false);
			// Method call that you expect to throw NoSuchElementException
			taskService.updateTask(2L, updateTask);
		});
	}
	
	@Test
	public void testDeleteTask() {

		// Mock data
		Task mockTask = new Task(1L, "Task 1", true);

		// Mocking behavior of TaskRepository
		when(taskRepository.findById(any(Long.class))).thenReturn(Optional.of(mockTask));

		// Calling the service method
		taskService.deleteTask(1L);

		// Verifying behavior
		verify(taskRepository).findById(any(Long.class));
		verify(taskRepository).delete(any(Task.class));
	}
}
