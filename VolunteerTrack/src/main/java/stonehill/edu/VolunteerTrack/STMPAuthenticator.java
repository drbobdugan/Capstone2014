package stonehill.edu.VolunteerTrack;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class STMPAuthenticator extends Authenticator {
	private String email;
	
	public STMPAuthenticator(String s)
	{
		super();
		email = new String(s);
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public PasswordAuthentication getPasswordAuthentication() {
		String password = "";
		//In case multiple email accounts are used they should be hard coded in else if statements to select right password
		if(email.equals("voltracktest14@gmail.com"))
				password = "csrocks55";
		//else if(email.equals("some other email account web app is using")
		//        password = "something";
		return new PasswordAuthentication(email, password);		
	}
}
