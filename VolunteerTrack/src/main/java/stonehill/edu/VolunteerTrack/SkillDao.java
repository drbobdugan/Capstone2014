package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class SkillDao extends Dao{
	public void delete(Object value) {
		Skill skill=(Skill) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Skill WHERE "+
			"Name='"+skill.getName()+"'");
			statement.executeQuery("DELETE FROM UserHasSkill WHERE "+
					"SkillName='"+skill.getName()+"'");
			statement.executeQuery("DELETE FROM EventRequiresSkill WHERE "+
					"SkillName='"+skill.getName()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
		Skill skill=(Skill) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Skill VALUES("+
			"'"+skill.getName()+"')");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Skill");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				result.add(new Skill(name));
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
		// CAN'T UPDATE?
		
	}
	public ArrayList<Object> getAllSkillsByUser(User u) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserHasSkill WHERE UserEmail='"+u.getEmail()+"'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("SkillName");
				result.add(new Skill(name));
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
