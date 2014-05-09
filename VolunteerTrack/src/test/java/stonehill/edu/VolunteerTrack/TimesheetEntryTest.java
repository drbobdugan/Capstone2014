package stonehill.edu.VolunteerTrack;

import static org.junit.Assert.*;
import java.util.Date;
import org.junit.*;

public class TimesheetEntryTest {

	@Test
	public void testGetUserEmail() {
		TimesheetEntry testEntry =new TimesheetEntry("jscherr@gmail.com",new Date(7),"puppy petting",true,true,4,"");
		String testName = testEntry.getUserEmail();
		boolean compareNames = "jscherr@gmail.com".equals(testName);
		assertTrue(compareNames);
	}

	@Test
	public void testGetDateTime() {
		TimesheetEntry testEntry =new TimesheetEntry("jscherr@gmail.com",new Date(7),"puppy petting",true,true,4,"");
		Date testName = testEntry.getDateTime();
		boolean compareNames = (new Date(7)).equals(testName);
		assertTrue(compareNames);
	}

}
