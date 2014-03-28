package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
public class PhotoDao extends Dao{
	public void delete(Object value) {
		Photo photo=(Photo) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Photo WHERE "+
		    "Link='"+photo.getLink()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value) {
		Photo photo=(Photo) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO Photo VALUES "+
		    "Name='"+photo.getName()+"', AND"+
		    "Link='"+photo.getLink()+"', AND"+
		    "UserEmail='"+photo.getUserEmail()+"', AND"+
		    "IsProfilePicture='"+(photo.getIsProfilePicture()?1:0)+"', AND"+
		    "IsVolunteerPicture='"+(photo.getIsVolunteerPicture()?1:0)+"', AND"+
		    "IsCoordinatorPicture='"+(photo.getIsCoordinatorPicture()?1:0)+"', AND"+
		    "IsPartnerPicture='"+(photo.getIsPartnerPicture()?1:0)+"'");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsSharedDocument");
				boolean icp=resultSet.getBoolean("IsSharedDocument");
				boolean ipp=resultSet.getBoolean("IsSharedDocument");
				boolean ispropic=resultSet.getBoolean("IsSharedDocument");
				result.add(new Photo(name,link,ispropic,ivp,icp,ipp,ue));
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
		Photo photo=(Photo) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("UPDATE Photo SET "+
		    "Name='"+photo.getName()+"', AND"+
		    "Link='"+photo.getLink()+"', AND"+
		    "UserEmail='"+photo.getUserEmail()+"', AND"+
		    "IsProfilePicture='"+(photo.getIsProfilePicture()?1:0)+"', AND"+
		    "IsVolunteerPicture='"+(photo.getIsVolunteerPicture()?1:0)+"', AND"+
		    "IsCoordinatorPicture='"+(photo.getIsCoordinatorPicture()?1:0)+"', AND"+
		    "IsPartnerPicture='"+(photo.getIsPartnerPicture()?1:0)+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getAllVolunteerPhotosByUser(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"' AND IsVolunteerPhoto='1'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsSharedDocument");
				boolean icp=resultSet.getBoolean("IsSharedDocument");
				boolean ipp=resultSet.getBoolean("IsSharedDocument");
				boolean ispropic=resultSet.getBoolean("IsSharedDocument");
				result.add(new Photo(name,link,ispropic,ivp,icp,ipp,ue));
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
	public ArrayList<Object> getAllCoordinatorPhotosByUser(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"' AND IsCoordinatorPhoto='1'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsSharedDocument");
				boolean icp=resultSet.getBoolean("IsSharedDocument");
				boolean ipp=resultSet.getBoolean("IsSharedDocument");
				boolean ispropic=resultSet.getBoolean("IsSharedDocument");
				result.add(new Photo(name,link,ispropic,ivp,icp,ipp,ue));
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
	public ArrayList<Object> getAllPartnerPhotosByUser(User user) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"' AND IsPartnerPhoto='1'");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsSharedDocument");
				boolean icp=resultSet.getBoolean("IsSharedDocument");
				boolean ipp=resultSet.getBoolean("IsSharedDocument");
				boolean ispropic=resultSet.getBoolean("IsSharedDocument");
				result.add(new Photo(name,link,ispropic,ivp,icp,ipp,ue));
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
	public Photo getPhotoByUserAndName(User user,String name) {
		Photo result=null;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"' AND Name='"+name+"'");
			//get tuples
			if(resultSet.next()){
				String n=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsSharedDocument");
				boolean icp=resultSet.getBoolean("IsSharedDocument");
				boolean ipp=resultSet.getBoolean("IsSharedDocument");
				boolean ispropic=resultSet.getBoolean("IsSharedDocument");
				result=(new Photo(n,link,ispropic,ivp,icp,ipp,ue));
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
