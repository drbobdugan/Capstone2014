package stonehill.edu.VolunteerTrack;

import org.apache.wicket.request.Request;
import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;

public class CustomSession extends WebSession {

	public CustomSession(Request request) {
		super(request);
		// TODO Auto-generated constructor stub
	}

	private static final long serialVersionUID = 1L;
	private User user, switchBack;	
	
	//public CustomSession(Request request) {
	//	super(request);
	//}

	public static CustomSession get()
	{
		return (CustomSession) Session.get();
	}
		
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public User getSwitchUser() {
		return switchBack;
	}
	
	public void setSwitchBack(User user) {
		this.switchBack = user;
	}
	// User is set at login time...
	public boolean isAuthenticated()
	{
		return (user!=null);
	}
}
