package stonehill.edu.VolunteerTrack;

import java.io.Serializable;

public class Photo implements Serializable 
{
	private String name;
	private String link;
	private Boolean isProfilePicture;
	private Boolean isVolunteerPicture;
	private Boolean isCoordinatorPicture;
	private Boolean isPartnerPicture;
	private String userEmail;
	
	
	/**
	 * @return the name 
	 */
	public Photo(String name, String link, Boolean isProfilePicture, Boolean isVolunteerPicture, Boolean isCoordinatorPicture, Boolean isPartnerPicture,String userEmail)
	{
	 this.name = name;
	 this.link = link;
	 this.isProfilePicture = isProfilePicture;
	 this.isVolunteerPicture = isVolunteerPicture;
	 this.isCoordinatorPicture = isCoordinatorPicture;
	 this.isPartnerPicture = isPartnerPicture;
	 this.userEmail = userEmail;
	}
	public String getName()
	{
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) 
	{
		this.name = name;
	}
	/**
	 * @return the link
	 */
	public String getlink()
	{
		return link;
	}
	/**
	 * @param isProfilePicture the isProfilePicture to set
	 */
	public void setlink(String link) 
	{
		this.link = link;
	}
	/**
	 * @return the isProfilePicture
	 */
	public Boolean getIsProfilePicture() {
		return isProfilePicture;
	}
	/**
	 * @param isProfilePicture the isProfilePicture to set
	 */
	public void setIsProfilePicture(Boolean isProfilePicture) {
		this.isProfilePicture = isProfilePicture;
	}
	/**
	 * @return the isVolunteerPicture
	 */
	public Boolean getIsVolunteerPicture() {
		return isVolunteerPicture;
	}
	/**
	 * @param isVolunteerPicture the isVolunteerPicture to set
	 */
	public void setIsVolunteerPicture(Boolean isVolunteerPicture) {
		this.isVolunteerPicture = isVolunteerPicture;
	}
	/**
	 * @return the isCoordinatorPicture
	 */
	public Boolean getIsCoordinatorPicture() {
		return isCoordinatorPicture;
	}
	/**
	 * @param isCoordinatorPicture the isCoordinatorPicture to set
	 */
	public void setIsCoordinatorPicture(Boolean isCoordinatorPicture) {
		this.isCoordinatorPicture = isCoordinatorPicture;
	}
	/**
	 * @return the isPartnerPicture
	 */
	public Boolean getIsPartnerPicture() {
		return isPartnerPicture;
	}
	/**
	 * @param isPartnerPicture the isPartnerPicture to set
	 */
	public void setIsPartnerPicture(Boolean isPartnerPicture) {
		this.isPartnerPicture = isPartnerPicture;
	}
	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}
}
