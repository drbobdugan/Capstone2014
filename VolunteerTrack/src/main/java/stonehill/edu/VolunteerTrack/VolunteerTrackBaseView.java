package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.eclipse.jetty.util.log.Logger;

import com.googlecode.wicket.jquery.ui.form.RadioChoice;
import com.googlecode.wicket.jquery.ui.form.button.Button;

public class VolunteerTrackBaseView extends WebPage implements Serializable {

	BookmarkablePageLink vhome;
	BookmarkablePageLink phome;
	BookmarkablePageLink chome;
	BookmarkablePageLink pevent;
	BookmarkablePageLink vhour;
	BookmarkablePageLink pprofile;
	BookmarkablePageLink vprofile;
	BookmarkablePageLink pdocs;
	BookmarkablePageLink vdocs;
	BookmarkablePageLink preport;
	BookmarkablePageLink vreport;
	BookmarkablePageLink creport;
	BookmarkablePageLink psearch;
	BookmarkablePageLink vsearch;
	BookmarkablePageLink csearch;
	BookmarkablePageLink ccriteria;

	Form logout;
	String select = CustomSession.get().getState();
	Button switchSingle, switchDaul;
	LoginController log = new LoginController();

	public VolunteerTrackBaseView()
	{
		//Check someone is logged in, if not redirect to login page
		if(log.authenticate() == false)
			setResponsePage(LoginView.class);

		// Logout link always appears
		add(new Link("logout-link")
		{
			public void onClick()
			{
				CustomSession.get().setUser(null);
				setResponsePage(LoginView.class);
			}
		});

		
		int countPersonas = 0;
		if (CustomSession.get().getUser().getIsApprovedCoordinator()) countPersonas++;
		if (CustomSession.get().getUser().getIsApprovedPartner()) countPersonas++;
		if (CustomSession.get().getUser().getIsApprovedVolunteer()) countPersonas++;
		if (CustomSession.get().getSwitchOn()) countPersonas++;
		

		Label personaSwitchToLabel = new Label("persona-switch-to-label","Switch To:");
		add(personaSwitchToLabel);
		personaSwitchToLabel.setVisible(countPersonas > 1);

		Link personaCoordinatorLink = new Link("persona-coordinator-link")
		{
			public void onClick()
			{
				System.out.println("###### State switched to Coordinator ######");
				CustomSession.get().setState("Coordinator");
				LoginController log = new LoginController();
				log.redirectHome();
			}
		};
		add(personaCoordinatorLink);
		personaCoordinatorLink.setVisible(CustomSession.get().getUser().getIsApprovedCoordinator() && (countPersonas > 1));

		Link personaPartnerLink = new Link("persona-partner-link")
		{
			public void onClick()
			{
				if (CustomSession.get().getUser().getIsApprovedPartner())
				{
					System.out.println("###### State switched to Partner ######");
					CustomSession.get().setState("Partner");
					LoginController log = new LoginController();
					log.redirectHome();
				}
			}
		};
		add(personaPartnerLink);
		personaPartnerLink.setVisible(CustomSession.get().getUser().getIsApprovedPartner() && (countPersonas > 1));


		Link personaVolunteerLink = new Link("persona-volunteer-link")
		{
			public void onClick()
			{
				if (CustomSession.get().getUser().getIsApprovedVolunteer())
				{
					System.out.println("###### State switched to Volunteer ######");
					CustomSession.get().setState("Volunteer");
					LoginController log = new LoginController();
					log.redirectHome();
				}
			}
		};
		add(personaVolunteerLink);
		personaVolunteerLink.setVisible(CustomSession.get().getUser().getIsApprovedVolunteer() && (countPersonas > 1));

		Link switchUserLink = new Link("switch-user-link") 
		{
			public void onClick() {
				System.out.println("###### Clicked on switch-users-link TBD ######");
			}
		};
		add(switchUserLink);
		switchUserLink.setVisible(CustomSession.get().getSwitchOn());


		//Add all the links to the bar for menu, set visibility depending on which persona is in use
		add(vhome = new BookmarkablePageLink("vhome", VolunteerHomeView.class));
		add(phome = new BookmarkablePageLink("phome", PartnerHomeView.class));
		add(chome = new BookmarkablePageLink("chome", CoordinatorHomeView.class));
		add(vhour = new BookmarkablePageLink("vhour", VolunteerHourView.class));
		add(pevent = new BookmarkablePageLink("pevent", PartnerEventView.class));
		add(vprofile = new BookmarkablePageLink("vprofile", VolunteerProfileView.class));
		add(pprofile = new BookmarkablePageLink("pprofile", PartnerProfileView.class));
		add(vdocs = new BookmarkablePageLink( "vdocs", VolunteerDocumentView.class));
		add(pdocs = new BookmarkablePageLink( "pdocs", PartnerDocumentView.class));
		add(vreport = new BookmarkablePageLink( "vreport", VolunteerReportView.class));
		add(preport = new BookmarkablePageLink( "preport", PartnerReportView.class));
		add(creport = new BookmarkablePageLink( "creport", CoordinatorReportView.class));
		add(vsearch = new BookmarkablePageLink( "vsearch", VolunteerSearchView.class));
		add(psearch = new BookmarkablePageLink( "psearch", PartnerSearchView.class));
		add(csearch = new BookmarkablePageLink( "csearch", CoordinatorSearchPage.class));
		add(ccriteria = new BookmarkablePageLink( "ccriteria", CoordinatorEditCriteriaView.class));

		if(CustomSession.get().getState().equals("Coordinator")) {
			phome.setVisible(false);
			pevent.setVisible(false);
			pprofile.setVisible(false);
			pdocs.setVisible(false);
			preport.setVisible(false);
			psearch.setVisible(false);
			vhome.setVisible(false);
			vhour.setVisible(false);
			vprofile.setVisible(false);
			vdocs.setVisible(false);
			vreport.setVisible(false);
			vsearch.setVisible(false);
		} else if(CustomSession.get().getState().equals("Partner")) {
			chome.setVisible(false);
			creport.setVisible(false);
			csearch.setVisible(false);
			ccriteria.setVisible(false);
			vhome.setVisible(false);
			vhour.setVisible(false);
			vprofile.setVisible(false);
			vdocs.setVisible(false);
			vreport.setVisible(false);
			vsearch.setVisible(false);
		} else if(CustomSession.get().getState().equals("Volunteer")) {
			chome.setVisible(false);
			creport.setVisible(false);
			csearch.setVisible(false);
			ccriteria.setVisible(false);
			phome.setVisible(false);
			pevent.setVisible(false);
			pprofile.setVisible(false);
			pdocs.setVisible(false);
			preport.setVisible(false);
			psearch.setVisible(false);
		}
	}
}
