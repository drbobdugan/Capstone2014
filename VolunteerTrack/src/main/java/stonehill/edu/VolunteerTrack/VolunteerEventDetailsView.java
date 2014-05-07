package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class VolunteerEventDetailsView extends VolunteerTrackBaseView
{
	private Event event;  // list of events specific to the partner
	private TextField name, location, position;
	private TextArea description;
	private Form form;
	private String returnTo;
	private ArrayList daoEvents;
	private ArrayList volunteerList;
	private int aplicantid;
	
	public VolunteerEventDetailsView(Event event1, String returnTo,ArrayList daoEvents)
	{
		EventDao eD = new EventDao();
		event = event1;
		this.returnTo = returnTo;
		this.daoEvents = daoEvents;
	    populateItems();
	}  
	
	 private void populateItems() {
		  //  create the form 
		  form = new Form("form");
		  // create elements and add them to the form
		  form.add(new Label("eventName", event.getName()));
		  form.add(new Label("location", event.getLocation()));
		  form.add(new Label("description", event.getDescription()));
		  form.add(new Label("positions", event.getNumPositions()));
		  form.add(new Label("positionsLeft", event.getNumPositionsRemaining()));
		  
		  
		  form.add(new Button("apply"){  
			  @Override
				public void onSubmit() {
				      EventDao ed = new EventDao();
				      Event target = ed.getEventByNameDateTime(event.getName(), event.getCreatedDateTime());
				      if(target == null || target.getNumPositionsRemaining() < 1)
				    	  return;
				      target.setNumPositionsRemaining(target.getNumPositionsRemaining()-1);
				      ed.update(target);
				      ed.insertUserSignsUpForEvent(CustomSession.get().getUser(), event);
				      TimesheetEntryDao tS = new TimesheetEntryDao();
				      tS.insert(new TimesheetEntry(CustomSession.get().getUser().getEmail(), event.getCreatedDateTime(), event.getName(), false, false, 0, CustomSession.get().getUser().getOrganizationName()));
				     
				      
				      if(returnTo.equals("partnerHomeView"))
						  setResponsePage(PartnerHomeView.class);
					  else if(returnTo.equals("partnerEventView"))
					    setResponsePage(PartnerEventView.class);
					  else if(returnTo.equals("volunteerHomeView"))
						    setResponsePage(VolunteerHomeView.class);
					  else if(returnTo.equals("volunteerSearchResultPage"))
						  setResponsePage(new VolunteerSearchResultPage(daoEvents));
				}
			});
		  
		  
		  form.add(new Button("save"){  
			  @Override
				public void onSubmit() {
				  if(returnTo.equals("partnerHomeView"))
					  setResponsePage(PartnerHomeView.class);
				  else if(returnTo.equals("partnerEventView"))
				    setResponsePage(PartnerEventView.class);
				  else if(returnTo.equals("volunteerHomeView"))
					    setResponsePage(VolunteerHomeView.class);
				  else if(returnTo.equals("volunteerSearchResultPage"))
					  setResponsePage(new VolunteerSearchResultPage(daoEvents));
				}
			});

		  
		  // add the form to the page
		  add(form);
	}
	 private void linkTo(int i ){
		 User toLinkTo = (User)volunteerList.get(i);
		 setResponsePage(new SearchVolunteerProfileView(toLinkTo));
	 }
}