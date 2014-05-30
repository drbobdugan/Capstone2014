/*
 * PartnerEventView: This page is the view seen by a partner when the event tab is clicked.
 *          This page contains two tables that shows both upcoming and past events. Each  
 *          event is paired with Edit, Delete, and View Buttons for event CRUD. Also,
 *          the upcoming event table contains a New button to create a new event. 
 */

package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;


public class PartnerEventView extends VolunteerTrackBaseView
{
	private static final long serialVersionUID = 1L;
	private static final int VIEWBUTTON   = 0;
	private static final int EDITBUTTON   = 1;
	private static final int DELETEBUTTON = 2;
	
	private ArrayList<Event> futureEvents,pastEvents;  // list of events specific to the partner
    private int idFuture, idPast;
	public PartnerEventView()
	{
		futureEvents = new ArrayList<Event>();
		pastEvents = new ArrayList<Event>();
		 
		idFuture = 0;
		idPast = 0;
		
		EventDao dao = new EventDao();
		
		// Get all events and split up the events by future and past events
	    ArrayList<Event> events = dao.getAllEventsByOwner(CustomSession.get().getUser());
	    for(Event event: events)
	    {	
	    	if (event.getStartDateTime().after(new Date()))
	    		futureEvents.add(event);
	    	else 
	    		pastEvents.add(event);
	    }
	    
	    //sort
	    for (int i = 0; i < futureEvents.size(); i++){
	    	for (int j = 0; j < futureEvents.size(); j++){
		    	if(((Event)futureEvents.get(i)).getStartDateTime().before(((Event)futureEvents.get(j)).getStartDateTime())){
		    		Collections.swap(futureEvents, i, j);
		    	}
		    }
	    }
	    
	    //sort
	    for (int i = 0; i < pastEvents.size(); i++){
	    	for (int j = 0; j < pastEvents.size(); j++){
		    	if(((Event)pastEvents.get(i)).getStartDateTime().after(((Event)pastEvents.get(j)).getStartDateTime())){
		    		Collections.swap(pastEvents, i, j);
		    	}
		    }
	    }
	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(final Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("delete"){ public void onClick(){ processEventButton(event.getId(), DELETEBUTTON, true);}});
				item.add(new Link<Void>("edit"){ public void onClick(){ processEventButton(event.getId(), EDITBUTTON, true);}});
				item.add(new Link<Void>("view"){ public void onClick(){ processEventButton(event.getId(), VIEWBUTTON, true);}});
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
				item.add(new Label("date", dateFormat.format(event.getStartDateTime())));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
						{
							 private static final long serialVersionUID = 1L;

							 @Override
							 public String getObject()
							 {
								 return (item.getIndex() % 2 == 1) ? "even" : "odd";
							 }
						}));
			}
		};
		final DataView dataView2 = new DataView("simple2", new ListDataProvider(pastEvents)) {
			protected void populateItem(final Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("delete"){ public void onClick(){ processEventButton(event.getId(), DELETEBUTTON, false);}});
				item.add(new Link<Void>("edit"){ public void onClick(){ processEventButton(event.getId(), EDITBUTTON, false);}});
				item.add(new Link<Void>("view"){ public void onClick(){ processEventButton(event.getId(), VIEWBUTTON, false);}});
				item.add(new Label("name2", event.getName()));
				item.add(new Label("location2", event.getLocation()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
				item.add(new Label("date2", dateFormat.format(event.getStartDateTime())));
				item.add(new Label("positions2", event.getNumPositions()));
				item.add(new Label("available2", event.getNumPositionsRemaining()));
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
						{
							 private static final long serialVersionUID = 1L;

							 @Override
							 public String getObject()
							 {
								 return (item.getIndex() % 2 == 1) ? "even" : "odd";
							 }
						}));
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
	
	public void processEventButton(int eventId, int viewEditDelete, boolean isFuture)
	{
		Event event = null;
		ArrayList<Event> events = null;
		
		// Select future/past events
		if (isFuture)
		{
			events = futureEvents;
		}
		else
		{
			events = pastEvents;
		}
		
		// Find event
		for (int i=0; i < events.size(); i++)
		{
			event = events.get(i);
		
			if (eventId == event.getId() )
			{
				break;
			}
		}
		
		// Process the event
		switch (viewEditDelete)
		{
		case VIEWBUTTON:   
			setResponsePage(new PartnerEventDetailsView(event, "partnerEventView"));   
			break;
		case EDITBUTTON:   
			setResponsePage(new PartnerEditEventView(event));  
			break;
		case DELETEBUTTON: 
			EventDao eventDao = new EventDao();
	    	eventDao.delete(event);
	    	setResponsePage(PartnerEventView.class);
	    	break;
		}
	}
}