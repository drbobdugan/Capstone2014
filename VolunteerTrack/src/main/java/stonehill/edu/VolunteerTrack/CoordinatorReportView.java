package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
//html and date picker 
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.*;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Form;
//table imports

import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;



public class CoordinatorReportView extends VolunteerTrackBaseView {

	

	//radio button choices
	private static final List<String> TYPES = Arrays
			.asList(new String [] { "By Day", "By Week","By Month", "By Year", "By End Date"});
	//default selected option
	private String selected ="By Week";
	
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
    EventDao eventDao= new EventDao();
	ArrayList<Event> events;
	
	public CoordinatorReportView() {
	
		//choose time interval
		RadioChoice<String> reportBy=new RadioChoice<String>(
				"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		
	    //coordinator can choose from searching by volunteer
		RadioChoice<String> userType=new RadioChoice<String>(
				"userType",new PropertyModel<String>(this,"userSelected"),TYPE2);	
		//choose summary or detailed report
	RadioChoice<String> sumOrDetails=new RadioChoice<String>(
		   "sumOrDetails",new PropertyModel<String>(this,"reportTypeSelected"),TYPE3);
	
		DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
        DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));
		
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
        
        startDatePicker.setShowOnFieldClick(true);
        startDateTextField.add(startDatePicker);
        endDateTextField.add(endDatePicker); 
        
    	form.add(startDateTextField);
		form.add(endDateTextField);
		form.add(reportBy); 
		form.add(userType);//
		form.add(sumOrDetails);
		form.add(reports); //add button
		add(form);
	}   
         
		Form<?> form = new Form<Void>("form"){
			@Override
			protected void onSubmit(){
				info("Report By :" + selected);
			}
		};
		Button reports =new Button("Reports") {
			@Override
			public void onSubmit() {
			
			//if events all is selected then get them from the dao
			EventDao eventdao=new EventDao();
			ArrayList<Object> allEvents=eventdao.selectAll();
			
			//need all users to see who did what
			//get all volunteers from userDao
			UserDao userdao=new UserDao();
			ArrayList<Object> volunteers=userdao.getAllVolunteers();
			
			//TimesheetEntry timesheet=new TimesheetEntry();
			TimesheetEntryDao timedao=new TimesheetEntryDao();
			//conditions for userSelected = volunteer
			if(userSelected.equals("Volunteer")) {
				
				if(reportTypeSelected.equals("Summary")) {
					for(int i=0;i<volunteers.size();i++) {
						User user=(User)volunteers.get(i);
			ArrayList<Object>times=	timedao.getAllTimesheetEntriesByUser((User)volunteers.get(i));
			for(int j=0; j<times.size(); j++) {
				TimesheetEntry timesheet= ((TimesheetEntry)times.get(j));
				//gets all volunteer hours per volunteers
				totalVolunteerHours=totalVolunteerHours+timesheet.getHoursLogged();
			}
			//keep track of total hours between all  volunteers
					totalHours=totalHours+totalVolunteerHours;
					
					RepeatingView repeating = new RepeatingView("repeating");
			        add(repeating);
			        
			        final int x=1;
		            AbstractItem item = new AbstractItem(repeating.newChildId());

		            repeating.add(item);
		           
		            item.add(new Label("name", user.getFirstName() + " "  +user.getLastName()));
		            item.add(new Label("major",user.getMajor()));
		            item.add(new Label("Hours Worked", ""+totalVolunteerHours));
		            item.add(new Label("date" , new Date().toString()));
		            
		            final int idx = i;
		            item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
		            {
		                private static final long serialVersionUID = 1L;

		                @Override
		                public String getObject()
		                {
		                    return (idx % 2 == 1) ? "even" : "odd";
		                }
		            }));
		            
					}
				}
				
				else if(reportTypeSelected.equals("Details")) {
					//more detailed reports
					//make HTML more dynamic
				}		
			}
			
			else if(userSelected.equals("Partner")) {
			//get all partners
				ArrayList<Object> partners=userdao.getAllPartners();
				if(reportTypeSelected.equals("Summary")) {
					for(int i=0;i<partners.size();i++) {
						//for each partner
						//get the events they own
						ArrayList<Object>events=eventdao.getAllEventsByOwner((User)partners.get(i));
						int eventNum=events.size(); //list how many events theyve had in given time period
						
					}
					//do stuff in here
				}
				
				else if(reportTypeSelected.equals("Details")) {	
					//more detailed reports here
				}
			}
			 
			User user=new User();
			Event event= new Event(); //
		
			}
		};

}
