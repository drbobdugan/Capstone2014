package stonehill.edu.VolunteerTrack;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.io.*;

public class DocumentDao extends Dao{
	public void delete(Object value) {
		Document document=(Document) value;
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM Document WHERE "+
			"Name='"+document.getName()+"' AND "+
		    "dateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND "+
		    "UserEmail='"+document.getUserEmail()+"'");
			statement.executeQuery("DELETE FROM EventRequiresDocument WHERE "+
					"DocumentName='"+document.getName()+"' AND "+
				    "DocumentDateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND "+
				    "DocumentUserEmail='"+document.getUserEmail()+"'");
			statement.executeQuery("DELETE FROM DocumentSharedWithPartnerUser WHERE "+
					"DocumentName='"+document.getName()+"' AND "+
				    "DocumentDateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND "+
				    "DocumentUserEmail='"+document.getUserEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void insert(Object value){
		//working with file
		Document document=(Document) value;
		try{
			//create fileInputStream
			File file=document.getFile();
			FileInputStream in=new FileInputStream(file); 
			//connect
			connectToDatabase();
			//SQL statement
			PreparedStatement statement=connection.prepareStatement("INSERT INTO Document VALUES("+
		    "'"+document.getName()+"', "+
		    "'"+document.getType()+"', "+
		    "to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd'), "+
		    " ? , "+
		    "'"+document.getUserEmail()+"', "+
		    "'"+(document.getIsSharedDocument()?1:0)+"')");
			statement.setBinaryStream(1, in, (int)file.length());
			statement.executeUpdate();
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
	public void update(Object value) {
		Document document=(Document) value;
		try{
			//create fileInputStream
			File file=document.getFile();
			FileInputStream in=new FileInputStream(file); 
			//connect
			connectToDatabase();
			//SQL statement
			PreparedStatement statement=connection.prepareStatement("UPDATE Document SET "+
				    "Type='"+document.getType()+"', "+
					"Blob= ? ,"+
				    "IsSharedDocument='"+(document.getIsSharedDocument()?1:0)+"' WHERE "+
				    "Name='"+document.getName()+"' AND "+
				    "DateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND "+ 
				    "UserEmail='"+document.getUserEmail()+"'");
			statement.setBinaryStream(1, in, (int)file.length());
			statement.executeUpdate();
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
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document WHERE UserEmail='"+user.getEmail()+"' AND Name='"+n+"' AND DateUploaded=to_date('"+new java.sql.Date(d.getTime())+"', 'yyyy-mm-dd')");
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
				result =(new Document(name,type,date,file,userEmail,isSharedDocument));
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
	public void insertDocumentSharedWithPartner(Document document,User partner){
		//working with file
		try{
			//create fileInputStream
			File file=document.getFile();
			FileInputStream in=new FileInputStream(file); 
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("INSERT INTO DocumentSharedWithPartnerUser VALUES("+
		    "'"+document.getName()+"', "+
		    "to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd'), "+
		    "'"+document.getUserEmail()+"', "+
		    "'"+partner.getEmail()+"')");
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public void deleteDocumentSharedWithPartner(Document document, User partner) {
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			statement.executeQuery("DELETE FROM DocumentSharedWithPartnerUser WHERE "+
			"DocumentName='"+document.getName()+"' AND "+
		    "DocumentdateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND "+
		    "DocumentUserEmail='"+document.getUserEmail()+
		    "PartnerUserEmail='"+partner.getEmail()+"'");
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	public ArrayList<Object> getAllPartnersSharedWithDocument(Document document) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM UserEntity,documentSharedWithPartnerUser WHERE documentSharedWithPartnerUser.PartnerUserEmail=userEntity.Email AND documentSharedWithPartnerUser.DocumentName='"+document.getName()+"' AND documentSharedWithPartnerUser.DocumentDateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd') AND documentSharedWithPartnerUser.DocumentUserEmail='"+document.getUserEmail()+"'");
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
				String mj=resultSet.getString("major");
				String mi=resultSet.getString("minor");
				boolean iap=resultSet.getBoolean("IsApprovedPartner");
				boolean iac=resultSet.getBoolean("IsApprovedCoordinator");
				boolean iav=resultSet.getBoolean("IsApprovedVolunteer");
				String on=resultSet.getString("organizationName");
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
	public ArrayList<Object> getAllDocumentsSharedWithPartner(User partner) {
		ArrayList<Object> result=new ArrayList<Object>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT * FROM Document,DocumentSharedWithPartnerUser WHERE DocumentSharedWithPartnerUser.PartnerUserEmail='"+partner.getEmail()+"' AND DocumentSharedWithPartnerUser.DocumentName=Document.Name AND DocumentSharedWithPartnerUser.DocumentDateUploaded=Document.DateUploaded AND DocumentSharedWithPartnerUser.DocumentUserEmail=Document.UserEmail");
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
}
