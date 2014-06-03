package stonehill.edu.VolunteerTrack;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

import org.junit.*;
public class DocumentTest {

	@Test
	public void testGetDateUploaded() {
		Date d=new Date();
		Document doc=new Document(-1, "KelseyTest","", d, new File("Test.txt"),"", true, -1);
		Date uploadTest=doc.getDateUploaded();
		boolean dates=d.equals(uploadTest);
		assertTrue(dates);
	}
}