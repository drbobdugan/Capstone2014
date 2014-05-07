package stonehill.edu.VolunteerTrack;

import java.util.*;
import java.io.*;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.PropertyModel;

public class PartnerSearchResultsView extends VolunteerTrackBaseView {

	private static final long serialVersionUID = 1L;
	Form f;
	Label message;
   
	public PartnerSearchResultsView(ArrayList<Object>results) {
		
		f= new Form("form");	
		UserDao dao=new UserDao();
	//	results=dao.getAllVolunteers();

		if(results==null){
			 message=new Label("message","No Search Results");
			f.add(message);
		}
		else
		{
			message=new Label("message","There are "+results.size() +" results in your search");
			f.add(message);
		
		
	   RepeatingView repeating=new RepeatingView("repeating");
	   add(repeating);
		int index=0; 
		
	f.add(repeating);
		for(int i=0; i<results.size();i++) {
       final User user=(User)results.get(i);
	   final int x=i;
	    AbstractItem item = new AbstractItem(repeating.newChildId());
		repeating.add(item);
			
		item.add(new Link<Void>("profile"){ public void onClick(){ this.setResponsePage(new SearchVolunteerProfileView(user));}});
		item.add(new Label("Name",user.getFirstName()+" " +user.getLastName()));
		item.add(new Label("Major",user.getMajor()));
		item.add(new Label("Minor", user.getMinor()));	
		item.add(new Label("Email", user.getEmail()));
		item.add(new Label("City",user.getCity()));
		item.add(new Label("State",user.getState()));
		item.add(new Label("Zip", user.getZip()));
	
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
		
		}	
		
		/*
		ListView searchresults = new ListView("listview", results){
		   	protected void populateItem(ListItem item){
		   		Skill skill=(Skill)item.getModelObject();
		   		item.add(new Label("Name",skill.getName()));
		   		item.add(new Label("Major",skill.getName()));
		   		item.add(new Label("Minor", skill.getName()));
		   f.add(item);
		   	}
		   	
		};
		
		
		*/
		
		//searchresults.setReuseItems(true);
	
	//back button
   Button back=new Button("back") {
	   @Override
	   public void onSubmit() {
		 this.setResponsePage(PartnerSearchView.class);  
	   }
   }; 
  f.add(back);
   add(f);
	
/*
	
	        final int x=1;
	        AbstractItem item = new AbstractItem(repeating.newChildId());
	
	        repeating.add(item);
	     //   form2.add(repeating);
	
	  //  User user= (User) searchResults.get(i);
	  
	   //item.add(form2);
			
			
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
			*/
	}
}
