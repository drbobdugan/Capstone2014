package stonehill.edu.VolunteerTrack;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class EventDao extends Dao{
	public ArrayList<Event> getSearchResults(String organization, String location, String eventname,Date startDate,Date endDate,Date startTime,Date endTime, ArrayList<String> skills) {
		String skillQuery="";
		/*for(int i=0;i<skills.size();i++){
			skillQuery+=" AND EventRequiresSkill.SkillName="+skills.get(i);
		}*/
		ArrayList<Event> result=new ArrayList<Event>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			
			// Old query attempting to use EventRequiresSkill
			// ResultSet resultSet =statement.executeQuery("SELECT * FROM Event,UserOwnsEvent,UserEntity,EventRequiresSkill WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime AND UserEntity.Email=UserOwnsEvent.userEmail AND EventRequiresSkill.EventName = Event.Name AND EventRequiresSkill.EventDateTime = Event.CreatedDateTime AND UPPER(UserEntity.OrganizationName) LIKE UPPER('%" +organization+"%') AND UPPER(Event.Location) LIKE UPPER('%" +location+"%') AND UPPER(Event.Name) LIKE UPPER('%" +eventname+"%') AND Event.StartDateTime>=to_timestamp('"+new java.sql.Timestamp(startDate.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND Event.StartDateTime<=to_timestamp('"+new java.sql.Timestamp(endDate.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND EXTRACT(HOUR FROM to_timestamp(Event.StartDateTime))>=EXTRACT(HOUR FROM to_timestamp('"+new java.sql.Timestamp(startTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')) AND EXTRACT(HOUR FROM to_timestamp(Event.EndDateTime))<=EXTRACT(HOUR FROM to_timestamp('"+new java.sql.Timestamp(endTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')) "+skillQuery);
			//
			//old query w/ time
			//ResultSet resultSet =statement.executeQuery("SELECT * FROM Event,UserOwnsEvent,UserEntity WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime AND UserEntity.Email=UserOwnsEvent.userEmail AND UPPER(UserEntity.OrganizationName) LIKE UPPER('%" +organization+"%') AND UPPER(Event.Location) LIKE UPPER('%" +location+"%') AND UPPER(Event.Name) LIKE UPPER('%" +eventname+"%') AND Event.StartDateTime>=to_timestamp('"+new java.sql.Timestamp(startDate.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND Event.StartDateTime<=to_timestamp('"+new java.sql.Timestamp(endDate.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND EXTRACT(HOUR FROM to_timestamp(Event.StartDateTime))>=EXTRACT(HOUR FROM to_timestamp('"+new java.sql.Timestamp(startTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')) AND EXTRACT(HOUR FROM to_timestamp(Event.EndDateTime))<=EXTRACT(HOUR FROM to_timestamp('"+new java.sql.Timestamp(endTime.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')) "+skillQuery);
			
			
			ResultSet resultSet =statement.executeQuery("SELECT * FROM Event,UserOwnsEvent,UserEntity WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime AND UserEntity.Email=UserOwnsEvent.userEmail AND UPPER(UserEntity.OrganizationName) LIKE UPPER('%" +organization+"%') AND UPPER(Event.Location) LIKE UPPER('%" +location+"%') AND UPPER(Event.Name) LIKE UPPER('%" +eventname+"%')");
			while(resultSet.next()){
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date sd =resultSet.getTimestamp("StartDateTime");
				Date ed=resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String l =resultSet.getString("Location");
				String org=("OrganizationName");
				// TODO no sure how this will be handled yet
				Skill [] s= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( oe , name,  description,  l ,org,  tp,  tpr,createdDate, sd, ed, s));
			}
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public ArrayList<Object> getAllPendingAplicantsByPartner(User partner) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			//@@@ ZAC @@@ this query is confusing and might need some work, but it should return an arrayList with even entries being events owned by a partner and odd entries being users who signed up for them
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserOwnsEvent,UserSignsUpForEvent, UserEntity WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime AND UserSignsUpForEvent.EventName = Event.Name AND UserSignsUpForEvent.EventDateTime = Event.CreatedDateTime AND UserEntity.Email=UserSignsUpForEvent.userEmail AND UserOwnsEvent.UserEmail = '" + partner.getEmail()+"' AND Event.StartDateTime<to_timestamp('"+new java.sql.Timestamp((new Date()).getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			//get tuples
			while(resultSet.next()){
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				String org=("OrganizationName");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( oe , name,  description,  location ,org,  tp,  tpr,createdDate, startDate, endDate, skills));
				String e=resultSet.getString("Email");
				String p=resultSet.getString("Password");
				String fn=resultSet.getString("FirstName");
				String ln=resultSet.getString("LastName");
				String s=resultSet.getString("Street");
				String c=resultSet.getString("City");
				String st=resultSet.getString("State");
				String z=resultSet.getString("Zip");
				String pn=resultSet.getString("PhoneNumber");
				String pd=resultSet.getString("PartnerDescription");
				String vd=resultSet.getString("VolunteerDescription");
				boolean ip=resultSet.getBoolean("IsPartner");
				boolean ic=resultSet.getBoolean("IsCoordinator");
				boolean iv=resultSet.getBoolean("IsVolunteer");
				String mj=resultSet.getString("major");
				String mi=resultSet.getString("minor");
				boolean iap=resultSet.getBoolean("IsApprovedPartner");
				boolean iac=resultSet.getBoolean("IsApprovedCoordinator");
				boolean iav=resultSet.getBoolean("IsApprovedVolunteer");
				String on=resultSet.getString("OrganizationName");
				result.add(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav,on));
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
	
	ArrayList getUsersThatAreSignedUpForEvent(User user, Event e) {
		ArrayList row = new ArrayList(0);
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity, UserSignsUpForEvent "+
					"WHERE User.Email=UserSignsUpForEvent.UserEmail AND "+
					"UserSignsUpForEvent.EventName= '"+e.getName()+"' AND "+
					"UserSignsUpForEvent.EventDateTime=to_timestamp('"+new java.sql.Timestamp(e.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			//get tuples
			while(resultSet.next()){
				row.add(new Object[]{resultSet.getString("EventName"), resultSet.getTimestamp("CreatedDateTime"), resultSet.getInt("PositionsRemaining")});
		    }
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
		}
		catch(Exception ex){
			ex.printStackTrace();
		}
		return row;
		
	}
	public void delete(Object value) {
		Event event=(Event) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Event WHERE name = '"+  event.getName()  + "' AND " +
			"location = '" + event.getLocation()  + "' AND " +
			"CreatedDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserOwnsEvent WHERE UserOwnsEvent.EventName = Event.Name AND UserOwnsEvent.EventDateTime = Event.CreatedDateTime");
			//get tuples
			while(resultSet.next()){
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				// TODO owners email
				result.add( new Event ( oe , name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
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
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result.add( new Event ( oe , name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
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
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result =( new Event ( email, name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
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
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'))");
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
				String oe=resultSet.getString("UserEmail");
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				result.add(  new Event ( oe , name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
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
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'))");
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
				String name=resultSet.getString("Name");
				Date createdDate =resultSet.getTimestamp("CreatedDateTime");
				Date startDate =resultSet.getTimestamp("StartDateTime");
				Date endDate =resultSet.getTimestamp("EndDateTime");
				String description =resultSet.getString("Description");
				String location =resultSet.getString("Location");
				// TODO no sure how this will be handled yet
				Skill [] skills= {new Skill("not coded")};
				int tp=resultSet.getInt("TotalPositions");
				int tpr =resultSet.getInt("PositionsRemaining");
				
				String email = "";
				
				try{
					//SQL statement
					Statement statement2=connection.createStatement();
					ResultSet resultSet2=statement2.executeQuery("SELECT * FROM UserOwnsEvent WHERE EventDateTime=to_timestamp('"+new java.sql.Timestamp(createdDate.getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND"
									+ " EventName='"+name+"'");
					//get tuple
					if(resultSet2.next()){
						email = resultSet2.getString("UserEmail");
					}else{
						System.out.println("EventDao:getEventByNameDateTime() did not return a tuple.");
					}
					//clean up
					resultSet2.close();
					statement2.close();
				}
				catch(Exception e){
					e.printStackTrace();
				}
				
				result.add( new Event ( email , name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
			}
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
			
			/*for (Object o : result)
			{
				Event event = (Event)o;
				String email = "";
				
				try{
					//connect
					connectToDatabase();
					//SQL statement
					Statement statement2=connection.createStatement();
					ResultSet resultSet2=statement.executeQuery("SELECT * FROM UserOwnsEvent WHERE EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND"
									+ " EventName='"+event.getName()+"'");
					//get tuple
					if(resultSet2.next()){
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
			}*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	public void insertEventRequiresSkill(Skill skill, Event event) {
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO EventRequiresSkill VALUES("+
		    "'"+event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			 "'"+skill.getName()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteEventRequiresSkill(Skill skill, Event event) {
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM EventRequiresSkill WHERE "+
		    "SkillName='"+skill.getName()+"' AND "+
		    "EventName='"+event.getName()+"' AND "+
			"EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getSkillsRequired(Event event) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, EventRequiresSkill WHERE EventRequiresSkill.EventName = Event.Name AND EventRequiresSkill.EventDateTime = Event.CreatedDateTime AND Event.name = '" + event.getName()+"' AND Event.CreatedDateTime= to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("SkillName");
				result.add( new Skill (name));
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
	public void insertEventRequiresDocument(Document document, Event event) {
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO EventRequiresDocument VALUES("+
		    "'"+event.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"'"+document.getName()+"', "+
			"to_timestamp('"+new java.sql.Timestamp(document.getDateUploaded().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF'), "+
			"'"+document.getUserEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteEventRequiresDocument(Document document, Event event) {
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM EventRequiresSkill WHERE "+
		    "DocumentName='"+document.getName()+"' AND "+
		    "DocumentDataUploaded=to_timestamp('"+new java.sql.Timestamp(document.getDateUploaded().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF') AND "+
		    "DocumentUserEmail='"+document.getUserEmail()+"' AND "+
		    "EventName='"+event.getName()+"' AND "+
			"EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getDocumentsRequired(Event event) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document, EventRequiresDocument WHERE Document.UserEmail=EventRequiresDocument.DocumentUserEmail AND Document.DateUploaded=EventRequiresDocument.DocumentDateUploaded AND Document.Name=EventRequiresDocument.DocumentName and EventRequiresDocument.EventName='"+event.getName()+"EventRequiresDocument.EventDateTime=to_timestamp('"+new java.sql.Timestamp(event.getCreatedDateTime().getTime()).toString()+"','YYYY-MM-DD HH24:MI:SS.FF')");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String type=resultSet.getString("Type");
				Date date=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				String userEmail=resultSet.getString("UserEmail");
				boolean isSharedDocument=resultSet.getBoolean("IsSharedDocument");
				//blob to file
				File file= File.createTempFile(newFileName.getFileName(),"");
				BufferedInputStream in= new BufferedInputStream(blob.getBinaryStream());
				FileOutputStream out=new FileOutputStream(file);
				byte[] buffer=new byte[1024];
				int r=0;
				while((r=in.read(buffer))!=-1){
					out.write(buffer,0,r);
				}
				out.flush();
				out.close();
				in.close();
				blob.free();
				result.add(new Document(name,type,date,file,userEmail,isSharedDocument));
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
	public ArrayList getUsersSignedUpForEvent(Event event) {
			ArrayList<Object> result=new ArrayList<Object>();
			try{
				//connect
				connectToDatabase();
				//SQL statement
				Statement statement=connection.createStatement();
				ResultSet resultSet=statement.executeQuery("SELECT * FROM Event, UserSignsUpForEvent WHERE "+
				"UserSignsUpForEvent.EventName = Event.Name AND UserSignsUpForEvent.EventDateTime = Event.CreatedDateTime "+
						"AND Event.Name = '" + event.getName()+"'");
				//get tuples
				while(resultSet.next()){
					String oe=resultSet.getString("UserEmail");
					String name=resultSet.getString("Name");
					Date createdDate =resultSet.getTimestamp("CreatedDateTime");
					Date startDate =resultSet.getTimestamp("StartDateTime");
					Date endDate =resultSet.getTimestamp("EndDateTime");
					String description =resultSet.getString("Description");
					String location =resultSet.getString("Location");
					// TODO no sure how this will be handled yet
					Skill [] skills= {new Skill("not coded")};
					int tp=resultSet.getInt("TotalPositions");
					int tpr =resultSet.getInt("PositionsRemaining");
					result.add(  new Event ( oe , name, createdDate,startDate, endDate, description,  location ,  tp,  tpr, skills));
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
