import java.util.logging.Logger;
public class StudySession {
    private static final Logger logger = Logger.getLogger(StudySession.class.getName());

    private String sessionId;
    private Subject subject;
    private double durationInHours;
     private String sessionNotes;
    private int productivityRating;


   public StudySession(String sessionId, Subject subject, double durationInHours, String sessionNotes, int productivityRating) {
        this.sessionId = sessionId;
        this.subject = subject;
        this.durationInHours = durationInHours;
        this.sessionNotes = sessionNotes;
        this.productivityRating = productivityRating;
    }


    public String getSessionId() {
        return sessionId;
    }

    public Subject getSubject() {
        return subject;
    }

    public double getDurationInHours() {
        return durationInHours;
    }

    public String getSessionNotes() {
        return sessionNotes;
    }

    public int getProductivityRating() {
        return productivityRating;
    }


    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }


    public void displaySession() {
        logger.info("\n--- Study Session ---");
        logger.info("Session ID: " + sessionId);
        logger.info("Subject: " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        logger.info("Duration: " + durationInHours + " hours | Productivity Rating: " + productivityRating + "/5");
        logger.info("Notes: " + sessionNotes);
        logger.info("--------------------");
    }
}