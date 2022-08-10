package datastore_project.datastore;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 

public class ControllerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatastoreDAO dao;
 
    public void init() {
        Connection connection = (Connection)getServletContext().getAttribute("connection");
    	dao = new DatastoreDAO(connection);
    	try {
	    	DatabaseMetaData dbm = connection.getMetaData();
	        ResultSet rs = dbm.getTables(null, "PUBLIC", "PERSON", null);
	        if (!rs.next()) {
	        	Statement s = connection.createStatement();
	        	String sql = "Create table Person (id int NOT NULL AUTO_INCREMENT, firstName varchar(50), lastName varchar(50), "
	            		+ "PRIMARY KEY (id))";
	            s.execute(sql);
	            sql = "Create table Address (id int NOT NULL AUTO_INCREMENT, personId int, street varchar(50), city varchar(15), state varchar(15), postcode varchar(15), "
	            		+ "PRIMARY KEY (id), FOREIGN KEY (personId) REFERENCES Person (id)  )";
	            s.execute(sql);
	        }else{
	            System.out.println("already exists");
	        }
    	} catch (SQLException e) {e.printStackTrace();}
    }
 
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();
 
        System.out.println("Action: " + action);
        try {
            switch (action) {
            case "/newPerson":
                addNewPerson(request, response);
                break;
            case "/insertPerson":
                insertPerson(request, response);
                break;
            case "/deletePerson":
                deletePerson(request, response);
                break;
            case "/editPerson":
                editPerson(request, response);
                break;
            case "/updatePerson":
                updatePerson(request, response);
                break;
            case "/newAddress":
                addNewAddress(request, response);
                break;
            case "/insertAddress":
                insertAddress(request, response);
                break;
            case "/deleteAddress":
                deleteAddress(request, response);
                break;
            case "/editAddress":
                editAddress(request, response);
                break;
            case "/updateAddress":
                updateAddress(request, response);
                break;
            case "/viewAddresses":
                listAddresses(request, response);
                break;
            default:
                listPersons(request, response);
                break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
 
    private void listAddresses(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
    	int id = Integer.parseInt(request.getParameter("id"));
        Person person = dao.getPerson(id);
        request.setAttribute("person", person);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonAddress.jsp");
        dispatcher.forward(request, response);
	}

	private void updateAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("addressId"));
		int personId = Integer.parseInt(request.getParameter("personId"));
		String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        Address address = new Address(id, personId, street, city, state);
        dao.updateAddress(address);
        Person person = dao.getPerson(personId);
        request.setAttribute("person", person);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonAddress.jsp");
        dispatcher.forward(request, response);
	}

	private void editAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		int personId = Integer.parseInt(request.getParameter("personId"));
        Address address = dao.getAddress(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp");
        request.setAttribute("address", address);
        request.setAttribute("personId", personId);
        dispatcher.forward(request, response);
	}

	private void deleteAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int id = Integer.parseInt(request.getParameter("id"));
		int personId = Integer.parseInt(request.getParameter("personId")); 
        Address address = new Address(id);
        dao.deleteAddress(address);
        Person person = dao.getPerson(personId);
        request.setAttribute("person", person);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonAddress.jsp");
        dispatcher.forward(request, response);
	}

	private void insertAddress(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
		int personId = Integer.parseInt(request.getParameter("personId"));
		String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        
        Address address = new Address(personId, street, city, state);
        dao.insertAddress(address);
        Person person = dao.getPerson(personId);
        request.setAttribute("person", person);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonAddress.jsp");
        dispatcher.forward(request, response);		
	}

	private void addNewAddress(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int personId = Integer.parseInt(request.getParameter("personId"));
		RequestDispatcher dispatcher = request.getRequestDispatcher("Address.jsp");
        request.setAttribute("personId", personId);
        dispatcher.forward(request, response);
		
	}

	private void listPersons(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Person> peopleList = dao.findAllPeople();
        request.setAttribute("peopleList", peopleList);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Home.jsp");
        dispatcher.forward(request, response);
    }
 
    private void addNewPerson(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonPage.jsp");
        dispatcher.forward(request, response);
    }
 
    private void editPerson(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Person person = dao.getPerson(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("PersonPage.jsp");
        request.setAttribute("person", person);
        dispatcher.forward(request, response);
 
    }
 
    private void insertPerson(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Person person = new Person(firstName, lastName);
        dao.insertPerson(person);
        response.sendRedirect("list");
    }
 
    private void updatePerson(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        Person person = new Person(id, firstName, lastName);
        dao.updatePerson(person);
        response.sendRedirect("list");
    }
 
    private void deletePerson(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
 
        Person person = new Person(id);
        dao.deletePerson(person);
        response.sendRedirect("list");
 
    }

}
