package com.todo.mvc.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.todo.mvc.tasks.model.ResponseDTO;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class TaskExceptionHandler {
	
	/**
	 * This method handles all the exceptions thrown in application.
	 * 
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	public ResponseEntity<ResponseDTO> handleException(Exception e) {
		
		log.error("Exception in Task application :: {}", e.getMessage());
		
		ResponseDTO response = new ResponseDTO(null, ErrorCode.TASKERR01.name());
		
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		
		
	}

}
