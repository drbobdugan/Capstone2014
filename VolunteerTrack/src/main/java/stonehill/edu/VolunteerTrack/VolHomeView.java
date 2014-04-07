package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class VolHomeView extends VoltrackPage 
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
	
	public VolHomeView()
	{
		User currentUser = new User("kholmander@stonehill.edu","gunslinger", "keith", "holmander", "", "", "", "", "", "", "", false, false, true, true);
		
		
		
		
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		
		/*User user = new User("zbrown2@studesnt.stonehill.edu", "csrocks55", "Zac", "Brown", "320 Washington St.", "Easton", "Ma", "02357", "8025952931", "We help", "I help", true, false, false, false);
		User user2 = new User("Tom@animalshelter.com", "csrocks55", "Tom", "Brown", "10 Main St.", "Easton", "Ma", "02357", "5556661234", "We help", "I help", true, false, false, false);
		Date d1 = new Date(114,3,27);
		Date d2 = new Date(114,3,28);
		Skill[] skills = new Skill[0];
		Event e1 = new Event("Tom@animalshelter.com", "Petting Animals", d1, "pet some animals", "Animal Shelter", 1, 1, skills);
		Event e2 = new Event("Tom@animalshelter.com", "Feeding Animals", d1, "feed some animals", "Animal Shelter", 1, 1, skills);*/
		
		UserDao userDao = new UserDao();
		
		EventDao eventDao = new EventDao();
		
		TimesheetEntryDao teDao = new TimesheetEntryDao();
		
	    ArrayList<Object> userTimesheetEntry = teDao.getAllTimesheetEntriesByUser(currentUser);
	    ArrayList<Object> userEvents = new ArrayList<Object>();
	    
	    for (int i = 0; i < userTimesheetEntry.size(); i++)
	    {
	    	Event tempEvent = eventDao.getEventByNameDateTime(((TimesheetEntry)userTimesheetEntry.get(i)).getEventName(), 
	    			((TimesheetEntry)userTimesheetEntry.get(i)).getDateTime());
	    	userEvents.add(tempEvent);
	    }
	    
	    
	    
	    //message = new Label("message", "testing " + userTimesheetEntry.size() + " : " + userEvents.size());
	    
	    
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
	    	String eventPartner = ((Event)pastEvents.get(i)).getPartner().getEmail();
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
	    		HoursWorked hw = new HoursWorked(((Event)pastEvents.get(i)).getPartner().getEmail(), 
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
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("partner", ((User)event.getPartner()).getEmail()));
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
    public void viewEvent(Event event){
   	 // TODO redireect 
    }
    
    
}