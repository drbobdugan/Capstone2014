package stonehill.edu.VolunteerTrack;

import java.io.Serializable;
import java.awt.image.BufferedImage;
import java.util.Date;

public class Photo implements Serializable 
{
	private String name;
	private String userEmail;
	private Date date;
	private BufferedImage image;
	private Boolean isProfilePicture;
	private Boolean isVolunteerPicture;
	private Boolean isCoordinatorPicture;
	private Boolean isPartnerPicture;
	
	
	/**
	 * @return the name 
	 */
	public Photo(String name,String userEmail,Date date,BufferedImage image, Boolean isProfilePicture, Boolean isVolunteerPicture, Boolean isCoordinatorPicture, Boolean isPartnerPicture)
	{
	 this.name = name;
	 this.image = image;
	 this.date=date;
	 this.isProfilePicture = isProfilePicture;
	 this.isVolunteerPicture = isVolunteerPicture;
	 this.isCoordinatorPicture = isCoordinatorPicture;
	 this.isPartnerPicture = isPartnerPicture;
	 this.userEmail = userEmail;
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}


	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the userEmail
	 */
	public String getUserEmail() {
		return userEmail;
	}


	/**
	 * @param userEmail the userEmail to set
	 */
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}


	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}


	/**
	 * @return the image
	 */
	public BufferedImage getImage() {
		return image;
	}


	/**
	 * @param image the image to set
	 */
	public void setImage(BufferedImage image) {
		this.image = image;
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
}