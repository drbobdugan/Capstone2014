package stonehill.edu.VolunteerTrack;
import java.awt.*;

import java.awt.List;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
public class PartnerDocumentView  extends VolunteerTrackBaseView {
	private static final int VIEWBUTTON   = 0;
	private static final int EDITBUTTON   = 1;
	private static final int DELETEBUTTON = 2;
	
	User[]User; //user array that populates the volunteers that have entered documents linked to this partner
	ArrayList<Document> partnerDocuments;
	ArrayList<Document> volunteerDocuments;
	//FileUpload file=new FileUpload("fileUpload");
	//will populate with View/Delete Buttons connected to that document
	//ArrayList<Document> docs;
	//DocumentModel document=new DocumentModel("Cori Form","Animal shelter",a,"link","knap@gmail.com",true);
	User currentUser;
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
	transient DocumentDao docDao;
	transient UserDao userDao;
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
					Document testDoc = new Document (-1,newDocName.getModelObject(),newDocType.getModelObject(), new Date(), newFile,"partner@partner.com", false, -1);
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
		partnerDocuments=docDao.getAllDocumentsByUser(currentUser);
		volunteerDocuments=docDao.getAllDocumentsSharedWithPartner(currentUser);
		
		populateTables();	
	}
	private void populateTables() {
		final DataView dataView = new DataView("simple", new ListDataProvider(partnerDocuments)) {
			protected void populateItem(final Item item) {
				final Document document = (Document)item.getModelObject();
				item.add(new Link<Void>("delete"){ public void onClick(){ processDocumentButton(document.getId(), DELETEBUTTON, true);}});
				item.add(new Link<Void>("edit"){ public void onClick(){ processDocumentButton(document.getId(), EDITBUTTON, true);}});
				item.add(new Link<Void>("view"){ public void onClick(){ processDocumentButton(document.getId(), VIEWBUTTON, true);}});
				item.add(new Label("name", document.getName()));
				item.add(new Label("type", document.getName()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
				item.add(new Label("dateUploaded", dateFormat.format(document.getDateUploaded())));
	
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
				{
					 private static final long serialVersionUID = 1L;

					 @Override
					 public String getObject()
					 {
						 return (item.getIndex() % 2 == 1) ? "even" : "odd";
					 }
				}));
			}
		};
			
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
			
		final DataView dataView2 = new DataView("simple2", new ListDataProvider(volunteerDocuments)) {
			protected void populateItem(final Item item) {
				final Document document = (Document)item.getModelObject();
				item.add(new Link<Void>("volunteerDocumentView"){ public void onClick(){ processDocumentButton(document.getId(), VIEWBUTTON, true);}});
				item.add(new Label("volunteerDocumentName", document.getName()));
				item.add(new Label("volunteerDocumentType", document.getName()));
				SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
				item.add(new Label("volunteerDocumentDateUploaded", dateFormat.format(document.getDateUploaded())));
				item.add(new Label("volunteerDocumentEmail", document.getUserEmail()));
	
				
				item.add(AttributeModifier.replace("class", new AbstractReadOnlyModel<String>()
				{
					 private static final long serialVersionUID = 1L;

					 @Override
					 public String getObject()
					 {
						 return (item.getIndex() % 2 == 1) ? "even" : "odd";
					 }
				}));
			}
		};
			
		dataView2.setItemsPerPage(5);
		add(dataView2);
		add(new PagingNavigator("navigator2", dataView2));
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


public void processDocumentButton(int documentId, int viewEditDelete, boolean isPartner)
{
	Document document = null;
	ArrayList<Document> documents = null;
	
	// Select future/past events
	if (isPartner)
	{
		documents = partnerDocuments;
	}
	else
	{
		documents = volunteerDocuments;
	}
	
	// Find event
	for (int i=0; i < documents.size(); i++)
	{
		document = documents.get(i);
	
		if (documentId == document.getId() )
		{
			break;
		}
	}
	
	// Process the event
	switch (viewEditDelete)
	{
	case VIEWBUTTON:   
		System.out.println("PartnerDocumentView: processDocumentButton() viewing: " + document.getId() + ": " + document.getName());
		setResponsePage(new PartnerDocumentDetailsView(document, "partnerDocumentView"));   
		break;
	case EDITBUTTON:   
		setResponsePage(new PartnerEditDocumentView(document));  
		break;
	case DELETEBUTTON: 
		DocumentDao documentDao = new DocumentDao();
		documentDao.delete(document);
    	setResponsePage(PartnerDocumentView.class);
    	break;
	}
}



	}//end of class

