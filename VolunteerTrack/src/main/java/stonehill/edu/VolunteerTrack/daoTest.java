package stonehill.edu.VolunteerTrack;
import java.util.*;
public class daoTest {

	public static void test() {
		TimesheetEntryDao dao=new TimesheetEntryDao();
		ArrayList<Object> list=new ArrayList<Object>();
		TimesheetEntry t;
		dao.insert(t=new TimesheetEntry("jscherr@gmail.com",new Date(7),"puppy petting",true,true,4));
		list=dao.selectAll();
		for(Iterator i=list.iterator();i.hasNext();){
			System.out.println(((TimesheetEntry)i.next()).getUserEmail());
		}
		System.out.println(dao.getTimesheetEntry(new Date(7), "puppy petting", "jscherr@gmail.com").getEventName());
		dao.update(t);
		dao.delete(t);
		
		UserDao dao2=new UserDao();
		ArrayList<Object> list2=new ArrayList<Object>();
		User u;
		dao2.insert(u=new User("jscherr@gmail.com","g","","","","","","","","","",true,true,true,true));
		list2=dao2.selectAll();
		for(Iterator i=list2.iterator();i.hasNext();){
			System.out.println(((User)i.next()).getEmail());
		}
		dao2.update(u);
		dao2.delete(u);
		
		DocumentDao dao3=new DocumentDao();
		ArrayList<Object> list3=new ArrayList<Object>();
		Document d;
		dao3.insert(d=new Document("doc","",new Date(8),".com","j.com",true));
		list3=dao3.selectAll();
		for(Iterator i=list3.iterator();i.hasNext();){
			System.out.println(((Document)i.next()).getName());
		}
		dao3.update(d);
		dao3.delete(d);
		
		SkillDao dao4=new SkillDao();
		ArrayList<Object> list4=new ArrayList<Object>();
		Skill s;
		dao4.insert(s=new Skill("skill"));
		list4=dao4.selectAll();
		for(Iterator i=list4.iterator();i.hasNext();){
			System.out.println(((Skill)i.next()).getName());
		}
		dao4.update(s);
		dao4.delete(s);
	}

}
