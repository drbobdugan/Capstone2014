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

public class PartnerCreateEventView extends VolunteerTrackBaseView
{
	private Event newEvent;  // list of events specific to the partner
	private TextField name, location, position;
	private TextArea description;
	private Form form;
	private DateTextField date;
	private  DatePicker datePicker;
	private final Date time1 = new Date();
	private final Date time2 = new Date();
	
	public PartnerCreateEventView()
	{
	    populateItems();
	}  
	
	 private void populateItems() {
		  // create the event to insert
		 newEvent = new Event();  
		 newEvent.setOwnerEmail(CustomSession.get().getUser().getEmail());
		  //  create the form 
		  form = new Form("form");
		  // create elements and add them to the form
		  form.add(name = new TextField("eventName", new PropertyModel(newEvent, "name")));
		  form.add(location = new TextField("location", new PropertyModel(newEvent, "location")));
		  
		  //form.add(location = new TextField("start", new PropertyModel(newEvent, "location")));
		  //form.add(location = new TextField("end", new PropertyModel(newEvent, "location")));
		  
		  date = new DateTextField("date", new PropertyModel<Date>(newEvent, "createdDateTime"));
	      datePicker = new DatePicker(){
	        	protected String getAdditionalJavascript()
	        	{
	        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
	        	}
	        }; 
		  
	        datePicker.setShowOnFieldClick(true);
	        date.add(datePicker);
	        form.add(date);
	        
	        TimeField startTime = new TimeField("timeField1", new PropertyModel<Date>(this, "time1"));
			TimeField endTime = new TimeField("timeField2", new PropertyModel<Date>(this, "time2"));
			
			form.add(startTime);
			form.add(endTime);
	        
		  form.add(description = new TextArea("description", new PropertyModel(newEvent, "description")));
		  form.add(position = new TextField<Integer>("positions", new PropertyModel(newEvent, "numPositions")));
		  form.add(new Button("save"){  
			  @Override
				public void onSubmit() {
				  boolean success = true;
				  //try{
					  EventDao eD = new EventDao();
					  newEvent.setStartDateTime(getStart());
					  newEvent.setEndDateTime(getEnd());
					  newEvent.setNumPositionsRemaining(newEvent.getNumPositions());
					  eD.insert(newEvent); 
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
	 private Date getStart() {
			return time1;
		}
	 private Date getEnd() {
			return time2;
		}
/*	 private void saveEvent(){		     
		 Event newEvent = new Event( , name.toString(), new Date(), description.toString(), 
				 location.toString(), 2, 2, new Skill[0]);
	     EventDao eventDao = new EventDao();
	     eventDao.insert(newEvent);
	     setResponsePage(PartnerEventView.class); 
	 }*/
}