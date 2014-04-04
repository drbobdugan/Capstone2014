package stonehill.edu.VolunteerTrack;

import org.apache.wicket.util.tester.WicketTester;
import org.junit.Before;
import org.junit.Test;

import stonehill.edu.VolunteerTrack.LoginView;
import stonehill.edu.VolunteerTrack.WicketApplication;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage
{
	private WicketTester tester;

	@Before
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	@Test
	public void homepageRendersSuccessfully()
	{
		//start and render the test page
		tester.startPage(LoginView.class);

		//assert rendered page class
		tester.assertRenderedPage(LoginView.class);
	}
}
