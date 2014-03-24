package stonehill.edu.VolunteerTrack;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;

public class TaylorTest extends WebPage
{
public Label message;
public TaylorTest()
{
message=new Label("message", "kjasdf");
add(message);
}
}
