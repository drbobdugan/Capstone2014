package stonehill.edu.VolunteerTrack;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.*;
import java.io.*;

import javax.swing.text.html.ListView;

import org.apache.wicket.model.*;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.list.ListItem;
public class ParDocumentView  extends VoltrackPage {
	
	User[]User; //user array that populates the volunteers that have entered documents linked to this partner
	ArrayList<Document> documents=new ArrayList<Document>(); //this populates the top table with documents that the partner may require for an event with them, and also 
	//will populate with View/Delete Buttons connected to that document
	DocumentDao doc;
	
	//DocumentModel document=new DocumentModel("Cori Form","Animal shelter",a,"link","knap@gmail.com",true);
	
	private Label message1;
	private Label message2;
	private Label message3;
	Document document;
	public ParDocumentView()
	{
    //message=new Label("message", "Happy Sunday Everyone!!");
    //add(message);
		Date a=new Date();
	//	document=new Document("Cori Form","Animal shelter",a,"link","knap@gmail.com",true);
	
	//	final DocumentModel document =item.getModelObject();
		message1=new Label("Name",document.getName());
		message2=new Label("Date",a.toString());
		message3=new Label("Description",document.getType());
		
		}
	}


/*
	
	
*/	
	//docume.insert(document);
	
	//ListView listView=new ListView("YourDocumentTable",d);
//	private Label y=new Label("Name","AnimalShelter");
/*	
Button addDocument=new Button("Add") { @override
			
void onSubmit() {
	
	//takes in the value of the document name
	Scanner input=new Scanner(System.in);
	String docName=input.nextLine();
	//Scanner docinput=new Scanner(System.in);
	//String docLink=docinput.nextLine();
	
	//creates new Document and inserts it into the database
	document=new DocumentModel();
	document.setName(docName);
	doc.insert(document);

	

	//when the add button is clicked, a pop up with prompt the user for the information 
	//about the documents and they will browse on their computer and click upload
}

};
*/
/*
void populateTable(ArrayList<DocumentModel> d){
	
	//DataTable dataTable=new DataTable("datatable")
	Date a=new Date();
	d.add(new DocumentModel("Cori Form","Animal shelter",a,"link","knap@gmail.com",true));
	
	//ListView listView=new ListView("YourDocumentTable",d);
	Label tableEntry;
	tableEntry=new Label("Name");
	
	
}
*/






