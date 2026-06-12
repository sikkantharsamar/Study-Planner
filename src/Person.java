
import java.util.logging.Logger;

public abstract class Person {
        private static final Logger logger = Logger.getLogger(Person.class.getName());


    protected String name;
    protected String email;


    public Person(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public abstract void displayDetails();


    public void displayContactInfo() {
        logger.info("Name: " + name);
        logger.info("Email ID: " + email);
    }
}