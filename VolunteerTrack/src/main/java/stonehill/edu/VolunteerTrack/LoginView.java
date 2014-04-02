package stonehill.edu.VolunteerTrack;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class LoginView extends VoltrackPage {

	private static final Logger logger = Logger.getLogger(HomePage.class);
	User user;
	UserDao userDao = new UserDao();
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
		
		login.setModel(new Model(user));
		login.add(invalidLogin = new Label("invalidLogin", ""));
		login.add(new Label("emailLabel", "Email :"));
		login.add(new Label("passwordLabel", "Password :"));
		login.add(email = new TextField("emailTextField", new PropertyModel(user, "email")));
		login.add(password = new PasswordTextField("passwordTextField", new PropertyModel(user,"password")));
		
		login.add(new Button("loginButton") {
			@Override
			public void onSubmit() {   
				//error checking
				user = new User((User)login.getModelObject());
				user = userDao.getUserByUsername(user.getEmail());
				if(!user.getEmail().equals("") && user.getPassword().equals(password.getInput()))
				{
					CustomSession.get().setUser(user);
					setResponsePage(HomePage.class);
				}
				else
				{
					email.setModelObject("");
					password.setModelObject("");
					invalidLogin.setDefaultModel(new Model("Invalid Login"));
				}
			}
		});
		add(login);
		
		register.add(new Button("registerButton") {
			@Override
			public void onSubmit() {
				setResponsePage(new RegisterPage());
			}
		});
		add(register);
	}
	 
}
