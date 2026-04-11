import java.util.ArrayList;
import java.util.List;


public class Schedule {

    private String scheduleId;
    private String date;
    private List<Task> tasks;


    public Schedule(String scheduleId, String date) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.tasks = new ArrayList<>();
    }


    public String getScheduleId() {
        return scheduleId;
    }

    public String getDate() {
        return date;
    }

    public List<Task> getTasks() {
        return tasks;
    }


    public void addTask(Task task) {
        tasks.add(task);
        System.out.println("Task '" + task.getTaskName() + "' added to schedule for " + date);
    }


    public void removeTask(String taskId) {
        tasks.removeIf(task -> task.getTaskId().equals(taskId));
        System.out.println("Task removed from schedule.");
    }


    public void viewSchedule() {
        System.out.println("\n========== Schedule for " + date + " ==========");
        System.out.println("Schedule ID: " + scheduleId);

        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for this date.");
        } else {
            System.out.println("Total Tasks: " + tasks.size());
            System.out.println("\nTasks:");
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println("\n" + (i + 1) + ".");
                Task task = tasks.get(i);
                System.out.println("  Task: " + task.getTaskName());
                System.out.println("  Priority: " + task.getPriority());
                System.out.println("  Deadline: " + task.getDeadline());
                System.out.println("  Status: " + (task.isCompleted() ? "COMPLETED" : "PENDING"));
            }
        }
        System.out.println("===========================================");
    }
}