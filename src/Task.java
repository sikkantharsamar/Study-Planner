import java.util.logging.Logger;


public class Task {

    private static final Logger logger = Logger.getLogger(Task.class.getName());

    private String taskId;
    private String taskName;
    private String deadline;
    private String priority; 
     private String category;
    private double estimatedHours;
    private boolean isCompleted;


    public Task(String taskId, String taskName, String deadline, String priority, String category, double estimatedHours) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.priority = priority;
        this.category = category;
        this.estimatedHours = estimatedHours;
        this.isCompleted = false; 
    }


    public String getTaskId() {
        return taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getDeadline() {
        return deadline;
    }

    public String getPriority() {
        return priority;
    }
    public String getCategory() {
        return category;
    }
    public double getEstimatedHours() {
        return estimatedHours;
    }



    public boolean isCompleted() {
        return isCompleted;
    }


    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }


    public void markCompleted() {
        this.isCompleted = true;
        System.out.println("Task '" + taskName + "' marked as completed!");
    }


    public void displayTask() {
        System.out.println("\n--- Task Details ---");
        System.out.println("Task ID: " + taskId);
        System.out.println("Name: " + taskName);
        System.out.println("Deadline: " + deadline);
        System.out.println("Priority: " + priority);
        System.out.println("Status: " + (isCompleted ? "COMPLETED" : "PENDING"));
        System.out.println("-------------------");
    }
}