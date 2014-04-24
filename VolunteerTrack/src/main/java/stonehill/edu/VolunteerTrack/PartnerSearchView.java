package stonehill.edu.VolunteerTrack;

import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.io.*;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.html.form.CheckGroup;
//import org.apache.wicket.markup.html.form.CheckGroupSelector;
//import org.apache.wicket.markup.html.list.ListView;
public class PartnerSearchView extends VolunteerTrackBaseView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	TextField<String> FirstName, LastName, DateAvailable, major, minor, startTime, endTime;
	UserDao dao=new UserDao();
	ArrayList<Object> volunteers;
	
	ArrayList<User> searchvol=new ArrayList<User>();
    ArrayList<String>temp;	
    ArrayList<String>SearchCriteria;
    ArrayList<Object>searchResults;
    User currentUser;
    Label Namemessage;
    Label Majormessage;
    Label Minormessage;
    Label text;
    User user1;
    User user2;
    ArrayList<User> tempUser;
	public PartnerSearchView()
	{
		
		user1=new User("","","Kelsey","Napolione","","","","","","","",false,false,true,"ComputerScience","Religion",false,false,true,"");	
		user2=new User("","","Kelsey","Napolione","","","","","","","",false,false,true,"ComputerScience","Religion",false,false,true,"");
		
		dao.insert(user1);
		dao.insert(user2);
		ArrayList<Object> tempUser=new ArrayList<Object>();
		tempUser.add(user1);
		tempUser.add(user2);
		
		//insert into dao to test that it grabs from it correctly
		//dao.insert(user1);
		//dao.insert(user2);;
		
	currentUser=dao.getUserByUsername("partner@partner.com");
	User u=new User();
	Form form = new Form("form");
				
		form.setModel(new Model(currentUser));
		form.add(FirstName=new TextField<String>("FirstName",new PropertyModel(currentUser,"firstName")));
		form.add(LastName=new TextField<String>("LastName",new PropertyModel(currentUser,"lastName")));
	 	//form.add(DateAvailable=new TextField<String>("date"));
		form.add(major=new TextField<String>("Major",new PropertyModel(currentUser,"major")));
		form.add(minor=new TextField<String>("Minor",new PropertyModel(currentUser,"minor")));
	//	form.add(startTime=new TextField<String>("StartTime"));
	 // form.add(endTime=new TextField<String>("EndTime"));
		
		//add all the items to a list
		
			
	  //  populateSkills();
		

		SkillDao skilldao=new SkillDao();
		final ArrayList<Object>skillslist=skilldao.selectAll(); //get all skills
		//ArrayList<String> skillSelect=new ArrayList<String>();
		
		String [] skillarray=new String[skillslist.size()];
		for(int i=0;i<skillslist.size(); i++) {
			skillarray[i]=((Skill) skillslist.get(i)).getName();
		}
		List<String> fixedskills = Arrays.asList(skillarray);

			final CheckBoxMultipleChoice<String> skillBoxes=
					new CheckBoxMultipleChoice<String>("skills",new Model(skillslist), fixedskills);
			form.add(skillBoxes);	
	Button search=new Button("Search") { /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@Override
	
		
		//add skills
		
					
    public void onSubmit() {
			//add all the items to a list
			
			String first=FirstName.getDefaultModelObjectAsString();
			String last=LastName.getDefaultModelObjectAsString();
			String ma=major.getDefaultModelObjectAsString();
			String mi=minor.getDefaultModelObjectAsString();
			temp=new ArrayList<String>();
			temp.add("firstName");
			temp.add(first);
			temp.add("lastName");
			temp.add(last);
			//temp.add("dateAvaiable");
		//	temp.add(""+DateAvailable.toString());
			temp.add("major");
			temp.add(ma);
			temp.add("minor");
			temp.add(mi);
			//temp.add("startTime");
			//temp.add(startTime.toString());
			//temp.add("endTime");
			//temp.add(endTime.toString());
			final ResultSet results;
		
				//makes array of non null items in search criteria
			SearchCriteria=new ArrayList<String>();
			for(int i=0; i<temp.size(); i+=2) {
								
				if(!temp.get(i+1).equals(""))  {	
					SearchCriteria.add(temp.get(i));
					SearchCriteria.add(temp.get(i+1));	
				}	
			}
			
	ArrayList<Object>resultset=new ArrayList<Object>();
	
	//call dao to query the database
			searchResults= dao.getSearchResults(SearchCriteria);
			searchResults=dao.getAllVolunteers();
			
		int g=0;
			
				
				for(int h=0;h<searchResults.size(); h++){
				User user=(User) searchResults.get(h);
				if(temp.get(g+1).equals(user.getFirstName()) || temp.get(g+3).equals(user.getLastName())
						|| temp.get(g+5).equals(user.getMajor())) {
					resultset.add(searchResults.get(h));
					
				}
			
				}
			
	
			///for(int k=0;k<searchResults.size();k++) {
			//	resultset.add((User)searchResults.get(k));
	//		}
         	this.setResponsePage(new PartnerSearchResultsView(resultset));	
      }
		};
	form.add(search);
	
	 //   populateResultsTable(tempUser);
	
			
	        
	
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
		/*
			RepeatingView repeating = new RepeatingView("repeating");
	        add(repeating);
	        
	        int index=0;
	
			for(int i=0; i<tempUser.size(); i++) {
			
			
	        final int x=1;
            AbstractItem item = new AbstractItem(repeating.newChildId());

            repeating.add(item);
         //   form2.add(repeating);
      User user=(User)tempUser.get(i);
      //  User user= (User) searchResults.get(i);
        Namemessage=new Label("Name",user.getFirstName() + " "  +user.getLastName());
        Majormessage=new Label("Major",user.getMajor());
        Minormessage=new Label("Minor",""+user.getMinor());
         item.add(Namemessage);
         item.add(Majormessage);
         item.add(Minormessage);
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
		add(form);
		
	  }


		
}
							
		
		

