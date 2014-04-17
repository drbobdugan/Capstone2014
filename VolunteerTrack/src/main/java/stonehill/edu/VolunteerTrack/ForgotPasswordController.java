package stonehill.edu.VolunteerTrack;

import org.apache.commons.mail.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.Properties;

import java.util.*;

public class ForgotPasswordController extends WebPage {
	String email;
	
	//Unknown where our mail server will be, or what email we will use for now. all fields will need to be filled in
	public void Submit(String email) throws EmailException
	{
//		Email tosend = new SimpleEmail();
//		tosend.setHostName("stmp.gmail.com"); //need this
//		tosend.setSmtpPort(587); //need this
//		tosend.setAuthenticator(new DefaultAuthenticator("volunteertrack14@gmail.com", "csrocks55")); //need this
//		tosend.setStartTLSEnabled(true);
//		tosend.setFrom("volunteertrack14@gmail.com", "Volunteer Track"); //need this
//		tosend.setSubject("Password Change Service");
//		tosend.setMsg("UNKNOWN WHAT PROCEDURE WILL GO HERE"); //need this
//		tosend.addTo(email);
//		tosend.send();
		
		//normally would be email with url for this response page, still having trouble with email.
		setResponsePage(PasswordChangeView.class);
		
	}
	
	public boolean Change(String old, String password, String check, User user)
	{
		UserDao dao = new UserDao();
		if(old.equals(user.getPassword()) && password.equals(check)) //old password matches input & new passwords match
		{
			user.setPassword(password);
			dao.update(user);
			return true;
		}
		return false;
	}
}
