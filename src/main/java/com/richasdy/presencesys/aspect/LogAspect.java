package com.richasdy.presencesys.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAspect {

	private final Log log = LogFactory.getLog(getClass());

	// @Before("execution(* com.richasdy.presencesys.api.*.*(..))")
	public void beforeMethodApi(JoinPoint joinPoint) {
		log.info("before " + joinPoint);
	}

	// @After("execution(* com.richasdy.presencesys.api.*.*(..))")
	 public void afterMethodApi(JoinPoint joinPoint) {
	 log.info("after "+joinPoint);
	 }

	// entah kenapa jadi g jalan dengan before after
	// @Around("execution(* com.richasdy.presencesys.api.*.*(..))")
	public void aroundMethodApi() {
		log.info("around method");
	}

	@AfterReturning(pointcut = "execution(* com.richasdy.presencesys.api.*.*(..))", returning = "responseEntity")
	public void afterReturningMethodApi(JoinPoint joinPoint, ResponseEntity responseEntity) {
		log.info(joinPoint + " | return : " + responseEntity);
	}

	@AfterThrowing(pointcut="execution(* com.richasdy.presencesys.api.*.*(..))", throwing="t")
	 public void afterThrowingMethodApi(JoinPoint joinPoint, Throwable t) {
	 log.info(joinPoint+" | throwing : "+ t);
	 }

}
