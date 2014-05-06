package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.resource.Properties;

import java.util.*;

public class ForgotPasswordController extends WebPage {
	String email;

	public void Submit(String email)
	{
		NotificationDao noteDao = new NotificationDao();
		UserDao dao = new UserDao();
		
		noteDao.sendForgotPassword(email);
		User user = dao.getUserByUsername(email);
		user.setPassword("Stonehill14");
		dao.update(user);
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
