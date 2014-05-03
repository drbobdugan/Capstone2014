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
	private ArrayList futureEvents, eventAplicaions;  // list of events specific to the partner
	private User currentUser;        // User that is acessing the page
	private int eventid, aplicantid;
	public PartnerHomeView(){
		futureEvents = new ArrayList<Event>(0);  // make emtpy array List to fill
		currentUser = CustomSession.get().getUser();
		EventDao dao = new 	EventDao();
		eventid = 0;
		aplicantid = 0;
	    ArrayList<Object> allEvents = dao.getAllEventsByOwner(currentUser);
	    for(Object o: allEvents)
	    	if(((Event)o).getCreatedDateTime().after(new Date()))  // if the date of the event is in the future add it to the list
	    		futureEvents.add((Event)o);
	    eventAplicaions = new ArrayList();
	    ArrayList temp = dao.getAllPendingAplicantsByPartner(currentUser);
	    for(int i = 0; i < temp.size(); i+=2)
	    	eventAplicaions.add(new AppEntry((Event)temp.get(i),(User)temp.get(i+1)));
	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("view"){ public void onClick(){ viewEvent(eventid++);}});
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getCreatedDateTime().toString()));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
			}
		};
			
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));	
		
		final DataView dataView2 = new DataView("simple2", new ListDataProvider(eventAplicaions)) {
			protected void populateItem(Item item) {
				final AppEntry aE = (AppEntry)item.getModelObject();
				//item.add(new Link<Void>("aprove"){ public void onClick(){ aprove(aplicantid);}});
				//item.add(new Link<Void>("deny"){ public void onClick(){ deny(aplicantid++);}});
				item.add(new Link<Void>("linkTo"){ public void onClick(){ linkTo(aplicantid++);}});
				item.add(new Label("name2", aE.getUser().getFirstName()+ " " + aE.getUser().getLastName()));
				item.add(new Label("eventName", aE.getEvent().getName()));
				item.add(new Label("date2",  aE.getEvent().getCreatedDateTime()));
				item.add(new Label("available2", aE.getEvent().getNumPositionsRemaining()));
			}
		};
			
		dataView.setItemsPerPage(5);
		add(dataView2);
		add(new PagingNavigator("navigator2", dataView2));	
	}
	 private void linkTo(int i ){
		 User toLinkTo = ((AppEntry)eventAplicaions.get(i)).getUser();
		 // setResponsePage(new ______________(toLinkTO, "partnerHomeView"));
	 }
	    public void deny(int i){
	    	Event eventToView = (Event) futureEvents.get(i);
	    	setResponsePage(new PartnerEventDetailsView(eventToView, "partnerHomeView"));
	    }
	    public void aprove(int i){
	    	Event eventToView = (Event) futureEvents.get(i);
	    	setResponsePage(new PartnerEventDetailsView(eventToView, "partnerHomeView"));
	    }
	// view an event
    public void viewEvent(int i){
    	Event eventToView = (Event) futureEvents.get(i);
    	setResponsePage(new PartnerEventDetailsView(eventToView, "partnerHomeView"));
    }
}