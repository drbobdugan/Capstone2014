package stonehill.edu.VolunteerTrack;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.Date;

import org.junit.*;

public class EventTest {

	@Test
	public void testGetEmail() {
		Event testEvent = new Event(-1, "zac@testing.com", "test event","this event is for testing",
				"In unit test land", "zac unit testing, co", 1,0, new Date(), new Date(), new Date(), new Skill[0]);
		String testEmail = testEvent.getOwnerEmail();
		boolean compareEmail = "zac@testing.com".equals(testEmail);
		assertTrue(compareEmail);
	}
	@Test
	public void testGetName() {
		Event testEvent = new Event(-1, "zac@testing.com", "test event","this event is for testing",
				"In unit test land", "zac unit testing, co", 1,0, new Date(), new Date(), new Date(), new Skill[0]);
		String testName = testEvent.getName();
		boolean compareName = "test event".equals(testName);
		assertTrue(compareName);
	}
	@Test
	public void testGetLocation() {
		Event testEvent = new Event(-1, "zac@testing.com", "test event","this event is for testing",
				"In unit test land", "zac unit testing, co", 1,0, new Date(), new Date(), new Date(), new Skill[0]);
		String testLocation = testEvent.getLocation();
		String testDescription = testEvent.getDescription();
		boolean compareLocation = "In unit test land".equals(testLocation);
		boolean compareDescription = "this event is for testing".equals(testLocation);
		
		assertTrue(compareLocation);
	}
	
	@Test
	public void testGetDescription() {
		Event testEvent = new Event(-1, "zac@testing.com", "test event","this event is for testing",
				"In unit test land", "zac unit testing, co", 1,0, new Date(), new Date(), new Date(), new Skill[0]);
		String testDescription = testEvent.getDescription();
		boolean compareDescription = "this event is for testing".equals(testDescription);
		
		assertTrue(compareDescription);
	}
}
