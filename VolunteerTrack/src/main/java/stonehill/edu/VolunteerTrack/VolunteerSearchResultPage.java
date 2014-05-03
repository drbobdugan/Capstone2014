package stonehill.edu.VolunteerTrack;





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

	
	public VolunteerSearchResultPage(ArrayList DaoEvents)
	{
		
		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);
		
		for(int i = 0; i < DaoEvents.size(); i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());
            repeating.add(item); 

          //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item.add(new Label("eventName", ((Event) DaoEvents.get(i)).getName()));
            item.add(new Label("eventLocation", ((Event) DaoEvents.get(i)).getLocation()));
            item.add(new Label("eventPartner", ((Event) DaoEvents.get(i)).getOwnerEmail()));
            item.add(new Label("eventDate", ((Event) DaoEvents.get(i)).getStartDateTime().toString()));
            item.add(new Label("eventTime", ((Event) DaoEvents.get(i)).getStartDateTime().getTime()));
            
            
            Form form4 = new Form("form4"){
            	protected void onSubmit(){
            		info("Form.onSubmit()");
            	}
            };
            
            Button apply = new Button("applyButton"){
            	@Override
            	public void onSubmit(){
            		info("Send to: ");
            		
            	}
            };
            form4.add(apply);
            
            item.add(form4);
            
            
		}
	}
}
