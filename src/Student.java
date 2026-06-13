import java.util.logging.Logger;

public class Student extends Person {
        private static final Logger logger = Logger.getLogger(Student.class.getName());


    private String studentId;
    private int studyXp;


    public Student(String studentId, String name, String email) {
        super(name, email); 
        this.studentId = studentId;
    }


    public String getStudentId() {
        return studentId;
    }


    @Override
    public void displayDetails() {
        System.out.println("\n=== Student Details ===");
        System.out.println("Student ID: " + studentId);
        displayContactInfo(); 
        System.out.println("======================");
    }


    public void study() {
        System.out.println(name + " is studying...");
    }
}