package com.todo.mvc.tasks.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.todo.mvc.tasks.model.ResponseDTO;

public class TaskExceptionHandlerTests {

    private final TaskExceptionHandler exceptionHandler = new TaskExceptionHandler();

    @Test
    public void testHandleException() {
        // Simulating an exception
        Exception exception = new Exception("Test exception message");

        // Invoking the handleException method
        ResponseEntity<ResponseDTO> responseEntity = exceptionHandler.handleException(exception);

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(ErrorCode.TASKERR01.name(), responseEntity.getBody().getErrorCode());
    }

    @Test
    public void testHandleRuntimeException() {
        // Simulating a runtime exception
        RuntimeException runtimeException = new RuntimeException("Test runtime exception message");

        // Invoking the handleException method
        ResponseEntity<ResponseDTO> responseEntity = exceptionHandler.handleException(runtimeException);

        // Verifying the response
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals(ErrorCode.TASKERR01.name(), responseEntity.getBody().getErrorCode());
    }
}
