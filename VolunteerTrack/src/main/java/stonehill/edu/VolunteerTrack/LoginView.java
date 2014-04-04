package stonehill.edu.VolunteerTrack;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class LoginView extends WebPage {
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
			    //User test = new User("kholmander@stonehill.edu","gunslinger", "keith", "holmander", "", "", "", "", "", "", "", false, false, true, true);
				//userDao.update(test);			    
			    user = new User((User)login.getModelObject());
				user = userDao.getUserByUsername(user.getEmail());
				if(!user.getEmail().equals("") && user.getPassword().equals(password.getInput()))
				{
					CustomSession.get().setUser(new User(user));
					System.out.println("@@ "+CustomSession.get().getUser().getIsVolunteer()+" / "+CustomSession.get().getUser().toString()+" @@");
					//System.out.println("@@ "+test.toString()+" / "+test.getIsVolunteer());
					setResponsePage(VolHomeView.class);
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
				setResponsePage(RegisterPage.class);
			}
		});
		add(register);
	}
	 
}
