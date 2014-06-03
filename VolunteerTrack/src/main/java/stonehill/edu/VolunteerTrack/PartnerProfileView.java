package stonehill.edu.VolunteerTrack;


import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.markup.html.image.resource.BufferedDynamicImageResource;

import com.googlecode.wicket.jquery.ui.form.button.Button;

public class PartnerProfileView extends VolunteerTrackBaseView {

	private static final long serialVersionUID = 1L;
	transient UserDao userDao= new UserDao();
	transient SkillDao skillsDao = new SkillDao();
	transient PhotoDao photoDao = new PhotoDao();

	Form uploadProfilePicture, profileInformation, passwordManagement, skillsManagement;
	TextField organization, email, phoneNumber, street, city, state, zip;
	private static TextField current, new_password, confirm_password; 
	User currentuser;
	Label feedback;
	private String currentPasswordPlaceholder = "";
	private String newPasswordPlaceholder = "";
	private String confirmPasswordPlaceholder = "";
	private TextArea<String> missionStatement;

	public PartnerProfileView()
	{
		final  PropertyModel<String> currentPasswordModel = new PropertyModel<String>(this, "currentPasswordPlaceholder");
		final  PropertyModel<String> newPasswordModel = new PropertyModel<String>(this, "newPasswordPlaceholder");
		final  PropertyModel<String> confirmPasswordModel = new PropertyModel<String>(this, "confirmPasswordPlaceholder");

		add(new Label ("currentPasswordPlaceholder", currentPasswordModel));
		add(new Label ("newPasswordPlaceholder", newPasswordModel));
		add(new Label ("confirmPasswordPlaceholder", confirmPasswordModel));
		currentuser = CustomSession.get().getUser();
		
		////////////////////////
		// Profile Picture Code...
		////////////////////////
	    ResourceReference profilePictureResourceReference =  new ResourceReference(PartnerProfileView.class, "profilePicture")
	    {
	            @Override
	            public IResource getResource()
	            {
	                final BufferedDynamicImageResource resource = new BufferedDynamicImageResource();
	                PhotoDao photoDao = new PhotoDao();
	                Photo photo = photoDao.getProfilePhotoByUser(currentuser);
	                resource.setImage(photo.getImage());
	                return resource;
	            }
	    };
	        
		final FileUploadField fileUpload = new FileUploadField("uploadPhoto");
		uploadProfilePicture = new Form("uploadProfilePicture")
		{	@Override
			public void onSubmit() 
		{
			final FileUpload uploadedFile=fileUpload.getFileUpload();

			if(uploadedFile!=null) 
			{
				try 
				{
					System.out.println("PartnerProfileView:uploadProfilePcitureForm:onSubmit(): uploadedFile size: " + uploadedFile.getSize());
					File theFile= uploadedFile.writeToTempFile();
					BufferedImage theBufferedImage = ImageIO.read(theFile);
					System.out.println("PartnerProfileView:uploadProfilePcitureForm:onSubmit(): theBufferedImage size: " + theBufferedImage.getHeight());
					Photo thePhoto = new Photo(-1,uploadedFile.getClientFileName(),currentuser.getEmail(),new Date(), theBufferedImage,true, currentuser.getIsVolunteer(),currentuser.getIsCoordinator(), currentuser.getIsPartner());
					photoDao.insert(thePhoto);
				}
				catch(Exception e) 
				{
					throw new IllegalStateException("PartnerProfileView:PartnerProfileView(): Error trying to do the .create or .writeto");
				}		
			}
		}
		};
		
		uploadProfilePicture.setMultiPart(true);
		uploadProfilePicture.add(fileUpload);
		add(new org.apache.wicket.markup.html.image.Image("profilePicture", profilePictureResourceReference));
		
		profileInformation = new Form<Void>("profileInformation");
		profileInformation.setModel(new Model(currentuser));


		profileInformation.add(organization = new TextField<String>("organization", new PropertyModel(currentuser,"organizationName")));
		profileInformation.add(email = new TextField<String>("email", new PropertyModel(currentuser, "email")));
		profileInformation.add(phoneNumber = new TextField<String>("phoneNumber", new PropertyModel(currentuser, "phoneNumber")));
		profileInformation.add(street = new TextField<String>("street", new PropertyModel(currentuser, "street")));
		profileInformation.add(city = new TextField<String>("city", new PropertyModel(currentuser, "city")));
		profileInformation.add(state = new TextField<String>("state", new PropertyModel(currentuser, "state")));
		profileInformation.add(zip = new TextField<String>("zip", new PropertyModel(currentuser, "zip")));
		profileInformation.add(missionStatement = new TextArea<String>("missionStatement", new PropertyModel(currentuser, "missionStatement")));

		Button save = new Button("saveProfile")
		{
			@Override
			public void onSubmit()
			{
				userDao.update(currentuser);
				setResponsePage(PartnerProfileView.class);
			}
		};

		Button cancel = new Button("cancel")
		{
			@Override
			public void onSubmit()
			{
				setResponsePage(PartnerProfileView.class);
			}
		};

		profileInformation.add(save);
		profileInformation.add(cancel);
		//Password Management============================================================
		passwordManagement = new Form<Void>("passwordManagement");
		passwordManagement.setModel(new Model(currentuser));
		// new TextField<String>("name", messageModel);
		passwordManagement.add(current = new TextField<String>("current", currentPasswordModel));
		passwordManagement.add(new_password = new TextField<String>("new_password", newPasswordModel));
		passwordManagement.add(confirm_password = new TextField<String>("confirm_password", confirmPasswordModel));
		passwordManagement.add(feedback= new Label ("feedback"));
		Button confirm= new Button("updatePassword")
		{
			@Override
			public void onSubmit()
			{

				String c = current.getDefaultModelObjectAsString();
				String np= new_password.getDefaultModelObjectAsString();
				String conp= confirm_password.getDefaultModelObjectAsString();

				System.out.println(c);
				System.out.println(np);
				System.out.println(conp);
				if(c.equals(currentuser.getPassword())  && np.equals(conp)){
					currentuser.setPassword(np);
					userDao.update(currentuser);
					feedback.setDefaultModel(new Model("Password Change Success"));
					//messageModel.setObject("Password Change Success");
				}
				setResponsePage(PartnerProfileView.class);
			}
		};

		passwordManagement.add(confirm);
		
		//SkillsManagement===============================================================
		skillsManagement= new Form<Void>("skillsManagement");
		ArrayList<Object> skillslist = skillsDao.selectAll();
		ArrayList<Object> userskills = skillsDao.getAllSkillsByUser(currentuser);
		final ArrayList<String> skillsSelect = new ArrayList<String>();

		Button saveskills= new Button("saveSkills"){
			@Override
			public void onSubmit(){
				userDao.deleteAllUserSkills(currentuser);
				for(int i=0; i<skillsSelect.size();i++){
					Skill temp = new Skill(skillsSelect.get(i));
					userDao.insertUserHasSkill(currentuser, temp);
				}
				this.setResponsePage(PartnerProfileView.class);
			}
		};

		String[] skillarray = new String[skillslist.size()];

		for(int i=0;i<userskills.size();i++)
		{
			skillsSelect.add(((Skill)userskills.get(i)).getName());
		}

		for(int i=0;i<skillslist.size();i++)
		{
			skillarray[i]=((Skill)skillslist.get(i)).getName();
		}

		List<String> fixedskills = Arrays.asList(skillarray);

		//skillsSelect.add("Animals");

		final CheckBoxMultipleChoice<String> skillsBoxes = new CheckBoxMultipleChoice<String>("skills",new Model(skillsSelect),fixedskills);

		skillsManagement.add(saveskills);
		skillsManagement.add(skillsBoxes);

		add(uploadProfilePicture);
		add(profileInformation);
		add(passwordManagement);
		add(skillsManagement);
	}

}
