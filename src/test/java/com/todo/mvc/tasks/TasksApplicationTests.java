package com.todo.mvc.tasks;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TasksApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Test
    public void mainMethodRunsSuccessfully() {
        // Test that the main method runs without throwing exceptions
        TasksApplication.main(new String[] {});
    }

}
