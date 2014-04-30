package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.yui.calendar.TimeField;
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
	private final Date time1 = new Date();
	private final Date time2 = new Date();

	public PartnerEventDetailsView(Event event1, String returnTo)
	{
		event = event1;
		this.returnTo = returnTo;
	    populateItems();
	}  
	
	 @SuppressWarnings("deprecation")
	private void populateItems() {
		  //  create the form 
		  form = new Form("form");
		  // create elements and add them to the form
		  form.add(new Label("eventName", event.getName()));
		  form.add(new Label("location", event.getLocation()));
		  form.add(new Label("description", event.getDescription()));
		  form.add(new Label("positions", event.getNumPositions()));
		  form.add(new Label("positionsLeft", event.getNumPositionsRemaining()));
		  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyy");
		  form.add(new Label("date", dateFormat.format(event.getCreatedDateTime())));
		  
		  dateFormat = new SimpleDateFormat("h:mm a");
		  form.add(new Label("timeField1",dateFormat.format(event.getStartDateTime())));
		  form.add(new Label("timeField2",dateFormat.format(event.getEndDateTime())));
		
		  form.add(new Button("save"){  
			  @Override
				public void onSubmit() {
				  if(returnTo.equals("partnerHomeView"))
					  setResponsePage(PartnerHomeView.class);
				  else if(returnTo.equals("partnerEventView"))
				    setResponsePage(PartnerEventView.class);
				  else if(returnTo.equals("volunteerHomeView"))
					    setResponsePage(VolunteerHomeView.class);
				  else if(returnTo.equals("volunteerSearchPage"))
					  setResponsePage(VolunteerSearchView.class);
				}
			});

		  // add the form to the page
		  add(form);
	}

	 // takes in a date object with hours (0-24) and returns a string in the format HH:MM AM/PM
	private String conver24To12Hour(Date date) {
		int _24Hour = date.getHours();
		int min = date.getMinutes();
		String mins = "";
		String cat = "AM";
		if(_24Hour == 0)
			_24Hour = 12;
		else if(_24Hour  > 12){
			_24Hour -= 12;
			cat = "PM";
		}
		if (min < 10)
			mins="0"+min;
		else
			mins = "" + min;
		return _24Hour + ":" + mins + " " + cat;
	}

}