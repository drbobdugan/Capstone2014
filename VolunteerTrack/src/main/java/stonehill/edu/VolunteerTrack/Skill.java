package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

public class Skill implements Serializable {
	private String name;
	public Skill(String n){
		name=n;
	}
	public void setName(String n){
		name=n;
	}
	public String getName(){
		return name;
	}
	public boolean equals(Object o){
		return ((Skill)o).getName().equals(name);
	}
}
