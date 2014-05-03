package stonehill.edu.VolunteerTrack;
import java.io.Serializable;
import java.util.Date;

public class AppEntry implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Event event;
	private User user;
	public AppEntry(){
		event = null;
		user = null;
	}
	public AppEntry(Event event, User user){
		this.event = event;
		this.user = user;
	}
	public User getUser(){
		return user;
	}
	public void setUser(User value){
		user = value;
	}
	public Event getEvent(){
		return event;
	}
	public void setEvent(Event value){
		event = value;
	}
}