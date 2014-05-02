package stonehill.edu.VolunteerTrack;


import java.io.File;
import java.io.Serializable;
import java.util.*;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.FileUpload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;



public class VolunteerDocumentUpdateView extends VolunteerTrackBaseView
{
	TextField<String> NewDocumentName;
	TextField<String> NewDocumentType;
	
	User CurrentUser = CustomSession.get().getUser();

	FileUploadField fileUpload;
    Document theDoc;

	public VolunteerDocumentUpdateView(Document passedInDoc)
	{
		theDoc=passedInDoc;
		Form form1 = new Form("form1"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};

		String nameSplit = theDoc.getName();
        String[] splitted = nameSplit.split("\\.");
        String nameDisplay = splitted[0];
        
        for(int q =1; q<splitted.length;q++)
        {
          if(q != splitted.length-1)
          {
        	  nameDisplay = nameDisplay + "." + splitted[q];
          }
        }
        
		NewDocumentName = new TextField<String>("addDocumentName",Model.of(nameDisplay)); 
		NewDocumentType = new TextField<String>("addDocumentType",new PropertyModel(theDoc,"type"));



		Button addDB = new Button("addDB"){
			@Override
			public void onSubmit(){
				info("Add Doucment was clicked : ");
				final FileUpload uploadedFile = fileUpload.getFileUpload();
				//System.out.println(uploadedFile);
				
				String newName = NewDocumentName.getModelObject();
				String newType= NewDocumentType.getModelObject();
                File newFile = new File("/home/ubuntu/Desktop/test.txt");
                
                
				
				if(uploadedFile != null)
				{
					String fileUploadedName = uploadedFile.getClientFileName();
                    String[] splitted =fileUploadedName.split("\\.");
                    String fileType = splitted[splitted.length-1];

					try{
						newFile = uploadedFile.writeToTempFile();
						// set it to session user
						Document one = new Document(newName+"."+fileType,newType, new Date(),newFile,CurrentUser.getEmail(), false);
						
						DocumentDao dao = new DocumentDao();
						dao.delete(theDoc);
						dao.insert(one);
						
						
						this.setResponsePage(VolunteerDocumentView.class);
					}
					catch(Exception E)
					{
						throw new IllegalStateException("Error trying to do the .create or .writeto");
					}
				}
				else
				{
					String fileUploadedName = theDoc.getName();
                    String[] splitted =fileUploadedName.split("\\.");
                    String fileType = splitted[splitted.length-1];
                    
					    Document one = new Document(newName+"."+fileType,newType, new Date(),theDoc.getFile(),CurrentUser.getEmail(), false);
					    DocumentDao dao = new DocumentDao();
						dao.delete(theDoc);
						dao.insert(one);
						this.setResponsePage(VolunteerDocumentView.class);
				}


				// create(NewDocumentName, NewDocumentType,fileUpload);
			}
		};

		form1.setMultiPart(true);

		form1.add(NewDocumentName);
		form1.add(NewDocumentType);
		form1.add(fileUpload= new FileUploadField("fileUpload"));
		form1.add(addDB);

		add(form1);
		
		
		
	}
}
