package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.*;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;


public class VolunteerDocuments extends WebPage
{

	public Label message;
	public VolunteerDocuments()
	{
		
		
		List<String> choices= Arrays.asList(new String[] {"Select A Partner","Pet Shop", "Homeless Shelter", "Nemi" });
	    String selected = "Select A Partner";
	    
	    IModel dropdown = new Model<String>(selected);
		DropDownChoice<String> listSites = new DropDownChoice<String>("sites", dropdown, choices);
		DropDownChoice<String> listSites2 = new DropDownChoice<String>("sites2", dropdown, choices);

		add(listSites);
		add(listSites2);
		
		FileUploadField fileUpload = new FileUploadField("fileUpload");
		add(fileUpload);
		
		
		RepeatingView repeating = new RepeatingView("repeating");
        add(repeating);
		
		for(int i = 0; i<10; i++)
		{
			AbstractItem item = new AbstractItem(repeating.newChildId());

            repeating.add(item);
            

            //item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
            item.add(new Label("contactid", "Blank"));
            item.add(new Label("firstname", "Blank"));
            item.add(new Label("lastname",  "Blank"));
            item.add(new Label("homephone", "Blank"));
            item.add(new Label("cellphone", "Blank"));

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

			
			
			
		}
			
	}
	public void delete(){
   	
    }
	public void save(){
   	 
    }
	public void update(){
   	 
    }
	public void create(){
   	 
    }
}
