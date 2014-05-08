package stonehill.edu.VolunteerTrack;

import static org.junit.Assert.*;




import java.awt.image.BufferedImage;
import java.util.Date;

import org.junit.*;

public class PhotoTest {

	@Test
	public void testGetName() {
		Photo testPhoto = new Photo("KaushalTest","Kaushal@Kaushal.com",new Date(),new BufferedImage(1,2,3), false,true, true, false);
		String testName = testPhoto.getName();
		boolean compareNames = "KaushalTest".equals(testName);
		assertTrue(compareNames);
	}

	@Test
	public void testGetUserEmail() {
		Photo testPhoto = new Photo("KaushalTest","Kaushal@Kaushal.com",new Date(),new BufferedImage(1,2,3), false,true, true, false);
		String testEmail = testPhoto.getUserEmail();
		boolean compareEmails = "Kaushal@Kaushal.com".equals(testEmail);
		assertTrue(compareEmails);
	}

}
