package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.eclipse.jetty.util.log.Logger;

public class VoltrackPage extends WebPage implements Serializable {
	
	BookmarkablePageLink vhome;
	BookmarkablePageLink phome;
	BookmarkablePageLink pevent;
	BookmarkablePageLink vhour;
	BookmarkablePageLink pprofile;
	BookmarkablePageLink vprofile;
	BookmarkablePageLink pdocs;
	BookmarkablePageLink vdocs;
	BookmarkablePageLink preport;
	BookmarkablePageLink vreport;
	BookmarkablePageLink psearch;
	BookmarkablePageLink vsearch;
	
	public VoltrackPage()
	{
		
		add(vhome = new BookmarkablePageLink("vhome", VolHomeView.class));
		add(phome = new BookmarkablePageLink("phome", ParHomeView.class));
		add(vhour = new BookmarkablePageLink("vhour", VolHourView.class));
		add(pevent = new BookmarkablePageLink("pevent", ParEventView.class));
		add(vprofile = new BookmarkablePageLink("vprofile", VolProfileView.class));
		add(pprofile = new BookmarkablePageLink("pprofile", ParProfileView.class));
		add(vdocs = new BookmarkablePageLink( "vdocs", VolDocumentView.class));
		add(pdocs = new BookmarkablePageLink( "pdocs", ParDocumentView.class));
		add(vreport = new BookmarkablePageLink( "vreport", VolReportView.class));
		add(preport = new BookmarkablePageLink( "preport", ParReportView.class));
		add(vsearch = new BookmarkablePageLink( "vsearch", VolSearchView.class));
		add(psearch = new BookmarkablePageLink( "psearch", ParSearchView.class));
		
		System.out.println("############# "+CustomSession.get().getUser().getIsVolunteer());
		
		
		if(CustomSession.get().getUser().getIsVolunteer() == true)
		{
			phome.setVisible(false);
			pevent.setVisible(false);
			pprofile.setVisible(false);
			pdocs.setVisible(false);
			preport.setVisible(false);
			psearch.setVisible(false);
		}
		
		if(CustomSession.get().getUser().getIsPartner() == true)
		{
			vhome.setVisible(false);
			vhour.setVisible(false);
			vprofile.setVisible(false);
			vdocs.setVisible(false);
			vreport.setVisible(false);
			vsearch.setVisible(false);
		}
		
		//How to handle coordinator?
	}
}
