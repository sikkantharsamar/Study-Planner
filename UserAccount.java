
public class UserAccount {

    private String username;
    private String password;
    private Student student; // Composition with Student (which extends Person)


    public UserAccount(String username, String password, Student student) {
        this.username = username;
        this.password = password;
        this.student = student;
    }


    public String getUsername() {
        return username;
    }

    public Student getStudent() {
        return student;
    }


    public boolean validateCredentials(String inputUsername, String inputPassword) {
        return this.username.equals(inputUsername) && this.password.equals(inputPassword);
    }


    public void displayAccountInfo() {
        System.out.println("\n=== Account Information ===");
        System.out.println("Username: " + username);
        System.out.println("Student: " + student.getName());
        System.out.println("==========================");
    }
}