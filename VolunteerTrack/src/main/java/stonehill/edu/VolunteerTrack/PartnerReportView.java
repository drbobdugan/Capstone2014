package stonehill.edu.VolunteerTrack;

import java.util.*;

import org.apache.wicket.markup.html.basic.Label;
//html and date picker 
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Session;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.*;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
//table imports

import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.CSVDataExporter;
import org.apache.wicket.extensions.markup.html.repeater.data.table.export.ExportToolbar;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;


public class PartnerReportView extends VolunteerTrackBaseView {

	//check group
	private static CheckGroup<Event> group; 
	//radio button choices
	private static final List<String> TYPES = Arrays
			.asList(new String [] { "By Day", "By Week","By Month", "By Year", "By End Date"});
	//default selected 
	private String selected ="By Week";
	private static final long serialVersionUID = 1L;

	//stuff for setting up calendars
	private Date startDate = new Date();
	private Date endDate = new Date();

	private DateTextField startDateTextField=null, endDateTextField=null;

	private Date checkStartDate= new Date();
	private Date checkEndDate= new Date();

	private Button runReport, filterEvents;
	transient EventDao eventDao= new EventDao();

	ArrayList<Event> events;

	public PartnerReportView()
	{			
		//	    RadioChoice<String>  reportBy = new RadioChoice<String>("calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		//	    //reportBy.setVisible(false);
		//		 startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
		//         endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));
		//		
		//        DatePicker startDatePicker = new DatePicker(){
		//        	
		//        	protected String getAdditionalJavascript()
		//        	{
		//        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
		//        	}
		//        }; 
		//        
		//       DatePicker endDatePicker = new DatePicker(){
		//        	
		//        	protected String getAdditionalJavascript()
		//        	{
		//        		return "${calendar}.cfg.setProperty(\"navigator\",true,false); ${calendar}.render()";
		//        	}
		//        }; 
		//        
		//        startDatePicker.setShowOnFieldClick(true);
		//        startDateTextField.add(startDatePicker);
		//        endDateTextField.add(endDatePicker); 
		//        
		//
		//        
		//        filterEvents= new Button("filterEvents"){
		//        	@SuppressWarnings("deprecation")
		//			@Override
		//        	public void onSubmit(){
		//        		
		//                	checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
		//                	checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());
		//                	
		//                setResponsePage(new PartnerReportView(checkStartDate, checkEndDate));
		//        	}
		//        };
		//          
		//		Form<?> form = new Form<Void>("form");
		//		
		//        Event selectedEvent=new Event();
		//        ArrayList<Object> temp = eventDao.selectAll();
		//        ArrayList<Event> e = new ArrayList<Event>();
		//		//filter events by date
		//        for(int i =0; i<temp.size(); i++)
		//        {
		//        	e.add(((Event)temp.get(i)));
		//        	
		//        }
		//        //create grouping
		//        Form<?> groupForm = new Form<Void>("groupForm")
		//        {
		//              	@Override
		//      			public void onSubmit(){
		//      				//info("Report By :" + selected);
		//      				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
		//      				String test="";
		//      				for(int i =0; i<selectedEvents.size(); i++){
		//      					test = test + selectedEvents.get(i).getName() + " ";
		//      				}
		//      				
		//      				setResponsePage(new PartnerReportResultsView(selectedEvents));
		//      				//info("selected event(s): " + test);
		//      			}
		//        };	
		//        
		//        group = new CheckGroup<Event>("group", new ArrayList<Event>());
		//        //creates list of events in html
		//        ListView events = new ListView("events", e){
		//        	protected void populateItem(ListItem item){
		//        		item.add(new Check("checkbox", item.getModel()));
		//        		item.add(new Label("eventName", new PropertyModel(item.getModel(),"name")));
		//        	}
		//        };
		//        
		//        events.setReuseItems(true);
		//        group.add(events);
		//        group.add(new CheckGroupSelector("groupselector"));
		//        groupForm.add(group);
		//        add(groupForm);
		//        
		//
		//        add(new FeedbackPanel("feedback"));
		//
		//        form.add(filterEvents);
		//		form.add(startDateTextField);
		//		form.add(endDateTextField);
		//		form.add(reportBy);
		//		add(form);

		processPartnerReport(startDate,endDate);
	}

	public PartnerReportView(Date start, Date end)
	{
		processPartnerReport(start,end);
	}

	public void processPartnerReport(Date start, Date end)
	{
		startDate=start;
		endDate = end;
		RadioChoice<String>  reportBy = new RadioChoice<String>("calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);

		startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
		endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));

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
		startDateTextField.add(startDatePicker);
		endDateTextField.add(endDatePicker); 


		Form eventDateForm = new Form("eventDateForm")
		{
			@Override
			public void onSubmit()
			{
				checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
				checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());

				setResponsePage(new PartnerReportView(checkStartDate, checkEndDate));
			}
		};
		eventDateForm.add(endDateTextField);
		eventDateForm.add(startDateTextField);

		Event selectedEvent=new Event();
		ArrayList<Event> allPartnerEvents = eventDao.getAllEventsByOwner(CustomSession.get().getUser());
		ArrayList<Event> filteredPartnerEvents  = new ArrayList<Event>();

		//filter events by date
		for(int i =0; i<allPartnerEvents.size(); i++)
		{
			Event event = (Event)allPartnerEvents.get(i);
			if(start.before(event.getStartDateTime()) && end.after(event.getEndDateTime())){
				filteredPartnerEvents.add(((Event)allPartnerEvents.get(i)));
			}

		}
		//create grouping	
		group = new CheckGroup<Event>("group", new ArrayList<Event>());

		//creates list of events in html
		ListView events = new ListView("events", filteredPartnerEvents){
			protected void populateItem(final ListItem item){
				item.add(new Check("checkbox", item.getModel()));
				item.add(new Label("name", new PropertyModel(item.getModel(),"name")));
				item.add(new Label("location", new PropertyModel(item.getModel(),"location")));
				item.add(new Label("startDate", new PropertyModel(item.getModel(),"startDateTime")));
				item.add(new Label("endDate", new PropertyModel(item.getModel(),"endDateTime")));
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

		events.setReuseItems(true);
		group.add(events);
		group.add(new CheckGroupSelector("groupselector"));

		Form form = new Form("form")
		{
			@Override
			public void onSubmit()
			{
				System.out.println("Report By :" + selected);
				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
				String test="";
				for(int i =0; i<selectedEvents.size(); i++){
					test = test + selectedEvents.get(i).getName() + " ";
				}

				setResponsePage(new PartnerReportResultsView(selectedEvents));
				System.out.println("selected event(s): " + test);
			}
		};
		//         {
		//                	 @Override
		//                	 public void onSubmit()
		//                	 {
		//                		 //info("Report By :" + selected);
		//                		 ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
		//                		 String test="";
		//                		 for(int i =0; i<selectedEvents.size(); i++){
		//                			 test = test + selectedEvents.get(i).getName() + " ";
		//                		 }
		//
		//                		 setResponsePage(new PartnerReportResultsView(selectedEvents));
		//                		 //info("selected event(s): " + test);
		//         };

		//form.add(runReport);
		form.add(group);
		form.add(reportBy);
		add(eventDateForm);
		add(form);
	}
}
