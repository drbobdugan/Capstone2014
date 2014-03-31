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
	private ArrayList<Event> futureEvents,pastEvents;  // list of events specific to the partner
	
	public PartnerEventView()
	{
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		// TODO get current user -> 
		User theUser = new User("testEmail", "pass", "adsf", "asdf", "asdf", "aadsf", "asdf", "asdf", "af", "", "aasf", false, false, false, false);
	    EventDao dao = new EventDao();
	    
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
		 ArrayList list = new ArrayList();
		 Skill [] skills = null;
		 list.add(new Event("email@place.com","Petting Puppies",  new Date(),"petting the puppoes", "Animal Shelter", 5, 3, skills));
			     
		final DataView dataView = new DataView("simple", new ListDataProvider(list)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
			}
		};
		
		dataView.setItemsPerPage(10);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
		
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