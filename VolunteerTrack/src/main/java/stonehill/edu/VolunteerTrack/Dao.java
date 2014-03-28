package stonehill.edu.VolunteerTrack;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.ArrayList;

public abstract class Dao {
	private String driverName="oracle.jdbc.driver.OracleDriver";
	private String connectionURL="jdbc:oracle:thin:@//204.144.14.137:1521/orcl";
	private Driver driver;
	protected Connection connection;
	
	//connect to the database
	protected void connectToDatabase(){
		try{
			System.out.println("Trying to connect to database...");
			//Load the database specific vendor driver
			driver=(java.sql.Driver)Class.forName(driverName).newInstance();
			//Connect to the database
			connection=DriverManager.getConnection(connectionURL,"Capstone2014","csrocks55");
			System.out.println("Connection successfull!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	protected void disconnectFromDatabase(){
		try{
			System.out.println("Trying to disconnect from the database...");
			connection.close();
			System.out.println("Disconnection successful!");
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	public abstract void delete(Object value);
	public abstract void insert(Object value);
	public abstract ArrayList<Object> selectAll();
	public abstract void update(Object value);
}
