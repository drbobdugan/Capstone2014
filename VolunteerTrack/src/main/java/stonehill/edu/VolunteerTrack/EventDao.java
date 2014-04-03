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
			"location = '" + event.getLocation() +"' AND " +
			"DateTime=to_date('"+new java.sql.Date(event.getDate().getTime())+"','yyyy-mm-dd')");
			
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
	     try {
			throw new Exception();
		} catch (Exception e) {
			System.out.println("DONT USE INSERT IN EVENT DAO()");
		}
	}
	public void insert(Object value, Object value2) {
		Event event=(Event) value;
		User user = (User)value2;
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Event VALUES('"+
		    event.getName()+"', "+
            "to_date('"+new java.sql.Date(event.getDate().getTime())+"','yyyy-mm-dd'), "+
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
			statement.executeQuery("INSERT INTO UserOwnsEvent VALUES('"+ user.getEmail() + "', '"+
			event.getName()+"', "+
            "to_date('"+new java.sql.Date(event.getDate().getTime())+"','yyyy-mm-dd'))");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				Date date =resultSet.getDate("DateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( "Owners  Email" , name, date,  description,  location ,  tp,  tpr, skills));
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
	public ArrayList<Object> selectAllByPartner(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserOwnsEvent WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.DateTime AND UserOwnsEvent.UserEmail = '" + user.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				System.out.println("asdfasdfasfasdfdassfsadfasfsadF");
				String owner = resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date date =resultSet.getDate("DateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( owner, name, date,  description,  location ,  tp,  tpr, skills));
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
			"Name='"+event.getName()+"', "+
		    "Date='"+event.getDate()+"', "+
		    "Description='"+event.getDescription()+"', "+
		    "Location='"+event.getLocation()+"', "+
		    "TotalPositions='"+event.getNumPositions()+"', "+
		    "PositionsRemaining='"+event.getNumPositionsRemaining()+"'WHERE "+   
		    "Name='"+event.getName()+"'");
		
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
}