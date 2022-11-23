package Model;

/**
 *
 * @author kevin
 */
public class Customer {
    
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String direction;
    private String phoneNumber;

    public Customer() {
    }
    
    public Customer(Customer person) {
        
    }

    public Customer(int id, String firstName, String lastName, String email, String direction, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.direction = direction;
        this.phoneNumber = phoneNumber;
    }

    public String getDirection() {
        return direction;
    }
    
    public void setDirection(String direction) {
        this.direction = direction;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    
}