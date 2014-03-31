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
			statement.executeQuery("DELETE FROM UserOwnsPhoto WHERE "+
		    "UserEmail='"+photo.getUserEmail()+"' AND "+
			"PhotoLink='"+photo.getLink()+"'");
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
			statement.executeQuery("INSERT INTO Photo VALUES("+
		    "'"+photo.getName()+"', "+
		    "'"+photo.getLink()+"', "+
		    "'"+(photo.getIsProfilePicture()?1:0)+"', "+
		    "'"+(photo.getIsVolunteerPicture()?1:0)+"', "+
		    "'"+(photo.getIsCoordinatorPicture()?1:0)+"', "+
		    "'"+(photo.getIsPartnerPicture()?1:0)+"')");
			statement.executeQuery("INSERT INTO UserOwnsPhoto VALUES("+
		    "'"+photo.getUserEmail()+"', "+
			"'"+photo.getLink()+"')");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo,UserOwnsPhoto WHERE Photo.Link=UserOwnsPhoto.PhotoLink");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
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
		    "Name='"+photo.getName()+"', "+
		    "IsProfilePicture='"+(photo.getIsProfilePicture()?1:0)+"', "+
		    "IsVolunteerPicture='"+(photo.getIsVolunteerPicture()?1:0)+"', "+
		    "IsCoordinatorPicture='"+(photo.getIsCoordinatorPicture()?1:0)+"', "+
		    "IsPartnerPicture='"+(photo.getIsPartnerPicture()?1:0)+"' WHERE "+
		    "Link='"+photo.getLink()+"'");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo,UserOwnsPhoto WHERE UserEmail='"+user.getEmail()+"' AND IsVolunteerPhoto='1' AND Photo.Link=UserOwnsPhoto.PhotoLink");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo,UserOwnsPhoto WHERE UserEmail='"+user.getEmail()+"' AND IsCoordinatorPhoto='1' AND Photo.Link=UserOwnsPhoto.PhotoLink");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo,UserOwnsPhoto WHERE UserEmail='"+user.getEmail()+"' AND IsPartnerPhoto='1' AND Photo.Link=UserOwnsPhoto.PhotoLink");
			//get tuples
			while(resultSet.next()){
				String name=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"' AND Name='"+name+"' AND Photo.Link=UserOwnsPhoto.PhotoLink");
			//get tuples
			if(resultSet.next()){
				String n=resultSet.getString("Name");
				String link=resultSet.getString("Link");
				String ue=resultSet.getString("UserEmail");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
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
