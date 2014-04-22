package stonehill.edu.VolunteerTrack;

import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.Button;

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
		add(invalid = new Label("invalid", ""));
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
				RegisterController control = new RegisterController();	
				int pass = 0;
				for(int i =0; i < TYPES.size(); i++)
					if(TYPES.get(i).equals(select))
						pass=i;
				if(control.RegisterUser(user.getEmail(), user.getPassword(), check, pass) == true)
					control.RedirectLogin();
				else
					invalid.setDefaultModel(new Model("There was an issue, please try again"));
					
			}
		});
		
		add(register);
	}

}
