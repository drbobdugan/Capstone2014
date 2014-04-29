package stonehill.edu.VolunteerTrack;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import com.googlecode.wicket.jquery.ui.form.button.Button;

public class LoginView extends WebPage {
	User user;
	TextField email;
	PasswordTextField password;
	Form login, register, change;
	Label invalid;
	LoginController log;
	
	public LoginView()
	{
		user = new User();
		log = new LoginController();		
		login = new Form("login");
		register = new Form("register");
		change = new Form("change");

		add(invalid = new Label("invalid", ""));
		login.add(email = new TextField("emailTextField", new PropertyModel(user, "email")));
		login.add(password = new PasswordTextField("passwordTextField", new PropertyModel(user,"password")));
		
		login.add(new Button("log") {
			@Override
			public void onSubmit() {
				if(log.authenticate(user.getEmail(), user.getPassword()) == true)
				{
					System.out.println("##### Passed Authentication #####");
					log.redirectHome();
				}
				else
					invalid.setDefaultModel(new Model("There was an issue, try again."));	
			}
		});
		add(login);
		
		register.add(new Button("reg") {
			@Override
			public void onSubmit() {
				log.redirectRegister();
			}
		});
		add(register);
		
		change.add(new Button("pass") {
			@Override
			public void onSubmit() {
				log.redirectPassword();
			}
		});
		add(change);
		
		
		
	}
	 
}
