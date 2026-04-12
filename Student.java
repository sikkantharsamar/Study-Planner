
public class Student extends Person {

    private String studentId;


    public Student(String studentId, String name, String email) {
        super(name, email); 
        this.studentId = studentId;
    }


    public String getStudentId() {
        return studentId;
    }


    @Override
    public void displayDetails() {
        System.out.println("\n=== Student's Details ===");
        System.out.println("Student ID: " + studentId);
        displayContactInfo(); 
        System.out.println("=================");
    }


    public void study() {
        System.out.println(name + " is studying...");
    }
}
