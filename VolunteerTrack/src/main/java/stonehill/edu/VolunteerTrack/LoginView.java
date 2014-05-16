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
	LoginController loginController;
	
	public LoginView()
	{
		user = new User();
		loginController = new LoginController();
		login = new Form("login");
		add(login);
		add(invalid = new Label("invalid", ""));
		
		login.add(email = new TextField("emailTextField", new PropertyModel(user, "email")));
		login.add(password = new PasswordTextField("passwordTextField", new PropertyModel(user,"password")));
		
		login.add(new Button("login-button") {
			@Override
			public void onSubmit() {
				if(loginController.authenticateLogin(user.getEmail(), user.getPassword()) == true)
					loginController.redirectHome();
				else
					invalid.setDefaultModel(new Model("There was an issue, try again."));	
			}
		});
		
		login.add(new Button("register-button") {
			@Override
			public void onSubmit() {
				loginController.redirectRegister();
			}
		});
		
		login.add(new Button("forgot-password-button") {
			@Override
			public void onSubmit() {
				loginController.redirectPassword();
			}
		});	
	}
}
