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
        logger.info("Task '" + taskName + "' marked as completed!");
    }


    public void displayTask() {
        logger.info("\n--- Task Details ---");
        logger.info("Task ID: " + taskId + " | Name: " + taskName);
        logger.info("Category: " + category + " | Est. Time: " + estimatedHours + " hours");
        logger.info("Deadline: " + deadline + " | Priority: " + priority);
        logger.info("Status: " + (isCompleted ? "COMPLETED" : "PENDING"));
        logger.info("-------------------");
    }
}