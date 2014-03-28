package stonehill.edu.VolunteerTrack;
import java.util.*;
public class daoTest {

	public static void test() {
		TimesheetEntryDao dao=new TimesheetEntryDao();
		ArrayList<Object> list=new ArrayList<Object>();
		list=dao.selectAll();
		for(Iterator i=list.iterator();i.hasNext();){
			System.out.println(((TimesheetEntry)i.next()).getUserEmail());
		}
		TimesheetEntry t;
		dao.insert(t=new TimesheetEntry("jscherr@gmail.com",new Date(),"puppy petting",true,true,4));
		System.out.println(dao.getTimesheetEntry(new Date(), "puppy petting", "jscherr@gmail.com").getEventName());
		dao.delete(t);
	}

}
