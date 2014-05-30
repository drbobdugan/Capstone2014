package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
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
import org.apache.wicket.model.AbstractReadOnlyModel;
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
	private ArrayList<User> volunteerList;
	private int aplicantid;

	public PartnerEventDetailsView(Event event, String returnTo)
	{
		this.event = event;
		this.returnTo = returnTo;

		EventDao eD = new EventDao();
		volunteerList = eD.getUsersSignedUpForEvent(event);		
		populateItems();
	}  

	@SuppressWarnings("deprecation")
	private void populateItems() {

		// create elements and add them to the form
		add(new TextField("eventName", new Model(event.getName())));
		add(new TextField("location", new Model(event.getLocation())));
		add(new TextArea("description", new Model(event.getDescription())));
		add(new TextField("positions", new Model(event.getNumPositions())));
		add(new TextField("positionsLeft", new Model(event.getNumPositionsRemaining())));

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
		add(new TextField("startDate", new Model(dateFormat.format(event.getStartDateTime()))));
		add(new TextField("endDate", new Model(dateFormat.format(event.getEndDateTime()))));


		dateFormat = new SimpleDateFormat("h:mm a");
		add(new TextField("timeField1",new Model(dateFormat.format(event.getStartDateTime()))));
		add(new TextField("timeField2",new Model(dateFormat.format(event.getEndDateTime()))));

		//  create the form 
		form = new Form("form");
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
			protected void populateItem(final Item item) {
				final User user = (User)item.getModelObject();
				item.add(new Link<Void>("linkTo"){ public void onClick(){ linkTo(user);}});
				item.add(new Label("nameFirst", user.getFirstName()));
				item.add(new Label("nameLast", user.getLastName()));
				item.add(new Label("userEmail", user.getEmail()));

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

		// add the form to the page
		add(form);
	}
	private void linkTo(User user ){
		setResponsePage(new SearchVolunteerProfileView(user));
	}
}