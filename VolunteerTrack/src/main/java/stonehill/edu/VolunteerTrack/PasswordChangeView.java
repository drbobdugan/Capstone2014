package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class PasswordChangeView extends WebPage {

	Form change;
	PasswordTextField password, checker, oldpass;
	String check, old, pass;
	User user;
	Label invalid;
	
	public PasswordChangeView(String email)
	{
		//test();
		//user = new User();
		//System.out.println("######### "+user.toString()+" / "+pass+" ######");
		final String lookup = email;
		change = new Form("change");
		change.add(oldpass = new PasswordTextField("old", new PropertyModel(this, "old")));
		change.add(password = new PasswordTextField("password", new PropertyModel(this, "pass")));
		change.add(checker = new PasswordTextField("checker", new PropertyModel(this, "check")));
		change.add(invalid = new Label("invalid", ""));
		change.add(new Button("changeButton") {
			@Override
			public void onSubmit() {
				ForgotPasswordController control =  new ForgotPasswordController();
				UserDao dao = new UserDao();
				user = dao.getUserByUsername(lookup);
				if(user != null)
					control.Change(old, pass, check, user);
				else
					invalid.setDefaultModel(new Model("An error occurred, try again"));
			}
		});
		add(change);
	}
}
