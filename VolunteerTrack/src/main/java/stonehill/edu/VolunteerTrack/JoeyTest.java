package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;

public class JoeyTest extends WebPage
{
public Label message;
public JoeyTest()
{
message=new Label("message","Joey");
add(message);
}

}