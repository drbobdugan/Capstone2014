package stonehill.edu.VolunteerTrack;

import java.io.File;
import java.io.Serializable;
import java.util.*;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;


public class VolunteerHourView extends VolunteerTrackBaseView
{

	public Label message;
 	public String selected;
 	public ArrayList<Object> userTimesheetEntry;
 	TimesheetEntryDao teDao;
 	User currentUser;
 	
	public VolunteerHourView()
	{
		 currentUser = new User("kholmander@stonehill.edu","gunslinger", "keith", "holmander", "", "", "", "", "", "", "", false, false, true, true, "", "");
		
		teDao = new TimesheetEntryDao();
		
	    userTimesheetEntry = teDao.getAllTimesheetEntriesByUser(currentUser);
	    ArrayList userTEtemp = userTimesheetEntry;
	    int counter = 0;
		
	    for (int i = 0; i < userTimesheetEntry.size(); i++)
	    {
	    	if (((TimesheetEntry)userTimesheetEntry.get(i)).getIsApproved())
	    	{
	    		userTEtemp.remove( i - counter);
	    		counter++;
	    	}
	    }
	    
	    userTimesheetEntry = userTEtemp;
	  
	  makePage(userTimesheetEntry);
			
	}
	public void makePage(List userEvents){
		
		
		RepeatingView repeating = new RepeatingView("repeating");
        add(repeating);
		
		for(int i = 0; i < userEvents.size(); i++)
		{
			final int x = i;
			AbstractItem item = new AbstractItem(repeating.newChildId());
            repeating.add(item); 

            item.add(new Label("event", ((TimesheetEntry)userEvents.get(i)).getEventName()));
            item.add(new Label("partner", ((TimesheetEntry)userEvents.get(i)).getEventName()));
            item.add(new Label("date", ((TimesheetEntry)userEvents.get(i)).getDateTime()));
            item.add(new Label("hours", ((TimesheetEntry)userEvents.get(i)).getHoursLogged()));
            
       // form is made    
            Form form = new Form("form"){
            	protected void onSubmit(){
            		//info("Form.onSubmit()");
            	}
            };
            
        // delete buttons made and added to the form
            Button delete1 = new Button("delete"){
            	@Override
            	public void onSubmit(){
            		//info("Delete : " + x);
            		delete(x);
            		
            	}
            };
            form.add(delete1);
            
       // update buttons made and added tot he forms
            Button update1 = new Button("update"){
            	@Override
            	public void onSubmit(){
            		//info("Update : " + x);
            		update(x);
            	}
            };
            form.add(update1);
            
       // approve buttons made and added to the form
            Button approve1 = new Button("approve"){
            	@Override
            	public void onSubmit(){
            		//info("View : " + x );
            		approve(x);
            	}
            };
            form.add(approve1);
            
         // save buttons made and added to the form
            Button save1 = new Button("save"){
            	@Override
            	public void onSubmit(){
            		//info("View : " + x );
            		save(x);
            	}
            };
            form.add(save1);
            
       // add first form
            item.add(form);
            
            
      
            

            final int idx = i;
            item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public String getObject()
                {
                    return (idx % 2 == 1) ? "even" : "odd";
                }
            }));
		}  	
		
		
		// form for add event
		 Form form3 = new Form("form3"){
         	protected void onSubmit(){
         		info("Form.onSubmit()");
         	}
         };

		
		Button addevent = new Button("addevent"){
        	@Override
        	public void onSubmit(){
        		info("Add Doucment was clicked : ");
        		addEvent();
        	}
        };
		
		form3.add(addevent);

		add(form3);
    }
	

	public void approve(int x){
		((TimesheetEntry)userTimesheetEntry.get(x)).setIsApproved(true);
		this.setResponsePage(VolunteerHourView.class);
   	
    }
	public void delete(int x){
		teDao.delete(userTimesheetEntry.get(x));
		this.setResponsePage(VolunteerHourView.class);
   	
    }
	public void save(int x){
   	 
		this.setResponsePage(VolunteerHourView.class);
		
    }
	public void update(int x){
		
		this.setResponsePage(VolunteerHourView.class);
   	 
    }
	public void addEvent(){
		Date date = new Date(114, 6, 12);
		TimesheetEntry te = new TimesheetEntry(currentUser.getEmail(), date, "TestAddEvent", false, false, 0);
		teDao.insert(te);
		this.setResponsePage(VolunteerHourView.class);
   	 
    }
}

