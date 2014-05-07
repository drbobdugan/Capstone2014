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
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
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
	 
	 final DateTextField startDateTextField, endDateTextField;
	 
	 private Date checkStartDate= new Date();
	 private Date checkEndDate= new Date();
	 
	 private Button runReport, filterEvents;
    EventDao eventDao= new EventDao();
	ArrayList<Event> events;
	
	public PartnerReportView()
	{
		//add(new FeedbackPanel("feedback"));
				
		RadioChoice<String>  reportBy = new RadioChoice<String>(
				"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		
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
        

        
        filterEvents= new Button("filterEvents"){
        	@Override
        	public void onSubmit(){
        		
                	checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
                	checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());
                	
                setResponsePage(new PartnerReportView(checkStartDate, checkEndDate));
        	}
        };
        
        runReport= new Button("runReport"){
        	@Override
			public void onSubmit(){
				//info("Report By :" + selected);
				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
				String test="";
				for(int i =0; i<selectedEvents.size(); i++){
					test = test + selectedEvents.get(i).getName() + " ";
				}
				
				setResponsePage(new PartnerReportResultsView(selectedEvents));
				//info("selected event(s): " + test);
			}
        };
        
		Form<?> form = new Form<Void>("form");
		
        Event selectedEvent=new Event();
        ArrayList<Object> temp = eventDao.selectAll();
        ArrayList<Event> e = new ArrayList<Event>();
		//filter events by date
        for(int i =0; i<temp.size(); i++)
        {
        	e.add(((Event)temp.get(i)));
        	
        }
        //create grouping
        group = new CheckGroup<Event>("group", new ArrayList<Event>());
        //creates list of events in html
        ListView events = new ListView("events", e){
        	protected void populateItem(ListItem item){
        		item.add(new Check("checkbox", item.getModel()));
        		item.add(new Label("eventName", new PropertyModel(item.getModel(),"name")));
        	}
        };
        
        events.setReuseItems(true);
        group.add(events);
        group.add(new CheckGroupSelector("groupselector"));
        group.add(runReport);
        

        add(new FeedbackPanel("feedback"));

        //form.add(runReport);
        form.add(filterEvents);
        form.add(group);
		form.add(startDateTextField);
		form.add(endDateTextField);
		form.add(reportBy);
		add(form);
		
	}

	public PartnerReportView(Date start, Date end)
	{
		startDate=start;
		endDate = end;
		RadioChoice<String>  reportBy = new RadioChoice<String>(
				"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		
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
         
         filterEvents= new Button("filterEvents"){
         	@Override
         	public void onSubmit(){
         		
                 	checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
                 	checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());
                 	
                 	
                 setResponsePage(new PartnerReportView(checkStartDate, checkEndDate));
         	}
         };
         
         runReport= new Button("runReport"){
         	@Override
 			public void onSubmit(){
 				//info("Report By :" + selected);
 				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
 				String test="";
 				for(int i =0; i<selectedEvents.size(); i++){
 					test = test + selectedEvents.get(i).getName() + " ";
 				}
 				
 				setResponsePage(new PartnerReportResultsView(selectedEvents));
 				//info("selected event(s): " + test);
 			}
         };

         Form<?> form = new Form<Void>("form");
 		
         Event selectedEvent=new Event();
         ArrayList<Object> temp = eventDao.selectAll();
         ArrayList<Event> e = new ArrayList<Event>();
 		//filter events by date
         for(int i =0; i<temp.size(); i++)
         {
        	 Event event = (Event)temp.get(i);
        	if(start.before(event.getStartDateTime()) && end.after(event.getEndDateTime())){
         	e.add(((Event)temp.get(i)));
        	}
         	
         }
         //create grouping
         group = new CheckGroup<Event>("group", new ArrayList<Event>());
         //creates list of events in html
         ListView events = new ListView("events", e){
         	protected void populateItem(ListItem item){
         		item.add(new Check("checkbox", item.getModel()));
         		item.add(new Label("eventName", new PropertyModel(item.getModel(),"name")));
         	}
         };
         
         events.setReuseItems(true);
         group.add(events);
         group.add(new CheckGroupSelector("groupselector"));
         group.add(runReport);
         

         add(new FeedbackPanel("feedback"));

         //form.add(runReport);
        form.add(filterEvents);
        form.add(group);
 		form.add(startDateTextField);
 		form.add(endDateTextField);
 		form.add(reportBy);
 		add(form);


	}
}
