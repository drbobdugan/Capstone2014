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
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.jquery.ui.form.button.Button;

public class SearchPartnerProfileView extends VolunteerTrackBaseView {

	private static final long serialVersionUID = 1L;
	UserDao userDao= new UserDao();
	SkillDao skillsDao = new SkillDao();

	Form uploadProfilePicture, profileInformation, passwordManagement, skillsManagement;
	TextField organization, email, phoneNumber, street, city, state, zip;
	
	public SearchPartnerProfileView(User user)
	{
		User currentuser = user;
		profileInformation = new Form("profileInformation");
		profileInformation.setModel(new Model(currentuser));		
		profileInformation.add(organization = new TextField<String>("organization", new PropertyModel(currentuser,"organizationName")));
		profileInformation.add(email = new TextField<String>("email", new PropertyModel(currentuser, "email")));
		profileInformation.add(phoneNumber = new TextField<String>("phoneNumber", new PropertyModel(currentuser, "phoneNumber")));
		profileInformation.add(street = new TextField<String>("street", new PropertyModel(currentuser, "street")));
		profileInformation.add(city = new TextField<String>("city", new PropertyModel(currentuser, "city")));
		profileInformation.add(state = new TextField<String>("state", new PropertyModel(currentuser, "state")));
		profileInformation.add(zip = new TextField<String>("zip", new PropertyModel(currentuser, "zip")));

		organization.setEnabled(false);
		email.setEnabled(false);
		phoneNumber.setEnabled(false);
		street.setEnabled(false);
		city.setEnabled(false);
		state.setEnabled(false);
		zip.setEnabled(false);
		add(profileInformation);

		skillsManagement= new Form<Void>("skillsManagement");
		ArrayList<Object> skillslist = skillsDao.selectAll();
		ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(currentuser);
		final ArrayList<String> skillsSelect = new ArrayList<String>();


		String[] skillarray = new String[skillslist.size()];
		for(int i=0;i<userskills.size();i++)
			skillsSelect.add(((Skill)userskills.get(i)).getName());

		for(int i=0;i<skillslist.size();i++)
			skillarray[i]=((Skill)skillslist.get(i)).getName();

		List<String> fixedskills = Arrays.asList(skillarray);

		skillsSelect.add("Animals");

		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>("skills",new Model(skillsSelect),fixedskills);
		skillsBoxes.setEnabled(false);
		skillsManagement.add(skillsBoxes);
		add(skillsManagement);
	}

}
