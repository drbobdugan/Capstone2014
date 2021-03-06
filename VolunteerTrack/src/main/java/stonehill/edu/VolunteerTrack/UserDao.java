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
			statement.executeQuery("DELETE FROM UserEntity WHERE id = " + user.getId());
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
			"'"+user.getMajor()+"', "+
			"'"+user.getMinor()+"', "+
			"'"+(user.getIsApprovedPartner()?1:0)+"', "+
			"'"+(user.getIsApprovedCoordinator()?1:0)+"', "+
			"'"+(user.getIsApprovedVolunteer()?1:0)+"', "+
			"'"+user.getOrganizationName()+"', " +
			"'"+user.getMissionStatement()+"', " + 
			user.getId());
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
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement=resultSet.getString("missionStatement");
				result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
			"Email='"+user.getEmail()+"', "+
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
			"IsPartner='"+(user.getIsPartner()?1:0)+"', "+
			"IsCoordinator='"+(user.getIsCoordinator()?1:0)+"', "+
			"IsVolunteer='"+(user.getIsVolunteer()?1:0)+"', "+
		    "Major='"+user.getMajor()+"', "+
		    "Minor='"+user.getMinor()+"', "+
			"IsApprovedPartner='"+(user.getIsApprovedPartner()?1:0)+"', "+
			"IsApprovedCoordinator='"+(user.getIsApprovedCoordinator()?1:0)+"', "+
			"IsApprovedVolunteer='"+(user.getIsApprovedVolunteer()?1:0)+"', "+
			"OrganizationName='"+user.getOrganizationName()+"' WHERE "+
			"Id="+user.getId()+"");
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
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionStatement");
				result = (new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
	
	public User getUserByUserId(int id) {
		
		User result=null;
		
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE Id="+id);
			//get tuples
			while(resultSet.next()){
				int rsId = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				result = new User(rsId,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement);
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
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
	
	
	public ArrayList<Object> getSearchResults(ArrayList<Object> criteria) {
		StringBuilder sb=new StringBuilder();
		for( int i=0; i<criteria.size()-1;i ++)
		{
			sb.append(" AND UPPER(" + criteria.get(i) + ") LIKE UPPER('%" +criteria.get(i+1)+"%')");
		}
		ArrayList<Object> searchResult=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet =statement.executeQuery("SELECT * FROM UserEntity WHERE IsVolunteer='1'" + sb);
			
			while(resultSet.next())
			{	
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				searchResult.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav,on, missionStatement));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return searchResult;
	}
	
	public void insertUserHasSkill(Object value1, Object value2) {
		User user=(User) value1;
		Skill skill=(Skill) value2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO UserHasSkill VALUES("+user.getEmail()+","+skill.getName()+"',"+user.getId()+")");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteUserHasSkill(Object value1, Object value2) {
		User user=(User) value1;
		Skill skill=(Skill) value2;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM UserHasSkill WHERE UserEntityId="+user.getId()+", AND SkillName='"+skill.getName()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteAllUserSkills(Object value1) {
		User user=(User) value1;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM UserHasSkill WHERE UserEntityId="+user.getId());
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public ArrayList<Object> getAllSkillsByUser(Object value) {
		User user=(User) value;
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserHasSkill WHERE UserEntityId=" + user.getId());
			//get tuples
			while(resultSet.next()){
				String n=resultSet.getString("SkillName");
				result.add(new Skill(n));
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
	
	public void insertUserOwnsEvent(Object value1, Object value2) {
		EventDao eventDao = new EventDao();
		eventDao.insertUserOwnsEvent(value1, value2);
	}
	
	public void deleteUserOwnsEvent(Object value1, Object value2) {
		EventDao eventDao = new EventDao();
		eventDao.deleteUserOwnsEvent(value1, value2);
	}
	public void insertUserSignsUpForEvent(Object user1, Object event2) {
		EventDao eventDao = new EventDao();
		eventDao.insertUserSignsUpForEvent(user1, event2);
	}
	
	public void deleteUserSignsUpForEvent(Object user1, Object event2) {
		EventDao eventDao = new EventDao();
		eventDao.deleteUserSignsUpForEvent(user1, event2);
	}
	public ArrayList SearchUsersByOrganizationName(String organization){
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE UPPER(OrganizationName) LIKE UPPER('%"+organization+"%')");
			//get tuples
			while(resultSet.next()){
				int id = resultSet.getInt("Id");
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
				String on=resultSet.getString("organizationName");
				String missionStatement = resultSet.getString("missionstatement");
				result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav,on, missionStatement));
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
public ArrayList SearchUsersByNameMajorMinor(String name,String  major, String minor){
	ArrayList<Object> result=new ArrayList<Object>();
	//split up name
	String[] splitName=name.split(" ");
	try{
		//connect
		connectToDatabase();
		//SQL statement
		Statement statement=connection.createStatement();
		ResultSet resultSet=null;
		if(splitName.length>1){
			resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE UPPER(FirstName) LIKE UPPER('%"+splitName[0]+"%') AND UPPER(LastName) LIKE UPPER('%"+splitName[1]+"%') AND UPPER(Major) LIKE UPPER('%"+major+"%') AND UPPER(Minor) LIKE UPPER('%"+minor+"%')");
		}
		else{
			resultSet=statement.executeQuery("SELECT * FROM UserEntity WHERE (UPPER(FirstName) LIKE UPPER('%"+splitName[0]+"%') OR UPPER(LastName) LIKE UPPER('%"+splitName[0]+"%')) AND UPPER(Major) LIKE UPPER('%"+major+"%') AND UPPER(Minor) LIKE UPPER('%"+minor+"%')");
		}
		//get tuples
		while(resultSet.next()){
			int id = resultSet.getInt("Id");
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
			String on=resultSet.getString("organizationName");
			String missionStatement = resultSet.getString("missionstatement");
			result.add(new User(id,e,p,fn,ln,s,c,st,z,pn,pd,vd,ip,ic,iv,mj,mi,iap,iac,iav, on, missionStatement));
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
