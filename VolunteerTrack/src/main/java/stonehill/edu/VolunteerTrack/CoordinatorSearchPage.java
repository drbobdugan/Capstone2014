package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import com.googlecode.wicket.jquery.ui.form.button.Button;

public class CoordinatorSearchPage  extends VolunteerTrackBaseView 
{
	TextField<String> partnerName;
	TextField<String> studentName;
	TextField<String> major;
	TextField<String> minor;
	ArrayList users;

	public CoordinatorSearchPage()
	{
		//this is the form for all the textfields and buttons
		Form form = new Form("form"){
			protected void onSubmit()
			{
				info("Form.onSubmit()");
			}
		};

		//the textfield for the partner search parameters
		partnerName = new TextField<String>("partnerName",Model.of("")); 
		
		//the textfields for criteria to filter students by
		studentName = new TextField<String>("studentName",Model.of("")); 
		major = new TextField<String>("major",Model.of("")); 
		minor = new TextField<String>("minor",Model.of("")); 

		Button searchPartnerButton = new Button("searchPartnerButton"){
			@Override
			public void onSubmit(){
				info("Send to: ");
				String partnerNameEntered = partnerName.getModelObject();

				// if the user doesn't enter anything it passes in a blank string that will bring back all partners
				if(partnerNameEntered == null)
				{
					partnerNameEntered = "";
				}

				//makes dao for search and gets results. sends results to the next page for display
				UserDao dao = new UserDao();
				ArrayList<User> returnList = dao.SearchUsersByOrganizationName(partnerNameEntered);
				setResponsePage(new CoordinatorPartnerSearchResultView(returnList));
			}
		};

		Button searchVolunteerButton = new Button("searchVolunteerButton"){
			@Override
			public void onSubmit(){
				info("Send to: ");
				String studentNameEntered = studentName.getModelObject();
				String majorEntered = major.getModelObject();
				String minorEntered = minor.getModelObject();
				
				//whatever isnt entered turns into an empty string so it prevents filtration by that. 
				if(studentNameEntered == null)
				{
					studentNameEntered = "";
				}
				if(majorEntered == null)
				{
					majorEntered = "";
				}
				if(minorEntered == null)
				{
					minorEntered = "";
				}

				//makes dao for search and gets results. sends results to the next page for display
				UserDao dao = new UserDao();
				ArrayList<User> returnList = dao.SearchUsersByNameMajorMinor(studentNameEntered, majorEntered, minorEntered);
				setResponsePage(new CoordinatorVolunteerSearchResultView(returnList));
			}
		};
		
		
        //adds everything to the forum and the forum to the page
		form.add(partnerName);
		form.add(studentName);
		form.add(major);
		form.add(minor);
		form.add(searchPartnerButton);
		form.add(searchVolunteerButton);

		add(form);
	}
}
