package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
public class DocumentDao extends Dao{
	public void delete(Object value) {
		Document document=(Document) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Document WHERE "+
		    "Link='"+document.getLink()+"', AND"+
		    "UserEmail'"+document.getUserEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
		Document document=(Document) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Document VALUES "+
		    "Name='"+document.getName()+"', AND"+
		    "Type='"+document.getType()+"', AND"+
		    "DateUploaded='"+new java.sql.Date(document.getDateUploaded().getTime())+"', AND"+
		    "Link='"+document.getLink()+"', AND"+
		    "IsSharedDocument='"+(document.getIsSharedDocument()?1:0)+"', AND"+
		    "UserEmail'"+document.getUserEmail()+"'");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String type=resultSet.getString("Type");
				Date date=resultSet.getDate("DateUploaded");
				String link=resultSet.getString("Link");
				String userEmail=resultSet.getString("UserEmail");
				boolean isSharedDocument=resultSet.getBoolean("IsSharedDocument");
				result.add(new Document(name,type,date,link,userEmail));
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
		Document document=(Document) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("UPDATE Document SET "+
		    "Name='"+document.getName()+"', AND"+
		    "Type='"+document.getType()+"', AND"+
		    "DateUploaded='"+new java.sql.Date(document.getDateUploaded().getTime())+"', AND"+
		    "Link='"+document.getLink()+"', AND"+
		    "IsSharedDocument='"+(document.getIsSharedDocument()?1:0)+"', AND"+
		    "UserEmail'"+document.getUserEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public Document getDocumentByUserNameAndDateTime(User user, String n, Date d) {
		Document result=null;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document WHERE UserEmail='"+user.getEmail()+"' AND Name='"+n+"' AND DateUploaded='"+new java.sql.Date(d.getTime())+"'");
			//get tuples
			if(resultSet.next()){
				String name=resultSet.getString("Name");
				String type=resultSet.getString("Type");
				Date date=resultSet.getDate("DateUploaded");
				String link=resultSet.getString("Link");
				String userEmail=resultSet.getString("UserEmail");
				boolean isSharedDocument=resultSet.getBoolean("IsSharedDocument");
				result=(new Document(name,type,date,link,userEmail));
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
	public ArrayList<Object> getAllDocumentsByUser(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document WHERE UserEmail='"+user.getEmail()+"' AND IsSharedDocument='0'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String type=resultSet.getString("Type");
				Date date=resultSet.getDate("DateUploaded");
				String link=resultSet.getString("Link");
				String userEmail=resultSet.getString("UserEmail");
				boolean isSharedDocument=resultSet.getBoolean("IsSharedDocument");
				result.add(new Document(name,type,date,link,userEmail));
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
	public ArrayList<Object> getAllSharedDocumentsByUser(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document WHERE UserEmail='"+user.getEmail()+"' AND IsSharedDocument='1'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String type=resultSet.getString("Type");
				Date date=resultSet.getDate("DateUploaded");
				String link=resultSet.getString("Link");
				String userEmail=resultSet.getString("UserEmail");
				boolean isSharedDocument=resultSet.getBoolean("IsSharedDocument");
				result.add(new Document(name,type,date,link,userEmail));
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
