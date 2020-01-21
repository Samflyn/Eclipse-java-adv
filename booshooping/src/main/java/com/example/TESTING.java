package com.example;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class TESTING {
	public static void main(String[] args) {
//		List<Items> i = new ArrayList<Items>();
//		Items ii = new Items();
//		ii.setId(1);
//		i.add(ii);
//		ii = new Items();
//		ii.setId(2);
//		i.add(ii);
//		ii = new Items();
//		ii.setId(3);
//		i.add(ii);
//		ii = new Items();
//		ii.setId(4);
//		i.add(ii);
//		ii = new Items();
//		ii.setId(5);
//		i.add(ii);
//		Optional<Items> any = i.stream().filter(iii -> iii.getId()==3).findAny();
//		System.out.println(any);
		// Recipient's email ID needs to be mentioned.
		String to = "fromaddress@gmail.com";

		// Sender's email ID needs to be mentioned
		String from = "toaddress@gmail.com";

		// Assuming you are sending email from through gmails smtp
		String host = "smtp.gmail.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass username and password
		Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("fromaddress@gmail.com", "*******");

			}

		});

		// Used to debug SMTP issues
		session.setDebug(true);

		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			// Now set the actual message
			message.setText("This is actual message");

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

		// Recipient's email ID needs to be mentioned.
		String t = "toaddress@gmail.com";

		// Sender's email ID needs to be mentioned
		String fro = "fromaddress@gmail.com";

		// Assuming you are sending email from through gmails smtp
		String hos = "smtp.gmail.com";

		// Get system properties
		Properties prop = System.getProperties();

		// Setup mail server
		prop.put("mail.smtp.host", hos);
		prop.put("mail.smtp.port", "465");
		prop.put("mail.smtp.ssl.enable", "true");
		prop.put("mail.smtp.auth", "true");

		// Get the Session object.// and pass
		Session ses = Session.getInstance(prop, new javax.mail.Authenticator() {

			protected PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication("youremail@gmail.com", "*******");

			}

		});
		// session.setDebug(true);
		try {
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(ses);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(fro));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(t));

			// Set Subject: header field
			message.setSubject("This is the Subject Line!");

			Multipart multipart = new MimeMultipart();

			MimeBodyPart attachmentPart = new MimeBodyPart();

			MimeBodyPart textPart = new MimeBodyPart();

			try {
				File f = new File("H:\\pepipost_tutorials\\javaemail1.PNG");
				attachmentPart.attachFile(f);
				textPart.setText("This is text");
				multipart.addBodyPart(textPart);
				multipart.addBodyPart(attachmentPart);
			} catch (IOException e) {
				e.printStackTrace();
			}

			message.setContent(multipart);

			System.out.println("sending...");
			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}
