package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.Label;

public class TaylorTest extends WebPage
{
public Label message;
public TaylorTest()
{
message=new Label("message", "Taylor");
add(message);
}
}
