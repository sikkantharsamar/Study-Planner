import java.util.logging.Logger;

public class Student extends Person {
        private static final Logger logger = Logger.getLogger(Student.class.getName());


    private String studentId;
    private int studyXp;


    public Student(String studentId, String name, String email) {
        super(name, email); 
        this.studentId = studentId;
        this.studyXp = 0;
    }


    public String getStudentId() {
        return studentId;
    }
    public int getStudyXp() {
        return studyXp;
    }
    public int getLevel() {
        return (studyXp / 100) + 1;
    }

    public void addXp(int points) {
        this.studyXp += points;
        logger.info("🌟 Awesome! You earned " + points + " XP! (Current Level: " + getLevel() + ")");
    }



    @Override
    public void displayDetails() {
        logger.info("\n=== Student Details ===");
        logger.info("Student ID: " + studentId);
        displayContactInfo();
        logger.info("Academic Level: " + getLevel());
        logger.info("Total XP: " + studyXp);
        logger.info("======================");
    }


    public void study() {
        logger.info(name + " is studying...");
    }
}