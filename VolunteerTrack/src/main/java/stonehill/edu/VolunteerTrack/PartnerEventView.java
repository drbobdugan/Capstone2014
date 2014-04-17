/*
 * PartnerEventView: This page is the view seen by a partner when the event tab is clicked.
 *          This page contains two tables that shows both upcoming and past events. Each  
 *          event is paired with Edit, Delete, and View Buttons for event CRUD. Also,
 *          the upcoming event table contains a New button to create a new event. 
 */

package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;


public class PartnerEventView extends VolunteerTrackBaseView
{
	private static final long serialVersionUID = 1L;
	private ArrayList futureEvents,pastEvents;  // list of events specific to the partner
    private int idFuture, idPast;
	public PartnerEventView()
	{
		futureEvents = new ArrayList<Event>(0);
		pastEvents = new ArrayList<Event>(0);
		 
		idFuture = 0;
		idPast = 0;
		
		EventDao dao = new EventDao();
		
		// Get all events and split up the events by future and past events
	    ArrayList<Object> events = dao.getAllEventsByOwner(	 CustomSession.get().getUser());
	    for(Object o: events)
	    	if(((Event)o).getDate().after(new Date()))
	    		futureEvents.add((Event)o);
	    	else 
	    		pastEvents.add((Event)o);
	
	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("delete"){ public void onClick(){ deleteEventFuture(idFuture);}});
				item.add(new Link<Void>("edit"){ public void onClick(){ editEventFuture(idFuture);}});
				item.add(new Link<Void>("view"){ public void onClick(){ viewEventFuture(idFuture++);}});
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
				item.add(new Link<Void>("delete"){ public void onClick(){ deleteEventPast(idPast);}});
				item.add(new Link<Void>("edit"){ public void onClick(){ editEventPast(idPast);}});
				item.add(new Link<Void>("view"){ public void onClick(){ viewEventPast(idPast++);}});
				item.add(new Label("name2", event.getName()));
				item.add(new Label("location2", event.getLocation()));
				item.add(new Label("date2", event.getDate().toString()));
				item.add(new Label("positions2", event.getNumPositions()));
				item.add(new Label("available2", event.getNumPositionsRemaining()));
			}
		};
		
		add(new Link<Void>("new"){ public void onClick(){ createNewEvent();}});
		
		dataView.setItemsPerPage(5);
		dataView2.setItemsPerPage(5);
		
		add(dataView);
		add(dataView2);
		
		add(new PagingNavigator("navigator", dataView));	
		add(new PagingNavigator("navigator2", dataView2));
	}

	// create a new event
	public void createNewEvent(){
	    setResponsePage(PartnerCreateEventView.class);
	}
	
	public void viewEventFuture(int i ){
		if(i < futureEvents.size())
		     viewEvent((Event)futureEvents.get(i));
	}
	public void viewEventPast(int i ){
		if(i < pastEvents.size())
		     viewEvent((Event)pastEvents.get(i));
	}
	public void editEventFuture(int i ){
		if(i < futureEvents.size())
		     editEvent((Event)futureEvents.get(i));
	}
	public void editEventPast(int i ){
		if(i < pastEvents.size())
		    editEvent((Event)pastEvents.get(i));
	}
	public void deleteEventFuture(int i ){
		if(i < futureEvents.size())
		     deleteEvent((Event)futureEvents.get(i));
	}
	public void deleteEventPast(int i ){
		if(i < pastEvents.size())
		     deleteEvent((Event)pastEvents.get(i));
	}
	    
	// view an event
    public void viewEvent(Event e){
   	 // TODO redireect 
    	//setResponsePage(PartnerEventDetailsView.class);
    }
    
    // edit an event
    public void editEvent(Event e){
   	 //TODO redirect
    	//setResponsePage(PartnerEventEditView.class);
    }
    
    // delete an event
    public void deleteEvent(Event e){
    	EventDao eventDao = new EventDao();
    	eventDao.delete(e);
    	setResponsePage(PartnerEventView.class);
    }
}