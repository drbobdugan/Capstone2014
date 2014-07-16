package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


public class TimesheetEntryDao extends Dao{
	public TimesheetEntryDao(){}
	
	/////////////////////////////////////////////////////////////////////////////
	public void delete(Object value) {
		TimesheetEntry timesheetEntry=(TimesheetEntry) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM TimeSheetEntry WHERE Id = "+ timesheetEntry.getId());
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////
	public void insert(Object value) {
		TimesheetEntry timesheetEntry=(TimesheetEntry) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO TimeSheetEntry VALUES("+
		    "'"+timesheetEntry.getUserEmail()+"', "+
		    "to_timestamp('"+new java.sql.Timestamp(timesheetEntry.getDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
		    "'"+timesheetEntry.getEventName()+"', "+
			"'"+(timesheetEntry.getIsSubmitted()?1:0)+"', "+
			"'"+(timesheetEntry.getIsApproved()?1:0)+"', "+
			"'"+timesheetEntry.getHoursLogged()+"', "+
			"'"+timesheetEntry.getOrganizationName()+"'"+
			timesheetEntry.getEventId()+ ", "+ 
			timesheetEntry.getUserId() + ", "+
			this.getUniqueId() + ")");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	//////////////////////////////////////////////////////////////////////////////////
	public void update(Object value) {
		TimesheetEntry timesheetEntry=(TimesheetEntry)value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("UPDATE TimeSheetEntry SET "+
					"OrganizationName='"+timesheetEntry.getOrganizationName()+"', "+
					"HoursLogged='"+timesheetEntry.getHoursLogged()+"', "+
					"IsApproved='"+(timesheetEntry.getIsApproved()?1:0)+"', "+
					"IsSubmitted='"+(timesheetEntry.getIsSubmitted()?1:0)+"',"+
					"UserEmail='"+timesheetEntry.getUserEmail()+"', "+
					"UserId="+timesheetEntry.getUserId()+", "+
					"EventName='"+timesheetEntry.getEventName()+"', "+ 
					"EventId="+timesheetEntry.getEventId()+", "+
					"DateTime=to_timestamp('"+new java.sql.Timestamp(timesheetEntry.getDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') " + 
					"WHERE Id= "+timesheetEntry.getId());
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/////////////////////////////////////////////////////////////////////////////
	public ArrayList<Object> selectAll() {
		String sql = "SELECT * FROM TimeSheetEntry";
		ArrayList<TimesheetEntry> timesheetEntryList = selectProcessor(sql);
		ArrayList<Object> result = new ArrayList<Object>();
		
		for (TimesheetEntry timesheetEntry : timesheetEntryList)
		{
			result.add(timesheetEntry);
		}
		
		return result;
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public ArrayList<TimesheetEntry> getTimesheetEntriesByEventUser(Event event, User user){
		String sql = "SELECT * FROM TimeSheetEntry WHERE EventId = " + event.getId() + " AND UserId = " + user.getId();
		return selectProcessor(sql);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	public ArrayList<TimesheetEntry> getTimesheetEntriesByUser(User user){
		String sql = "SELECT * FROM TimeSheetEntry WHERE UserId='"+user.getId()+"'";
		return selectProcessor(sql);
	}
	
	//////////////////////////////////////////////////////////////////////////////////
	public ArrayList<TimesheetEntry> getTimesheetEntriesByEvent(User event){
		String sql = "SELECT * FROM TimeSheetEntry WHERE EventId='"+event.getId()+"'";
		return selectProcessor(sql);
	}
	
   //////////////////////////////////////////////////////////////////////////////////
	private ArrayList<TimesheetEntry> selectProcessor(String sql) {
		ArrayList<TimesheetEntry> result=new ArrayList<TimesheetEntry>();
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			//get tuples
			while(resultSet.next()){
				int id = resultSet.getInt("id");
				String userEmail=resultSet.getString("UserEmail");
				int userId = resultSet.getInt("UserId");
				Date date=resultSet.getTimestamp("DateTime");
				String eventName=resultSet.getString("EventName");
				int eventId = resultSet.getInt("EventId");
				boolean isApproved=resultSet.getBoolean("IsApproved");
				boolean isSubmitted=resultSet.getBoolean("IsSubmitted");
				int hoursLogged=resultSet.getInt("HoursLogged");
				String organizationName=resultSet.getString("OrganizationName");
				result.add(new TimesheetEntry(userEmail,date,eventName,isSubmitted,isApproved,hoursLogged,organizationName, eventId, userId, id));
			}
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
}
