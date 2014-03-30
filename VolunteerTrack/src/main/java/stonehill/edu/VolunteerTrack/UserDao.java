package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
public class UserDao extends Dao{
	public void delete(Object value) {
		User user=(User) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM UserEntity WHERE "+
		    "Email='"+user.getEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
		User user=(User) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO UserEntity VALUES("+
		    "'"+user.getEmail()+"', "+
		    "'"+user.getPassword()+"', "+
		    "'"+user.getFirstName()+"', "+
		    "'"+user.getLastName()+"', "+
		    "'"+user.getStreet()+"', "+
		    "'"+user.getCity()+"', "+
		    "'"+user.getState()+"', "+
		    "'"+user.getZip()+"', "+
		    "'"+user.getPhoneNumber()+"', "+
		    "'"+user.getPartnerDescription()+"', "+
		    "'"+user.getVolunteerDescription()+"', "+
			"'"+(user.getIsPartner()?1:0)+"', "+
			"'"+(user.getIsCoordinator()?1:0)+"', "+
			"'"+(user.getIsVolunteer()?1:0)+"', "+
			"'"+(user.getIsApproved()?1:0)+"')");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity");
			//get tuples
			while(resultSet.next()){
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
				boolean ia=resultSet.getBoolean("IsApproved");
				result.add(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,ia));
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
		User user=(User) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("UPDATE UserEntity SET "+
		    "Password='"+user.getPassword()+"', "+
		    "FirstName='"+user.getFirstName()+"', "+
		    "LastName='"+user.getLastName()+"', "+
		    "Street='"+user.getStreet()+"', "+
		    "City='"+user.getCity()+"', "+
		    "State='"+user.getState()+"', "+
		    "Zip='"+user.getZip()+"', "+
		    "PhoneNumber='"+user.getPhoneNumber()+"', "+
		    "PartnerDescription='"+user.getPartnerDescription()+"', "+
		    "VolunteerDescription='"+user.getVolunteerDescription()+"', "+
			"IsApproved='"+(user.getIsApproved()?1:0)+"', "+
			"IsPartner='"+(user.getIsPartner()?1:0)+"', "+
			"IsCoordinator='"+(user.getIsCoordinator()?1:0)+"', "+
			"IsVolunteer='"+(user.getIsVolunteer()?1:0)+"' WHERE "+
			"Email='"+user.getEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public User getUserByUsername(String name) {
		User result=null;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE Email='"+name+"'");
			//get tuples
			if(resultSet.next()){
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
				boolean ia=resultSet.getBoolean("IsApproved");
				result=(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,ia));
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
	public ArrayList<Object> getAllPartners() {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE IsPartner='1'");
			//get tuples
			while(resultSet.next()){
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
				boolean ia=resultSet.getBoolean("IsApproved");
				result.add(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,ia));
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
	public ArrayList<Object> getAllCoordinators() {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE IsCoordinator='1'");
			//get tuples
			while(resultSet.next()){
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
				boolean ia=resultSet.getBoolean("IsApproved");
				result.add(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,ia));
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
	public ArrayList<Object> getAllVolunteers() {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE IsVolunteer='1'");
			//get tuples
			while(resultSet.next()){
				
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
				boolean ia=resultSet.getBoolean("IsApproved");
				result.add(new User(e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,ia));
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
