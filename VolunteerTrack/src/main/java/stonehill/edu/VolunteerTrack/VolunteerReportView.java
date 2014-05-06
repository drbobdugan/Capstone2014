package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.Form;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;

public class VolunteerReportView extends VolunteerTrackBaseView {
	
	//check group
		private static CheckGroup<Event> group; 
		//radio button choices
		private static final List<String> TYPES = Arrays
				.asList(new String [] { "By Day", "By Week","By Month", "By Year", "By End Date"});
		//default selected 
		private String selected ="By Week";
		private static final long serialVersionUID = 1L;
		
		//stuff for setting up calendars
		 private final Date startDate = new Date();
		 private final Date endDate = new Date();
		 
		 TimesheetEntryDao timeDao = new TimesheetEntryDao();
	    EventDao eventDao= new EventDao();
		ArrayList<Event> events;
		
		public VolunteerReportView()
		{
			RadioChoice<String>  reportBy = new RadioChoice<String>(
					"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
			
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
	        startDateTextField.add(startDatePicker);
	        endDateTextField.add(endDatePicker); 
	        User currentuser= CustomSession.get().getUser();
	        
	        //create table of events current user participated in
	        Event selectedEvent=new Event();
	        ArrayList<Object> temp = eventDao.getAllEventsByOwner(currentuser);
	        ArrayList<Event> e = new ArrayList<Event>();
	        
	        for(int i =0; i<temp.size(); i++)
	        {
	        	e.add(((Event)temp.get(i)));
	        }
	        
	         group = new CheckGroup<Event>("group", new ArrayList<Event>());
	       
	        ListView events = new ListView("events", e){
	        	protected void populateItem(ListItem item){
	        		item.add(new Check("checkbox", item.getModel()));
	        		item.add(new Label("eventName", new PropertyModel(item.getModel(),"name")));
	        	}
	        };
	        
			Form<?> form = new Form<Void>("form"){
				@Override
				protected void onSubmit(){
					//info("Report By :" + selected);
					ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
					String test="";
					for(int i =0; i<selectedEvents.size(); i++){
						test = test + selectedEvents.get(i).getName() + " ";
					}
					
					setResponsePage(new PartnerReportResultsView(selectedEvents));
					//info("selected event(s): " + test);
				}
			};
	        
	        events.setReuseItems(true);
	        group.add(events);
	        
	        //get all time sheets asscoicated with current user
	        TimesheetEntry selectedTimeSheetEntry= new TimesheetEntry();
	        ArrayList<Object> tempTimeSheets = timeDao.getAllTimesheetEntriesByUser(currentuser);
	        ArrayList<TimesheetEntry> t = new ArrayList<TimesheetEntry>();
	        
	        form.add(group);
			form.add(startDateTextField);
			form.add(endDateTextField);
			form.add(reportBy);
			add(form);
	        
	        
	        //
		}

}
