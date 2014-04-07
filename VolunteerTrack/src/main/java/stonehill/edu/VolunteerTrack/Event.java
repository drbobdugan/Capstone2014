package stonehill.edu.VolunteerTrack;

import java.awt.Point;
import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable {
     private String OwnerEmail, name, description, location;
     private int numPositions, numPositionsRemaining;
     private Date dateTime;
     private Skill [] skills;
     
     public Event () {
    	 OwnerEmail = "";
    	 name = "";
    	 dateTime = null;
    	 description = "";
    	 location = "";
    	 numPositions = 0;
    	 numPositionsRemaining = 0;
    	 skills = new Skill[0];
     }
     public Event (String partnerEmail, String name, Date dateTime, String description, String location , int numPositions, int numPositionsRemaining,Skill[] skills) {
     	 this.OwnerEmail = partnerEmail;
    	 this.name = name;
    	 this.dateTime = dateTime;
    	 this.description = description;
    	 this.location = location;
    	 this.numPositions = numPositions;
    	 this.numPositionsRemaining = numPositionsRemaining;
    	 this.skills = skills;
     }
     
     public User getPartner(){
    	 UserDao dao = new UserDao();
    	 return dao.getUserByUsername(OwnerEmail);
     }
     
     public String getName(){
    	 return name;
     }
     
     public void setName(String value){
    	 name = value;
     }
     
     public Date getDate(){
    	 return dateTime;
     }
     
     public void setDate(Date value){
    	 dateTime = value;
     }
     
     public String getDescription(){
    	 return description;
     }
     
     public void setDescription(String value){
    	 description = value;
     }
     
     public String getLocation(){
    	 return location;
     }
     
     public void setLocation(String value){
    	 location = value;
     }
     
     public int getNumPositions(){
    	 return numPositions;
     }
     
     public void setNumPositions(int value){
    	 numPositions = value;
     }
     
     public int getNumPositionsRemaining(){
    	 return numPositionsRemaining;
     }
     
     public void setNumPositionsRemaining(int value){
    	 numPositionsRemaining = value;
     }

     public Skill[] getSkills(){
         return skills;
     }
     
     public void setInterests(Skill[] value){
          skills = value;
     }
	/**
	 * @return the ownerEmail
	 */
	public String getOwnerEmail() {
		return OwnerEmail;
	}
	/**
	 * @param ownerEmail the ownerEmail to set
	 */
	public void setOwnerEmail(String ownerEmail) {
		OwnerEmail = ownerEmail;
	}
	/**
	 * @return the dateTime
	 */
	public Date getDateTime() {
		return dateTime;
	}
	/**
	 * @param dateTime the dateTime to set
	 */
	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}
	/**
	 * @param skills the skills to set
	 */
	public void setSkills(Skill[] skills) {
		this.skills = skills;
	}
}
