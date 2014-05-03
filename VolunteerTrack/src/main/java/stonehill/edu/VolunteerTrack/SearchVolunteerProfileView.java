package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class SearchVolunteerProfileView extends VolunteerTrackBaseView {

	private static final long serialVersionUID = 1L;
	UserDao userDao = new UserDao();
	SkillDao skillsDao = new SkillDao();
	TextField email, firstName, lastName, street, city, state, zip, phoneNumber;
	TextArea description;
	Form profileInformation;
	Form skillsManagement;
	ArrayList<String> skillsSelect;

	static Logger logger = Logger.getLogger(SearchVolunteerProfileView.class);
	
	public SearchVolunteerProfileView(User user)
	{
		User currentuser = user;
		profileInformation = new Form("profileInformation");
		profileInformation.setDefaultModel(new Model(currentuser));
		// add text boxes to form
		profileInformation.add(email = new TextField<String>("email", new PropertyModel(currentuser, "email")));
		profileInformation.add(firstName = new TextField<String>("firstName",new PropertyModel(currentuser, "firstName")));
		profileInformation.add(lastName = new TextField<String>("lastName",new PropertyModel(currentuser, "lastName")));
		profileInformation.add(street = new TextField<String>("street",new PropertyModel(currentuser,"street" )));
		profileInformation.add(city = new TextField<String>("city",new PropertyModel(currentuser, "city")));
		profileInformation.add(state = new TextField<String>("state",new PropertyModel(currentuser, "state")));
		profileInformation.add(zip = new TextField<String>("zip", new PropertyModel(currentuser, "zip")));
		profileInformation.add(phoneNumber = new TextField<String>("phoneNumber",new PropertyModel(currentuser,"phoneNumber")));
		profileInformation.add(description = new TextArea<String>("description",new PropertyModel(currentuser, "volunteerDescription")));

		email.setEnabled(false);
		firstName.setEnabled(false);
		lastName.setEnabled(false);
		street.setEnabled(false);
		city.setEnabled(false);
		state.setEnabled(false);
		zip.setEnabled(false);
		phoneNumber.setEnabled(false);
		description.setEnabled(false);
		add(profileInformation);

		// create form for checkboxes
		skillsManagement = new Form("form2");
		ArrayList<Object> skillslist = skillsDao.selectAll();
		ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(currentuser);
		skillsSelect = new ArrayList<String>();

		String[] skillarray = new String[skillslist.size()];

		for (int i = 0; i < skillslist.size(); i++)
			skillarray[i] = ((Skill)skillslist.get(i)).getName();

		for (int i = 0; i < userskills.size(); i++)
			skillsSelect.add(((Skill)userskills.get(i)).getName());

		List<String> fixedskills = Arrays.asList(skillarray);

		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>("skills", new Model(skillsSelect), fixedskills);
		skillsManagement.add(skillsBoxes);
		add(skillsManagement);	
	}
}
