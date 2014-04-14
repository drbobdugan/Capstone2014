package stonehill.edu.VolunteerTrack;


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
	
	UserDao userDao= new UserDao();
	SkillDao skillsDao = new SkillDao();
	
	User currentuser;
	Label message;
	
	public PartnerProfileView(final PageParameters parameters)
	{
		//final Input input= new Input();
		//setDefaultModel(new CompoundPropertyModel<Input>(input));
		

		//will eventually get from session
		currentuser = userDao.getUserByUsername("ssiff@students.stonehill.edu");
		
		Form<?> form = new Form<Void>("form")
		{
			@Override
			protected void onSubmit()
			{
				//do some stuff
			}
		};
		
		form.setModel(new Model(currentuser));
		
		Button upload = new Button("upload_photo")
		{
			@Override
			public void onSubmit()
			{
				info("upload_photo.onSubmit executed");
			}
		};
		
		Button save = new Button("saveProfile")
		{
			@Override
			public void onSubmit()
			{
				//overwrite current partner info using dao and values in input fields
				//userDao = new UserDao();
				//userDao.update(currentuser);
				//this.setResponsePage(PartnerProfileView.class);
			}
		};
		
		Button cancel = new Button("cancel")
		{
			@Override
			public void onSubmit()
			{
				//this.setResponsePage(PartnerProfileView.class);
			}
		};
		
		form.add(upload);
		form.add(save);
		form.add(cancel);
		
		form.add(new TextField<String>("organization"));
		form.add(new TextField<String>("email",new PropertyModel(currentuser, "email")));
		form.add(new TextField<String>("phoneNumber",new PropertyModel(currentuser, "phoneNumber")));
		form.add(new TextField<String>("street",new PropertyModel(currentuser, "street")));
		form.add(new TextField<String>("city",new PropertyModel(currentuser, "city")));
		form.add(new TextField<String>("state",new PropertyModel(currentuser, "state")));
		form.add(new TextField<String>("zip",new PropertyModel(currentuser, "zip")));
		form.add(new TextField<String>("links"));
		form.add(new TextField<String>("current"));
		form.add(new TextField<String>("new_password"));
		form.add(new TextField<String>("confirm_password"));
		form.add(new TextArea<String>("mission"));
		
		//Form 2-------------------------------
		Form<?> form2 = new Form<Void>("form2")
		{
			@Override
			protected void onSubmit()
			{
				//do some stuff
			}
		};
		//checklist stuff
		
		//User user= userDao.getUserByUsername("ssiff@students.stonehill.edu");
		
		
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
		
		form2.add(skillsBoxes);

		add(form);
		add(form2);
		


		

	}
	
}
