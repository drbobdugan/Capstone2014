package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.yui.calendar.TimeField;


public class VolunteerSearchView extends VolunteerTrackBaseView 
{
	private final Date startDate = new Date();
	private final Date endDate = new Date();

	private final Date time1 = new Date();
	private final Date time2 = new Date();

	public ArrayList DaoEvents;
	public RepeatingView repeating;
	private String partnerSelected;
	private String eventSelected;
	private String locationSelected; 

	static Logger logger = Logger.getLogger(VolunteerSearchView.class);
	
	public VolunteerSearchView()
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


		Form form2 = new Form("form2");



		// create dao for skills
		// get all skills
		// get user specific skills
		SkillDao skillsDao = new SkillDao(); 
		ArrayList<Object> skillslist = skillsDao.selectAll();

		ArrayList<String> skillsSelect = new ArrayList<String>();

		String[] skillarray = new String[skillslist.size()];


		for (int i = 0; i < skillslist.size(); i++)
		{
			skillarray[i] = ((Skill)skillslist.get(i)).getName();
		}


		List<String> fixedskills = Arrays.asList(skillarray);


		// create checkboxes for each skill, with users skills pre-checked
		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>(
				"skills", new Model(skillsSelect), fixedskills);

		// add checkboxes to form2
		form2.add(skillsBoxes);

		form2.add(new Button("submitButton") {
			@Override
			public void onSubmit() {

				makeTable(partnerSelected,eventSelected,locationSelected);

			}
		});
		// add form2 to page
		add(form2);


		Form form3 = new Form("form3"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};

		TimeField startTime = new TimeField("timeField1", new PropertyModel<Date>(this, "time1"));
		TimeField endTime = new TimeField("timeField2", new PropertyModel<Date>(this, "time2"));


		form3.add(startTime);
		form3.add(endTime);
		add(form3);



		repeating = new RepeatingView("repeating");
		add(repeating);


		form.add(startDateTextField);
		form.add(endDateTextField);
		form.add(partnerList);
		form.add(locationList);
		form.add(eventList);

		add(form);


	}

	public void makeTable(String partnerSelected,String eventSelected,String locationSelected)
	{
		ArrayList filteredEvents = new ArrayList();


		for(int i = 0; i < DaoEvents.size(); i++)
		{
			/*
		  if(partnerSelected.equals("--------- Select A Partner ----------"))
		  {
			 partnerSelected = ((Event) DaoEvents.get(i)).getOwnerEmail();
		  }
		  if(eventSelected.equals("---------- Select An Event ----------"))
		  {
			 eventSelected = ((Event) DaoEvents.get(i)).getName();
		  }
		  if(locationSelected.equals("--------- Select A Location---------"))
		  {
			 locationSelected = ((Event) DaoEvents.get(i)).getLocation();
		  }
			 */

			if((partnerSelected.equals(((Event) DaoEvents.get(i)).getOwnerEmail())) && (eventSelected.equals(((Event) DaoEvents.get(i)).getName())) && (locationSelected.equals(((Event) DaoEvents.get(i)).getLocation())))
			{
				filteredEvents.add(DaoEvents.get(i));
			}
			filteredEvents.add(DaoEvents.get(i));

		}



		for(int i = 0; i < filteredEvents.size(); i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item); 


			//item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
			item.add(new Label("eventName", ((Event) filteredEvents.get(i)).getName()));
			item.add(new Label("eventLocation", ((Event) filteredEvents.get(i)).getLocation()));
			item.add(new Label("eventPartner", ((Event) filteredEvents.get(i)).getOwnerEmail()));
			item.add(new Label("eventDate", ((Event) filteredEvents.get(i)).getStartDateTime().toString()));
			item.add(new Label("eventTime", ((Event) filteredEvents.get(i)).getStartDateTime().getTime()));
			final User pass = ((Event) filteredEvents.get(i)).getPartner();

			Form form4 = new Form("form4"){
				protected void onSubmit(){
					info("Form.onSubmit()");
				}
			};

			Button apply = new Button("applyButton"){
				@Override
				public void onSubmit(){
					info("Send to: ");
				}
			};
			
			Form form5 = new Form("form5");
			
			Button view = new Button("viewButton") {
				@Override
				public void onSubmit() {
					//KBH redirect to view this user person's profile i.e. in this case partner hosting the event
					logger.info("## What user are we looking at? "+pass.toString()+" ##");
					setResponsePage(new SearchPartnerProfileView(pass));
				}
			};
			
			form4.add(apply);
			form5.add(view);
			item.add(form4);
			item.add(form5);





		}
	}
}
