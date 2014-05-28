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

public class PartnerHomeView extends VolunteerTrackBaseView{
	private ArrayList futureEvents, eventVolunteers;  // list of events specific to the partner
	private User currentUser;        // User that is acessing the page
	private int eventid, aplicantid;
	public PartnerHomeView(){
		futureEvents = new ArrayList<Event>(0);  // make emtpy array List to fill
		currentUser = CustomSession.get().getUser();
		EventDao dao = new 	EventDao();
		eventid = 0;
		aplicantid = 0;
		
		// get partner's events
	    ArrayList<Event> allEvents = dao.getAllEventsByOwner(currentUser);
	    
	    // filter out events that have already happened
	    for(Event event: allEvents)
	    	if(event.getStartDateTime().after(new Date()))  
	    		futureEvents.add(event);
	    
	    //sort by event date/time
	    for (int i = 0; i < futureEvents.size(); i++){
	    	for (int j = 0; j < futureEvents.size(); j++){
		    	if(((Event)futureEvents.get(i)).getStartDateTime().before(((Event)futureEvents.get(j)).getStartDateTime())){
		    		Collections.swap(futureEvents, i, j);
		    	}
		    }
	    }
	    
	    
	    eventVolunteers = new ArrayList();
	    ArrayList temp = dao.getAllPendingAplicantsByPartner(currentUser);
	    for(int i = 0; i < temp.size(); i+=2)
	    	eventVolunteers.add(new AppEntry((Event)temp.get(i),(User)temp.get(i+1)));
	    
	    for (int i = 0; i < eventVolunteers.size(); i++){
	    	for (int j = 0; j < eventVolunteers.size(); j++){
		    	if(((Event)((AppEntry)eventVolunteers.get(i)).getEvent()).getStartDateTime().before(((Event)((AppEntry)eventVolunteers.get(j)).getEvent()).getStartDateTime())){
		    		Collections.swap(eventVolunteers, i, j);
		    	}
		    }
	    } 
	    populateTables();
	}  
	
	 private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(futureEvents)) {
			protected void populateItem(final Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Link<Void>("view"){ public void onClick(){ viewEvent(eventid++);}});
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
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
			
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));	
		
		final DataView dataView2 = new DataView("simple2", new ListDataProvider(eventVolunteers)) {
			protected void populateItem(final Item item) {
				final AppEntry aE = (AppEntry)item.getModelObject();
				//item.add(new Link<Void>("aprove"){ public void onClick(){ aprove(aplicantid);}});
				//item.add(new Link<Void>("deny"){ public void onClick(){ deny(aplicantid++);}});
				final int i = aplicantid++;
				item.add(new Link<Void>("linkTo"){ public void onClick(){ linkTo(i);}});
				item.add(new Label("name2", aE.getUser().getFirstName()+ " " + aE.getUser().getLastName()));
				item.add(new Label("eventName", aE.getEvent().getName()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				item.add(new Label("date2",  dateFormat.format(aE.getEvent().getStartDateTime())));
				item.add(new Label("available2", aE.getEvent().getNumPositionsRemaining()));
				
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
			
		dataView2.setItemsPerPage(5);
		add(dataView2);
		add(new PagingNavigator("navigator2", dataView2));	
	}
	 private void linkTo(int i ){
		 User toLinkTo = ((AppEntry)eventVolunteers.get(i)).getUser();
		 setResponsePage(new SearchVolunteerProfileView(toLinkTo));
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