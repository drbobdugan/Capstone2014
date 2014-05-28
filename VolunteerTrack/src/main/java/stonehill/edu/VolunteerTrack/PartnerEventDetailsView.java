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
	private ArrayList volunteerList;
	private int aplicantid;

	public PartnerEventDetailsView(Event event, String returnTo)
	{
		EventDao eD = new EventDao();
		this.event = event;
		this.returnTo = returnTo;
		volunteerList = new ArrayList(0);
		volunteerList = eD.getUsersSignedUpForEvent(event);		
		aplicantid = 0;
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
		  
		  SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
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

			final DataView dataView = new DataView("simple", new ListDataProvider(volunteerList)) {
				protected void populateItem(Item item) {
					final AppEntry aE = (AppEntry)item.getModelObject();
					final int i = aplicantid++;
					item.add(new Link<Void>("linkTo"){ public void onClick(){ linkTo(i);}});
					item.add(new Label("nameFirst", aE.getUser().getFirstName()));
					item.add(new Label("nameLast", aE.getUser().getLastName()));
				}
			};
				
			dataView.setItemsPerPage(5);
			form.add(dataView);
			form.add(new PagingNavigator("navigator", dataView));	
		  
		  // add the form to the page
		  add(form);
	}
	 private void linkTo(int i ){
		 User toLinkTo = (User)volunteerList.get(i);
		 setResponsePage(new SearchVolunteerProfileView(toLinkTo));
	 }
}