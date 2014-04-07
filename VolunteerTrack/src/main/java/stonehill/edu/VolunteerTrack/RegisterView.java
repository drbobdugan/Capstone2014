package stonehill.edu.VolunteerTrack;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class RegisterView extends WebPage {
	private static final List<String> TYPES = Arrays.asList(new String[] { "Partner", "Volunteer", "Coordinator" });
	private String select = "Partner", check;
	BookmarkablePageLink home;
	
	PasswordTextField password, checker;
	EmailTextField email;
	TextField phone, address;
	
	Form register;
	User user, test;
	Label invalid;
	
	public RegisterView()
	{
		user = new User();
		add(home = new BookmarkablePageLink("home", LoginView.class));
		register = new Form("register");
		register.add(invalid = new Label("invalid", ""));
		RadioChoice<String> userType = new RadioChoice<String>("userType", new PropertyModel<String>(this, "select"), TYPES);
		register.add(userType);
		register.add(email = new EmailTextField("email", new PropertyModel(user, "Email")));
		register.add(password = new PasswordTextField("password", new PropertyModel(user, "Password")));
		register.add(checker = new PasswordTextField("checker", new PropertyModel(this, "check")));
		register.add(phone = new TextField("phone", new PropertyModel(user, "PhoneNumber")));
		register.add(address = new TextField("address", new PropertyModel(user, "Street")));
		
		register.add(new Button("create") {
			@Override
			public void onSubmit() {
				UserDao userDao = new UserDao();
				//Add secondary password field to ensure proper password entered, atm cannot evoke .equals on user.getPassword.equals(checker)
				//if user does not exist
				if(userDao.getUserByUsername(user.getEmail()) == null)
				{
					//Check passwords entered match
					if(user.getPassword().equals(check))
					{
						System.out.println(" ## user does not exist & passwords match ##");
						//find out what type of users they wish to be
						if(select.equals("Volunteer")) {
							user.setIsVolunteer(true);
						}else if(select.equals("Partner")) {
							user.setIsPartner(true);
						} else { //Coordinator
							user.setIsCoordinator(true);
						}
						user.setIsApproved(false);
						userDao.insert(user);
						setResponsePage(LoginView.class);
					}
					else //passwords do not match
					{
						user = new User();
						invalid.setDefaultModel(new Model("Passwords did not match"));
					}
				}else{
					System.out.println(" ## user exists ##");
					user = new User();
					invalid.setDefaultModel(new Model("User already exists"));
				}
			}
		});
		
		add(register);
	}

}
