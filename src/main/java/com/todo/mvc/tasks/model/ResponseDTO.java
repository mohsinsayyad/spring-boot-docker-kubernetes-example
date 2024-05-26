package com.todo.mvc.tasks.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDTO {

	private Object data;
	private String errorCode;
}
