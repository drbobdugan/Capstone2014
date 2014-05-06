package stonehill.edu.VolunteerTrack;
import java.awt.*;

import java.awt.List;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

import javax.swing.text.html.ListView;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.repeater.RepeatingView;
public class PartnerDocumentView  extends VolunteerTrackBaseView {
	
	User[]User; //user array that populates the volunteers that have entered documents linked to this partner
	ArrayList<Object> partnerdocuments;
	ArrayList<Object> volunteerdocuments;
	//FileUpload file=new FileUpload("fileUpload");
	//will populate with View/Delete Buttons connected to that document
	DocumentDao docDao;
	UserDao userDao;
	//ArrayList<Document> docs;
	//DocumentModel document=new DocumentModel("Cori Form","Animal shelter",a,"link","knap@gmail.com",true);
	User currentUser;
	ArrayList<Document> yourdocs;
	ArrayList<Document> volunteerdocs;
	final TextField<String> newDocName = new TextField<String>("DocumentName",Model.of("")); 
    final TextField<String> newDocType=new TextField<String>("DocumentType",Model.of(""));
    FileUploadField fileUpload;
   // Form repeatingform;
    Button viewButton;
    Button DeleteButton;
    Button updateButton;
    Button viewVolDoc;
    Button upload;
    Document doc;
	public PartnerDocumentView()
	{
		docDao=new DocumentDao();
		Form<?>uploadForm=new Form<Void>("uploadform") {
			protected void onSubmit(){
        		info("Form.onSubmit()");
        	}
		};
/*
		upload=new Button("uploadDoc") { 
			private static final long serialVersionUID = 1L;
			@Override
	    public void onSubmit() {
		//delete the document
	//		info("Delete : " + x);
    //	delete(x);
			}
		};
		*/
		
		Button add=new Button("Add") {
			@Override
			public void onSubmit() {
				
			final FileUpload uploadedFile=fileUpload.getFileUpload();
			//do stuff	
			if(uploadedFile!=null) {
				File newFile=new File ("/home/ubuntu/Desktop/test.txt");
				
				try {
					newFile=uploadedFile.writeToTempFile();
					Document testDoc = new Document (newDocName.getModelObject(),newDocType.getModelObject(),new Date(), newFile, "partner@partner.com",false);
					docDao.insert(testDoc);
					this.setResponsePage(PartnerDocumentView.class);
				}
		     catch(Exception e) {
		    	 throw new IllegalStateException("Error trying to do the .create or .writeto");
		    	    	 
		     }		
			}
			}
		};
		uploadForm.setMultiPart(true);
		
	//	uploadForm.add(upload);
		uploadForm.add(newDocName);
		uploadForm.add(newDocType);
		uploadForm.add(fileUpload=new FileUploadField("uploadDoc"));
		uploadForm.add(add);
		add(uploadForm);
		
		userDao=new UserDao();
		currentUser=CustomSession.get().getUser();
        //get the docs from the database
		partnerdocuments=docDao.getAllDocumentsByUser(currentUser);
		volunteerdocuments=docDao.getAllDocumentsSharedWithPartner(currentUser);

	//add(form);
		yourdocs=new ArrayList<Document>();
		volunteerdocs=new ArrayList<Document>();
		for(int i=0; i<partnerdocuments.size();i++) {
			yourdocs.add((Document)partnerdocuments.get(i));
		}
		for(int i=0;i<volunteerdocuments.size();i++) {
			volunteerdocs.add((Document)volunteerdocuments.get(i));
		}
		//generate the tables
		generateyourDocs(yourdocs);
		generateVolunteerDocs(volunteerdocs);	
		
	}
	
public void	generateyourDocs(ArrayList<Document>yourdocs) {
		
		RepeatingView repeating = new RepeatingView("repeating");
        add(repeating);
        //repeatingform.add(repeating);
      int  index=0;
 
		for(int i=0; i<yourdocs.size(); i++) {
	
        final int x=i;
        AbstractItem item = new AbstractItem(repeating.newChildId());

     repeating.add(item);
    
    
 	//add the forms
     //form for add button
     
		Form<?> buttonform = new Form<Void>("buttonform")
				{
					@Override
					protected void onSubmit()
					{
						//do some stuff
						info("Form.onSubmit()");
					}
				};
		
	
		
		buttonform.add(new DownloadLink("viewButton",yourdocs.get(i).getFile(),yourdocs.get(i).getName()));

		// add first form to the repeater
	 // buttonform.add(viewButton);
		
		 DeleteButton=new Button("deleteButton") { 
			private static final long serialVersionUID = 1L;
			@Override
	    public void onSubmit() {
		//delete the document
			info("Delete : " + x);
     	delete(x);
			}
		};
		buttonform.add(DeleteButton);
		
		 updateButton=new Button("updateButton") { /**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			@Override
		
	    public void onSubmit() {
		//view the document
				info("View : " + x);
     		update(x);
     		
			}
		};
		buttonform.add(updateButton);
	//	add(repeatingform);
		add(buttonform);
	  	 item.add(buttonform);
		 item.add(new Label("document",yourdocs.get(i).getName()));
	     item.add(new Label("dateUploaded", yourdocs.get(i).dateUploaded));
	     item.add(new Label("Description",yourdocs.get(i).getType()));
		//repeatingform.add(repeating);
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
            index++;
            
         //   item.add(form);
    	//	item.add(form2);
		}
	
		}


public void generateVolunteerDocs(ArrayList<Document> volunteerdocs) {
	
	
	RepeatingView repeating=new RepeatingView("repeating2");
	add(repeating);
	int index=0;
for(int i=0;i<volunteerdocs.size(); i++) {
	
	  final int x=i;
	    AbstractItem item = new AbstractItem(repeating.newChildId());
	    
		repeating.add(item);

	Form<?> Volunteerform = new Form<Void>("Volunteerform")
			{
				@Override
				protected void onSubmit()
				{
					//do some stuff
					info("Form.onSubmit()");
				}
			};
/*
			viewVolDoc=new Button("viewVolDoc") { 
					 
					
					private static final long serialVersionUID = 1L;
					@Override
				
			    public void onSubmit() {
				//view the document
						info("View : " + x);
		     		view(x);
		     		
					}
				};
				*/
Volunteerform.add(new DownloadLink("viewVolDoc",volunteerdocs.get(i).getFile(),volunteerdocs.get(i).getName()));

				// add first form to the repeater			
	//		Volunteerform.add(new DownloadLink("viewVolDoc",volunteerdocs.get(i).getFile(),volunteerdocs.get(i).getName()));

			// add first form to the repeater
			//	Volunteerform.add(viewVolDoc);
  add(Volunteerform);
	item.add(Volunteerform);
	item.add(new Label("Name",volunteerdocs.get(i).getName()));
	item.add(new Label("DocName",volunteerdocs.get(i).getName()));
	item.add(new Label("Date", volunteerdocs.get(i).getDateUploaded()));
	item.add(new Label("Type",volunteerdocs.get(i).getType()));
	
		
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
        index++;
}
	
	
	
}//end of volunteer doc table method

	

public void delete(int x){
	//yourdocs.remove(x);
	docDao.delete(yourdocs.get(x));
		this.setResponsePage(PartnerDocumentView.class);
    }
/*
public void create() {
	//get the filename
	Document newDocument;
	String filename=fileUpload.toString();
	//this part is still uncertain
    File newFile=new File(filename);
  //  newDocument=new Document(filename, newDocType.toString(),new Date(),newFile, "knapolione@students.stonehill.edu",false);
	docDao.insert(newDocument);
    this.setResponsePage(VolunteerDocumentView.class);
}
*/
public void view(int x) {
	this.setResponsePage(PartnerDocumentView.class);
}
public void update(int x) {
	this.setResponsePage(new PartnerDocumentUpdateView(yourdocs.get(x)));
}



	}//end of class

