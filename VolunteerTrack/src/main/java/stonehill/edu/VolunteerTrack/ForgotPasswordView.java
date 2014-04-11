package stonehill.edu.VolunteerTrack;

import org.apache.commons.mail.EmailException;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class ForgotPasswordView extends WebPage {
	Form change;
	TextField email;
	Label invalid;
	String pass;
	
	public ForgotPasswordView()
	{
		change = new Form("change");
		change.add(email = new TextField("email", new PropertyModel(this, "pass")));
		change.add(invalid = new Label("invalid"));
		change.add(new Button("changeButton") {
			@Override
			public void onSubmit() {
				ForgotPasswordController control = new ForgotPasswordController();
				try {
					control.Submit(pass);
				} catch (EmailException e) {
					invalid.setDefaultModel(new Model("There was an issue, try again."));
				}
			}
		});
		add(change);
		
	}
}
