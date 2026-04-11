import java.util.List;
import java.util.ArrayList;


public class ReportGenerator implements Reportable {
    private List<Task> tasks;
    private List<StudySession> sessions;
    private int notificationCounter = 1;


    public ReportGenerator() {
        this.tasks = new ArrayList<>();
        this.sessions = new ArrayList<>();
    }


    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }


    public void setSessions(List<StudySession> sessions) {
        this.sessions = sessions;
    }


    @Override
    public void generateReport() {
        generateTaskReport();
        generateStudyReport();
    }


    @Override
    public String getReportType() {
        return "Complete Study Report";
    }


    public void generateTaskReport() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║        TASK SUMMARY REPORT             ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (tasks.isEmpty()) {
            System.out.println("No tasks available.");
            return;
        }

        int completed = 0, pending = 0;
        int highPriority = 0, mediumPriority = 0, lowPriority = 0;


        for (Task task : tasks) {
            if (task.isCompleted()) {
                completed++;
            } else {
                pending++;
            }

            String priority = task.getPriority().toUpperCase();
            if (priority.equals("HIGH")) {
                highPriority++;
            } else if (priority.equals("MEDIUM")) {
                mediumPriority++;
            } else if (priority.equals("LOW")) {
                lowPriority++;
            }
        }


        System.out.println("\nTask Statistics:");
        System.out.println("  Total Tasks: " + tasks.size());
        System.out.println("  Completed: " + completed);
        System.out.println("  Pending: " + pending);
        System.out.println("\nPriority Distribution:");
        System.out.println("  High Priority: " + highPriority);
        System.out.println("  Medium Priority: " + mediumPriority);
        System.out.println("  Low Priority: " + lowPriority);


        System.out.println("\n--- Detailed Task List ---");
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            System.out.println("\n" + (i + 1) + ". " + task.getTaskName());
            System.out.println("   Priority: " + task.getPriority() + " | Deadline: " + task.getDeadline());
            System.out.println("   Status: " + (task.isCompleted() ? "✓ COMPLETED" : "○ PENDING"));
        }
        System.out.println("\n=========================================\n");
    }


    public void generateStudyReport() {
        System.out.println("\n");
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║       STUDY SESSION REPORT             ║");
        System.out.println("╚════════════════════════════════════════╝");

        if (sessions.isEmpty()) {
            System.out.println("No study sessions recorded.");
            return;
        }

        double totalHours = 0;

        System.out.println("\nStudy Sessions:");
        for (int i = 0; i < sessions.size(); i++) {
            StudySession session = sessions.get(i);
            totalHours += session.getDurationInHours();

            System.out.println("\n" + (i + 1) + ". Subject: " + session.getSubject().getSubjectName());
            System.out.println("   Subject Code: " + session.getSubject().getSubjectCode());
            System.out.println("   Duration: " + session.getDurationInHours() + " hours");
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Total Study Sessions: " + sessions.size());
        System.out.printf("Total Study Time: %.2f hours\n", totalHours);
        System.out.printf("Average Session Duration: %.2f hours\n", totalHours / sessions.size());
        System.out.println("\n=========================================\n");
    }


    public void displayProgress() {
        System.out.println("\n========== Progress Report ==========");

        if (tasks.isEmpty()) {
            System.out.println("No tasks available to track progress.");
            return;
        }

        int completedTasks = 0;
        int pendingTasks = 0;

        for (Task task : tasks) {
            if (task.isCompleted()) {
                completedTasks++;
            } else {
                pendingTasks++;
            }
        }

        int totalTasks = completedTasks + pendingTasks;
        double progress = (totalTasks == 0) ? 0.0 : (completedTasks * 100.0) / totalTasks;

        System.out.println("Completed Tasks: " + completedTasks);
        System.out.println("Pending Tasks: " + pendingTasks);
        System.out.println("Total Tasks: " + totalTasks);
        System.out.printf("Progress: %.2f%%\n", progress);


        int progressPercentage = (int) progress;
        System.out.print("Progress Bar: [");
        for (int i = 0; i < 20; i++) {
            if (i < progressPercentage / 5) {
                System.out.print("=");
            } else {
                System.out.print(" ");
            }
        }
        System.out.println("]");
        System.out.println("====================================");


        displayProgressNotification(progress, completedTasks, pendingTasks);
    }


    private void displayProgressNotification(double progress, int completed, int pending) {

        int highPriorityPending = 0;
        for (Task t : tasks) {
            if (!t.isCompleted() && t.getPriority().equalsIgnoreCase("HIGH")) {
                highPriorityPending++;
            }
        }

        String progressMessage = "";

        if (progress == 100) {
            progressMessage = "Perfect! 100% completion rate! All tasks done!";
        } else if (progress >= 75) {
            progressMessage = "Outstanding progress! You're at " + String.format("%.0f", progress) + "%. Almost there!";
        } else if (progress >= 50) {
            progressMessage = "Good work! You're halfway there at " + String.format("%.0f", progress) + "%. Keep going!";
        } else if (progress >= 25) {
            progressMessage = "You've made progress at " + String.format("%.0f", progress) + "%. Stay consistent!";
        } else if (progress > 0) {
            progressMessage = "You've started! Current progress: " + String.format("%.0f", progress) + "%. Every step counts!";
        } else {
            progressMessage = "Time to get started! You have " + tasks.size() + " task(s) waiting.";
        }


        if (highPriorityPending > 0) {
            progressMessage += " ALERT: You have " + highPriorityPending + " HIGH priority task(s) pending!";
        }

        showNotification(progressMessage);
    }


    public void showNotification(String message) {
        String notificationId = "AUTO_N" + notificationCounter++;
        System.out.println("\n****************************************");
        System.out.println("        NOTIFICATION");
        System.out.println("****************************************");
        System.out.println("ID: " + notificationId);
        System.out.println("Message: " + message);
        System.out.println("****************************************\n");
    }
}