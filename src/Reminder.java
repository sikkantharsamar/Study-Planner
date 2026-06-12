import java.util.logging.Logger;

public class Reminder {

    private static final Logger logger = Logger.getLogger(Reminder.class.getName());

    private String reminderId;
    private String message;
    private String reminderDate;
    private Task linkedTask;
    private boolean isDismissed;

    public Reminder(String reminderId, String message, String reminderDate, Task linkedTask) {
        this.reminderId = reminderId;
        this.message = message;
        this.reminderDate = reminderDate;
        this.linkedTask = linkedTask;
        this.isDismissed = false;
    }

    public String getReminderId() { return reminderId; }
    public String getMessage() { return message; }
    public String getReminderDate() { return reminderDate; }
    public Task getLinkedTask() { return linkedTask; }
    public boolean isDismissed() { return isDismissed; }

    public void dismiss() {
        this.isDismissed = true;
        logger.info("Reminder '" + reminderId + "' dismissed.");
    }

    public void displayReminder() {
        logger.info("\n--- Reminder ---");
        logger.info("ID       : " + reminderId);
        logger.info("Message  : " + message);
        logger.info("Date     : " + reminderDate);
        if (linkedTask != null) {
            logger.info("Task     : " + linkedTask.getTaskName() + " (Due: " + linkedTask.getDeadline() + ")");
        }
        logger.info("Status   : " + (isDismissed ? "Dismissed" : "Active"));
        logger.info("----------------");
    }
}