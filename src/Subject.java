import java.util.logging.Logger;
public class Subject {

    private String subjectCode;
    private String subjectName;
    private int creditHours;



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

Logger logger = Logger.getLogger(getClass().getName());

    public void displaySubject() {
        logger.info("\n--- Subject Info ---");
        logger.info("Code: " + subjectCode);
        logger.info("Name: " + subjectName);
        logger.info("Credit Hours: " + creditHours);
        logger.info("-------------------");
    }
}