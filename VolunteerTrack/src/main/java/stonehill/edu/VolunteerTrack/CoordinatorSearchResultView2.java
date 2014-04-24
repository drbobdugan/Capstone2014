
package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class CoordinatorSearchResultView2 extends VolunteerTrackBaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final ArrayList users;

    public CoordinatorSearchResultView2(ArrayList<User> theUsers)
	{
    	RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		
    	users=theUsers;
    	
    	for(int i = 0; i < theUsers.size(); i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item); 

             String usersName = ((User) theUsers.get(i)).getFirstName() + " " + ((User) theUsers.get(i)).getLastName();
			//item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
			item.add(new Label("volunteerName", usersName));
			item.add(new Label("major", ((User) theUsers.get(i)).getMajor()));
			item.add(new Label("minor", ((User) theUsers.get(i)).getMinor()));


			Form form4 = new Form("form"){
				protected void onSubmit(){
					info("Form.onSubmit()");
				}
			};

			final int tempi = i;
			Button apply = new Button("userButton"){
				@Override
				public void onSubmit(){
					info("Send to: ");
					
					User wantedUser = (User)users.get(tempi);
					User CurrentUser = CustomSession.get().getUser();
					
					CustomSession.get().setUser(wantedUser);
					CustomSession.get().setSwitchBack(CurrentUser);
					this.setResponsePage(VolunteerHomeView.class);
				}
			};
			form4.add(apply);

			item.add(form4);
		}
    	
    	
    	
		
	}
}
