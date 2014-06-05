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
			statement.executeQuery("DELETE FROM EventRequiresDocument WHERE documentId = " + document.getId());
			statement.executeQuery("DELETE FROM DocumentSharedWithPartnerUser WHERE documentId = " + document.getId());
			statement.executeQuery("DELETE FROM Document WHERE id = " + document.getId());
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
		UserDao userDao = new UserDao();
		document.setId( getUniqueId());
		User user = userDao.getUserByUsername(document.getUserEmail());
		document.setUserId(user.getId());
		
		try{
			//create fileInputStream
			File file=document.getFile();
			FileInputStream in=new FileInputStream(file); 
			//connect
			connectToDatabase();
			//SQL statement
			String sql = "INSERT INTO Document VALUES("+
		    "'"+document.getName()+"', "+
		    "'"+document.getType()+"', "+
		    "to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd'), "+
		    " ? , "+
		    "'"+document.getUserEmail()+"', "+
		    "'"+(document.getIsSharedDocument()?1:0)+"', " +
		    document.getId() + ", " + 
		    document.getUserId() + ")";
			
			System.out.println(sql);
		    
		    PreparedStatement statement=connection.prepareStatement(sql);
			statement.setBinaryStream(1, in, (int) file.length());
			statement.executeUpdate();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
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
				    "IsSharedDocument='"+(document.getIsSharedDocument()?1:0)+"', " +  
				    "Name='"+document.getName()+"', " + 
				    "DateUploaded=to_date('"+new java.sql.Date(document.getDateUploaded().getTime())+"', 'yyyy-mm-dd'), " +  
				    "UserEmail='"+document.getUserEmail()+"', " + 
				    "UserId = " + document.getUserId() + 
				    "WHERE id="+document.getId());
			
			statement.setBinaryStream(1, in, (int)file.length());
			statement.executeUpdate();
			disconnectFromDatabase();
		
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<Object> selectAll() {
		String sql = "SELECT * FROM Document";
		ArrayList<Document> documentList =documentsSelectProcessor(sql);
		ArrayList<Object> result = new ArrayList<Object>();
		
		for (Document document : documentList)
		{
			result.add(document);
		}
		
		return result;
	}
	
	public Document getDocumentByUserNameAndDateTime(User user, String n, Date d) {
			String sql = "SELECT * FROM Document WHERE UserEmail='"+user.getEmail()+"' AND Name='"+n+"' AND DateUploaded=to_date('"+new java.sql.Date(d.getTime())+"', 'yyyy-mm-dd')";
			return documentSelectProcessor(sql);
	}
	
	public ArrayList<Document> getAllDocumentsByUser(User user) {
		String sql = "SELECT * FROM Document WHERE UserId="+user.getId();
		return documentsSelectProcessor(sql);
	}
	
	public ArrayList<Document> getAllSharedDocumentsByUser(User user) {
		String sql = "SELECT * FROM Document WHERE UserId="+user.getId() + " AND IsSharedDocument='1'";
		return documentsSelectProcessor(sql);
	}
	
	
	public ArrayList<Document> getAllDocumentsSharedWithPartner(User partner) {
		String sql = "SELECT * FROM Document,DocumentSharedWithPartnerUser WHERE DocumentSharedWithPartnerUser.PartnerUserEmail='"+partner.getEmail()+"' AND DocumentSharedWithPartnerUser.DocumentName=Document.Name AND DocumentSharedWithPartnerUser.DocumentDateUploaded=Document.DateUploaded AND DocumentSharedWithPartnerUser.DocumentUserEmail=Document.UserEmail";
		return documentsSelectProcessor(sql);	
	}
	
	public void insertDocumentSharedWithPartner(Document document, User partner){
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
		    "'"+partner.getEmail()+"'," +
		    document.getId() + ", " +
		    partner.getId() + ", " +
		    document.getUserId() + ")");
		    
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
			statement.executeQuery("DELETE FROM DocumentSharedWithPartnerUser WHERE documentId = " + document.getId() + " AND partnerId = " + partner.getId());
			statement.close();
			disconnectFromDatabase();
		}
	    catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public ArrayList<User> getAllPartnersSharedWithDocument(Document document) {
		ArrayList<User> result=new ArrayList<User>();
		try{
			//connect
			connectToDatabase();
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery("SELECT PartnerId FROM Document, DocumentSharedWithPartnerUser WHERE Document.id = DocumentSharedWithPartnerUser.DocumentId AND Document.Id = " + document.getId());
			
			//get tuples
			ArrayList<Integer> idList = new ArrayList<Integer>();
			
			while(resultSet.next())
			{
				int partnerId=resultSet.getInt("PartnerId");
				idList.add(partnerId);
			}
	
			//clean up
			resultSet.close();
			statement.close();
			disconnectFromDatabase();
			
			UserDao userDao = new UserDao();
			for (Integer id : idList)
			{
				result.add(userDao.getUserByUserId(id));
			}
			
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	
	public ArrayList<Document> documentsSelectProcessor(String sql) {
		ArrayList<Document> result=new ArrayList<Document>();
		try{
			//connect
			connectToDatabase();
	
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next())
			{
				result.add(processTuple(resultSet));
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
	
	public Document documentSelectProcessor(String sql) {
		Document result=null;
		try{
			//connect
			connectToDatabase();
	
			//SQL statement
			Statement statement=connection.createStatement();
			ResultSet resultSet=statement.executeQuery(sql);
			while (resultSet.next())
			{
				result = (processTuple(resultSet));
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


	Document processTuple(ResultSet resultSet) 
	{
		try 
		{
			int id = resultSet.getInt("Id");
			String name = resultSet.getString("Name");
			String type = resultSet.getString("Type");
			Date date = resultSet.getDate("DateUploaded");
			Blob blob = resultSet.getBlob("Blob");
			String userEmail = resultSet.getString("UserEmail");
			boolean isSharedDocument = resultSet.getBoolean("IsSharedDocument");
			int userId = resultSet.getInt("UserId");
			
			// blob to file
			File file = File.createTempFile(newFileName.getFileName(), "");
			BufferedInputStream in = new BufferedInputStream(blob.getBinaryStream());
			FileOutputStream out = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int r = 0;
			
			while ((r = in.read(buffer)) != -1) 
			{
				out.write(buffer, 0, r);
			}
		
			out.flush();
			out.close();
			in.close();
			blob.free();

			return new Document(id, name, type, date, file, userEmail, isSharedDocument, userId);

		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}

		return null;
	}
}
