package com.todo.mvc.tasks.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.todo.mvc.tasks.entities.Task;
import com.todo.mvc.tasks.repository.TaskRepository;

@Service
public class TaskDAOImpl implements ITaskDAO {

	@Autowired
	private TaskRepository taskRepository;

	/**
	 * This method retrieves all the tasks from DB.
	 */
	@Override
	public List<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	/**
	 * This method retrieves specific task for given task id
	 */
	@Override
	public Task getTaskById(@PathVariable Long id) {
		return taskRepository.findById(id).orElseThrow();
	}

	/**
	 * This method creates new tasks in DB.
	 */
	@Override
	public Task addTask(@RequestBody Task task) {
		return taskRepository.save(task);
	}

	/**
	 * This method updates task for give task id.
	 */
	@Override
	public Task updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
		Task existingTask = taskRepository.findById(id).orElseThrow(); // if task id not present in DB then NoSuchElementException is thrown.
		existingTask.setTitle(updatedTask.getTitle());
		existingTask.setCompleted(updatedTask.isCompleted());
		return taskRepository.save(existingTask);
	}

	/**
	 * This method deletes task from DB.
	 */
	@Override
	public void deleteTask(@PathVariable Long id) {

		Task existingTask = taskRepository.findById(id).orElseThrow(); // if task id not present in DB then NoSuchElementException is thrown.
		taskRepository.delete(existingTask);
	}

}
