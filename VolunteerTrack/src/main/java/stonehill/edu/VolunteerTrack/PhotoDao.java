package stonehill.edu.VolunteerTrack;
import java.io.*;
import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.awt.image.BufferedImage;

import javax.imageio.*;

public class PhotoDao extends Dao{
	public void delete(Object value) {
		Photo photo=(Photo) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Photo WHERE id = " + photo.getId());
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
			//create inputStream
			BufferedImage image= photo.getImage();
			ByteArrayOutputStream bs=new ByteArrayOutputStream();
			ImageIO.write(image,"png", bs);
			InputStream in=new ByteArrayInputStream(bs.toByteArray());
			
			//connect
			connectToDatabase();
			photo.setId(getUniqueId());
			//SQL statement
			String sql = "INSERT INTO Photo VALUES("+
		    "'"+photo.getName()+"', "+
			"to_date('"+new java.sql.Date(photo.getDate().getTime())+"','yyyy-mm-dd'), "+
		    " ? , "+
		    "'"+photo.getUserEmail()+"', "+
		    "'"+(photo.getIsProfilePicture()?1:0)+"', "+
		    "'"+(photo.getIsVolunteerPicture()?1:0)+"', "+
		    "'"+(photo.getIsCoordinatorPicture()?1:0)+"', "+
		    "'"+(photo.getIsPartnerPicture()?1:0)+"')" + 
		    photo.getId();
		    
		    PreparedStatement statement=connection.prepareStatement(sql);
			statement.setBinaryStream(1, in, (int)bs.size());
			statement.executeUpdate();
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
				int id=resultSet.getInt("Id");
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(id,name,ue,d,i,ispropic,ivp,icp, ipp));
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
			//create inputStream
			BufferedImage image= photo.getImage();
			ByteArrayOutputStream bs=new ByteArrayOutputStream();
			ImageIO.write(image,"png", bs);
			InputStream in=new ByteArrayInputStream(bs.toByteArray());
			//connect
			connectToDatabase();
			//SQL statement
			PreparedStatement statement=connection.prepareStatement("UPDATE Photo SET "+
		    "IsProfilePicture='"+(photo.getIsProfilePicture()?1:0)+"', "+
		    "IsVolunteerPicture='"+(photo.getIsVolunteerPicture()?1:0)+"', "+
		    "IsCoordinatorPicture='"+(photo.getIsCoordinatorPicture()?1:0)+"', "+
		    "Blob= ? , "+
		    "IsPartnerPicture='"+(photo.getIsPartnerPicture()?1:0)+"' WHERE "+
		    "Id='"+photo.getId());
			statement.setBinaryStream(1, in, (int)bs.toByteArray().length);
			statement.executeUpdate();
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
				int id = resultSet.getInt("Id");
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(id,name,ue,d,i,ispropic,ivp,icp, ipp));
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
				int id = resultSet.getInt("Id");
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(id,name,ue,d,i,ispropic,ivp,icp, ipp));
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
				int id = resultSet.getInt("Id");
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(id,name,ue,d,i,ispropic,ivp,icp, ipp));
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
	
	public Photo getProfilePhotoByUser(User user) {
		
		Photo result = null;
		
		try
		{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE UserEmail='"+user.getEmail()+"'"+
													   " AND isProfilePicture=1 " + 
			                                           " AND isVolunteerPicture="	+ (user.getIsVolunteer()?1:0)+
			                                           " AND isCoordinatorPicture=" + (user.getIsCoordinator()?1:0)+
			                                           " AND isPartnerPicture="		+ (user.getIsPartner()?1:0));
			//get tuples
			while(resultSet.next()){
				
				int id = resultSet.getInt("Id");
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				
				result = new Photo(id,name,ue,d,i,ispropic,ivp,icp, ipp);
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
	
	public Photo getPhotoById(int id) {
		Photo result=null;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Photo WHERE Id = " + id);
			//get tuples
			if(resultSet.next()){
				int imageId = resultSet.getInt("Id");
				String name = resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result=(new Photo(imageId,name,ue,d,i,ispropic,ivp,icp, ipp));
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
