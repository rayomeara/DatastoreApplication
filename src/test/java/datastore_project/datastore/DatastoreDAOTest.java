package datastore_project.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class DatastoreDAOTest extends TestCase {
	
	private String jdbcURL;
	private String username;
	private String password;
	
	private DatastoreDAO datastoreDAO;
	
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public DatastoreDAOTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( DatastoreDAOTest.class );
    }

    private void setupData() throws SQLException {
    	jdbcURL = "jdbc:h2:./test";
        username = "sa";
        password = "1234";
        datastoreDAO = new DatastoreDAO(DriverManager.getConnection(jdbcURL, username, password));
        
        Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 embedded database.");
 
        Statement statement = connection.createStatement();
        
        createTables(statement);
        insertSampleRecords(connection, statement);
        
        String sql = "SELECT * FROM Person";
        ResultSet resultSet = statement.executeQuery(sql);
 
        int count = 0;
 
        while (resultSet.next()) {
            count++;
 
            int ID = resultSet.getInt("id");
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Address WHERE personId = ?");
            statement2.setInt(1, ID);
            ResultSet resultSet2 = statement2.executeQuery();
            int addressCount = 0;
            while(resultSet2.next()) {
            	addressCount++;
            	int addressID = resultSet2.getInt("id");
            	String street = resultSet2.getString("street");
            	String city = resultSet2.getString("city");
            	String state = resultSet2.getString("state");
                System.out.println("Address #" + addressCount + ": " + addressID + ", " + street + ", " + city + ", " + state);
            }
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            System.out.println("Student #" + count + ": " + ID + ", " + firstName + ", " + lastName);
        }
 
        connection.close();
    }
    
    private void createTables(Statement s) throws SQLException {
		String sql = "DROP TABLE IF EXISTS person, address";
        s.executeUpdate(sql);
        
        sql = "Create table Person (id int NOT NULL AUTO_INCREMENT, firstName varchar(50), lastName varchar(50), "
        		+ "PRIMARY KEY (id))";
        s.execute(sql);
        sql = "Create table Address (id int NOT NULL AUTO_INCREMENT, personId int, street varchar(50), city varchar(15), state varchar(15), postcode varchar(15), "
        		+ "PRIMARY KEY (id), FOREIGN KEY (personId) REFERENCES Person (id)  )";
        s.execute(sql);
	}
	
	private void insertSampleRecords(Connection connection, Statement statement) throws SQLException {
		String sql = "Insert into person (firstName, lastName) values ('Tony', 'Jones')";
        int rows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (rows > 0) {
            System.out.println("Inserted a new person.");
        }
        
        Long generatedKey = null;
        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            generatedKey = rs.getLong(1);
        }
        
        PreparedStatement prepStatement = connection.prepareStatement("Insert into address (personId, street, city, state) values (?, '1 Boing Road', 'Cork', 'Cork')");
        prepStatement.setLong(1, 1);
        rows = prepStatement.executeUpdate();
        if (rows > 0) {
            System.out.println("Inserted a new address.");
        }
        
        sql = "Insert into address (personId, street, city, state) values (" + generatedKey + ", '1 Carling Lough', 'Galway', 'Galway')";
        rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Inserted a new address.");
        }
        
        sql = "Insert into address (personId, street, city, state) values (" + generatedKey + ", '1 Glendale Avenue', 'Limerick', 'Limerick')";
        rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Inserted a new address.");
        }
        
        sql = "Insert into person (firstName, lastName) values ('Frank', 'Jones')";
        rows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (rows > 0) {
            System.out.println("Inserted a new person.");
        }
        
        rs = statement.getGeneratedKeys();
        if (rs.next()) {
            generatedKey = rs.getLong(1);
        }
        
        sql = "Insert into address (personId, street, city, state) values (" + generatedKey + ", '2 Boing Road', 'Cork', 'Cork')";
        rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Inserted a new address.");
        }
        
        sql = "Insert into person (firstName, lastName) values ('Joann', 'Grimes')";
        rows = statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
        if (rows > 0) {
            System.out.println("Inserted a new person.");
        }
        
        rs = statement.getGeneratedKeys();
        if (rs.next()) {
            generatedKey = rs.getLong(1);
        }
        
        sql = "Insert into address (personId, street, city, state) values (" + generatedKey + ", '3 Boing Road', 'Cork', 'Cork')";
        rows = statement.executeUpdate(sql);
        if (rows > 0) {
            System.out.println("Inserted a new address.");
        }
		
	}

    
    public void testSetupData() throws SQLException {
   		setupData();
    	
    	Connection connection = DriverManager.getConnection(jdbcURL, username, password);
        System.out.println("Connected to H2 embedded database.");
 
        Statement statement = connection.createStatement();
        
        String sql = "SELECT * FROM Person";
        ResultSet resultSet = statement.executeQuery(sql);
 
        int count = 0;
        while (resultSet.next()) {
            count++;
        }
        
        assertEquals(3, count);
        
        connection.close();
    }
    
    public void testFindAllPeople() throws SQLException {
   		setupData();
    	
   		List<Person> people = datastoreDAO.findAllPeople();
   		        
        assertEquals(3, people.size());        
    }
    
    public void testInsertPerson() throws SQLException {
   		setupData();
    	
   		Person person = new Person("Timmy", "Tighter");
   		datastoreDAO.insertPerson(person);
   		
   		List<Person> people = datastoreDAO.findAllPeople();
   		
        assertEquals(4, people.size());
    }
    
    public void testDeletePerson() throws SQLException {
   		setupData();
    	
   		Person person = new Person(3);
   		datastoreDAO.deletePerson(person);
   		
   		List<Person> people = datastoreDAO.findAllPeople();
        
        assertEquals(2, people.size());
    }
    
    public void testGetPerson() throws SQLException {
   		setupData();
    	
   		Person person = datastoreDAO.getPerson(1);
   		
   		assertEquals("Tony", person.getFirstName());
   		assertEquals("Jones", person.getLastName());
   		assertEquals(3, person.getAddresses().size());
    }
    
    public void testUpdatePerson() throws SQLException {
   		setupData();
    	
   		Person person = datastoreDAO.getPerson(1);
   		person.setLastName("Grenola");
   		datastoreDAO.updatePerson(person);
   		
   		Person updatedPerson = datastoreDAO.getPerson(1);
   		assertEquals("Grenola", updatedPerson.getLastName());
    }
    
    public void testGetAddress() throws SQLException {
    	setupData();
    	
    	Address address = datastoreDAO.getAddress(1);
    	assertEquals("1 Boing Road", address.getStreet());
    	assertEquals("Cork", address.getCity());
    	assertEquals("Cork", address.getState());
    }
    
    public void testInsertAddress() throws SQLException {
    	setupData();
    	
    	Address address = new Address(99, 1, "test", "test", "test");
    	datastoreDAO.insertAddress(address);
    	
    	Person person = datastoreDAO.getPerson(1);
    	assertEquals(4, person.getAddresses().size());
    }
    
    public void testDeleteAddress() throws SQLException {
    	setupData();
    	
    	Address address = new Address(1);
    	datastoreDAO.deleteAddress(address);
    	
    	Person person = datastoreDAO.getPerson(1);
    	assertEquals(2, person.getAddresses().size());
    }
    
    public void testUpdateAddress() throws SQLException {
    	setupData();
    	
    	Address address = new Address(1, 1, "10 Rashford Road", "Tralee", "Kerry");
    	datastoreDAO.updateAddress(address);
    	
    	Address updatedAddress = datastoreDAO.getAddress(1);
    	assertEquals("10 Rashford Road", updatedAddress.getStreet());
    	assertEquals("Tralee", updatedAddress.getCity());
    	assertEquals("Kerry", updatedAddress.getState());
    }
}
