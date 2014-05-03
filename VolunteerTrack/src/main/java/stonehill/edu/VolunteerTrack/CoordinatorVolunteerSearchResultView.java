
package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.Button;

public class CoordinatorVolunteerSearchResultView extends VolunteerTrackBaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	ArrayList users;
	static Logger logger = Logger.getLogger(CoordinatorVolunteerSearchResultView.class);
	
	public CoordinatorVolunteerSearchResultView(ArrayList<User> theUsers)
	{

		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);

		users=theUsers;

		for(int i = 0; i < theUsers.size(); i++)
		{
			logger.debug("## Coordinator Vol. Search Results : "+i+" : "+users.get(i).toString()+" ##");
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item); 

			String usersName = ((User) theUsers.get(i)).getFirstName() + " " + ((User) theUsers.get(i)).getLastName();
			
			//adds all the variables to each row
			item.add(new Label("volunteerName", usersName));
			item.add(new Label("major", ((User) theUsers.get(i)).getMajor()));
			item.add(new Label("minor", ((User) theUsers.get(i)).getMinor()));


			Form form2 = new Form("form"){
				protected void onSubmit(){
					info("Form.onSubmit()");
				}
			};

			
			// this handles the switch persona button that switches the users persona to the user of the row they clicked
			final int tempi = i;
			Button apply = new Button("userButton"){
				@Override
				public void onSubmit(){
					info("Send to: ");
					LoginController log = new LoginController();
					log.switchUser((User)users.get(tempi));
				}
			};
			form2.add(apply);

			item.add(form2);
		}


		Form form2 = new Form("form2"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};

		Button back=new Button("back") {
			@Override
			public void onSubmit() {
				this.setResponsePage(CoordinatorSearchPage.class);  
			}
		}; 
		form2.add(back);
		add(form2);
	}
}
