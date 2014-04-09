package stonehill.edu.VolunteerTrack;

import java.util.*;

import org.apache.wicket.markup.html.basic.Label;
//html and date picker 
import org.apache.wicket.markup.html.form.*;
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


public class ParReportView extends VoltrackPage {
	
	//radio button choices
	private static final List<String> TYPES = Arrays
			.asList(new String [] { "By Day", "By Week","By Month", "By Year", "By End Date"});
	//default selected option
	private String selected ="By Week";
	private static final long serialVersionUID = 1L;
	
	//stuff for setting up calendars
	 private final Date startDate = new Date();
	 private final Date endDate = new Date();
	 
    EventDao eventDao= new EventDao();
	ArrayList<Event> events;
	
	public ParReportView()
	{
		//add(new FeedbackPanel("feedback"));
		
		RadioChoice<String>  reportBy = new RadioChoice<String>(
				"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
		
		DateTextField startDateTextField = new DateTextField("startDateTextField", new PropertyModel<Date>(this, "startDate"));
        DateTextField endDateTextField = new DateTextField("endDateTextField", new PropertyModel<Date>(this, "endDate"));
		
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
        
        //create table of events
   /*     ArrayList<Object> temp = eventDao.selectAll();
        System.out.println("           Temp Size is :" +      temp.size());
        for(Object o: temp){
        	events.add((Event)o);
        } */
           
       // makeEventsTable();
        
		Form<?> form = new Form<Void>("form"){
			@Override
			protected void onSubmit(){
				//info("Report By :" + selected);
			}
		};
		
		form.add(startDateTextField);
		form.add(endDateTextField);
		form.add(reportBy);
		add(form);
		
	}
	
	/*public void makeEventsTable()
	{
		final DataView dataView = new DataView("eventTable", new ListDataProvider(events)) {
			protected void populateItem(Item item) {
				final Event event = (Event)item.getModelObject();
				item.add(new Label("name", event.getName()));
				item.add(new Label("location", event.getLocation()));
				item.add(new Label("date", event.getDate().toString()));
				item.add(new Label("positions", event.getNumPositions()));
				item.add(new Label("available", event.getNumPositionsRemaining()));
			}
		};
		
		dataView.setItemsPerPage(5);
		add(dataView);
		add(new PagingNavigator("navigator", dataView));
	} */

}
