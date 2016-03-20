package com.marcolenzo.gameboard.migrations;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.marcolenzo.gameboard.model.NotificationJob;
import com.marcolenzo.gameboard.repositories.NotificationJobRepository;

@Component
public class WelcomeEmailMigration {

	@Autowired
	private NotificationJobRepository repository;

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
