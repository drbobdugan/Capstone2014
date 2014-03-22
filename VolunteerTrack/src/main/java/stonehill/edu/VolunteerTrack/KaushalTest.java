package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.Label;

public class KaushalTest extends WebPage
{
public Label message;
public KaushalTest()
{
message=new Label("Yippie Kiyay");
add(message);
}

}
