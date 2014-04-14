package stonehill.edu.VolunteerTrack;

import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class PartnerSearchView extends VolunteerTrackBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField firstName, LastName, dateAvailable,major, minor, startTime, endTime;
	UserDao dao=new UserDao();
	Form form;
	Form form2;
	ArrayList<Object> volunteers;
	ArrayList<User> searchvol=new ArrayList<User>();
    ArrayList<String>temp;	
    ArrayList<Object>SearchCriteria;
    ArrayList<Object>searchResults;
    
	public PartnerSearchView()
	{
		User currentUser=dao.getUserByUsername("knapolione@students.stonehill.edu");
	
		//this may not be what we want
		form=new Form("form");
		form.setModel(new Model(currentUser));
		form.add(firstName=new TextField<String>("FirstName", new PropertyModel(currentUser,"FirstName")));
		form.add(LastName=new TextField<String>("LastName", new PropertyModel(currentUser,"LastName")));
		form.add(dateAvailable=new TextField<String>("DateAvailable", new PropertyModel(currentUser,"DateAvailable")));
		form.add(major=new TextField<String>("Major", new PropertyModel(currentUser,"Major")));
		form.add(minor=new TextField<String>("FirstName", new PropertyModel(currentUser,"Minor")));
		form.add(startTime=new TextField<String>("StartTime", new PropertyModel(currentUser,"StartTime")));
		form.add(endTime=new TextField<String>("EndTime", new PropertyModel(currentUser,"EndTime")));
		
		//add all the items to a list
		temp=new ArrayList<String>();
		temp.add("firstName");
		temp.add(firstName.toString());
		temp.add("LastName");
		temp.add(LastName.toString());
		temp.add("dateAvaiable");
		temp.add(dateAvailable.toString());
		temp.add("major");
		temp.add(major.toString());
		temp.add("minor");
		temp.add(minor.toString());
		temp.add("startTime");
		temp.add(startTime.toString());
		temp.add("endTime");
		temp.add(endTime.toString());
		final ResultSet results;
	
	    populateSkills();
	Button search=new Button("Search") { /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
	
    public void onSubmit() {
			
		
				//makes array of non null items in search criteria
			SearchCriteria=new ArrayList<Object>();
			
			for(int i=0; i<temp.size(); i+=2) {
								
				if(temp.get(i+1)!=null)  {
					
					SearchCriteria.add(temp.get(i));
					SearchCriteria.add(temp.get(i+1));
					
				}
				
			}
	
			
		searchResults= dao.getSearchResults(SearchCriteria);
		populateResultsTable();
		//this.setResponsePage(PartnerSearchView.class);		
		}
		};
	}
	
	public void populateSkills() {
		form2=new Form("form2");
		SkillDao skilldao=new SkillDao();
		ArrayList<Object>skillslist=skilldao.selectAll();
		ArrayList<String> skillSelect=new ArrayList<String>();
		
		String [] skillarray=new String[skillslist.size()];
		for(int i=0;i<skillslist.size(); i++) {
			skillarray[i]=((Skill) skillslist.get(i)).getName();
		}
		List<String> fixedskills = Arrays.asList(skillarray);
		final CheckBoxMultipleChoice<String> skillBoxes=new CheckBoxMultipleChoice<String>("skills", new Model(skillSelect), fixedskills);
		form2.add(skillBoxes);
		
	}
		
		public void populateResultsTable() {
			//when I tested with the dummy information
			/*
			User user1=new User();
			user1.setFirstName("Kelsey");
			user1.setLastName("Napolione");
			user1.setMajor("Computer Science");
			user1.setMinor("Religion");
			
			User user2=new User();
			user2.setFirstName("Kelsey");
			user2.setLastName("Napolione");
			user2.setMajor("Computer Science");
			user2.setMinor("Religion");
			ArrayList<User> tempUser=new ArrayList<User>();
			tempUser.add(user1);
			tempUser.add(user2);
			*/
			for(int i=0; i<searchResults.size(); i++) {
			RepeatingView repeating = new RepeatingView("repeating");
	        add(repeating);
	        

	        final int x=1;
            AbstractItem item = new AbstractItem(repeating.newChildId());

            repeating.add(item);
   
            User user= (User) searchResults.get(i);
        
          item.add(new Label("Name", user.getFirstName() + " "  +user.getLastName()));
          item.add(new Label("Major",user.getMajor()));
          item.add(new Label("Minor", user.getMinor()));
			
			
			
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
		}
		
		
			
	
	
}
