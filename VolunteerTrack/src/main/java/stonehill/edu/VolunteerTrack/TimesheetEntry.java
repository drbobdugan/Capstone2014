package stonehill.edu.VolunteerTrack;
import java.util.*;
import java.io.Serializable;
public class TimesheetEntry implements Serializable{
	
	private int Id;
	private int UserId;
	private String UserEmail;
	private Date DateTime;
	private String EventName;
	private int EventId;
	private boolean IsSubmitted;
	private boolean IsApproved;
	private int HoursLogged;
	private String OrganizationName;
	
	public TimesheetEntry(){
		Id = -1;
		UserId = -1;
		UserEmail="";
		DateTime=new Date();
		EventName="";
		EventId=-1;
		IsSubmitted=false;
		IsApproved=false;
		HoursLogged=0;
		OrganizationName="";
	}
	public TimesheetEntry(String userEmail,Date dateTime,String eventName,boolean isSubmitted,boolean isApproved,int hoursLogged,String organizationName, int eventId, int userId, int Id){
		UserEmail=userEmail;
		DateTime=dateTime;
		EventName=eventName;
		this.EventId = eventId;
		IsSubmitted=isSubmitted;
		IsApproved=isApproved;
		HoursLogged=hoursLogged;
		OrganizationName=organizationName;
		this.UserId = userId;
		this.Id = Id;
	}
	public TimesheetEntry(String userEmail,String eventName,boolean isSubmitted,boolean isApproved,int hoursLogged,String organizationName, int eventId, int Id){
		UserEmail=userEmail;
		DateTime=new Date();
		EventName=eventName;
		this.EventId = eventId;
		IsSubmitted=isSubmitted;
		IsApproved=isApproved;
		HoursLogged=hoursLogged;
		OrganizationName=organizationName;
		this.Id = Id;
		this.UserId = UserId;
	}
	
	
	
	public int getId() {
		return Id;
	}
	public void setId(int id) {
		Id = id;
	}
	public int getUserId() {
		return UserId;
	}
	public void setUserId(int userId) {
		UserId = userId;
	}
	
	public String getUserEmail(){
		return UserEmail;
	}
	public Date getDateTime(){
		return DateTime;
	}
	public void setDateTime(Date dt){
		DateTime=dt;
	}
	public String getEventName(){
		return EventName;
	}
	public void setEventId(int eventId){
		EventId=eventId;
	}
	public int getEventId(){
		return EventId;
	}
	public void setEventName(String en){
		EventName=en;
	}
	public boolean getIsSubmitted(){
		return IsSubmitted;
	}
	public void setIsSubmitted(boolean is){
		IsSubmitted=is;
	}
	public boolean getIsApproved(){
		return IsApproved;
	}
	public void setIsApproved(boolean ia){
		IsApproved=ia;
	}
	public int getHoursLogged(){
		return HoursLogged;
	}
	public void setHoursLogged(int hl){
		HoursLogged=hl;
	}
	public String getOrganizationName() {
		return OrganizationName;
	}
	public void setOrganizationName(String organizationName) {
		OrganizationName = organizationName;
	}
}
