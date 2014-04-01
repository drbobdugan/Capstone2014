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
import org.apache.wicket.request.mapper.parameter.PageParameters;

public class PartnerProfileView extends VoltrackPage {
	
	public PartnerProfileView(final PageParameters parameters)
	{
		//final Input input= new Input();
		//setDefaultModel(new CompoundPropertyModel<Input>(input));
		
		Form<?> form = new Form<Void>("form")
		{
			@Override
			protected void onSubmit()
			{
				//do some stuff
			}
		};
		
		
		Button upload = new Button("upload_photo")
		{
			@Override
			public void onSubmit()
			{
				info("upload_photo.onSubmit executed");
			}
		};
		
		Button save = new Button("submit")
		{
			@Override
			public void onSubmit()
			{
				//overwrite current partner info using dao and values in input fields
			}
		};
		
		Button cancel = new Button("cancel")
		{
			@Override
			public void onSubmit()
			{
				info("cancel.onSubmit executed");
			}
		};
		
		//ssiff@students.stonehill.edu
		UserDao userDao= new UserDao();
		User user= userDao.getUserByUsername("ssiff@students.stonehill.edu");
		
		SkillDao skillsDao = new SkillDao();
		ArrayList<Object> skillslist = skillsDao.selectAll();
		ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(user);
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
		
		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>("skills",new Model(skillsSelect),fixedskills);
		form.add(upload);
		form.add(save);
		form.add(cancel);
		
		form.add(new TextField<String>("organization",Model.of("")));
		form.add(new TextField<String>("email",Model.of("")));
		form.add(new TextField<String>("phone",Model.of("")));
		form.add(new TextField<String>("street",Model.of("")));
		form.add(new TextField<String>("city",Model.of("")));
		form.add(new TextField<String>("state",Model.of("")));
		form.add(new TextField<String>("zip",Model.of("")));
		form.add(new TextField<String>("links",Model.of("")));
		form.add(new TextField<String>("current",Model.of("")));
		form.add(new TextField<String>("new_password",Model.of("")));
		form.add(new TextField<String>("confirm_password",Model.of("")));
		
		//form.add(new CheckBox("category"));
		form.add(skillsBoxes);
		form.add(new TextArea<String>("mission"));
		add(form);
		


		

	}
	
	//private static class Input
	//{
		//create partner object by selecting from userDao
		
		/*
		 * Partner p= new Partner();
		 * use getters from partner object model to set fields
		public String organization = "";
		public String email = "";
		public String phone = "";
		public String street = "";
		public String city = "";
		public String state = "";
		public String zip = "";
		public String links = "";
		
		public String mission="";

		*/
	//}

}
