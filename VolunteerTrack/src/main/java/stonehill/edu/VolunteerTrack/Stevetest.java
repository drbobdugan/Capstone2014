/**
 * 
 */
package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import org.apache.wicket.markup.html.*;
import org.apache.wicket.markup.html.basic.Label;


public class Stevetest extends WebPage
{
private Label message;
public Stevetest()
{
message=new Label("message", "Hello!");
add(message);
}
}
/**
 * 
 */
