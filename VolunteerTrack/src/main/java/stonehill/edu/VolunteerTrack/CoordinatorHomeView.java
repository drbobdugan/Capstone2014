package stonehill.edu.VolunteerTrack;



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
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;


public class CoordinatorHomeView extends VolunteerTrackBaseView
{

	public Label message;
 	public String selected;
 	public ArrayList<User> thePartners;
 	public ArrayList<User> theCoordinators;
 	public ArrayList<User> theUsers;
 	UserDao dao;
 	
	public CoordinatorHomeView()
	{
		thePartners = new ArrayList<User>();
		theUsers = new ArrayList<User>();
		theCoordinators = new ArrayList<User>();
	 
        dao = new UserDao();
		
		ArrayList<Object> temp = dao.selectAll();
		for(int i = 0; i <temp.size();i++)
		{
			theUsers.add((User)temp.get(i));
			if (!((User)temp.get(i)).getIsApprovedPartner() && ((User)temp.get(i)).getIsPartner()) // user not approved and wants to be partner
			{
				thePartners.add((User)temp.get(i));
			}
			if (!((User)temp.get(i)).getIsApprovedCoordinator() && ((User)temp.get(i)).getIsCoordinator()) // user not approved and wants to be partner
			{
				theCoordinators.add((User)temp.get(i));
			}
		}
		

		
	  makePage(thePartners, theCoordinators);
			
	}
	public void makePage(List<User> thePartners, List<User> theCoordinators){
		
	   
		RepeatingView repeating = new RepeatingView("repeating");
        add(repeating);
		
		for(int i = 0; i < thePartners.size(); i++)
		{
			final int x = i;
			AbstractItem item = new AbstractItem(repeating.newChildId());
            repeating.add(item); 

            //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item.add(new Label("name", thePartners.get(i).getFirstName() + " " + 
            thePartners.get(i).getLastName() + 
            " " + thePartners.get(i).getEmail() + 
            " approved: " + thePartners.get(i).getIsApprovedPartner() + 
            " partner: " + thePartners.get(i).getIsPartner() +
            " coordinator: " + thePartners.get(i).getIsCoordinator()
            ));
            
       // form is made    
            Form form = new Form("form"){
            	protected void onSubmit(){
            		info("Form.onSubmit()");
            	}
            };
            
        // delete buttons made and added to the form
            Button delete1 = new Button("notapprove"){
            	@Override
            	public void onSubmit(){
            		info("Delete : " + x);
            		notApprovePartner(x);
            		
            	}
            };
            form.add(delete1);
            
       // update buttons made and added tot he forms
            Button update1 = new Button("approve"){
            	@Override
            	public void onSubmit(){
            		info("Update : " + x);
            		approvePartner(x);
            	}
            };
            form.add(update1);
            
            
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
		
		
		RepeatingView repeating2 = new RepeatingView("repeating2");
        add(repeating2);
		
		for(int i = 0; i < theCoordinators.size(); i++)
		{
			final int x = i;
			AbstractItem item2 = new AbstractItem(repeating2.newChildId());
            repeating2.add(item2); 

            //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item2.add(new Label("name2", theCoordinators.get(i).getFirstName() + " " + 
            		theCoordinators.get(i).getLastName() + 
            		" " + theCoordinators.get(i).getEmail() + 
            		" approved: " + theCoordinators.get(i).getIsApprovedCoordinator() + 
            " partner: " + theCoordinators.get(i).getIsPartner() +
            " coordinator: " + theCoordinators.get(i).getIsCoordinator()
            ));
            
       // form is made    
            Form form2 = new Form("form2"){
            	protected void onSubmit(){
            		info("Form.onSubmit()");
            	}
            };
            
        // delete buttons made and added to the form
            Button delete2 = new Button("notapprove2"){
            	@Override
            	public void onSubmit(){
            		info("Delete : " + x);
            		notApproveCoordinator(x);
            		
            	}
            };
            form2.add(delete2);
            
       // update buttons made and added tot he forms
            Button update2 = new Button("approve2"){
            	@Override
            	public void onSubmit(){
            		info("Update : " + x);
            		approveCoordinator(x);
            	}
            };
            form2.add(update2);
            
            
       // add first form
            item2.add(form2);
            
            
           
            
          

            final int idx = i;
            item2.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
            {
                private static final long serialVersionUID = 1L;

                @Override
                public String getObject()
                {
                    return (idx % 2 == 1) ? "even" : "odd";
                }
            }));
		} 
		
    }
	
	public String getChoice()
	{
	 return selected;
	}
	
	public void notApprovePartner(int x){
		
		User tempUser = thePartners.get(x);
		tempUser.setIsPartner(false);
		dao.update(tempUser);
		this.setResponsePage(CoordinatorHomeView.class);
   	
    }
	
	public void approvePartner(int x){
		User tempUser = thePartners.get(x);
		tempUser.setApprovedPartner(true);
		dao.update(tempUser);
		this.setResponsePage(CoordinatorHomeView.class);
   	
    }

	public void notApproveCoordinator(int x){
		
		User tempUser = theCoordinators.get(x);
		tempUser.setIsCoordinator(false);
		dao.update(tempUser);
		this.setResponsePage(CoordinatorHomeView.class);
   	
    }
	
	public void approveCoordinator(int x){
		
		User tempUser = theCoordinators.get(x);
		tempUser.setApprovedCoordinator(true);
		dao.update(tempUser);
		this.setResponsePage(CoordinatorHomeView.class);
   	
    }
	
	
	
	public void create(){
		
		this.setResponsePage(CoordinatorHomeView.class);
   	 
    }
    public void sendDoc(String receiver, int x){
		
		this.setResponsePage(CoordinatorHomeView.class);
   	 
    }
}
