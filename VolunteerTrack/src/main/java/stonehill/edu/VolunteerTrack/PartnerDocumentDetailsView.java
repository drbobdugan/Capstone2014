package stonehill.edu.VolunteerTrack;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.extensions.yui.calendar.TimeField;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.util.resource.FileResourceStream;
import org.apache.wicket.util.resource.IResourceStream;

public class PartnerDocumentDetailsView extends VolunteerTrackBaseView
{
	private Document document;  // list of events specific to the partner
	private ArrayList<User> partnerList;
	private TextField name, location, position;
	private TextArea description;
	private Form form;
	private String returnTo;
	private final Date time1 = new Date();
	private final Date time2 = new Date();
	private ArrayList<User> volunteerList;
	private int aplicantid;

	public PartnerDocumentDetailsView(Document document, String returnTo)
	{
		this.document = document;
		this.returnTo = returnTo;

		DocumentDao documentDao = new DocumentDao();		
		partnerList = documentDao.getAllPartnersSharedWithDocument(document);	
		
		populateItems();
	}  

	

	@SuppressWarnings("deprecation")
	private void populateItems() {

		// create elements and add them to the form
		add(new TextField("name", new Model(document.getName())));
		add(new TextField("type", new Model(document.getType())));
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyy");
		add(new TextField("dateUploaded", new Model(dateFormat.format(document.getDateUploaded()))));
		
		IModel fileModel = new AbstractReadOnlyModel(){
		    public Object getObject() { 
		        return document.getFile();
		    }
		};

		DownloadLink link = new DownloadLink("file", fileModel, document.getName())
		{
			public void onClick()
			{
				final File file = (File)getModelObject();
				IResourceStream resourceStream = new FileResourceStream(new org.apache.wicket.util.file.File(file));
				ResourceStreamRequestHandler handler = new ResourceStreamRequestHandler(resourceStream, document.getName());        
		        getRequestCycle().scheduleRequestHandlerAfterCurrent(handler);
			}
		};
		add(link);
		
		add(new TextField("userEmail", new Model(document.getUserEmail())));

		//  create the form 
		form = new Form("form");
		form.add(new Button("back"){  
			@Override
			public void onSubmit() {
				if(returnTo.equals("partnerDocumentView"))
					setResponsePage(PartnerDocumentView.class);
			}
		});

		final DataView dataView = new DataView("simple", new ListDataProvider(partnerList)) {
			protected void populateItem(final Item item) {
				final User user = (User)item.getModelObject();
				item.add(new Link<Void>("view"){ public void onClick(){ view(user);}});
				item.add(new Label("organizationName", user.getOrganizationName()));
				item.add(new Label("userEmail", user.getEmail()));

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

		// add the form to the page
		add(form);
	}
	private void view(User partner ){
		setResponsePage(new SearchPartnerProfileView(partner));
	}
}