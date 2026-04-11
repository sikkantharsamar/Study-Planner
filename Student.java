
public class Student extends Person {

    private String studentId;


    public Student(String studentId, String name, String email) {
        super(name, email); // Call parent constructor
        this.studentId = studentId;
    }


    public String getStudentId() {
        return studentId;
    }


    @Override
    public void displayDetails() {
        System.out.println("\n=== Student Details ===");
        System.out.println("Student ID: " + studentId);
        displayContactInfo(); // Call inherited method from Person
        System.out.println("======================");
    }


    public void study() {
        System.out.println(name + " is studying...");
    }
}