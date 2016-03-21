package com.marcolenzo.gameboard.services;

import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

import com.marcolenzo.gameboard.model.NotificationJob;
import com.marcolenzo.gameboard.model.User;
import com.marcolenzo.gameboard.repositories.NotificationJobRepository;
import com.marcolenzo.gameboard.repositories.UserRepository;

/**
 * Services for the management of emails.
 * @author Marco Lenzo
 *
 */
@Component
public class EmailServices {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServices.class);

	@Value("${mail.smtp.host}")
	private String smtpHost;

	@Value("${mail.smtp.port}")
	private String smtpPort;
	
	@Value("${mail.smtp.username}")
	private String username;
	
	@Value("${mail.smtp.password}")
	private String password;

	@Autowired
	private SpringTemplateEngine templateEngine;

	@Autowired
	private NotificationJobRepository notificationJobRepository;

	@Autowired
	private UserRepository userRepository;


	public void sendMail(String to, String from, String subject, String text) {

		Properties props = System.getProperties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject(subject);
			message.setText(text, "utf-8", "html");
			Transport.send(message);
		}
		catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	@Scheduled(cron = "0 0 0/1 * * ?")
	public void sendNotifications() {
		LOGGER.info("Email Scheduled Job --- START ...");
		List<NotificationJob> jobs = notificationJobRepository.findByIsDoneIsFalse();
		for (NotificationJob job : jobs) {
			LOGGER.info("Email Scheduled Job --- Executing job {}", job.getId());
			List<User> users = userRepository.findAll();
			for (User user : users) {
				final Context ctx = new Context();
				final String htmlContent = this.templateEngine.process(job.getTemplate(), ctx);
				sendMail(user.getEmail(), "gameboard.info@gmail.com", job.getSubject(), htmlContent);
			}
			job.setDone(true);
			notificationJobRepository.save(job);
			LOGGER.info("Email Scheduled Job --- Job {} done.", job.getId());
		}
	}

	// public void sendWelcomeEmail(String to, String from) {
	// final Context ctx = new Context();
	// ctx.setVariable("name", "Marco");
	// final String htmlContent = this.templateEngine.process("signup", ctx);
	// sendMail(to, from, "Welcome!", htmlContent);
	// }

}
