package stonehill.edu.VolunteerTrack;

import java.io.File;
import java.util.Date;

import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.model.Model;



public class PartnerDocumentUpdateView extends VolunteerTrackBaseView {
	final TextField<String> newDocName = new TextField<String>("DocumentName",Model.of("")); 
    final TextField<String> newDocType=new TextField<String>("DocumentType",Model.of(""));
    FileUploadField fileUpload;
	public PartnerDocumentUpdateView(final Document doc) {
		//update document stuff here
		
		
		Form uploadform=new Form("uploadform");
		Form backform=new Form("backForm");
		Button back=new Button("back") {
			@Override
			public void onSubmit(){
			this.setResponsePage(PartnerDocumentView.class);	
		}
		};
		backform.add(back);
		add(backform);
		
		
		Button view=new Button("view") {
			@Override
			public void onSubmit() {
				//do stuff here..add download link
			}
		};
		uploadform.add(view);
		
		
		Button add=new Button("Add") {
			@Override
			public void onSubmit() {
				
				final FileUpload uploadedFile=fileUpload.getFileUpload();
				//do stuff	
				if(uploadedFile!=null) {
					File newFile=new File ("/home/ubuntu/Desktop/test.txt");
					
					try {
						DocumentDao docDao=new DocumentDao();
						newFile=uploadedFile.writeToTempFile();
						Document test = new Document (newDocName.getModelObject(),newDocType.getModelObject(),new Date(), newFile, "partner@partner.com",false);
						docDao.delete(doc);
						docDao.insert(test);
						this.setResponsePage(PartnerDocumentView.class);
					}
			     catch(Exception e) {
			    	 throw new IllegalStateException("Error trying to do the .create or .writeto");
			    	    	 
			     }		
				}
			}
		};
		uploadform.setMultiPart(true);
		uploadform.add(add);
		uploadform.add(newDocName);
		uploadform.add(newDocType);
		uploadform.add(fileUpload=new FileUploadField("uploadDoc"));
		add(uploadform);
		
		
		
	}
}
