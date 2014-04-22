package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class VolunteerHomeView extends VolunteerTrackBaseView 
{
	
	public class HoursWorked implements Serializable 
	{
		String name;
		int hours;
		
		public HoursWorked()
		{
			name = "";
			hours = 0;
		}
		
		public HoursWorked(String n, int h)
		{
			name = n;
			hours = h;
		}
		public void setHours(int h)
		{
			hours = h;
		}
		public String getName()
		{
			return name;
		}
		public int getHours()
		{
			return hours;
		}
	}
	
	private ArrayList futureEvents,pastEvents, userHours;  // list of events specific to the partner
	Label message;
	private int idFuture;
	
	public VolunteerHomeView()
	{
		User currentUser = new User("kholmander@stonehill.edu","gunslinger", "keith", "holmander", "", "", "", "", "", "", "", false, false, true, true, "", "");
		
		idFuture = 0;
		
		
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		
		
		UserDao userDao = new UserDao();
		
		EventDao eventDao = new EventDao();
		
		TimesheetEntryDao teDao = new TimesheetEntryDao();
		
	    ArrayList<Object> userTimesheetEntry = teDao.getAllTimesheetEntriesByUser(currentUser);
	    
	    ArrayList<Object> userEvents2 = eventDao.getAllEventsByUserTimeSheetEntries(currentUser);
	    
	    ArrayList<Object> userEvents = eventDao.getAllEventsByUserTimeSheetEntries(currentUser);
	    
	    
	   
	    
	    message = new Label("message", "testing " + ((Event)userEvents2.get(0)).getOwnerEmail() + " : " + ((Event)userEvents2.get(1)).getOwnerEmail());
	    add(message);
	    
	    for(Object o: userEvents){
	    	if(((Event)o).getDate().after(new Date())){
	    		futureEvents.add((Event)o);
	    	} 
	    	else {
	    		pastEvents.add((Event)o);
	    	}
	    }
	    //futureEvents = userEvents;
		
	    userHours = new ArrayList<HoursWorked>();
	    
	    
	    for (int i = 0; i < pastEvents.size(); i++)
	    {
	    	String eventPartner = ((Event)pastEvents.get(i)).getOwnerEmail();
	    	int check = 0;
	    	for (int n = 0; n < userHours.size(); n++) // go thru all already made hoursworked objects to add to existing
	    	{
	    		if (((HoursWorked)userHours.get(n)).getName().trim().compareToIgnoreCase(eventPartner.trim()) == 0)
	    		{
	    			check = n + 1;
	    			n = userHours.size();
	    		}
	    	}
	    	if (check == 0)
	    	{
	    		HoursWorked hw = new HoursWorked(((Event)pastEvents.get(i)).getOwnerEmail(), 
	    				((TimesheetEntry)userTimesheetEntry.get(i)).getHoursLogged());
	    		userHours.add(hw);
	    	}
	    	else // check != 0 which means the hoursworked object for that partner exists
	    	{
	    		int x = ((HoursWorked)userHours.get(check-1)).hours + ((TimesheetEntry)userTimesheetEntry.get(i)).getHoursLogged();
	    		((HoursWorked)userHours.get(check-1)).setHours(x);
	    	}
	    }
	    
	    int totalHours = 0;
	    
	    for (int i = 0; i < userHours.size(); i++)
	    {
	    	totalHours = totalHours + ((HoursWorked)userHours.get(i)).getHours();
	    }
	    
	    
	    HoursWorked last = new HoursWorked("Total Hours", totalHours);
	    
	    userHours.add(last);
	    
	    
	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("view"){ public void onClick(){ viewEventFuture(idFuture++);}});
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("partner", event.getOwnerEmail()));
			}
		};
		final DataView dataView2 = new DataView("simple2", new ListDataProvider(userHours)) {
			protected void populateItem(Item item) {
				final HoursWorked hw = (HoursWorked)item.getModelObject();
				item.add(new Label("name", hw.getName()));
				item.add(new Label("hours", hw.getHours()));
			}
		};
		dataView.setItemsPerPage(5);
		
		add(dataView);
		add(dataView2);
		
		add(new PagingNavigator("navigator", dataView));
		add(new PagingNavigator("navigator2", dataView));
	}

	// view an event
	    public void viewEvent(Event e){
	    	setResponsePage(new PartnerEventDetailsView(e, "volunteerHomeView"));
	    }
    
    public void viewEventFuture(int i ){
		if(i < futureEvents.size())
		     viewEvent((Event)futureEvents.get(i));
	}
    
}