package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;

public class NotificationDao extends WebPage implements Serializable {
	private static String admin = new String("voltracktest14@gmail.com");
	static Logger logger = Logger.getLogger(NotificationDao.class);
	
	public static void sendRegistrationNotice(String email)
	{
		try {
			//send notice to student
			String message = "Hello, We have received your registration request and are currently reviewing it. You will receive a notification shortly when your request has been processed."; 
			EmailSender sendMail = new EmailSender(email, admin, "Volunteer Track Registration", message);
			sendMail.start();
		}
		catch(Exception e) {
			logger.info("## ERROR : "+e.getMessage());
			e.printStackTrace();
		}
		//send notice to admin
	}
	
	public static void sendApprovedRegistration(String email)
	{
		try {
			//send notice of approved registration
			String message = "Hello, We have approved your registration request and you are now able to loginController in to Volunteer Track. Thank you for your registration and future use of use of our services.";
			EmailSender sendMail = new EmailSender(email, admin, "Volunteer Track Registration approval", message);
			sendMail.start();
		}
		catch(Exception e) {
			logger.info("## ERROR : "+e.getMessage());
			e.printStackTrace();
		}
		
	}
	
	public static void sendForgotPassword(String email)
	{
		try {
			//send notice of password change for forgot password
			String message = "Hello, We have received a request from you that you have forgetten your password. If this was not requested by you or someone on your behalf please contact an administrator. Otherwise your password has been reset to Stonehill14. Be sure to change your password next time you access our service. Thank you.";
			EmailSender sendMail = new EmailSender(email, admin, "Volunteer Track Forgotten Password service", message);
			sendMail.start();
		} catch(Exception e) {
			logger.info("## ERROR : "+e.getMessage());
			e.printStackTrace();
		}
	}
}
