package stonehill.edu.VolunteerTrack;

import java.util.*;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.markup.html.basic.Label;
//html and date picker 
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.Session;
import org.apache.wicket.Component;
import org.apache.wicket.extensions.*;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.form.Check;
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
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableTreeProvider;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.markup.repeater.data.DataView;
import org.apache.wicket.markup.repeater.data.ListDataProvider;
import org.apache.wicket.model.IModel;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.ui.panel.JQueryFeedbackPanel;
import com.googlecode.wicket.jquery.ui.widget.accordion.AccordionPanel;
import com.googlecode.wicket.jquery.ui.widget.tabs.AjaxTab;
import com.googlecode.wicket.jquery.ui.widget.tabs.SimpleTab;


public class PartnerReportResultsView extends VolunteerTrackBaseView {
	
	private static final long serialVersionUID = 1L;
	private ArrayList<Event> reportResults = new ArrayList<Event>();
	private ArrayList<User> usersSignedUpForEvent = new ArrayList<User>();
	private ArrayList allUsers;
	private User currentuser = CustomSession.get().getUser();
	private EventDao dao = new EventDao();
	private TimesheetEntryDao timesheetEntryDao = new TimesheetEntryDao();
	private UserDao userDao = new UserDao(); 
	private Button button;
	
	private int numberOfEvents;
	private int numberOfVolunteers;
	private int totalNumberofHours;
	
	private String timePeriod;
	
	public PartnerReportResultsView (ArrayList<Event> events){
		
		// Set up back button on page
		Form reportResultsView= new Form("reportResultsView")
		{
			@Override
			public void onSubmit() 
			{
				setResponsePage(PartnerReportView.class);
			}
		};
		add(reportResultsView);
		
		// Get users signed up for each event
		HashMap<Event, ArrayList<TimesheetEntry>> eventTimesheetMap = new HashMap<Event, ArrayList<TimesheetEntry>>();
		
		for (Event event : events)
		{
			ArrayList<TimesheetEntry> timesheetsForEvent = new ArrayList<TimesheetEntry>();
			
			for (User user : dao.getUsersThatAreSignedUpForEvent(currentuser, event))
			{
			   ArrayList<TimesheetEntry> userTimeSheets = timesheetEntryDao.getTimesheetEntriesByEventUser(event, user);
			   
			   for (TimesheetEntry entry : userTimeSheets)
			   {
				   timesheetsForEvent.add(entry);
			   }
			}
			
			eventTimesheetMap.put(event, timesheetsForEvent);
		}
		
		
		//
		//=========================
		Options options= new Options();
		options.set("heightStyle", Options.asString("content"));
		
		//set up Accordion//
		final AccordionPanel accordion = new AccordionPanel("accordion", this.newTabList(null), options){
			
			private static final long serialVersionUID = 1L;
			
			@Override
			public void onActivate(AjaxRequestTarget target, int index, ITab tab)
			{
				info(String.format("selected tab: #%d - %s", index, tab.getTitle().getObject()));
			}
			
		};
		reportResultsView.add(accordion);
   }
	
	private List<ITab> newTabList(ArrayList<Event> events)
	{
		List<ITab> tabs = new ArrayList<ITab>();
		String tabTitle="";
		String content="";
		//generate a new tab for each event
		for(int i =0; i<events.size(); i++){
			Event e = events.get(i);
			 tabTitle= e.getName() + " " + e.getStartDateTime() + " " + e.getLocation();
			ArrayList<User>volunteers = dao.getUsersThatAreSignedUpForEvent(currentuser, events.get(i));
			//System.out.println("////////////!!!!!~~~~~~" + volunteers.size() + "////////////!!!!!~~~~~~");
			if(volunteers.size()!=0)
			{
			for(int j=0; j<volunteers.size();j++)
			{
			User volunteer= volunteers.get(i);
			ArrayList<TimesheetEntry> t = timesheetEntryDao.getTimesheetEntriesByEventUser(e, volunteer);
			//content=content + " " + volunteer.getEmail() + ", ";
			//content =  content + volunteer.getFirstName() + " " +  volunteer.getLastName() + " "  + t.getHoursLogged();
			tabs.add( new SimpleTab(Model.of(tabTitle), Model.of(content)));
			}
		}
			else
			{
				content = "No Volunteers Signed Up for this Event";
				tabs.add( new SimpleTab(Model.of(tabTitle), Model.of(content)));
			}
		}
		return tabs;
	}
	
}