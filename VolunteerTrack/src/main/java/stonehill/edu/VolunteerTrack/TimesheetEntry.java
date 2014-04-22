package stonehill.edu.VolunteerTrack;
import java.util.*;
import java.io.Serializable;
public class TimesheetEntry implements Serializable{
	private String UserEmail;
	private Date DateTime;
	private String EventName;
	private boolean IsSubmitted;
	private boolean IsApproved;
	private int HoursLogged;
	public TimesheetEntry(){
		UserEmail="";
		DateTime=new Date();
		EventName="";
		IsSubmitted=false;
		IsApproved=false;
		HoursLogged=0;
	}
	public TimesheetEntry(String ue,Date dt,String en,boolean is,boolean ia,int hl){
		UserEmail=ue;
		DateTime=dt;
		EventName=en;
		IsSubmitted=is;
		IsApproved=ia;
		HoursLogged=hl;
	}
	public TimesheetEntry(String ue,String en,boolean is,boolean ia,int hl){
		UserEmail=ue;
		DateTime=new Date();
		EventName=en;
		IsSubmitted=is;
		IsApproved=ia;
		HoursLogged=hl;
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
}
