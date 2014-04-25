package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.model.PropertyModel;
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
	
	public VolunteerTrackBaseView()
	{
		logout = new Form("logout");
		logout.add(new Button("logoutButton"){
			@Override
			public void onSubmit() {
				CustomSession.get().setUser(null);
				setResponsePage(LoginView.class);
			}
		});
		
		ArrayList<String> TYPES = new ArrayList<String>();
		User user = CustomSession.get().getUser();
		if(user.getIsApprovedCoordinator())
			TYPES.add("Coordinator");
		if(user.getIsApprovedPartner())
			TYPES.add("Partner");
		if(user.getIsApprovedVolunteer())
			TYPES.add("Volunteer");
		
		RadioChoice<String> userType = new RadioChoice<String>("userType", new PropertyModel<String>(this, "select"), TYPES);
		logout.add(userType);
		switchSingle = new Button("switchButton") {
			@Override
			public void onSubmit() {
				System.out.println("###### State switched to "+select+" ######");
				CustomSession.get().setState(select);
				LoginController log = new LoginController();
				log.redirectHome();
			}
		};
		logout.add(switchSingle);
		switchDaul = new Button("duality") {
			@Override
			public void onSubmit() {
				System.out.println("###### Switching back to Coordinator #####");
				LoginController log = new LoginController();
				log.switchBack();
			}
		};
		logout.add(switchDaul);
		add(logout);
		
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
		
		if(CustomSession.get().getSwitchOn() == false) {
			switchDaul.setVisible(false);			
		}
		//How to handle coordinator?
	}
}
