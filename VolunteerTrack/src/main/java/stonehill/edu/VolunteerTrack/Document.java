package stonehill.edu.VolunteerTrack;

import java.util.Date;
import java.io.File;
import java.io.Serializable;

public class Document implements Serializable {
	int id;
	String name;
	String type;
	Date dateUploaded;
	File file;
	String userEmail;
	boolean isSharedDocument;
	int userId;
	
public Document() {
	id = -1;
	name = null;
	type = null;
	dateUploaded = null;
	file = null;
	userEmail = null;
	isSharedDocument = false;
	int userId = -1;
}

public Document(int id, String name, String type, Date dateUploaded, File file,String userEmail, boolean isSharedDocument, int userId)
{
	this.id = id;
	this.name=name;
	this.type=type;
	this.dateUploaded=dateUploaded;
	this.file=file;
	this.userEmail=userEmail;
	this.isSharedDocument=isSharedDocument;
	this.userId = userId;
}

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public int getUserId() {
	return userId;
}

public void setUserId(int userId) {
	this.userId = userId;
}

/**
 * @return the name
 */
public String getName() {
	return name;
}

/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

/**
 * @return the type
 */
public String getType() {
	return type;
}

/**
 * @param type the type to set
 */
public void setType(String type) {
	this.type = type;
}

/**
 * @return the dateUploaded
 */
public Date getDateUploaded() {
	return dateUploaded;
}

/**
 * @param dateUploaded the dateUploaded to set
 */
public void setDateUploaded(Date dateUploaded) {
	this.dateUploaded = dateUploaded;
}

/**
 * @return the file
 */
public File getFile() {
	return file;
}

/**
 * @param file the file to set
 */
public void setFile(File file) {
	this.file = file;
}

/**
 * @return the userEmail
 */
public String getUserEmail() {
	return userEmail;
}

/**
 * @param userEmail the userEmail to set
 */
public void setUserEmail(String userEmail) {
	this.userEmail = userEmail;
}

/**
 * @return the isSharedDocument
 */
public boolean getIsSharedDocument() {
	return isSharedDocument;
}

/**
 * @param isSharedDocument the isSharedDocument to set
 */
public void setSharedDocument(boolean isSharedDocument) {
	this.isSharedDocument = isSharedDocument;
}

}