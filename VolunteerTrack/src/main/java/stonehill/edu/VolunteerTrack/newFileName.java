package stonehill.edu.VolunteerTrack;

public class newFileName {
	private static newFileName nfn=new newFileName();
	static int current;
	private newFileName(){
		current=0;
	}
	public static String getFileName(){
		current++;
		return "temp"+current;
	}
	public static newFileName start(){
		return nfn;
	}
}
