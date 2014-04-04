package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.resource.ContextRelativeResource;

public class VolProfileView extends VoltrackPage {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	User currentuser; 
	TextField email, firstName, lastName, street, city, state, zip, phoneNumber;
	TextArea description;
	Image propic;
	Form form1, form2, form3;
	UserDao userDao = new UserDao();
	Label message;
	
    public VolProfileView()
	{
    	// hardcode current user, eventually will get user from session
    	currentuser = userDao.getUserByUsername("ssiff@students.stonehill.edu");
    	
    	
    	// create form for page
    	form1 = new Form("form1");
    	
    	
    	//set model of form
		form1.setModel(new Model(currentuser));
    
		
		// add text boxes to form
		form1.add(email = new TextField<String>("email", new PropertyModel(currentuser, "email")));
		form1.add(firstName = new TextField<String>("firstName",new PropertyModel(currentuser, "firstName")));
		form1.add(lastName = new TextField<String>("lastName",new PropertyModel(currentuser, "lastName")));
		form1.add(street = new TextField<String>("street",new PropertyModel(currentuser,"street" )));
		form1.add(city = new TextField<String>("city",new PropertyModel(currentuser, "city")));
		form1.add(state = new TextField<String>("state",new PropertyModel(currentuser, "state")));
		form1.add(zip = new TextField<String>("zip", new PropertyModel(currentuser, "zip")));
		form1.add(phoneNumber = new TextField<String>("phoneNumber",new PropertyModel(currentuser,"phoneNumber")));
		form1.add(description = new TextArea<String>("description",new PropertyModel(currentuser, "volunteerDescription")));
		
		email.setEnabled(false);
		
		// save button
		form1.add(new Button("save") {
			@Override
			public void onSubmit() {
			userDao = new UserDao();
			userDao.update(currentuser);
			this.setResponsePage(VolProfileView.class);
			}
		});
		
		// cancel button
		form1.add(new Button("cancel") {
			@Override
			public void onSubmit() {
			this.setResponsePage(VolProfileView.class);
			}
		});
		
		// add textfields and textfield buttons
		add(form1);
		
		// create form for checkboxes
    	form2 = new Form("form2");
		
    	// create dao for skills
    	// get all skills
    	// get user specific skills
		SkillDao skillsDao = new SkillDao(); 
		  ArrayList<Object> skillslist = skillsDao.selectAll();
		  ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(currentuser);
		  ArrayList<String> skillsSelect = new ArrayList<String>();
		  
		 String[] skillarray = new String[skillslist.size()];
		 
		 
		 for (int i = 0; i < skillslist.size(); i++)
		 {
			 skillarray[i] = ((Skill)skillslist.get(i)).getName();
		 }
		
		 for (int i = 0; i < userskills.size(); i++)
		 {
			 skillsSelect.add(((Skill)userskills.get(i)).getName());
		 }
		List<String> fixedskills = Arrays.asList(skillarray);
		
		
		// simulate user has animal skill while there is no way to add a skill to a user in db
		skillsSelect.add("Animals");
		
		// create checkboxes for each skill, with users skills pre-checked
		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>(
				"skills", new Model(skillsSelect), fixedskills);

		// add checkboxes to form2
		form2.add(skillsBoxes);
		
		// messages to check checkboxes working correctly
		form2.add(message = new Label("message3", "empty"));
		form2.add(message = new Label("message4", "empty"));
		
		// save skills button
		form2.add(new Button("saveskills") {
			@Override
			public void onSubmit() {
				
				
				
			this.setResponsePage(VolProfileView.class);
			}
		});
		
		// cancel skills button
		form2.add(new Button("cancelskills") {
			@Override
			public void onSubmit() {
			this.setResponsePage(VolProfileView.class);
			}
		});
		
		// add form2 to page
		add(form2);
		
		
		// create form for page
    	form3 = new Form("form3");
    	
    	Photo photo = new Photo("Steve pro pic", "", 
				true, true, false, false,"ssiff@students.stonehill.edu");
    	
    	//set model of form
		form3.setModel(new Model(photo));
		
		form3.add(propic = new Image("propic", new ContextRelativeResource(photo.getLink())));
		
		
		// upload new picture button
				form3.add(new Button("upload") {
					@Override
					public void onSubmit() {
						// upload a new picture
					}
				});
				
		add(form3);
	
	}
}
