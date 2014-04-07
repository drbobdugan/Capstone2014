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
			statement.executeQuery("DELETE FROM Photo WHERE "+
		    "Name='"+photo.getName()+"' AND "+
			"DateUploaded=to_date('"+new java.sql.Date(photo.getDate().getTime())+"','yyyy-mm-dd') AND "+
		    "UserEmail='"+photo.getUserEmail()+"'");
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
			ImageIO.write(image,photo.getName().substring(photo.getName().indexOf('.')+1), bs);
			InputStream in=new ByteArrayInputStream(bs.toByteArray());
			//connect
			connectToDatabase();
			//SQL statement
			PreparedStatement statement=connection.prepareStatement("INSERT INTO Photo VALUES("+
		    "'"+photo.getName()+"', "+
			"to_date('"+new java.sql.Date(photo.getDate().getTime())+"','yyyy-mm-dd'), "+
		    " ? , "+
		    "'"+photo.getUserEmail()+"', "+
		    "'"+(photo.getIsProfilePicture()?1:0)+"', "+
		    "'"+(photo.getIsVolunteerPicture()?1:0)+"', "+
		    "'"+(photo.getIsCoordinatorPicture()?1:0)+"', "+
		    "'"+(photo.getIsPartnerPicture()?1:0)+"')");
			statement.setBinaryStream(1, in, (int)bs.toByteArray().length);
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
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(name,ue,d,i,ispropic,ivp,icp,ipp));
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
			ImageIO.write(image,photo.getName().substring(photo.getName().indexOf('.')+1), bs);
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
		    "Name='"+photo.getName()+"' AND "+
			"DateUploaded=to_date('"+new java.sql.Date(photo.getDate().getTime())+"','yyyy-mm-dd') AND "+
		    "UserEmail='"+photo.getUserEmail()+"'");
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
				String name=resultSet.getString("Name");
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(name,ue,d,i,ispropic,ivp,icp,ipp));
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
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(name,ue,d,i,ispropic,ivp,icp,ipp));
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
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result.add(new Photo(name,ue,d,i,ispropic,ivp,icp,ipp));
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
				String ue=resultSet.getString("UserEmail");
				Date d=resultSet.getDate("DateUploaded");
				Blob blob=resultSet.getBlob("Blob");
				boolean ivp=resultSet.getBoolean("IsVolunteerPicture");
				boolean icp=resultSet.getBoolean("IsCoordinatorPicture");
				boolean ipp=resultSet.getBoolean("IsPartnerPicture");
				boolean ispropic=resultSet.getBoolean("IsProfilePicture");
				BufferedImage i =ImageIO.read(blob.getBinaryStream());
				result=(new Photo(name,ue,d,i,ispropic,ivp,icp,ipp));
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
