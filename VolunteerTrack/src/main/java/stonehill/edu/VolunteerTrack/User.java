package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

public class User implements Serializable {
	private int    id;
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
	private String major;
	private String minor;
	private boolean isApprovedPartner;
	private boolean isApprovedCoordinator;
	private boolean isApprovedVolunteer;
	private String organizationName;
	private String missionStatement;
	
	
	// For serialization
	static final long serialVersionUID = 9L;
	
	public User()
	{
		int id=-1;
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
		major="";
		minor="";
		isApprovedPartner = false;
		isApprovedCoordinator = false;
		isApprovedVolunteer = false;
		organizationName = "";
		missionStatement = "";
	}

	public User(int id, String email, String password, String first, String last, String street, String city, String state, String zip, String phone, String partnerDescription, String volunteerDescription, boolean isPartner, boolean isCoordinator, boolean isVolunteer, String major, String minor, boolean isApprovedPartner, boolean isApprovedCoordinator, boolean isApprovedVolunteer, String organizationName, String missionStatement)
	{
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = first;
		this.lastName = last;
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phoneNumber = phone;
		this.partnerDescription = partnerDescription;
		this.volunteerDescription = volunteerDescription;
		this.isPartner = isPartner;
		this.isCoordinator = isCoordinator;
		this.isVolunteer = isVolunteer;
		this.major=major;
		this.minor=minor;
		this.isApprovedPartner = isApprovedPartner;
		this.isApprovedCoordinator = isApprovedCoordinator;
		this.isApprovedVolunteer = isApprovedVolunteer;
		this.organizationName = organizationName;
		this.missionStatement = missionStatement;
	}
	
	//added constructor to create a user from a user for login purpose
	public User(User user)
	{
		id = user.getId();
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
		major=user.getMajor();
		minor=user.getMinor();
		isApprovedPartner = user.getIsApprovedPartner();
		isApprovedCoordinator = user.getIsApprovedCoordinator();
		isApprovedVolunteer = user.getIsApprovedVolunteer();
		organizationName = user.getOrganizationName();
		missionStatement = user.getMissionStatement();
	}
	
	public String getMinor() {
		// TODO Auto-generated method stub
		return minor;
	}

	public String getMajor() {
		// TODO Auto-generated method stub
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}
	public void setMinor(String minor) {
		this.minor = minor;
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
	
	public boolean getIsApprovedPartner() {
		return isApprovedPartner;
	}

	public void setApprovedPartner(boolean isApprovedPartner) {
		this.isApprovedPartner = isApprovedPartner;
	}

	public boolean getIsApprovedCoordinator() {
		return isApprovedCoordinator;
	}

	public void setApprovedCoordinator(boolean isApprovedCoordinator) {
		this.isApprovedCoordinator = isApprovedCoordinator;
	}

	public boolean getIsApprovedVolunteer() {
		return isApprovedVolunteer;
	}

	public void setApprovedVolunteer(boolean isApprovedVolunteer) {
		this.isApprovedVolunteer = isApprovedVolunteer;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	@Override
	public String toString()
	{
		return ""+email+" / "+password+" / "+firstName+" / "+lastName;
	}

	public String getMissionStatement() {
		return missionStatement;
	}

	public void setMissionStatement(String missionStatement) {
		this.missionStatement = missionStatement;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}