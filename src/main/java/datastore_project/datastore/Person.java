package datastore_project.datastore;

import java.util.List;

public class Person {
	protected int id;
    protected String firstName;
    protected String lastName;
    protected List<Address> addresses;
 
    public Person() {
    }
 
    public Person(int id) {
        this.id = id;
    }
 
    public Person(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }
     
    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName, List<Address> addresses) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
	}

	public Person(String firstName, String lastName, List<Address> addresses) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.addresses = addresses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}

}
