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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class VolunteerEditHoursView extends VolunteerTrackBaseView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField event, partner, date, hours;
	Form form1;
	TimesheetEntryDao teDao;
	TimesheetEntry currentEntry;
	Label message;
	ArrayList<String> skillsSelect;
	Boolean isNew;
	DatePicker startDatePicker;
	
    public VolunteerEditHoursView(TimesheetEntry inputEntry, Boolean in)
	{
    	teDao = new TimesheetEntryDao();
    	isNew = in;
    	// create form for page
    	form1 = new Form("form1");
    	
    	//currentEntry = new TimesheetEntry();
    	currentEntry = inputEntry;
    	
    	//set model of form
		form1.setModel(new Model(currentEntry));
    
		
		// add text boxes to form
		form1.add(event = new TextField<String>("EventName",new PropertyModel(currentEntry, "EventName")));
		form1.add(partner = new TextField<String>("Partner",new PropertyModel(currentEntry, "EventName")));
		form1.add(date = new TextField<String>("DateTime",new PropertyModel(currentEntry, "DateTime")));
		form1.add(hours = new TextField<String>("HoursLogged",new PropertyModel(currentEntry,"HoursLogged" )));
		
		if (!isNew)
		{
			event.setEnabled(false);
			partner.setEnabled(false);
			date.setEnabled(false);
		}
		else
		{
			startDatePicker = new DatePicker(){
	        	
	        	protected String getAdditionalJavascript()
	        	{
	        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
	        	}
	        }; 
	        startDatePicker.setShowOnFieldClick(true);
	        date.add(startDatePicker);
		}
		
		// save button
		form1.add(new Button("save") {
			@Override
			public void onSubmit() {
				if (isNew) // insert new entry
				{
					teDao.insert(currentEntry);
				}
				else // update entry
				{
					teDao.update(currentEntry);
				}
			this.setResponsePage(VolunteerHourView.class);
			}
		});
		
		// cancel button
		form1.add(new Button("cancel") {
			@Override
			public void onSubmit() {
			this.setResponsePage(VolunteerHourView.class);
			}
		});
		
		// add textfields and textfield buttons
		add(form1);
		
		
	}
}
