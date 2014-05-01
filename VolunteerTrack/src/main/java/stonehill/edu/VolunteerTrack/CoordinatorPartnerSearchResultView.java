
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

public class CoordinatorPartnerSearchResultView extends VolunteerTrackBaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	ArrayList users;
	
	static Logger logger = Logger.getLogger(CoordinatorPartnerSearchResultView.class);

	public CoordinatorPartnerSearchResultView(ArrayList<User> theUsers)
	{
		
		
		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);

		users=theUsers;

		for(int i = 0; i < theUsers.size(); i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item); 
			//item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
			item.add(new Label("partnerName", ((User) theUsers.get(i)).getOrganizationName()));
			item.add(new Label("partnerLocation", ((User) theUsers.get(i)).getCity()));

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
					LoginController log = new LoginController();
					log.switchUser((User)users.get(tempi));
				}
			};
			form4.add(apply);

			item.add(form4);
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
