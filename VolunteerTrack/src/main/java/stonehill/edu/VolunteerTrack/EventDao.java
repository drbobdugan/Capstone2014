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
		Event event=(Event) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Event VALUES('"+
		    event.getName()+"', "+
            "to_date('"+new java.sql.Date(event.getDate().getTime())+"','yyyy-mm-dd'), "+
		    "'"+event.getDescription()+"', "+
		    "'"+event.getLocation()+"', 'none' ,"+ 
		    "'"+event.getNumPositions()+"', "+
		    "'"+event.getNumPositionsRemaining()+"')");
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
				Skill [] skills= {new Skill(resultSet.getString("Interests"))};
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
		    "Interests='"+event.getSkills()+"', "+
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
	public ArrayList<Object> getAllEventsByPartener(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserOwnsEvent WHERE UserEmail='"+user.getEmail()+"'"+ 
			"AND Event.Name = UserOwnsEvent.EventName AND Event.DateTime = UserOwnsEvent.EventDateTime ");
			
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				Date date =resultSet.getDate("Date");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				
				ResultSet skillSet=statement.executeQuery("Select SkillName From Event, EventRequiresSkill Where Event.Name=EventRequiresSkill.EventName and Event.DateTime=EventRequiresSkill.EventDateTime and Event.name='"+name+"' and Event.DateTime=to_date('"+new java.sql.Date(date.getTime())+"','yyyy-mm-dd')");	
				ArrayList<Skill> tempList = new ArrayList<Skill>(0);
				while(skillSet.next()){
					tempList.add(new Skill(skillSet.getString("skillName")));
				}
				result.add( new Event ( "Owners  Email" , name, date,  description,  location ,  tp,  tpr, (Skill[])tempList.toArray()));
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