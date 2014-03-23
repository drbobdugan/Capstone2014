package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class ZachariahView extends WebPage 
{
	private Label message;

	public ZachariahView()
	{
		message = new Label("message","Its Zac!");
		add(message);
	}
}
