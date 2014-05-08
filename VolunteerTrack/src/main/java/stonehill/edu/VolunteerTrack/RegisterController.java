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
			User user = new User(email, password, "", "", "", "", "", "", "", "", "", false, false, false, "", "", false, false, false, "");

			if(userType == 0)
				user.setIsPartner(true);
			else if(userType == 1)
				user.setIsVolunteer(true);
			else if(userType == 2)
				user.setIsCoordinator(true);
			
			NotificationDao nDao = new NotificationDao();
			nDao.sendRegistrationNotice(user.getEmail());
			dao.insert(user);

			if(keithtest(user) == true)
				System.out.println("User was added and is not yet approved!");
			else
				System.out.println("User was not added");
			return true;
		}
		return false;
	}

	public void RedirectLogin()
	{
		setResponsePage(LoginView.class);
	}

	public boolean keithtest(User user)
	{
		UserDao dao = new UserDao();
		//check if user was created and not approved yet
		User test = dao.getUserByUsername(user.getEmail());
		if(test.getIsApprovedCoordinator() == false) {
			System.out.println("This is an unapproved coordinator");}
		if(test.getIsApprovedPartner() == false) {
			System.out.println("This is an unapproved partner");}
		if(test.getIsApprovedVolunteer() == false) {
			System.out.println("This is an unapproved volunteer");}

		return false;
	}
}
