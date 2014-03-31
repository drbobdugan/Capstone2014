package stonehill.edu.VolunteerTrack;



import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.Check;
import org.apache.wicket.markup.html.form.CheckGroup;
import org.apache.wicket.markup.html.form.CheckGroupSelector;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.model.CompoundPropertyModel;

public class PartnerProfileView extends WebPage {
	
	public PartnerProfileView()
	{
		final Input input= new Input();
		setDefaultModel(new CompoundPropertyModel<Input>(input));
		
		Form<?> form = new Form("form")
		{
			@Override
			protected void onSubmit()
			{
				//do some stuff
			}
		};
		
		
		Button upload = new Button("upload_photo")
		{
			@Override
			public void onSubmit()
			{
				info("upload_photo.onSubmit executed");
			}
		};
		
		Button save = new Button("submit")
		{
			@Override
			public void onSubmit()
			{
				//overwrite current partner info using dao and values in input fields
			}
		};
		
		Button cancel = new Button("cancel")
		{
			@Override
			public void onSubmit()
			{
				info("cancel.onSubmit executed");
			}
		};
		form.add(upload);
		form.add(save);
		form.add(cancel);
		add(form);
		
		form.add(new TextField<String>("organization"));
		form.add(new TextField<String>("email"));
		form.add(new TextField<String>("phone"));
		form.add(new TextField<String>("street"));
		form.add(new TextField<String>("city"));
		form.add(new TextField<String>("state"));
		form.add(new TextField<String>("zip"));
		form.add(new TextField<String>("links"));
		form.add(new TextField<String>("current"));
		form.add(new TextField<String>("new_password"));
		form.add(new TextField<String>("confirm_password"));
		
		form.add(new TextArea<String>("mission"));
		

	}
	
	private static class Input
	{
		//create partner object by selecting from userDao
		
		/*
		 * Partner p= new Partner();
		 * use getters from partner object model to set fields
		public String organization = "";
		public String email = "";
		public String phone = "";
		public String street = "";
		public String city = "";
		public String state = "";
		public String zip = "";
		public String links = "";
		
		public String mission="";

		*/
	}

}
