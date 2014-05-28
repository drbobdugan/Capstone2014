package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
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

public class PartnerEditEventView extends VolunteerTrackBaseView
{
	private Event theEvent;  // list of events specific to the partner
	private TextField name, location, position;
	private TextArea description;
	private Form form;
	private DateTextField date;
	private  DatePicker datePicker;
	private TimeField time1, time2;
	public PartnerEditEventView(Event e)
	{
		theEvent = e;
	    populateItems();
	}  
	
	 private void populateItems() {
		  // create the event to insert
		  //  create the form 
		  form = new Form("form");
		  // create elements and add them to the form
		  form.add(name = new TextField("eventName", new PropertyModel(theEvent, "name")));
		  form.add(location = new TextField("location", new PropertyModel(theEvent, "location")));
		  
		  form.add(time1 = new TimeField("timeField1", new PropertyModel(theEvent, "time1")));
		  form.add(time2 = new TimeField("timeField2", new PropertyModel(theEvent, "time2")));
		  
		  date = new DateTextField("date", new PropertyModel<Date>(theEvent, "createdDateTime"));
	      datePicker = new DatePicker(){
	        	protected String getAdditionalJavascript()
	        	{
	        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
	        	}
	        }; 
		  
	        datePicker.setShowOnFieldClick(true);
	        date.add(datePicker);
	        form.add(date);
	        
		  form.add(description = new TextArea("description", new PropertyModel(theEvent, "description")));
		  form.add(position = new TextField<Integer>("positions", new PropertyModel(theEvent, "numPositions")));
		  form.add(new Button("save"){  
			  @Override
				public void onSubmit() {
				  boolean success = true;
				  //try{
					  EventDao eD = new EventDao();
					  eD.update(theEvent); 
					  System.out.println("Updating event name with: "+ theEvent.getName());
				//  } catch(Exception e){
				//	  success = false;
				//  }
				  if(success)
				    setResponsePage(PartnerEventView.class);
				}
			});
		  form.add(new Link<Void>("cancel"){ 
				public void onClick(){ 
					setResponsePage(PartnerEventView.class); 
				}});
		  // add the form to the page
		  add(form);
			   
	}
/*	 private void saveEvent(){		     
		 Event theEvent = new Event( , name.toString(), new Date(), description.toString(), 
				 location.toString(), 2, 2, new Skill[0]);
	     EventDao eventDao = new EventDao();
	     eventDao.insert(theEvent);
	     setResponsePage(PartnerEventView.class); 
	 }*/
}