package stonehill.edu.VolunteerTrack;

import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;

public class PartnerDocumentView  extends VolunteerTrackBaseView 
{
	private static final long serialVersionUID = 1L;
	
	private static final int VIEWBUTTON   = 0;
	private static final int EDITBUTTON   = 1;
	private static final int DELETEBUTTON = 2;
	
	ArrayList<Document> partnerDocuments;
	ArrayList<Document> volunteerDocuments;
	User currentUser;
	
	final TextField<String> documentName = new TextField<String>("documentName",Model.of("")); 
    final TextField<String> documentType=new TextField<String>("documentType",Model.of(""));
    FileUploadField fileUploadField;
	transient DocumentDao documentDao;
	
	public PartnerDocumentView()
	{
		documentDao = new DocumentDao();
		
		// Add a new document
		Form<?>uploadForm=new Form<Void>("uploadDocumentForm") 
		{
			@Override
			public void onSubmit() 
			{
				try 
				{
					final FileUpload uploadedFile=fileUploadField.getFileUpload();
					
					if (uploadedFile!=null) 
					{
						File file = uploadedFile.writeToTempFile();
						Document document = new Document (documentDao.getUniqueId(), documentName.getModelObject(),documentType.getModelObject(), new Date(), file, currentUser.getEmail(), false, currentUser.getId());
						documentDao.insert(document);
						this.setResponsePage(PartnerDocumentView.class);
					}
				}
				catch(Exception e) 
				{
					throw new IllegalStateException("Error trying to do the .create or .writeto");
				}		
			}
		};
		uploadForm.setMultiPart(true);
		uploadForm.add(documentName);
		uploadForm.add(documentType);
		uploadForm.add(fileUploadField=new FileUploadField("uploadDocument"));
		add(uploadForm);
		
        // Display Partner and Volunteer Shared with Partner Documents
		currentUser=CustomSession.get().getUser();
		partnerDocuments=documentDao.getAllDocumentsByUser(currentUser);
		volunteerDocuments=documentDao.getAllDocumentsSharedWithPartner(currentUser);
		
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


public void processDocumentButton(int documentId, int viewEditDelete, boolean isPartner)
{
	Document document = null;
	ArrayList<Document> documents = null;
	
	if (isPartner)
	{
		documents = partnerDocuments;
	}
	else
	{
		documents = volunteerDocuments;
	}
	
	// Find document
	for (int i=0; i < documents.size(); i++)
	{
		document = documents.get(i);
	
		if (documentId == document.getId() )
		{
			break;
		}
	}
	
	// Process the document
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