
public class StudySession {

    private String sessionId;
    private Subject subject;
    private double durationInHours;


    public StudySession(String sessionId, Subject subject, double durationInHours) {
        this.sessionId = sessionId;
        this.subject = subject;
        this.durationInHours = durationInHours;
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


    public void setDurationInHours(double durationInHours) {
        this.durationInHours = durationInHours;
    }


    public void displaySession() {
        System.out.println("\n--- Study Session ---");
        System.out.println("Session ID: " + sessionId);
        System.out.println("Subject: " + subject.getSubjectName() + " (" + subject.getSubjectCode() + ")");
        System.out.println("Duration: " + durationInHours + " hours");
        System.out.println("--------------------");
    }
}