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

public class PartnerEventView extends VoltrackPage 
{
	private ArrayList futureEvents,pastEvents;  // list of events specific to the partner
	
	public PartnerEventView()
	{
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		// TODO get current user -> 
		User theUser = new User("testEmail", "pass", "adsf", "asdf", "asdf", "aadsf", "asdf", "asdf", "af", "", "aasf", false, false, false, false);
	    EventDao dao = new EventDao();
	    
	    
	    Event e = new Event("joe@yahoo.com", "Event Name 1", new Date(), "des", "loc", 6, 4, new Skill[0]);
		dao.insert(e);
		e = new Event("zac@stonehill.edu", "Event Name 2", new Date(), "des", "loc", 3, 1, new Skill[0]);
		dao.insert(e);
	    e = new Event("billy@bob.com", "Event Name 3", new Date(), "des", "loc", 4, 2, new Skill[0]);
		dao.insert(e);
		
		
		futureEvents = dao.selectAll();
		//System.out.println(ed.selectAll().size());
		
	//    ed.delete(e);
	    
	    
	    /* TODO must test dao first 
	    ArrayList<Object> events = (ArrayList<Object>) dao.getAllEventsByPartener(theUser);
	    for(Object o: events){
	    	if(((Event)o).getDate().after(new Date())){
	    		futureEvents.add((Event)o);
	    	} else {
	    		pastEvents.add((Event)o);
	    	}
	    }
	    */
	    populateTables();
	}  
	
	 private void populateTables() {
	
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
			}
		};
		

		final DataView dataView2 = new DataView("simple2", new ListDataProvider(pastEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Label("name2", event.getName()));
				item.add(new Label("location2", event.getLocation()));
				item.add(new Label("date2", event.getDate().toString()));
				item.add(new Label("positions2", event.getNumPositions()));
				item.add(new Label("available2", event.getNumPositionsRemaining()));
			}
		};
		
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
		
		dataView2.setItemsPerPage(5);
		add(dataView2);
		add(new PagingNavigator("navigator2", dataView2));
		
	}

	// view an event
    public void viewEvent(Event event){
   	 // TODO redireect 
    }
    
    // create a new event
    public void createNewEvent(){
   	 //TODO redirect
    }
    
    // edit an event
    public void editEvent(Event event){
   	 //TODO redirect
    }
    
    // delete an event
    public void deleteEvent(Event event){
   	 // TODO redirect
    }
}