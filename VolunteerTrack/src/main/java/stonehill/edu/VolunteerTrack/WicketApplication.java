package stonehill.edu.VolunteerTrack;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;


/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 * 
 * @see stonehill.edu.BookVoter.Start#main(String[])
 */
public class WicketApplication extends WebApplication
{    	
	// This method allows us to create a customized session object which we can populate with 
	public Session newSession(Request request, Response response) {
		System.out.println("newSession()");
		daoTest.test();
		return new CustomSession(request);
	}
	
	/**
	 * @see org.apache.wicket.Application#getHomePage()
	 */
	public Class<LoginView> getHomePage()
	{
		return LoginView.class;
	}

}
