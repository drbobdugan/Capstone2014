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

public class PartnerEventDetailsView extends VolunteerTrackBaseView
{
	private Event event;  // list of events specific to the partner
	private TextField name, location, position;
	private TextArea description;
	private Form form;
	private String returnTo;
	public PartnerEventDetailsView(Event event1, String returnTo)
	{
		event = event1;
		this.returnTo = returnTo;
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
		  form.add(new Button("save"){  
			  @Override
				public void onSubmit() {
				  if(returnTo.equals("partnerHomeView"))
					  setResponsePage(PartnerHomeView.class);
				  else if(returnTo.equals("partnerEventView"))
				    setResponsePage(PartnerEventView.class);
				  else if(returnTo.equals("volunteerHomeView")){
					    setResponsePage(VolunteerHomeView.class);
				  }
				}
			});

		  // add the form to the page
		  add(form);
			   
	}

}