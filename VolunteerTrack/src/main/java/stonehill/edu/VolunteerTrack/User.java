package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

public class User implements Serializable {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String street;
	private String city;
	private String state;
	private String zip;
	private String phoneNumber;
	private String partnerDescription;
	private String volunteerDescription;
	private boolean isPartner;
	private boolean isCoordinator;
	private boolean isVolunteer;
	private boolean isApproved;
	
	// For serialization
	static final long serialVersionUID = 9L;
	
	public User()
	{
		email = "";
		password = "";
		firstName = "";
		lastName = "";
		street = "";
		city = "";
		state = "";
		zip = "";
		phoneNumber = "";
		partnerDescription = "";
		volunteerDescription = "";
		isPartner = false;
		isCoordinator = false;
		isVolunteer = false;
		isApproved = false;
	}

	public User(String ema, String pass, String fir, String las, String str, String cit, String sta, String zi, String pho, String par, String vol, boolean isp, boolean isc, boolean isv, boolean isa)
	{
		email = ema;
		password = pass;
		firstName = fir;
		lastName = las;
		street = str;
		city = cit;
		state = sta;
		zip = zi;
		phoneNumber = pho;
		partnerDescription = par;
		volunteerDescription = vol;
		isPartner = isp;
		isCoordinator = isc;
		isVolunteer = isv;
		isApproved = isa;
				
	}
	
	//added constructor to create a user from a user for login purpose
	public User(User user)
	{
		email = user.getEmail();
		password = user.getPassword();
		firstName = user.getFirstName();
		lastName = user.getLastName();
		street = user.getStreet();
		city = user.getCity();
		state = user.getState();
		zip = user.getZip();
		phoneNumber = user.getPhoneNumber();
		partnerDescription = user.getPartnerDescription();
		volunteerDescription = user.getVolunteerDescription();
		isPartner = user.getIsPartner();
		isCoordinator = user.getIsCoordinator();
		isVolunteer = user.getIsVolunteer();
		isApproved = user.getIsApproved();
	}
	
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the zip
	 */
	public String getZip() {
		return zip;
	}

	/**
	 * @param zip the zip to set
	 */
	public void setZip(String zip) {
		this.zip = zip;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the partnerDescription
	 */
	public String getPartnerDescription() {
		return partnerDescription;
	}

	/**
	 * @param partnerDescription the partnerDescription to set
	 */
	public void setPartnerDescription(String partnerDescription) {
		this.partnerDescription = partnerDescription;
	}

	/**
	 * @return the volunteerDescription
	 */
	public String getVolunteerDescription() {
		return volunteerDescription;
	}

	/**
	 * @param volunteerDescription the volunteerDescription to set
	 */
	public void setVolunteerDescription(String volunteerDescription) {
		this.volunteerDescription = volunteerDescription;
	}

	/**
	 * @return the isPartner
	 */
	public boolean getIsPartner() {
		return isPartner;
	}

	/**
	 * @param isPartner the isPartner to set
	 */
	public void setIsPartner(boolean isPartner) {
		this.isPartner = isPartner;
	}

	/**
	 * @return the isCoordinator
	 */
	public boolean getIsCoordinator() {
		return isCoordinator;
	}

	/**
	 * @param isCoordinator the isCoordinator to set
	 */
	public void setIsCoordinator(boolean isCoordinator) {
		this.isCoordinator = isCoordinator;
	}

	/**
	 * @return the isVolunteer
	 */
	public boolean getIsVolunteer() {
		return isVolunteer;
	}

	/**
	 * @param isVolunteer the isVolunteer to set
	 */
	public void setIsVolunteer(boolean isVolunteer) {
		this.isVolunteer = isVolunteer;
	}

	/**
	 * @return the isApproved
	 */
	public boolean getIsApproved() {
		return isApproved;
	}

	/**
	 * @param isApproved the isApproved to set
	 */
	public void setIsApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}
	
	@Override
	public String toString()
	{
		return ""+email+" / "+password+" / "+firstName+" / "+lastName;
	}
	
	
}