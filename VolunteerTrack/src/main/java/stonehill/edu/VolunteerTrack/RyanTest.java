/**
 * 
 */
package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;


public class RyanTest extends WebPage
{
private Label message;
public RyanTest()
{
message=new Label("message", "Hi");
add(message);
}
}
/**
 * 
 */
