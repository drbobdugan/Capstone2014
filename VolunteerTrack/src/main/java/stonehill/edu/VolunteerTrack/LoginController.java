package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;

public class LoginController extends WebPage implements Serializable {
	
	static Logger logger = Logger.getLogger(LoginController.class);
	
	String username, password;
	User user;

	public boolean authenticate()
	{
		if(CustomSession.get().getUser() == null)
			return false;
		return true;
	}
	//what is the point? you return void. Changed to boolean from void
	public boolean authenticateLogin(String username, String password)
	{
		//output();
		UserDao dao = new UserDao();
		user = dao.getUserByUsername(username);
		if(user != null) 
			if(user.getPassword().equals(password)) {
				CustomSession.get().setUser(user);
				logger.debug("###### SetUser, passwords matched #####");
				if(user.getIsCoordinator() == true)
					CustomSession.get().setState("Coordinator");
				else if(user.getIsPartner() == true)
					CustomSession.get().setState("Partner");
				else if(user.getIsVolunteer() == true)
					CustomSession.get().setState("Volunteer");
				logger.debug("####### State = "+CustomSession.get().getState()+" ######");
				return true;
			}
		return false;
	}

	public void switchUser(User swap)
	{
		CustomSession.get().setSwitchUser(CustomSession.get().getUser());
		CustomSession.get().setUser(swap);
		CustomSession.get().setSwitchOn(true);
		logger.debug("## LoginController : Switching to user > "+swap.toString()+" ##");
		logger.debug("## IsApproved - Partner: "+swap.getIsApprovedPartner()+", Volunteer: "+swap.getIsApprovedVolunteer()+", Coordinator: "+swap.getIsApprovedCoordinator()+" ##");
		if(swap.getIsApprovedPartner()) {
			logger.debug("## Swap user is a partner ##");
			CustomSession.get().setState("Partner");
			setResponsePage(PartnerHomeView.class);
		}
		else if(swap.getIsApprovedVolunteer()) {
			logger.debug("## Swap user is a volunteer ##");
			CustomSession.get().setState("Volunteer");
			setResponsePage(VolunteerHomeView.class);
		}
	}
	public void switchBack()
	{
		logger.debug("## LoginController : Switching back users > "+CustomSession.get().getSwitchUser().toString()+" ##");
		CustomSession.get().setUser(CustomSession.get().getSwitchUser());
		CustomSession.get().setSwitchOn(false);
		CustomSession.get().setSwitchUser(null);
		logger.debug("## Swapping back to coordinator ##");
		CustomSession.get().setState("Coordinator");
	}

	public void redirectHome()
	{
		user = CustomSession.get().getUser();
		if( user.getIsApprovedCoordinator() == true && CustomSession.get().getState().equals("Coordinator"))
		{	setResponsePage(CoordinatorHomeView.class);}
		else if( user.getIsApprovedPartner() == true && CustomSession.get().getState().equals("Partner"))
		{	setResponsePage(PartnerHomeView.class);}
		else if( user.getIsApprovedVolunteer() == true && CustomSession.get().getState().equals("Volunteer"))
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


	public void create()
	{
		/* tring ema, String pass, String fir, String las, String str, String cit, String sta,
		 * String zi, String pho, String par, String vol, boolean isp, boolean isc, boolean isv,
		 * String maj, String min, boolean isApprovedPartner, boolean isApprovedCoordinator, boolean isApprovedVolunteer, 
		 * String organizationName */
		UserDao dao = new UserDao();                                                                                                                                                                                               //boolean isp, boolean isc, boolean isv,           boolean isAPartner, boolean isACoordinator, boolean isAVolunteer  
		User user = new User(-1,"test1@gmail.com", "csrocks55","Micheal", "Singleton", "320 Washington Street", "Easton", "MA", "02356", "Blank Photo", "This student has no partner description", "This is a volunteer description string", false, true, true, "Computer Science", "Database Programming", false, true, true, "This student has no partner name", "");
		dao.insert(user);
		user = new User(-1,"test2@gmail.com", "csrocks55","Joey", "Scherr", "320 Washington Street", "Easton", "MA", "02356", "Blank Photo", "This is a partner description string", "This partner has no volunteer description string", true, false, false, "None", "None", true, false, false, "Joeys Wildlife Rescue", "");
		dao.insert(user);
		user = new User(-1,"test3@gmail.com", "csrocks55","Zachery", "Brown", "320 Washington Street", "Easton", "MA", "02356", "Blank Photo", "This is a student has no partner description string", "This partner has no volunteer description string", false, false, true, "Computer Science", "Project Management", false, false, true, "This student has no partner name", "");
		dao.insert(user);
		user = new User(-1,"test4@gmail.com", "csrocks55","Keith", "Holmander", "320 Washington Street", "Easton", "MA", "02356", "Blank Photo", "This is a partner description string", "This is a volunteer description string", true, true, true, "Computer Science", "Wicket Programming", true, true, true, "Veterns Hopistal", "");
		dao.insert(user);	
	}

	public void check()
	{
		UserDao dao = new UserDao();
		ArrayList<Object> test = dao.getAllCoordinators();
		for(int i = 0; i < test.size(); i++) {
			logger.debug("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
		}
		test = dao.getAllPartners();
		for(int i = 0; i < test.size(); i++) {
			logger.debug("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
		}
		test = dao.getAllVolunteers();
		for(int i = 0; i < test.size(); i++) {
			logger.debug("#### "+i+" ("+((User)test.get(i)).toString()+" #####");
		}
		logger.debug("##### LoginController.Check() found user test1@gmail.com #####");
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
	}


}
