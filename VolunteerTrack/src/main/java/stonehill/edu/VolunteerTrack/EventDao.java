package stonehill.edu.VolunteerTrack;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class EventDao extends Dao{
	public void delete(Object value) {
		Event event=(Event) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Event WHERE name = '"+  event.getName()  + "' AND " +
			"location = '" + event.getLocation() + "' "); /* +"' AND " +
			"CreatedDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')"); */
			
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
		Event event=(Event) value;
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Event VALUES('"+
		    event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"to_timestamp('"+new java.sql.Timestamp(event.getStartDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"to_timestamp('"+new java.sql.Timestamp(event.getEndDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"'"+event.getDescription()+"', "+ 
			"'"+event.getLocation()+"', "+ 
		    "'"+event.getNumPositions()+"', "+
		    "'"+event.getNumPositionsRemaining()+"')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO UserOwnsEvent VALUES('"+ 
			event.getOwnerEmail() + "', '"+
			event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'))");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> selectAll() {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event,UserOwnsEvent WHERE Event.Name=UserOwnsEvent.EventName AND Event.CreatedDateTime=UserOwnsEvent.EventDateTime");
			//get tuples
			while(resultSet.next()){
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getDate("CreatedDateTime");
				Date startDate =resultSet.getDate("StartDateTime");
				Date endDate =resultSet.getDate("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( oe , name, createdDate, startDate, endDate,  description,  location ,  tp,  tpr, skills));
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
	public ArrayList<Object> getAllEventsByOwner(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserOwnsEvent WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime AND UserOwnsEvent.UserEmail = '" + user.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				System.out.println("asdfasdfasfasdfdassfsadfasfsadF");
				String owner = resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getDate("CreatedDateTime");
				Date startDate =resultSet.getDate("StartDateTime");
				Date endDate =resultSet.getDate("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result.add( new Event ( owner, name, createdDate, startDate, endDate, description,  location ,  tp,  tpr, skills));
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
	public void update(Object value) {
		Event event=(Event) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();

			statement.executeQuery("UPDATE Event SET "+
			"StartDateTime=to_timestamp('"+new java.sql.Timestamp(event.getStartDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"EndDateTime=to_timestamp('"+new java.sql.Timestamp(event.getEndDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
		    "Description='"+event.getDescription()+"', "+
		    "Location='"+event.getLocation()+"', "+
		    "TotalPositions='"+event.getNumPositions()+"', "+
		    "PositionsRemaining='"+event.getNumPositionsRemaining()+"'WHERE "+   
		    "Name='"+event.getName()+"' AND "+
		    "CreatedDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') ");
		
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public Event getEventByNameDateTime(String eventName, Date dateTime) {
		Event result = null;
		String email = "";
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserOwnsEvent WHERE EventDateTime=to_timestamp('"+new java.sql.Timestamp(dateTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND"
							+ " EventName='"+eventName+"'");
			//get tuple
			if(resultSet.next()){
				email = resultSet.getString("UserEmail");
			}else{
				System.out.println("EventDao:getEventByNameDateTime() did not return a tuple.");
			}
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event WHERE CreatedDateTime=to_timestamp('"+new java.sql.Timestamp(dateTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND"
							+ " Name='"+eventName+"'");
			//get tuple
			if(resultSet.next()){
				//String owner = resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getDate("CreatedDateTime");
				Date startDate =resultSet.getDate("StartDateTime");
				Date endDate =resultSet.getDate("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result=(new Event ( email , name, createdDate, startDate, endDate,  description,  location ,  tp,  tpr, skills));
			}else{
				System.out.println("EventDao:getEventByNameDateTime() did not return a tuple.");
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
	public void insertUserOwnsEvent(Object user1, Object event2) {
		User user=(User) user1;
		Event event=(Event) event2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO UserOwnsEvent VALUES("+
		    "'"+user.getEmail()+"', "+
		    "'"+event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteUserOwnsEvent(Object user1, Object event2) {
		User user=(User) user1;
		Event event=(Event)event2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM UserOwnsEvent WHERE "+
		    "UserEmail='"+user.getEmail()+"', AND "+
		    "EventName='"+event.getName()+"', AND "+
			"EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getAllEventsSignedUpFor(User user){
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserSignsUpForEvent WHERE UserSignsUpForEvent.EventName = Event.Name AND UserSignsUpForEvent.EventDateTime = Event.CreatedDateTime AND UserSignsUpForEvent.UserEmail = '" + user.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				String owner = resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getDate("CreatedDateTime");
				Date startDate =resultSet.getDate("StartDateTime");
				Date endDate =resultSet.getDate("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result.add( new Event ( owner, name, createdDate, startDate, endDate,  description,  location ,  tp,  tpr, skills));
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
	public void insertUserSignsUpForEvent(Object user1, Object event2) {
		User user=(User) user1;
		Event event=(Event) event2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO UserSignsUpForEvent VALUES("+
		    "'"+user.getEmail()+"', "+
		    "'"+event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteUserSignsUpForEvent(Object user1, Object event2) {
		User user=(User) user1;
		Event event=(Event) event2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM UserSignsUpForEvent WHERE "+
		    "UserEmail='"+user.getEmail()+"', AND "+
		    "EventName='"+event.getName()+"', AND "+
			"EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getAllEventsByUserTimeSheetEntries(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, TimeSheetEntry WHERE TimeSheetEntry.EventName = Event.Name AND TimeSheetEntry.DateTime = Event.CreatedDateTime AND TimeSheetEntry.UserEmail = '" + user.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				System.out.println("asdfasdfasfasdfdassfsadfasfsadF");
				String owner = resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getDate("CreatedDateTime");
				Date startDate =resultSet.getDate("StartDateTime");
				Date endDate =resultSet.getDate("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result.add( new Event ( owner, name, createdDate, startDate, endDate,  description,  location ,  tp,  tpr, skills));
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
