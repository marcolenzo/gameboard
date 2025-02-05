package com.marcolenzo.gameboard.migrations;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcolenzo.gameboard.model.NotificationJob;
import com.marcolenzo.gameboard.repositories.NotificationJobRepository;

/**
 * Migration component to create a welcome email notification job.
 * This migration ensures that a welcome email notification job is created
 * if it does not already exist in the database.
 */
@Component
public class WelcomeEmailMigration {

	@Autowired
	private NotificationJobRepository repository;

	/**
	 * Executes the welcome email migration.
	 * This method is called after the bean is constructed and checks if the
	 * welcome email notification job exists. If it does not exist, it creates
	 * and saves a new welcome email notification job.
	 */
	@PostConstruct
	public void execute() {
		NotificationJob welcomeJob = repository.findOne("welcome");
		if (welcomeJob == null) {
			welcomeJob = new NotificationJob();
			welcomeJob.setId("welcome");
			welcomeJob.setDone(false);
			welcomeJob.setSubject("Gameboard Newsletter");
			welcomeJob.setTemplate("mail-welcome");
			repository.save(welcomeJob);
		}
	}

}
