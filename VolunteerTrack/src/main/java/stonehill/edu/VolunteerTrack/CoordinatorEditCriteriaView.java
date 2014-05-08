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
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.jquery.ui.form.button.Button;

public class CoordinatorEditCriteriaView extends VolunteerTrackBaseView{
	
	private static final long serialVersionUID = 1L;
	
	SkillDao skillsDao = new SkillDao();
	private static CheckGroup<Skill> group;
	private static TextField<String> name;
	private String message = "";
	Form editCriteria;
	CheckBoxMultipleChoice<String> skillBoxes;
	ListView skills;
	
	Button addSkills, deleteSkills;
	
	private String inputValue;
	
	public CoordinatorEditCriteriaView()
	{
		PropertyModel<String> messageModel = new PropertyModel<String>(this, "message");
		
		add(new Label ("message", messageModel));
		
		Form<?> editCriteria= new Form<Void>("editCriteria");
		//editCriteria.setDefaultModelObject("");
		ArrayList<Object> temp = skillsDao.selectAll();
		ArrayList<Skill> s = new ArrayList<Skill>();
		
        for(int i =0; i<temp.size(); i++)
        {
        	s.add(((Skill)temp.get(i)));
        }
		
        group = new CheckGroup<Skill>("group", new ArrayList<Skill>());
        
        // name = new TextField<String>("name");
        name = new TextField<String>("name", messageModel);
        
        ListView skills = new ListView("skills", s){
        	protected void populateItem(ListItem item){
        		item.add(new Check("checkbox", item.getModel()));
        		item.add(new Label("skillName", new PropertyModel(item.getModel(),"name")));
        	}
        };
        
        skills.setReuseItems(true);
        group.add(skills);
        addSkills=new Button("addSkills"){
        	@Override
        	public void onSubmit(){
        		
        		String temp = name.getDefaultModelObjectAsString();
        		//message= name.getDefaultModelObjectAsString();
          		Skill s = new Skill(temp);
        		skillsDao.insert(s);
        		this.setResponsePage(CoordinatorEditCriteriaView.class);
        		
        	}
        };
        
        deleteSkills=new Button("deleteSkills"){
        	@Override
        	public void onSubmit(){
				ArrayList<Skill> selectedSkills = (ArrayList<Skill>) group.getDefaultModelObject();
				String test="";
				for(int i =0; i<selectedSkills.size(); i++){
					test =  selectedSkills.get(i).getName();
					Skill temp = new Skill(test);
					skillsDao.delete(temp);
				}
				
				setResponsePage(CoordinatorEditCriteriaView.class);
				//info("selected event(s): " + test);
        	}
        };
        
        editCriteria.add(name);
        editCriteria.add(group);
        editCriteria.add(addSkills);
        editCriteria.add(deleteSkills);
        add(editCriteria);
		
	}

}
