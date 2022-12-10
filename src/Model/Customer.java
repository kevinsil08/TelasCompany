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
    private String docCiRuc;

    public Customer() {
    }
    
    public Customer(Customer person) {
        
    }

    public Customer(int id, String firstName, String lastName, String docCiRuc , String direction ,String email  ) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.direction = direction;
        this.docCiRuc = docCiRuc;
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

    public String getDocCiRuc() {
        return docCiRuc;
    }

    public void setDocCiRuc(String docCiRuc) {
        this.docCiRuc = docCiRuc;
    }


    
    
}