package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;

public class RegisterController extends WebPage {
	String email, password, passwordConfirm;
	int userType;

	public boolean RegisterUser(String email, String password, String passwordConfirm, int userType)
	{
		UserDao dao = new UserDao();
		//Check if user already exists              and passwords match
		if(dao.getUserByUsername(email) == null && password.equals(passwordConfirm))
		{
			User user = new User(email, password, "", "", "", "", "", "", "", "", "", false, false, false, false, "", "");
			switch(userType)
			{
			case 1: user.setIsPartner(true);
			case 2: user.setIsVolunteer(true);
			case 3: user.setIsCoordinator(true);
			}
			dao.insert(user);
			return true;
		}
		return false;
	}

	public void RedirectLogin()
	{
		setResponsePage(LoginView.class);
	}
}
