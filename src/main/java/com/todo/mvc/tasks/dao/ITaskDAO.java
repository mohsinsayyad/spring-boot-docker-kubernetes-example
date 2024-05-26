package com.todo.mvc.tasks.dao;

import java.util.List;

import com.todo.mvc.tasks.entities.Task;

public interface ITaskDAO {

	List<Task> getAllTasks();

	Task getTaskById(Long id);

	Task addTask(Task task);

	Task updateTask(Long id, Task updatedTask);

	void deleteTask(Long id);
	
	
}
