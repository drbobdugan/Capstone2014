package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;

public class LoginController extends WebPage {
	String username, password;
	User user;
	
	//what is the point? you return void. Changed to boolean from void
	public boolean authenticate(String username, String password)
	{
		//output();
		UserDao dao = new UserDao();
		user = dao.getUserByUsername(username);
		if(user != null) 
			if(user.getPassword().equals(password)) {
				CustomSession.get().setUser(user);
				return true;
			}
		return false;
	}
	
	public void redirectHome()
	{
		//Which home page? partner, volunteer, 
		if(user.getIsVolunteer() == true)
			setResponsePage(VolHomeView.class);
		if(user.getIsPartner() == true)
			setResponsePage(ParHomeView.class);
		if(user.getIsCoordinator() == true)
			setResponsePage(CorHomeView.class);
	}
	
	public void redirectRegister()
	{
		setResponsePage(RegisterView.class);
	}
	
	public void redirectPassword()
	{
		setResponsePage(ForgotPasswordView.class);
	}
}
