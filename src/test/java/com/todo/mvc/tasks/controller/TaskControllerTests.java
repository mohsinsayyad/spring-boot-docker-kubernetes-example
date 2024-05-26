package com.todo.mvc.tasks.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.mvc.tasks.dao.ITaskDAO;
import com.todo.mvc.tasks.entities.Task;

@SpringBootTest
@AutoConfigureMockMvc
public class TaskControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ITaskDAO taskDao;

	@Test
	public void testGetAllTasks() throws Exception {
		// Mock data
		Task task1 = new Task(1L, "Task 1", true);
		Task task2 = new Task(2L, "Task 2", false);
		List<Task> tasks = Arrays.asList(task1, task2);

		// Mock behavior
		when(taskDao.getAllTasks()).thenReturn(tasks);

		// Perform GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].title").value("Task 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[0].completed").value(true))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[1].id").value(2))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[1].title").value("Task 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data[1].completed").value(false));
	}

	@Test
	public void testGetAllTasksFailure() throws Exception {
		// Mock data
		List<Task> tasks = new ArrayList<>();

		// Mock behavior
		when(taskDao.getAllTasks()).thenReturn(tasks);

		// Perform GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(0));
	}

	@Test
	public void testAddTask() throws Exception {
		// Mock input data
		Task savedTask = new Task(1L, "Task 1", true);

		// Mock behavior
		when(taskDao.addTask(any(Task.class))).thenReturn(savedTask);

		// Perform POST request
		mockMvc.perform(MockMvcRequestBuilders.post("/api/tasks").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1, \"name\": \"Task 1\", \"completed\": true}")).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Task 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.completed").value(true));
	}

	@Test
	public void testGetTaskById() throws Exception {
		// Mock data
		Task task = new Task(1L, "Task 1", true);

		// Mock behavior
		when(taskDao.getTaskById(1L)).thenReturn(task);

		// Perform GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Task 1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.completed").value(true));
	}

	@Test
	public void testGetTaskByIdNotFoundCase() throws Exception {

		// Mock behavior
		when(taskDao.getTaskById(1L)).thenThrow(NoSuchElementException.class);

		// Perform GET request
		mockMvc.perform(MockMvcRequestBuilders.get("/api/tasks/1"))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("TASKERR01"));
	}

	@Test
	public void testUpdateTask() throws Exception {
		// Mock input data
		Task updatedTask = new Task(1L, "Task 2", true);

		// Mock behavior
		when(taskDao.updateTask(anyLong(), any(Task.class))).thenReturn(updatedTask);

		// Perform PUT request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/1").contentType(MediaType.APPLICATION_JSON)
				.content("{\"id\": 1, \"name\": \"Task 1\", \"completed\": true}")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.id").value(1))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.title").value("Task 2"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.completed").value(true));
	}
	
	@Test
	public void testUpdateTaskNotFoundCase() throws Exception {
		// Mock input data
		Task task = new Task(2L, "Task 2", true);

		// Mock behavior
		when(taskDao.updateTask(100L, task)).thenThrow(NoSuchElementException.class);

		// Perform PUT request
		mockMvc.perform(MockMvcRequestBuilders.put("/api/tasks/{id}", 100L).contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(task)))
				.andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.jsonPath("$.errorCode").value("TASKERR01"));
	}
	
	@Test
	public void testDeleteTask() throws Exception {
		// Prepare a task ID for deletion
		Long taskId = 1L;

		// Perform DELETE request to delete the task
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/tasks/{id}", taskId))
				.andExpect(MockMvcResultMatchers.status().isNoContent());

		// Verify that taskDao.deleteTask() is called with the correct task ID
		verify(taskDao).deleteTask(taskId);
	}
	
}
