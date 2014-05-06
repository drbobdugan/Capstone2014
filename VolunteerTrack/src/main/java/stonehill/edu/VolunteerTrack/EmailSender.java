package stonehill.edu.VolunteerTrack;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.URLName;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.smtp.SMTPTransport;

public class EmailSender extends Thread {
	public String toAddress, fromAddress, subject, body;
	
	public EmailSender(String toAddress, String fromAddress, String subject, String body)
	{
		this.toAddress = toAddress;
		this.fromAddress = fromAddress;
		this.subject = subject;
		this.body = body;
	}
	
	public synchronized void run()
	{
		Authenticator auth = new STMPAuthenticator("");
		try {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex1) {
				ex1.printStackTrace();
			}
			
			((STMPAuthenticator)auth).setEmail(fromAddress);
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			
			Session session = Session.getDefaultInstance(props, auth);
			
			session.setDebug(false);
			session.setPasswordAuthentication(new URLName("smtp.gmail.com"), ((STMPAuthenticator)auth).getPasswordAuthentication());
			
			Message msg = new MimeMessage(session);
			
			InternetAddress addressFrom = new InternetAddress(fromAddress);
			msg.setFrom(addressFrom);
			
			InternetAddress[] addressTo = new InternetAddress[1];
			addressTo[0] = new InternetAddress(toAddress);
			msg.setRecipients(Message.RecipientType.TO, addressTo);
			
			msg.setSubject(subject);
			msg.setContent(body, "text/plain");
			session.setPasswordAuthentication(new URLName("smtp.gmail.com"), ((STMPAuthenticator)auth).getPasswordAuthentication());
			SMTPTransport.send(msg);
			
			Thread.sleep(5000);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
