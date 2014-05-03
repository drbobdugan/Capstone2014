package stonehill.edu.VolunteerTrack;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
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

	TextField<String>  partnerName;
	TextField<String>  location;
	TextField<String>  eventName;

	private final Date startDate = new Date();
	private final Date endDate = new Date();
	
	String selected0;
	String selected1;
	String selected2;
	String selected3;
	String selected4;
	String selected5;
	
	DropDownChoice<String> startHR;
	DropDownChoice<String> startMIN;
	DropDownChoice<String> startAMPM;
	
	DropDownChoice<String> endHR;
	DropDownChoice<String> endMIN;
	DropDownChoice<String> endAMPM;
	
	List<String> hours = new ArrayList<String>(Arrays.asList("","00","01","02","03","04","05","06","07","09","10","12"));
	List<String> mins = new ArrayList<String>(Arrays.asList("","00","05","10","15","20","25","30","35","40","45","50","55"));
	List<String> ampm = new ArrayList<String>(Arrays.asList("","AM","PM"));

	public VolunteerSearchView()
	{
		// Create the form for the textfield entries
		Form form = new Form("PageForm"){
			protected void onSubmit()
			{
				info("Form.onSubmit()");
			}
		};

		//declare the textfields note they are global variables so they can later be accessed in an onSubmit
		partnerName = new TextField<String>("partnerName",Model.of("")); 
		location = new TextField<String>("location",Model.of("")); 
		eventName = new TextField<String>("eventName",Model.of("")); 

		//add everything to this form
		form.add(partnerName);
		form.add(location);
		form.add(eventName);

		


		// Creates the form for the two date fields


		// creates two textfields that handle date entries
		DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
		DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));

		// uses js to make the calendar appear on click and this is the datepicker for the start date 
		DatePicker startDatePicker = new DatePicker(){

			protected String getAdditionalJavascript()
			{
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
			}
		}; 

		// uses js to make the calendar appear on click and this is the datepicker for the  end date 
		DatePicker endDatePicker = new DatePicker(){

			protected String getAdditionalJavascript()
			{
				return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
			}
		}; 

		// this sets both to make the alender appear on click and adds them to the date textfields
		startDatePicker.setShowOnFieldClick(true);
		endDatePicker.setShowOnFieldClick(true);
		startDateTextField.add(startDatePicker);
		endDateTextField.add(endDatePicker); 

		// adds the date fields to the form and the form to the page
		form.add(startDateTextField);
		form.add(endDateTextField);

		
		
		
		// this is the form that handles the time selection option

	
		// this is a default string for all drop downs to default to the empty string
		selected0 = "";
		selected1 = "";
		selected2 = "";
		selected3 = "";
		selected4 = "";
		selected5 = "";
		
		
		// the start time drop downs
		startHR = new DropDownChoice<String>("startHR", new PropertyModel<String>(this,"selected0"), hours);
		form.add(startHR);
		
		startMIN = new DropDownChoice<String>("startMIN", new PropertyModel<String>(this,"selected1"), mins);
		form.add(startMIN);
		
		startAMPM = new DropDownChoice<String>("startAMPM", new PropertyModel<String>(this,"selected2"), ampm);
		form.add(startAMPM);
		
		
		// the end time drop downs
		endHR = new DropDownChoice<String>("endHR", new PropertyModel<String>(this,"selected3"), hours);
		form.add(endHR);
		
		endMIN = new DropDownChoice<String>("endMIN", new PropertyModel<String>(this,"selected4"), mins);
		form.add(endMIN);
		
		endAMPM = new DropDownChoice<String>("endAMPM", new PropertyModel<String>(this,"selected5"), ampm);
		form.add(endAMPM);
		
		

		
		
		
		// form for skills *******
		
		

		
		// this is the button for searching
		Button searchButton = new Button("searchButton"){
			@Override
			public void onSubmit(){
				info("Send to: ");
				String enteredPartnerName = partnerName.getModelObject();
				String enteredlocation= location.getModelObject();
				String enteredEventName = eventName.getModelObject();
				
				String enteredStartHR = startHR.getModelObject();
				String enteredStartMIN = startMIN.getModelObject();
				String enteredStartAMPM = startAMPM.getModelObject();
				
				String enteredEndHR = endHR.getModelObject();
				String enteredEndMIN = endMIN.getModelObject();
				String enteredEndAMPM = endAMPM.getModelObject();
				
				
				
				// use to get value [dropdown choice].getModelObject()
				//get all the search parameters and filter through nulls and blanks. call query to get array of events and send to next page
				
				
				
				EventDao theEvents = new EventDao();
				ArrayList DaoEvents =  theEvents.selectAll();
				
				
				setResponsePage(new VolunteerSearchResultPage(DaoEvents));


			}
		};
		
		form.add(searchButton);
		add(form);


	}
	public String getSelected0()
	{
		return selected0;
	}
	public String getSelected1()
	{
		return selected1;
	}
	public String getSelected2()
	{
		return selected2;
	}
	public String getSelected3()
	{
		return selected3;
	}
	public String getSelected4()
	{
		return selected4;
	}
	public String getSelected5()
	{
		return selected5;
	}
	
}
