package stonehill.edu.VolunteerTrack;

import java.io.Serializable;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.eclipse.jetty.util.log.Logger;

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
	
	Form logout;
	
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
		add(logout);
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
		
		if(CustomSession.get().getUser().getIsCoordinator() == true)
		{
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
		}
		else if(CustomSession.get().getUser().getIsPartner() == true)
		{
			chome.setVisible(false);
			creport.setVisible(false);
			vhome.setVisible(false);
			vhour.setVisible(false);
			vprofile.setVisible(false);
			vdocs.setVisible(false);
			vreport.setVisible(false);
			vsearch.setVisible(false);
			chome.setVisible(false);
			creport.setVisible(false);
		}
		else if(CustomSession.get().getUser().getIsVolunteer() == true)
		{
			chome.setVisible(false);
			creport.setVisible(false);
			phome.setVisible(false);
			pevent.setVisible(false);
			pprofile.setVisible(false);
			pdocs.setVisible(false);
			preport.setVisible(false);
			psearch.setVisible(false);
			chome.setVisible(false);
			creport.setVisible(false);
		}

		
		//How to handle coordinator?
	}
}
