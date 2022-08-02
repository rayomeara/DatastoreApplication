package datastore_project.datastore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class App 
{
	public static void main(String[] args) throws SQLException {
        String jdbcURL = "jdbc:h2:~/test";
        String username = "sa";
        String password = "1234";
 
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

	private static void createTables(Statement s) throws SQLException {
		String sql = "DROP TABLE IF EXISTS person, address";
        s.executeUpdate(sql);
        
        sql = "Create table Person (id int NOT NULL AUTO_INCREMENT, firstName varchar(50), lastName varchar(50), "
        		+ "PRIMARY KEY (id))";
        s.execute(sql);
        sql = "Create table Address (id int NOT NULL AUTO_INCREMENT, personId int, street varchar(50), city varchar(15), state varchar(15), postcode varchar(15), "
        		+ "PRIMARY KEY (id), FOREIGN KEY (personId) REFERENCES Person (id)  )";
        s.execute(sql);
	}
	
	private static void insertSampleRecords(Connection connection, Statement statement) throws SQLException {
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
}
