package com.todo.mvc.tasks.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todo.mvc.tasks.dao.ITaskDAO;
import com.todo.mvc.tasks.entities.Task;
import com.todo.mvc.tasks.model.ResponseDTO;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

	@Autowired
	private ITaskDAO taskDao;

	/**
	 * This method returns all the tasks.
	 * 
	 * @return
	 */
	@GetMapping
	public ResponseEntity<ResponseDTO> getAllTasks() {

		List<Task> taskList = taskDao.getAllTasks();

		return new ResponseEntity<>(new ResponseDTO(taskList, null), HttpStatus.OK);
	}

	/**
	 * This method return task details for given task id.
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public ResponseEntity<ResponseDTO> getTaskById(@PathVariable Long id) {
		Task task = taskDao.getTaskById(id);
		return new ResponseEntity<>(new ResponseDTO(task, null), HttpStatus.OK);
	}

	/**
	 * This method creates new task in DB.
	 * 
	 * @param task
	 * @return
	 */
	@PostMapping
	public ResponseEntity<ResponseDTO> addTask(@RequestBody Task task) {
		Task savedTask = taskDao.addTask(task);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDTO(savedTask, null));
	}

	/**
	 * This method updates existing task in DB.
	 * 
	 * @param id
	 * @param updatedTask
	 * @return
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> updateTask(@PathVariable Long id, @RequestBody Task updatedTask) {
		Task savedTask = taskDao.updateTask(id, updatedTask);
		return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO(savedTask, null));
	}

	/**
	 * This method deletes task for given task id from DB.
	 * 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

		taskDao.deleteTask(id);

		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
}
