package stonehill.edu.VolunteerTrack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import java.util.Date;

import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;



public class VolSearchView extends VoltrackPage 
{
  private final Date startDate = new Date();
  private final Date endDate = new Date();
	 
  public VolSearchView()
  {
	  
	  List<String> partners =  Arrays.asList(new String[] {"--------- Select A Partner ----------","Pet Shop", "Homeless Shelter", "Nemi" });
	  List<String> locations = Arrays.asList(new String[] {"--------- Select A Location---------","Stonehill Campus", "Easton, Ma", "Brockton", "Taunton" });
	  List<String> events =    Arrays.asList(new String[] {"---------- Select An Event ----------","Puppy Petting", "Rake Leaves at Church", "Walk People across the Street" });
 
	  Form form = new Form("form"){
      	protected void onSubmit(){
      		info("Form.onSubmit()");
      	}
      };
      
      
      String partnerSelected = "Select A Partner";
	  Model dropdownPartner = new Model<String>(partnerSelected); 
      DropDownChoice<String> partnerList = new DropDownChoice<String>("partners", dropdownPartner, partners);
        
        
      String locationSelected = "Select A Location";
	  Model dropdownLocation = new Model<String>(locationSelected); 
      DropDownChoice<String> locationList = new DropDownChoice<String>("locations", dropdownLocation, locations); 
      
      
      String eventSelected = "Select A Location";
	  Model dropdownEvent = new Model<String>(eventSelected); 
      DropDownChoice<String> eventList = new DropDownChoice<String>("events", dropdownEvent, events); 
      

	// Date selector fields and methods
	  DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
      DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));
	
      DatePicker startDatePicker = new DatePicker(){
    	
    	  protected String getAdditionalJavascript()
    	  {
    		  return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
    	  }
      }; 
    
      DatePicker endDatePicker = new DatePicker(){
    	
    	  protected String getAdditionalJavascript()
    	  {
    		  return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
    	  }
      }; 
      
      
      startDatePicker.setShowOnFieldClick(true);
      endDatePicker.setShowOnFieldClick(true);
      startDateTextField.add(startDatePicker);
      endDateTextField.add(endDatePicker); 
      
      Form form2 = new Form("form2");
		
      
      
  	// create dao for skills
  	// get all skills
  	// get user specific skills
		SkillDao skillsDao = new SkillDao(); 
		  ArrayList<Object> skillslist = skillsDao.selectAll();
		  
		  ArrayList<String> skillsSelect = new ArrayList<String>();
		  
		 String[] skillarray = new String[skillslist.size()];
		 
		 
		 for (int i = 0; i < skillslist.size(); i++)
		 {
			 skillarray[i] = ((Skill)skillslist.get(i)).getName();
		 }
		

		List<String> fixedskills = Arrays.asList(skillarray);
		
		
		// create checkboxes for each skill, with users skills pre-checked
		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>(
				"skills", new Model(skillsSelect), fixedskills);

		// add checkboxes to form2
		form2.add(skillsBoxes);
		
		form2.add(new Button("submitButton") {
			@Override
			public void onSubmit() {
		    
			this.setResponsePage(VolSearchView.class);
			}
		});
		// add form2 to page
		add(form2);
		
      
      
      
      
      
      
      form.add(startDateTextField);
	  form.add(endDateTextField);
      form.add(partnerList);
      form.add(locationList);
      form.add(eventList);
      
      add(form);
      
  
  }
}
