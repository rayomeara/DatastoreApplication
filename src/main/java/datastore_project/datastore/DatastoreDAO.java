package datastore_project.datastore;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatastoreDAO {
	private Connection connection;
	
	public DatastoreDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean insertPerson(Person person) throws SQLException {
        String sql = "INSERT INTO Person (firstName, lastName) VALUES (?, ?)";
         
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getFirstName());
        statement.setString(2, person.getLastName());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        return rowInserted;
    }
    
    public List<Person> findAllPeople() throws SQLException {
        List<Person> personList = new ArrayList<>();
         
        String sql = "SELECT * FROM Person";
         
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
         
        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
             
            Person person = new Person(id, firstName, lastName);
            personList.add(person);
        }
         
        resultSet.close();
        statement.close();
         
        return personList;
    }
    
    public boolean deletePerson(Person person) throws SQLException {
        String sql = "DELETE FROM Address where personId = ?";
         
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, person.getId());
         
        statement.executeUpdate();
        
        sql = "DELETE FROM Person where id = ?";
         
        statement = connection.prepareStatement(sql);
        statement.setInt(1, person.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        return rowDeleted;     
    }
     
    public boolean updatePerson(Person person) throws SQLException {
        String sql = "UPDATE Person SET firstName = ?, lastName = ?";
        sql += " WHERE id = ?";
         
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, person.getFirstName());
        statement.setString(2, person.getLastName());
        statement.setInt(3, person.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        return rowUpdated;     
    }
     
    public Person getPerson(int id) throws SQLException {
        Person person = null;
        String sql = "SELECT * FROM Person WHERE id = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
            String firstName = resultSet.getString("firstName");
            String lastName = resultSet.getString("lastName");
            
            PreparedStatement statement2 = connection.prepareStatement("SELECT * FROM Address WHERE personId = ?");
            statement2.setInt(1, id);
            ResultSet resultSet2 = statement2.executeQuery();
            List<Address> addresses = new ArrayList<Address>();
            while(resultSet2.next()) {
            	int addressID = resultSet2.getInt("id");
            	String street = resultSet2.getString("street");
            	String city = resultSet2.getString("city");
            	String state = resultSet2.getString("state");
            	Address address = new Address(addressID, id, street, city, state);
            	addresses.add(address);
            }
            
            person = new Person(id, firstName, lastName, addresses);
        }
         
        resultSet.close();
        statement.close();
         
        return person;
    }
    
    public Address getAddress(int id) throws SQLException {
    	Address address=null;
    	PreparedStatement statement = connection.prepareStatement("SELECT * FROM Address WHERE id = ?");
        statement.setInt(1, id);
         
        ResultSet resultSet = statement.executeQuery();
         
        if (resultSet.next()) {
        	int addressId = resultSet.getInt("id");
        	int personId = resultSet.getInt("personId");
        	String street = resultSet.getString("street");
        	String city = resultSet.getString("city");
        	String state = resultSet.getString("state");
        	address = new Address(addressId, personId, street, city, state);
        }
         
        resultSet.close();
        statement.close();
         
        return address;
    }
    
    public boolean insertAddress(Address address) throws SQLException {
        String sql = "INSERT INTO Address (street, city, state, personId) VALUES (?, ?, ?, ?)";
         
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, address.getStreet());
        statement.setString(2, address.getCity());
        statement.setString(3, address.getState());
        statement.setInt(4, address.getPersonId());
         
        boolean rowInserted = statement.executeUpdate() > 0;
        statement.close();
        return rowInserted;
    }
    
    public boolean deleteAddress(Address address) throws SQLException {
        String sql = "DELETE FROM Address where id = ?";
         
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, address.getId());
         
        boolean rowDeleted = statement.executeUpdate() > 0;
        statement.close();
        return rowDeleted;     
    }
     
    public boolean updateAddress(Address address) throws SQLException {
        String sql = "UPDATE Address SET street = ?, city = ?, state = ?";
        sql += " WHERE id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, address.getStreet());
        statement.setString(2, address.getCity());
        statement.setString(3, address.getState());
        statement.setInt(4, address.getId());
         
        boolean rowUpdated = statement.executeUpdate() > 0;
        statement.close();
        return rowUpdated;     
    }
}
