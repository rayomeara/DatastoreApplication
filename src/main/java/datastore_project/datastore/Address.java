package datastore_project.datastore;

public class Address {
	protected int id;
    protected int personId;
    protected String street;
    protected String city;
    protected String state;

    public Address() {
	}
	
    public Address(int id) {
		this.id = id;
	}
	
    public Address(int id, int personId, String street, String city, String state) {
		this(personId, street, city, state);
		this.id = id;
		
	}
	
    public Address(int personId, String street, String city, String state) {
		this.personId = personId;
		this.street = street;
		this.city = city;
		this.state = state;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}
