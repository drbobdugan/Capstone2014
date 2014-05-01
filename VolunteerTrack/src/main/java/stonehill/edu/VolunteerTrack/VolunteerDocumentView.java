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
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.form.upload.FileUpload;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;


public class VolunteerDocumentView extends VolunteerTrackBaseView
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Label message;
	public String selected;
	public ArrayList<Document> theDocs;
	DocumentDao dao;

	TextField<String> NewDocumentName;
	TextField<String> NewDocumentType;

	FileUploadField fileUpload;


	public VolunteerDocumentView()
	{
		theDocs = new ArrayList<Document>();

		dao = new DocumentDao();

		ArrayList<Object> temp = dao.selectAll();

		for(int i = 0; i <temp.size();i++)
		{
			theDocs.add((Document)temp.get(i));
			System.out.println(((Document)temp.get(i)).getName());
		}


		makePage(theDocs);

	}
	public void makePage(List<Document> theDocs){

		List<String> partners = new ArrayList<String>();
		partners.add("Select A Partner");

		UserDao theUsers = new UserDao();
		ArrayList partnerUsers = theUsers.getAllPartners();

		for(int i = 0; i < partnerUsers.size();i++)
		{
			partners.add(((User) partnerUsers.get(i)).getEmail());

		}

		RepeatingView repeating = new RepeatingView("repeating");
		add(repeating);

		for(int i = 0; i < theDocs.size(); i++)
		{
			final int x = i;
			AbstractItem item = new AbstractItem(repeating.newChildId());
			repeating.add(item); 

			//item.add(new ActionPanel("actions", new DetachableContactModel(contact)));
			
			String nameSplit = theDocs.get(i).getName();
            String[] splitted = nameSplit.split("\\.");
            String nameDisplay = splitted[0];
            
            for(int q =1; q<splitted.length;q++)
            {
              if(q != splitted.length-1)
              {
            	  nameDisplay = nameDisplay + "." + splitted[q];
              }
            }
            
            
			item.add(new Label("name", nameDisplay));
			item.add(new Label("type", theDocs.get(i).getType()));

			// form is made    
			Form form = new Form("form"){
				protected void onSubmit(){
					info("Form.onSubmit()");
				}
			};

			// delete buttons made and added to the form
			Button delete1 = new Button("deleteButton"){
				@Override
				public void onSubmit(){
					info("Delete : " + x);
					delete(x);

				}
			};
			form.add(delete1);

			// update buttons made and added tot he forms
			Button update1 = new Button("updateButton"){
				@Override
				public void onSubmit(){     //hello? 
					info("Update : " + x);
					update(x);
				}
			};
			form.add(update1);

			// view buttons made and added to the form
		
			
			form.add(new DownloadLink("viewButton",theDocs.get(i).getFile(),theDocs.get(i).getName()));

			// add first form
			item.add(form);


			item.add(new Label("LastUpdate", theDocs.get(i).getDateUploaded().toString()));

			// make second form for drop down and send button

			Form form2 = new Form("form2"){
				protected void onSubmit(){
					info("Form.onSubmit()");
				}
			};

			selected = "Select A Partner";
			IModel dropdown = new Model<String>(selected);

			DropDownChoice<String> listSites3 = new DropDownChoice<String>("sites3", dropdown, partners);
			form2.add(listSites3);


			// make send buttons and add to its form     
			Button send1 = new Button("sendButton"){
				@Override
				public void onSubmit(){
					info("Send to: ");
					sendDoc(getChoice(), x);
				}
			};
			form2.add(send1);

			item.add(form2);
			//add(form);


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

		Form form3 = new Form("form3"){
			protected void onSubmit(){
				info("Form.onSubmit()");
			}
		};



		NewDocumentName = new TextField<String>("addDocumentName",Model.of("")); 
		NewDocumentType = new TextField<String>("addDocumentType",Model.of(""));



		Button addDB = new Button("addDB"){
			@Override
			public void onSubmit(){
				info("Add Doucment was clicked : ");
				final FileUpload uploadedFile = fileUpload.getFileUpload();
				//System.out.println(uploadedFile);

				if(uploadedFile !=null)
				{
					File newFile = new File("/home/ubuntu/Desktop/test.txt");
					
					String fileUploadedName = uploadedFile.getClientFileName();
                    String[] splitted =fileUploadedName.split("\\.");
                    String fileType = splitted[splitted.length-1];
					
					

					try{
						newFile = uploadedFile.writeToTempFile();
						Document one = new Document(NewDocumentName.getModelObject()+"."+fileType,NewDocumentType.getModelObject(), new Date(),newFile,"volunteer@volunteer.com", false);
						dao.insert(one);
						this.setResponsePage(VolunteerDocumentView.class);
					}
					catch(Exception E)
					{
						throw new IllegalStateException("Error trying to do the .create or .writeto");
					}
				}


				// create(NewDocumentName, NewDocumentType,fileUpload);
			}
		};

		form3.setMultiPart(true);

		form3.add(NewDocumentName);
		form3.add(NewDocumentType);
		form3.add(fileUpload= new FileUploadField("fileUpload"));
		form3.add(addDB);

		add(form3);
	}

	public String getChoice()
	{
		return selected;
	}

	public void delete(int x){

		dao.delete(theDocs.get(x));
		this.setResponsePage(VolunteerDocumentView.class);

	}
	public void view(int x){
		
		
	//	File downloadFile = theDocs.get(x).getFile();
	//	IResourceStream resourceStream = new FileResourceStream(downloadFile);
	//	IRequestHandler target = new ResourceStreamRequestHandler(resourceStream);
	//	getRequestCycle().scheduleRequestHandlerAfterCurrent(target);

		//this.setResponsePage(VolunteerDocumentView.class);

	}
	public void update(int x){

		this.setResponsePage(VolunteerDocumentView.class);

	}
	public void create(){

		this.setResponsePage(VolunteerDocumentView.class);

	}
	public void sendDoc(String receiver, int x){

		this.setResponsePage(VolunteerDocumentView.class);

	}
}
