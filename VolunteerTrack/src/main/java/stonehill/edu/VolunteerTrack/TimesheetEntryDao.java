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
			statement.executeQuery("DELETE FROM TimeSheetEntry WHERE "+
		    "UserEmail='"+timesheetEntry.getUserEmail()+"' AND "+
		    "EventName='"+timesheetEntry.getEventName()+"' AND "+
			"DateTime=to_date('"+new java.sql.Timestamp(timesheetEntry.getDateTime().getTime()).toString()+"','yyyy-mm-dd hh:mm:ss.fffffffff')");
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
		    "to_date('"+new java.sql.Timestamp(timesheetEntry.getDateTime().getTime()).toString()+"','yyyy-mm-dd hh:mm:ss.fffffffff'), "+
		   //"to_date('2000-10-10','yyyy-mm-dd'), "+
		    "'"+timesheetEntry.getEventName()+"', "+
			"'"+(timesheetEntry.getIsSubmitted()?1:0)+"', "+
			"'"+(timesheetEntry.getIsApproved()?1:0)+"', "+
			"'"+timesheetEntry.getHoursLogged()+"', "+
			"'"+timesheetEntry.getOrganizationName()+"')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////
	public ArrayList<Object> selectAll() {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM TimeSheetEntry");
			//get tuples
			while(resultSet.next()){
				String userEmail=resultSet.getString("UserEmail");
				Date date=resultSet.getDate("DateTime");
				String eventName=resultSet.getString("EventName");
				boolean isApproved=resultSet.getBoolean("IsApproved");
				boolean isSubmitted=resultSet.getBoolean("IsSubmitted");
				int hoursLogged=resultSet.getInt("HoursLogged");
				String organizationName=resultSet.getString("OrganizationName");
				result.add(new TimesheetEntry(userEmail,date,eventName,isSubmitted,isApproved,hoursLogged,organizationName));
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
			"IsSubmitted='"+(timesheetEntry.getIsSubmitted()?1:0)+"' WHERE "+
			"UserEmail='"+timesheetEntry.getUserEmail()+"' AND "+
			"EventName='"+timesheetEntry.getEventName()+"' AND "+
			"DateTime=to_date('"+new java.sql.Timestamp(timesheetEntry.getDateTime().getTime()).toString()+"','yyyy-mm-dd hh:mm:ss.fffffffff') ");
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	/////////////////////////////////////////////////////////////////////////////////////
	public TimesheetEntry getTimesheetEntry(Date date, String eventName,String userEmail){
		TimesheetEntry result=null;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM TimeSheetEntry WHERE DateTime=to_date('"+new java.sql.Timestamp(date.getTime()).toString()+"','yyyy-mm-dd hh:mm:ss.fffffffff') AND EventName='"+eventName+"' AND UserEmail='"+userEmail+"'");
			//get tuples
			if(resultSet.next()){
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateTime");
				String en=resultSet.getString("EventName");
				boolean isApproved=resultSet.getBoolean("IsApproved");
				boolean isSubmitted=resultSet.getBoolean("IsSubmitted");
				int hoursLogged=resultSet.getInt("HoursLogged");
				String organizationName=resultSet.getString("OrganizationName");
				result=(new TimesheetEntry(ue,d,en,isSubmitted,isApproved,hoursLogged,organizationName));
			}else{
				System.out.println("TimesheetEntryDao:getTimesheetEntry() did not return a tuple.");
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
	//////////////////////////////////////////////////////////////////////////////////
	public ArrayList<Object> getAllTimesheetEntriesByUser(User user){
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM TimeSheetEntry WHERE UserEmail='"+user.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				String userEmail=resultSet.getString("UserEmail");
				Date date=resultSet.getDate("DateTime");
				String eventName=resultSet.getString("EventName");
				boolean isApproved=resultSet.getBoolean("IsApproved");
				boolean isSubmitted=resultSet.getBoolean("IsSubmitted");
				int hoursLogged=resultSet.getInt("HoursLogged");
				String organizationName=resultSet.getString("OrganizationName");
				result.add(new TimesheetEntry(userEmail,date,eventName,isSubmitted,isApproved,hoursLogged,organizationName));
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
