package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class HelloWorldNP extends WebPage{
	
	private Label message;
	
  public HelloWorldNP(){
	 message = new Label("message", "My Name is Nick Piazza");
	 add(message);
  }
}
