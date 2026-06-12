 import java.util.List;
import java.util.logging.Logger;

public class GoalTracker {

    private static final Logger logger = Logger.getLogger(GoalTracker.class.getName());

    private double weeklyGoalHours;
    private String goalPeriod;

    public GoalTracker(double weeklyGoalHours, String goalPeriod) {
        this.weeklyGoalHours = weeklyGoalHours;
        this.goalPeriod = goalPeriod;
    }

    public double getWeeklyGoalHours() { return weeklyGoalHours; }
    public String getGoalPeriod() { return goalPeriod; }

    public void setWeeklyGoalHours(double weeklyGoalHours) {
        this.weeklyGoalHours = weeklyGoalHours;
        logger.info("Weekly goal updated to " + weeklyGoalHours + " hours.");
    }

    public void setGoalPeriod(String goalPeriod) {
        this.goalPeriod = goalPeriod;
    }

    public double calculateTotalStudyHours(List<StudySession> sessions) {
        double total = 0;
        for (StudySession session : sessions) {
            total += session.getDurationInHours();
        }
        return total;
    }

    public void displayGoalStatus(List<StudySession> sessions) {
        double totalHours = calculateTotalStudyHours(sessions);
        double remaining = weeklyGoalHours - totalHours;
        double percentage = (weeklyGoalHours == 0) ? 0 : (totalHours / weeklyGoalHours) * 100;

        logger.info("\n========== GOAL TRACKER ==========");
        logger.info("Period        : " + goalPeriod);
        logger.info(String.format("Goal          : %.1f hours", weeklyGoalHours));
        logger.info(String.format("Studied So Far: %.1f hours", totalHours));

        if (remaining > 0) {
            logger.info(String.format("Remaining     : %.1f hours to reach goal", remaining));
        } else {
            logger.info("Goal Status   : GOAL ACHIEVED!");
        }

        logger.info(String.format("Completion    : %.1f%%", Math.min(percentage, 100.0)));

       
        int filled = (int) Math.min(percentage / 5, 20);
        StringBuilder progressStr = new StringBuilder("Progress      : [");
        for (int i = 0; i < 20; i++) {
            progressStr.append(i < filled ? "=" : " ");
        }
        progressStr.append("]");
        logger.info(progressStr.toString());

        
        if (percentage >= 100) {
            logger.info("Message       : Excellent! You have met your study goal. Keep it up!");
        } else if (percentage >= 75) {
            logger.info("Message       : Almost there! Just a bit more to hit your goal.");
        } else if (percentage >= 50) {
            logger.info("Message       : Halfway through your goal. Stay consistent!");
        } else if (percentage > 0) {
            logger.info("Message       : Good start! Keep adding study sessions.");
        } else {
            logger.info("Message       : No sessions recorded yet. Start studying to reach your goal!");
        }

        logger.info("===================================");
    }
}