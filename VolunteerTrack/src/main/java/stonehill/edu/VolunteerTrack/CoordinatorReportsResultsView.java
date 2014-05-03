package stonehill.edu.VolunteerTrack;

import java.util.*;
import java.io.*;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;

public class CoordinatorReportsResultsView extends VolunteerTrackBaseView {

Form f;
Label message;
	private static final long serialVersionUID = 1L;

	
	public CoordinatorReportsResultsView (ArrayList<Object> u, ArrayList<Integer> volunteerhours, Date date, int totalhours) {
		
		f=new Form("form");
		message=new Label("message", u.size() +" volunteers have worked " +totalhours+ " hours");
		f.add(message);
		
		   RepeatingView repeating=new RepeatingView("repeating");
		   add(repeating);
			int index=0; 
			
		   f.add(repeating);
		   AbstractItem   item = new AbstractItem(repeating.newChildId());
			repeating.add(item);
			item.add(new Label("One","Name"));
			item.add(new Label("Two","Major"));
			item.add(new Label("Three","Hours Worked"));	
			item.add(new Label("Four","Date"));	
			
			for(int i=0; i<u.size();i++) {
	        User user=(User)u.get(i);
		   final int x=i;
		   item = new AbstractItem(repeating.newChildId());
		   repeating.add(item);
			item.add(new Label("One",user.getFirstName()+" " +user.getLastName()));
			item.add(new Label("Two",user.getMajor()));
			item.add(new Label("Three", volunteerhours.get(i)+""));	
			item.add(new Label("Four", date.toString()));	
		
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
	         index++;	                 
			}
			Button back=new Button("back") {
				@Override
				public void onSubmit() {
				this.setResponsePage(CoordinatorReportView.class);	
				}
			};
			f.add(back);
			add(f);
		
			}	
	
	public CoordinatorReportsResultsView(ArrayList<Object>events) {
		f=new Form("form");
		message=new Label("message", " Partners have scheduled " +events.size()+ " events in this time period");
		f.add(message);
		RepeatingView repeating=new RepeatingView("repeating");
		   add(repeating);
			int index=0; 
			
		   f.add(repeating);
		   AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item);
			
		
			item.add(new Label("One","Organization Name"));
			item.add(new Label("Two","Event Name"));
			item.add(new Label("Three","Start Date/Time"));
			item.add(new Label("Four","End Date/Time"));
			
		for(int i=0;i<events.size();i++) {
			 final int x=i;
			   item = new AbstractItem(repeating.newChildId());
				repeating.add(item);
				Event event=(Event)events.get(i);
				
				item.add(new Label("One",event.getOrganizationName()));
			  	item.add(new Label("Two",event.getName()));
				item.add(new Label("Three", event.getStartDateTime()));	
				item.add(new Label("Four", event.getEndDateTime()));
		}
		Button back=new Button("back") {
			@Override
			public void onSubmit() {
			this.setResponsePage(CoordinatorReportView.class);	
			}
		};
		f.add(back);
		add(f);
	}
	
}
	

	

