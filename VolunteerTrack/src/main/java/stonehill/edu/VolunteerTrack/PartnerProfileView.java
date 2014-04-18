package stonehill.edu.VolunteerTrack;


import java.awt.Image;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PartnerProfileView extends VolunteerTrackBaseView {
	
	private static final long serialVersionUID = 1L;
	UserDao userDao= new UserDao();
	SkillDao skillsDao = new SkillDao();
	
	Form uploadProfilePicture, profileInformation, passwordManagement, skillsManagement;
	TextField email, phoneNumber, street, city, state, zip;
	TextField current, new_password, confirm_password; 
	User currentuser;
	Label feedback;
	
	public PartnerProfileView()
	{
		//will eventually get from session
		//currentuser = userDao.getUserByUsername("zbrown2@students.stonehill.edu");
		currentuser = CustomSession.get().getUser();
		
	    
//Profile Picture Management========================================================
	    uploadProfilePicture = new Form<Void>("uploadProfilePicture");
		Button upload = new Button("upload_photo");
		uploadProfilePicture.add(upload);
		//add(new StaticImage ("profilePic", new Model("www.atlkingpits.com/Male_Blue_Pitbulls/malakinew1.jpg")));
//Profile Information=============================================================
	    
		profileInformation = new Form<Void>("profileInformation");
	    profileInformation.setModel(new Model(currentuser));
		
		

		profileInformation.add(email = new TextField<String>("email", new PropertyModel(currentuser, "email")));
		profileInformation.add(phoneNumber = new TextField<String>("phoneNumber", new PropertyModel(currentuser, "phoneNumber")));
		profileInformation.add(street = new TextField<String>("street", new PropertyModel(currentuser, "street")));
		profileInformation.add(city = new TextField<String>("city", new PropertyModel(currentuser, "city")));
		profileInformation.add(state = new TextField<String>("state", new PropertyModel(currentuser, "state")));
		profileInformation.add(zip = new TextField<String>("zip", new PropertyModel(currentuser, "zip")));
		
		Button save = new Button("saveProfile")
		{
			@Override
			public void onSubmit()
			{
				//overwrite current partner info using dao and values in input fields
				//userDao = new UserDao();
				userDao.update(currentuser);
				setResponsePage(PartnerProfileView.class);
			}
		};
		
		Button cancel = new Button("cancel")
		{
			@Override
			public void onSubmit()
			{
				setResponsePage(PartnerProfileView.class);
			}
		};
		
		profileInformation.add(save);
		profileInformation.add(cancel);
//Password Management============================================================
		passwordManagement = new Form<Void>("passwordManagement");
		passwordManagement.setModel(new Model(currentuser));
		
		passwordManagement.add(current = new TextField<String>("current", new PropertyModel(currentuser, "password")));
		passwordManagement.add(new_password = new TextField<String>("new_password", new PropertyModel(currentuser, "password")));
		passwordManagement.add(confirm_password = new TextField<String>("confirm_password", new PropertyModel(currentuser, "password")));
		passwordManagement.add(feedback= new Label ("feedback"));
		Button confirm= new Button("updatePassword")
		{
			@Override
			public void onSubmit()
			{
				
				String c = current.getDefaultModelObjectAsString();
				String np= new_password.getDefaultModelObjectAsString();
				String conp= confirm_password.getDefaultModelObjectAsString();
				
				if(c.equals(currentuser.getPassword())  && np.equals(conp)){
					currentuser.setPassword(np);
					userDao.update(currentuser);
					feedback.setDefaultModel(new Model("Password Change Success"));
				}
				setResponsePage(PartnerProfileView.class);
			}
		};
		
		passwordManagement.add(confirm);
//SkillsManagement===============================================================
	    skillsManagement= new Form<Void>("skillsManagement");
		ArrayList<Object> skillslist = skillsDao.selectAll();
		ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(currentuser);
		ArrayList<String> skillsSelect = new ArrayList<String>();
		
		String[] skillarray = new String[skillslist.size()];
		
		for(int i=0;i<userskills.size();i++)
		{
			skillsSelect.add(((Skill)userskills.get(i)).getName());
		}
		
		for(int i=0;i<skillslist.size();i++)
		{
		     skillarray[i]=((Skill)skillslist.get(i)).getName();
		}

		List<String> fixedskills = Arrays.asList(skillarray);
		
		skillsSelect.add("Animals");
		
		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>("skills",new Model(skillsSelect),fixedskills);
		
		
		skillsManagement.add(skillsBoxes);
		
        add(uploadProfilePicture);
        add(profileInformation);
		add(passwordManagement);
		add(skillsManagement);
			}
	
}
