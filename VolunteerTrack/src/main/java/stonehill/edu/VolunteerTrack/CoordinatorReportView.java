package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
//table imports




//package stonehill.edu.VolunteerTrack;



import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.Button;



public class CoordinatorReportView extends VolunteerTrackBaseView {

	

	//radio button choices
//	private static final List<String> TYPES = Arrays
	//		.asList(new String [] { "By Day", "By Week","By Month", "By Year", "By End Date"});
	//default selected option
	//private String selected ="By Week";
	
	private static final List<String> TYPE2 = Arrays .asList(new String[] {"Volunteer","Partner"});
	private String userSelected="Volunteer";
	
	private static final List<String> TYPE3 = Arrays .asList(new String[] {"Summary", "Details"});
	private String reportTypeSelected="Summary";
	private static final long serialVersionUID = 1L;
	
	//stuff for setting up calendars
	 private final Date startDate = new Date();
	 private final Date endDate = new Date();
	 private int totalHours=0;
	 private int totalVolunteerHours=0;
	 Button reports;
    EventDao eventDao= new EventDao();
	ArrayList<Event> events;
	Form<?>form;
	ArrayList<Integer>hours;
	public CoordinatorReportView() {
		hours=new ArrayList<Integer>();  
				form = new Form<Void>("form"){
					@Override
					protected void onSubmit(){
				//		info("Report By :" + selected);
					}
				};			
		//choose time interval
	//	RadioChoice<String> reportBy=new RadioChoice<String>(
	//			"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		
	    //coordinator can choose from searching by volunteer
		RadioChoice<String> userType=new RadioChoice<String>(
				"userType",new PropertyModel<String>(this,"userSelected"),TYPE2);	
		//choose summary or detailed report
	RadioChoice<String> sumOrDetails=new RadioChoice<String>(
		   "sumOrDetails",new PropertyModel<String>(this,"reportTypeSelected"),TYPE3);
	
	final	DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
     final   DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));
		
        //start date choice
        DatePicker startDatePicker = new DatePicker(){
        	
        	protected String getAdditionalJavascript()
        	{
        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
        	}
        }; 
       //end date choice 
       DatePicker endDatePicker = new DatePicker(){
        	
        	protected String getAdditionalJavascript()
        	{
        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
        	}
        }; 
   

     
      final  Date end=endDateTextField.getModelObject();
		reports =new Button("reports") {
			@Override
			public void onSubmit() {
				final  Date start=startDateTextField.getModelObject();
				final  Date end=endDateTextField.getModelObject();
	
			EventDao eventdao=new EventDao();
			ArrayList<Object> allEvents=eventdao.selectAll();
	//eventdao.getAllEventsByUserTimeSheetEntries(user);
	     
			UserDao userdao=new UserDao();
			ArrayList<Object> volunteers=userdao.getAllVolunteers();
			ArrayList<Integer>eventNum=new ArrayList<Integer>();
			ArrayList<TimesheetEntry>mostRecentEvent=new ArrayList<TimesheetEntry>();
			TimesheetEntryDao timedao=new TimesheetEntryDao();

			TimesheetEntry timesheet=new TimesheetEntry();	
			if(userSelected.equals("Volunteer")) {	
				if(reportTypeSelected.equals("Summary")) {
					 
					for(int i=0;i<volunteers.size();i++) {
						User user=(User)volunteers.get(i);
			
			ArrayList<TimesheetEntry>times=	timedao.getTimesheetEntriesByUser((User)volunteers.get(i));
			for(int j=0; j<times.size(); j++) {
				timesheet= ((TimesheetEntry)times.get(j));
				//gets all volunteer hours per volunteers
				totalVolunteerHours=totalVolunteerHours+timesheet.getHoursLogged();
			}
			hours.add(totalVolunteerHours); 
			
		
	
			totalHours=totalHours+totalVolunteerHours;
				}
					this.setResponsePage(new CoordinatorReportsResultsView(volunteers, hours, new Date(), totalHours));
					
					
				}
				
				else if(reportTypeSelected.equals("Details")) {
					for(int i=0; i<volunteers.size(); i++) {
						User user=(User)volunteers.get(i);
						ArrayList<TimesheetEntry>times=	timedao.getTimesheetEntriesByUser((User)volunteers.get(i));
						for(int j=0; j<times.size(); j++) {
							timesheet= ((TimesheetEntry)times.get(j));
						//gets all volunteer hours per volunteers
					totalVolunteerHours=totalVolunteerHours+timesheet.getHoursLogged();
					
						}
						hours.add(totalVolunteerHours);
					    if(times.size()>0) {
				        eventNum.add(times.size()-1);
				        mostRecentEvent.add((TimesheetEntry)times.get(times.size()-1));
				        }
					    else {
					    eventNum.add(0);
					    mostRecentEvent.add(new TimesheetEntry());
					    }
						totalHours=totalHours+totalVolunteerHours;
				       
					}
					
					//more detailed reports
					//make HTML more dynamic
				
				
				
				this.setResponsePage(new CoordinatorReportsResultsView (volunteers, hours, new Date(), totalHours,eventNum,mostRecentEvent));
			}
			}	
			
			else if(userSelected.equals("Partner")) {
			//get all partners\
				ArrayList<Object>events=eventdao.selectAll();
				//ArrayList<Integer>volunteerNum=new ArrayList<Integer>();
			//	ArrayList<Object>filteredEvents=new ArrayList<Object>();
				//ArrayList<Object> partners=userdao.getAllPartners();
				if(reportTypeSelected.equals("Summary")) {
					
					 
					ArrayList<Object>filteredEvents=new ArrayList<Object>();
					for(int i=0;i<events.size();i++) {
						Event event=(Event)events.get(i);
					//	if(selected.equals("By Day")) {
					 if((event.getStartDateTime().compareTo(start)>=0 ) && (event.getStartDateTime().compareTo(end)<=0)) {
						filteredEvents.add(event);
					 }
					 else
					 {
						 events.remove(events.get(i));
					 }	
					this.setResponsePage(new CoordinatorReportsResultsView (filteredEvents));
			    }
					
				
		      this.setResponsePage(new CoordinatorReportsResultsView(events));
					}
				
				
				
				else if(reportTypeSelected.equals("Details")) {	
					ArrayList<Object>filteredEvents=new ArrayList<Object>();
						for(int i=0;i<events.size();i++) {
							Event event=(Event)events.get(i);
						//	if(selected.equals("By Day")) {
						 if((event.getStartDateTime().compareTo(start)>=0 ) && (event.getStartDateTime().compareTo(end)<=0)) {
							filteredEvents.add(event);
						 }
						 else
						 {
							events.remove(events.get(i));
						 }	
						this.setResponsePage(new CoordinatorReportsResultsView (filteredEvents, "D"));
				    }
				}
		//	}
		}
			
			 
			User user=new User();
			Event event= new Event(); //
		
			}
					
		};

		startDatePicker.setShowOnFieldClick(true);
        startDateTextField.add(startDatePicker);
        endDateTextField.add(endDatePicker); 
        
    	form.add(startDateTextField);
		form.add(endDateTextField);
	//	form.add(reportBy); 
		form.add(userType);//
		form.add(sumOrDetails);
		form.add(reports); //add button
		add(form);
}
}
