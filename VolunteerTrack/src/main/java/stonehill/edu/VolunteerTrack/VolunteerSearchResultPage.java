package stonehill.edu.VolunteerTrack;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.yui.calendar.TimeField;


public class VolunteerSearchResultPage extends VolunteerTrackBaseView 
{

	ArrayList DaoEvents;
	
	public VolunteerSearchResultPage(ArrayList passList)
	{
		
		Form form2 = new Form("backButton"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};

		Button back=new Button("back") {
			@Override
			public void onSubmit() {
				this.setResponsePage(VolunteerSearchView.class);  
			}
		}; 
		form2.add(back);
		add(form2);
		
		
		
		
		
		DaoEvents = passList;
		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		
		for(int i = 0; i < DaoEvents.size(); i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());
            repeating.add(item); 
            
            int year = ((Event) DaoEvents.get(i)).getStartDateTime().getYear() + 1900;
            DateFormat formatter = new SimpleDateFormat("hh:mm a");
            String time = formatter.format(((Event) DaoEvents.get(i)).getStartDateTime());
           
          
            item.add(new Label("eventName", ((Event) DaoEvents.get(i)).getName()));
            item.add(new Label("eventLocation", ((Event) DaoEvents.get(i)).getLocation()));
            item.add(new Label("eventPartner", ((Event) DaoEvents.get(i)).getPartner().getOrganizationName()));         
            item.add(new Label("eventDate", (((Event) DaoEvents.get(i)).getStartDateTime().getMonth() + "/" + ((Event) DaoEvents.get(i)).getStartDateTime().getDate() + "/" +  year)));
            item.add(new Label("eventTime", time));
            
            
            
            
            Form form4 = new Form("form4"){
            	protected void onSubmit(){
            		info("Form.onSubmit()");
            	}
            };
            
            final Event currentEvent = (Event) DaoEvents.get(i);
            Button apply = new Button("applyButton"){
            	@Override
            	public void onSubmit(){
            		info("Send to: ");
            		
            		setResponsePage(new VolunteerEventDetailsView(currentEvent,"volunteerSearchResultPage",DaoEvents));
            		
            	}
            };
            
            
            
            final User pass = ((Event) DaoEvents.get(i)).getPartner();
            Button partnerProfile = new Button("partnerProfile"){
            	@Override
            	public void onSubmit(){
            		info("Send to: ");
            		setResponsePage(new SearchPartnerProfileView(pass));
            		
            		
            	}
            };
            form4.add(apply);
            form4.add(partnerProfile);
            
            item.add(form4);
            
            
		}
	}
}



