package stonehill.edu.VolunteerTrack;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.Date;

import org.junit.*;

public class UserTest {

	@Test
	public void testGetEmail() {
		User testUser = new User("testemail@test.com", "testpass", "first", "last", "street", "city", "state", "zip", "phone", "description", 
				"description", false, false, false, "major", "minor", false, false, false, "orgname");
		String testEmail = testUser.getEmail();
		boolean compareEmail = "testemail@test.com".equals(testEmail);
		assertTrue(compareEmail);
	}
	@Test
	public void testGetFirstSName() {
		User testUser = new User("testemail@test.com", "testpass", "first", "last", "street", "city", "state", "zip", "phone", "description", 
				"description", false, false, false, "major", "minor", false, false, false, "orgname");
		String testName = testUser.getFirstName();
		boolean compareName = "first".equals(testName);
		assertTrue(compareName);
	}
	
	@Test
	public void testGetMajor() {
		User testUser = new User("testemail@test.com", "testpass", "first", "last", "street", "city", "state", "zip", "phone", "description", 
				"description", false, false, false, "major", "minor", false, false, false, "orgname");
		String testDescription = testUser.getMajor();
		boolean compareDescription = "major".equals(testDescription);
		
		assertTrue(compareDescription);
	}
}
