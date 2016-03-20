package com.marcolenzo.gameboard.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;

/**
 * Services for the management of emails.
 * @author Marco Lenzo
 *
 */
@Component
public class EmailServices {

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

	public void sendWelcomeEmail(String to, String from) {
		final Context ctx = new Context();
		ctx.setVariable("name", "Marco");
		final String htmlContent = this.templateEngine.process("signup-success", ctx);
		sendMail(to, from, "Welcome!", htmlContent);
	}

}
