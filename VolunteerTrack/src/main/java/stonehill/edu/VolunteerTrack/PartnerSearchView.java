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
	TextField FirstName, LastName, dateAvailable,major, minor, startTime, endTime;
	UserDao dao=new UserDao();
	ArrayList<Object> volunteers;
	ArrayList<User> searchvol=new ArrayList<User>();
    ArrayList<String>temp;	
    ArrayList<Object>SearchCriteria;
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
	
		user1=new User();
		user1.setFirstName("Kelsey");
		user1.setLastName("Napolione");
		user1.setMajor("Computer Science");
		user1.setMinor("Religion");
		
		user2=new User();
		user2.setFirstName("Kelsey");
		user2.setLastName("Napolione");
		user2.setMajor("Computer Science");
		user2.setMinor("Religion");
		dao.insert(user1);
		dao.insert(user2);
		ArrayList<Object> tempUser=new ArrayList<Object>();
		tempUser.add(user1);
		tempUser.add(user2);
		
		
		
			
		currentUser=dao.getUserByUsername("partner@partner.com");
	// final CheckGroup<Skill> group = new CheckGroup<Skill>("group", new ArrayList<Skill>());
		//this may not be what we want
		Form<?> form = new Form<Void>("form")
				{
					@Override
					protected void onSubmit()
					{
						//do some stuff
					}
				};
				
		form.setModel(new Model(currentUser));
		form.add(FirstName=new TextField<String>("FirstName", new PropertyModel(currentUser,"FirstName")));
		form.add(LastName=new TextField<String>("LastName", new PropertyModel(currentUser,"LastName")));
	//	form.add(dateAvailable=new TextField<String>("DateAvailable"));
		form.add(major=new TextField<String>("Major", new PropertyModel(currentUser,"Major")));
		form.add(minor=new TextField<String>("Minor", new PropertyModel(currentUser,"Minor")));
	//	form.add(startTime=new TextField<String>("StartTime"));
	//	form.add(endTime=new TextField<String>("EndTime"));
		
		//add all the items to a list
		temp=new ArrayList<String>();
		temp.add("firstName");
		temp.add(FirstName.toString());
		temp.add("LastName");
		temp.add(LastName.toString());
		//temp.add("dateAvaiable");
		//temp.add(dateAvailable.toString());
		temp.add("major");
		temp.add(major.toString());
		temp.add("minor");
		temp.add(minor.toString());
		//temp.add("startTime");
		//temp.add(startTime.toString());
		//temp.add("endTime");
		//temp.add(endTime.toString());
		final ResultSet results;
			
	  //  populateSkills();
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
	
			//searchResults= dao.getSearchResults(SearchCriteria);

	//	this.setResponsePage(PartnerSearchView.class);	

		}
	
		};
	
		
		form.add(search);
	  //  form.add(text);
	 //   populateResultsTable(tempUser);
	
	//add skills
		
		SkillDao skilldao=new SkillDao();
		ArrayList<Object>skillslist=skilldao.selectAll(); //get all skills
		//ArrayList<String> skillSelect=new ArrayList<String>();
		
		String [] skillarray=new String[skillslist.size()];
		for(int i=0;i<skillslist.size(); i++) {
			skillarray[i]=((Skill) skillslist.get(i)).getName();
		}
		List<String> fixedskills = Arrays.asList(skillarray);
			

			final CheckBoxMultipleChoice<String> skillBoxes=
					new CheckBoxMultipleChoice<String>("skills", fixedskills);
			
		form.add(skillBoxes);			
	        
	
		 
	
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
			RepeatingView repeating = new RepeatingView("repeating");
	        add(repeating);
	        
	        int index=0;
	
		//  text=new Label("text",""+searchResults.size());	
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
	        //	form2.add(Namemessage);
			//	form2.add(Majormessage);
			//	form2.add(Minormessage);
	      //  form2.add(item);
	          
			}
			
	//		text=new Label("results",""+index);
			//form.add(text);
			add(form);
	  }


		
}
	       //    add(form2);
	            //add forms to the page 

							
		
		

