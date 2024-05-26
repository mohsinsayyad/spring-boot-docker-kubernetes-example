package com.todo.mvc.tasks.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

	/**
	 * Method to log method entry. 
	 * 
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before("execution(* com.todo.mvc.tasks.controller.*.*(..))")
	public void loggerEntering(JoinPoint joinPoint) throws Exception {
		
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		Object[] args = joinPoint.getArgs();
		
		log.info("Entering {} and the method {} with input args {}", className, methodName, args);
	}
	
	/**
	 * Method to log method exit
	 * 
	 * @param joinPoint
	 * @throws Exception
	 */
	@Before("execution(* com.todo.mvc.tasks.controller.*.*(..))")
	public void loggerExiting(JoinPoint joinPoint) throws Exception {
		
		String className = joinPoint.getTarget().getClass().getSimpleName();
		String methodName = joinPoint.getSignature().getName();
		
		log.info("Exiting {} and the method {}", className, methodName);
	}
}
