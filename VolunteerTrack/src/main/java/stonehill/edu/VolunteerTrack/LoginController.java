package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;

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
				System.out.println("###### SetUser, passwords matched #####");
				if(user.getIsCoordinator() == true)
					CustomSession.get().setState("Coordinator");
				else if(user.getIsPartner() == true)
					CustomSession.get().setState("Partner");
				else if(user.getIsVolunteer() == true)
					CustomSession.get().setState("Volunteer");
				System.out.println("####### State = "+CustomSession.get().getState()+" ######");
				return true;
			}
		return false;
	}

	public void redirectHome()
	{
		user = CustomSession.get().getUser();
		if( (user.getIsCoordinator() == true && user.getIsApprovedCoordinator() == true) && CustomSession.get().getState().equals("Coordinator"))
		{	setResponsePage(CoordinatorHomeView.class);}
		else if( (user.getIsPartner() == true && user.getIsApprovedPartner() == true) && CustomSession.get().getState().equals("Partner"))
		{	setResponsePage(PartnerHomeView.class);}
		else if( (user.getIsVolunteer() == true && user.getIsApprovedVolunteer() == true) && CustomSession.get().getState().equals("Volunteer"))
		{	setResponsePage(VolunteerHomeView.class);}
	}

	public void redirectRegister()
	{
		setResponsePage(RegisterView.class);
	}

	public void redirectPassword()
	{
		setResponsePage(ForgotPasswordView.class);
	}

/*	public void create()
	{
		UserDao dao = new UserDao();
		User user = new User("test1@gmail.com","csrocks55", "test","user", "", "", "", "", "", "", "", false, true, false, "", "", false, true, false, "");
		dao.insert(user);
		user = new User("test2@gmail.com","csrocks55", "test","user", "", "", "", "", "", "", "", true, false, false, "", "", true, false, false, "");
		dao.insert(user);
		user = new User("test3@gmail.com","csrocks55", "test","user", "", "", "", "", "", "", "", false, false, true, "", "", false, false, true, "");
		dao.insert(user);
		user = new User("test4@gmail.com","csrocks55", "test","user", "", "", "", "", "", "", "", true, true, true, "", "", true, true, true, "");
		dao.insert(user);	
	}

	public void check()
	{
		UserDao dao = new UserDao();
		delete();
		User user = dao.getUserByUsername("test1@gmail.com");
		if(user == null) {
			System.out.println("##### LoginController.Create() due to null test1@gmail.com #####");
			create();
		} else {
			ArrayList<Object> test = dao.getAllCoordinators();
			for(int i = 0; i < test.size(); i++) {
				System.out.println("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
			}
			test = dao.getAllPartners();
			for(int i = 0; i < test.size(); i++) {
				System.out.println("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
			}
			test = dao.getAllVolunteers();
			for(int i = 0; i < test.size(); i++) {
				System.out.println("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
			}
			System.out.println("##### LoginController.Check() found user test1@gmail.com #####");

		}
	}
	
	public void delete()
	{
		UserDao dao = new UserDao();
		User test = dao.getUserByUsername("test1@gmail.com");
		dao.delete(test);
		test = dao.getUserByUsername("test2@gmail.com");
		dao.delete(test);
		test = dao.getUserByUsername("test3@gmail.com");
		dao.delete(test);
		test = dao.getUserByUsername("test4@gmail.com");
		dao.delete(test);
	}*/


}
