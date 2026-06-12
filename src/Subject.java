import java.util.logging.Logger;
public class Subject {
        private static final Logger logger = Logger.getLogger(Subject.class.getName());


    private String subjectCode;
    private String subjectName;
    private int creditHours;
    private int difficultyRating;
    private String targetGrade;



    public Subject(String subjectCode, String subjectName, int creditHours) {
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
        this.creditHours = creditHours;
    }


    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public int getCreditHours() {
        return creditHours;
    }


    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }


    public void displaySubject() {
        logger.info("\n--- Subject Info ---");
        logger.info("Code: " + subjectCode);
        logger.info("Name: " + subjectName);
        logger.info("Credit Hours: " + creditHours);
        logger.info("-------------------");
    }
}