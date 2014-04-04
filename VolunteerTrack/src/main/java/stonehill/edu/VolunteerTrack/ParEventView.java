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

public class ParEventView extends VoltrackPage 
{
	private ArrayList futureEvents,pastEvents;  // list of events specific to the partner
	
	public ParEventView()
	{
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		// TODO get current user -> 
		User user = new User("zbrown2@studesnt.stonehill.edu", "csrocks55", "Zac", "Brown", "320 Washington St.", "Easton", "Ma", "02357", "8025952931", "We help", "I help", true, false, false, false);
		EventDao dao = new EventDao();
	    ArrayList<Object> events = dao.selectAllByPartner(user);
	    for(Object o: events){
	    	if(((Event)o).getDate().after(new Date())){
	    		futureEvents.add((Event)o);
	    	} else {
	    		pastEvents.add((Event)o);
	    	}
	    }
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
		dataView2.setItemsPerPage(5);
		
		add(dataView);
		add(dataView2);
		
		add(new PagingNavigator("navigator", dataView));	
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