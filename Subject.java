
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


    public void displaySubject() {
        System.out.println("\n--- Subject Info ---");
        System.out.println("Code: " + subjectCode);
        System.out.println("Name: " + subjectName);
        System.out.println("Credit Hours: " + creditHours);
        System.out.println("-------------------");
    }
}