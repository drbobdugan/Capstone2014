package stonehill.edu.VolunteerTrack;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class LoginView extends WebPage {
	User user;
	TextField email;
	PasswordTextField password;
	Form login;
	Form register;
	Label invalidLogin;
	
	public LoginView()
	{
		user = new User();
		login = new Form("login");
		register = new Form("register");
		login.add(invalidLogin = new Label("invalidLogin", ""));
		login.add(email = new TextField("emailTextField", new PropertyModel(user, "email")));
		login.add(password = new PasswordTextField("passwordTextField", new PropertyModel(user,"password")));
		login.add(new Button("loginButton") {
			@Override
			public void onSubmit() {   			    
				UserDao userDao = new UserDao();
				User test = userDao.getUserByUsername(user.getEmail());
				if(test != null && user.getPassword().equals(test.getPassword()))
				{
					CustomSession.get().setUser(test);
					if(test.getIsApproved() == true)
					{
						if(test.getIsVolunteer() == true)
							setResponsePage(VolHomeView.class);
						if(test.getIsPartner() == true)
							setResponsePage(ParHomeView.class);
						//add coordinator when created
					}
					else //waiting on approval
					{
						user = new User();
						invalidLogin.setDefaultModel(new Model("Waiting on approval"));
					}
				}
				else
				{
					user = new User();
					invalidLogin.setDefaultModel(new Model("Invalid Login"));
				}
			}
		});
		add(login);
		register.add(new Button("registerButton") {
			@Override
			public void onSubmit() {
				setResponsePage(RegisterView.class);
			}
		});
		add(register);
	}
	 
}
