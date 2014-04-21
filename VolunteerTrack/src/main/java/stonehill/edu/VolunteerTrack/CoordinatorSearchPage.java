package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.yui.calendar.TimeField;




public class CoordinatorSearchPage  extends VolunteerTrackBaseView 
{

	private final Date startDate = new Date();
	private final Date endDate = new Date();

	public ArrayList DaoEvents;
	public RepeatingView repeating;
	private String partnerSelected;
	private String eventSelected;
	private String locationSelected; 

	public CoordinatorSearchPage()
	{
		EventDao theEvents = new EventDao();
		DaoEvents =  theEvents.selectAll();

		UserDao theUsers = new UserDao();
		ArrayList partnerUsers = theUsers.getAllPartners();

		List<String> partners = new ArrayList<String>();
		partners.add("--------- Select A Partner ----------");

		List<String> locations = new ArrayList<String>();
		locations.add("--------- Select A Location---------");

		List<String> events = new ArrayList<String>();
		events.add("---------- Select An Event ----------");


		for (int i = 0; i< DaoEvents.size(); i++)
		{
			events.add(((Event) DaoEvents.get(i)).getName());
			
			
			if((((Event) DaoEvents.get(i)).getLocation() != null) && (!(((Event) DaoEvents.get(i)).getLocation().equals(""))))
			{
				int tempCounter = 0;
				for(int j = 0; j < locations.size();j++)
				{
					if(!(((Event) DaoEvents.get(i)).getLocation()).equals(locations.get(j)))
					{
						tempCounter++;
					}
				}
				if(tempCounter == locations.size())
				{
					locations.add(((Event) DaoEvents.get(i)).getLocation());
				}
			}
		}

		for(int i = 0; i < partnerUsers.size();i++)
		{
			partners.add(((User) partnerUsers.get(i)).getEmail());

		}


		Form form = new Form("form"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};


		partnerSelected = "--------- Select A Partner ----------";
		Model dropdownPartner = new Model<String>(partnerSelected); 
		DropDownChoice<String> partnerList = new DropDownChoice<String>("partners", dropdownPartner, partners);


		locationSelected = "--------- Select A Location---------";
		Model dropdownLocation = new Model<String>(locationSelected); 
		DropDownChoice<String> locationList = new DropDownChoice<String>("locations", dropdownLocation, locations); 


		eventSelected = "---------- Select An Event ----------";
		Model dropdownEvent = new Model<String>(eventSelected); 
		DropDownChoice<String> eventList = new DropDownChoice<String>("events", dropdownEvent, events); 
		
		
		// Date selector fields and methods
				DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
				DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));

				DatePicker startDatePicker = new DatePicker(){

					protected String getAdditionalJavascript()
					{
						return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
					}
				}; 

				DatePicker endDatePicker = new DatePicker(){

					protected String getAdditionalJavascript()
					{
						return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
					}
				}; 

				startDatePicker.setShowOnFieldClick(true);
				endDatePicker.setShowOnFieldClick(true);
				startDateTextField.add(startDatePicker);
				endDateTextField.add(endDatePicker); 

		
		form.add(startDateTextField);
		form.add(endDateTextField);
		form.add(partnerList);
		form.add(locationList);
		form.add(eventList);

		add(form);



	}




}
