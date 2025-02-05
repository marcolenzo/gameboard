package com.marcolenzo.gameboard.aspects;

import java.time.LocalDateTime;
import java.util.List;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.marcolenzo.gameboard.model.Action;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.repositories.ActionRepository;

/**
 * Aspect for logging actions annotated with @ActionLoggable.
 * This aspect intercepts method calls annotated with @ActionLoggable,
 * logs the method execution details, and saves the action to the database.
 */
@Aspect
@Component
public class ActionLogAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(ActionLogAspect.class);

	@Autowired
	private ActionRepository repository;

	@Autowired
	private ObjectMapper mapper;

	/**
	 * Around advice that logs the execution of methods annotated with @ActionLoggable.
	 *
	 * @param point the join point representing the method execution
	 * @return the result of the method execution
	 * @throws Throwable if an error occurs during method execution
	 */
	@Around("execution(@com.marcolenzo.gameboard.annotations.ActionLoggable * *(..))")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long start = System.currentTimeMillis();
		Object result = point.proceed();

		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Action action = new Action();
		action.setName(MethodSignature.class.cast(point.getSignature()).getMethod().getName());

		// Parse args
		List<String> jsonStrings = Lists.newArrayList();
		for (Object obj : point.getArgs()) {

			jsonStrings.add(mapper.writeValueAsString(obj));
		}
		action.setArgs(jsonStrings);
		action.setResult(mapper.writeValueAsString(result));

		action.setUserId(currentUser.getId());
		action.setDatetime(LocalDateTime.now());

		repository.save(action);

		LOGGER.info("#{}({}): in {} ms by {}", MethodSignature.class.cast(point.getSignature()).getMethod().getName(),
				point.getArgs(), System.currentTimeMillis() - start,
				currentUser.getId() + " " + currentUser.getEmail());

		return result;
	}

}
