package stonehill.edu.VolunteerTrack;
import java.util.Date;

public class AppEntry{
	String vName, eName;
	int pos;
	Date eDate;
	public AppEntry(){
		vName = "";
		eName = "";
		pos = 0;
		eDate = new Date();
	}
	public AppEntry(String vName, String eName, Integer pos, Date eDate){
		this.vName = vName;
		this.eName = eName;
		this.pos = pos;
		this.eDate = eDate;
	}
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public String geteName() {
		return eName;
	}
	public void seteName(String eName) {
		this.eName = eName;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public Date geteDate() {
		return eDate;
	}
	public void seteDate(Date eDate) {
		this.eDate = eDate;
	}
	
}