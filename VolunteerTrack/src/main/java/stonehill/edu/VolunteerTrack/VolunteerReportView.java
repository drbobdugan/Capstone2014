package stonehill.edu.VolunteerTrack;

import java.util.*;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Form;
//table imports

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.Button;


public class VolunteerReportView extends VolunteerTrackBaseView {

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
	TimesheetEntryDao timesheetEntryDao = new TimesheetEntryDao();
	ArrayList<Event> events;

	private static final User currentUser = CustomSession.get().getUser();
	
	public VolunteerReportView()
	{
		//add(new FeedbackPanel("feedback"));

		RadioChoice<String>  reportBy = new RadioChoice<String>(
				"calendarRadio", new PropertyModel<String>(this,"selected"),TYPES);
        reportBy.setVisible(false);
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
			@SuppressWarnings("deprecation")
			@Override
			public void onSubmit(){

				checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
				checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());

				setResponsePage(new VolunteerReportView(checkStartDate, checkEndDate));
			}
		};

		runReport= new Button("runReport"){
			@Override
			public void onSubmit(){
				//info("Report By :" + selected);
				ArrayList<Object> temp = new ArrayList<Object>();
				ArrayList<TimesheetEntry> timesheets = new ArrayList<TimesheetEntry>();
				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
				temp =timesheetEntryDao.getAllTimesheetEntriesByUser(currentUser);
				//temp =timesheetEntryDao.selectAll();
				System.out.println("-------------------------------------------------");
				System.out.println("-------------------------------------------------");
				System.out.println("-------------------------------------------------");
				System.out.println("Temp Size is............" + temp.size());
				System.out.println("-------------------------------------------------");
				System.out.println("-------------------------------------------------");
				System.out.println("-------------------------------------------------");
				// get all timesheets for each event
				for(int i=0; i<selectedEvents.size(); i++){
					Event e = selectedEvents.get(i);
					TimesheetEntry t = (TimesheetEntry) temp.get(i);
					System.out.println("-------------------------------------------------");
					System.out.println("-------------------------------------------------");
					System.out.println("-------------------------------------------------");
					System.out.println("Timesheet for Event Name............" + t.getEventName());
					System.out.println("-------------------------------------------------");
					System.out.println("-------------------------------------------------");
					System.out.println("-------------------------------------------------");
					if(t.getEventName()!=null)
					{
						timesheets.add(t);
					}
				}

				//setResponsePage(new VolunteerReportResultsView(selectedEvents, timesheets));
				//info("selected event(s): " + test);
			}
		};

		Form<?> form = new Form<Void>("form");

		Event selectedEvent=new Event();
		ArrayList<Object> temp = eventDao.getAllEventsByUserTimeSheetEntries(currentUser);
		ArrayList<Event> e = new ArrayList<Event>();
		//add events to event list
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
//===================================================
	public VolunteerReportView(Date start, Date end)
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
			@SuppressWarnings("deprecation")
			@Override
			public void onSubmit(){

				checkStartDate= new Date(startDateTextField.getDefaultModelObjectAsString());
				checkEndDate = new Date(endDateTextField.getDefaultModelObjectAsString());


				setResponsePage(new VolunteerReportView(checkStartDate, checkEndDate));
			}
		};

		runReport= new Button("runReport"){
			@Override
			public void onSubmit(){
				//info("Report By :" + selected);
				ArrayList<Object> temp = new ArrayList<Object>();
				ArrayList<TimesheetEntry> timesheets = new ArrayList<TimesheetEntry>();
				ArrayList<Event> selectedEvents = (ArrayList<Event>) group.getDefaultModelObject();
				temp =timesheetEntryDao.getAllTimesheetEntriesByUser(currentUser);

				for(int i=0; i<selectedEvents.size(); i++){
					Event e = selectedEvents.get(i);
					TimesheetEntry t = timesheetEntryDao.getTimesheetEntry(e.getCreatedDateTime(), e.getName(), currentUser.getEmail());
					timesheets.add(t);
				}

				//setResponsePage(new VolunteerReportResultsView(selectedEvents, timesheets));
				//info("selected event(s): " + test);
			}
		};

		Form<?> form = new Form<Void>("form");

		Event selectedEvent=new Event();
		ArrayList<Object> temp = eventDao.getAllEventsByUserTimeSheetEntries(currentUser);
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
