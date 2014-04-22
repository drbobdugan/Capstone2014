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

public class PartnerHomeView extends VolunteerTrackBaseView{
	private ArrayList futureEvents;  // list of events specific to the partner
	private User currentUser;        // User that is acessing the page
	private int eventid;
	public PartnerHomeView(){
		futureEvents = new ArrayList<Event>(0);  // make emtpy array List to fill
		currentUser = CustomSession.get().getUser();
		EventDao dao = new 	EventDao();
		eventid = 0;
	    ArrayList<Object> allEvents = dao.getAllEventsByOwner(currentUser);
	    for(Object o: allEvents)
	    	if(((Event)o).getDate().after(new Date()))  // if the date of the event is in the future add it to the list
	    		futureEvents.add((Event)o);

	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("view"){ public void onClick(){ viewEvent(eventid++);}});
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
			}
		};
			
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));	
	}

	// view an event
    public void viewEvent(int i){
    	Event eventToView = (Event) futureEvents.get(i);
    	setResponsePage(new PartnerEventDetailsView(eventToView, "partnerHomeView"));
    }
}