import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class SmartStudyPlannerApp {

    private static Scanner scanner = new Scanner(System.in);
    private static AuthenticationService authService = new AuthenticationService();
    private static List<Task> allTasks = new ArrayList<>();
    private static List<Subject> allSubjects = new ArrayList<>();
    private static List<StudySession> allSessions = new ArrayList<>();
    private static List<Schedule> allSchedules = new ArrayList<>();
    private static ReportGenerator reportGenerator = new ReportGenerator();
    private static int taskCounter = 1;
    private static int scheduleCounter = 1;
    private static int sessionCounter = 1;

    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════════════╗");
        System.out.println("║   WELCOME TO SMARTSTUDY PLANNER SYSTEM        ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");


        if (!handleRegistrationAndLogin()) {
            System.out.println("Exiting application...");
            return;
        }

        boolean running = true;
        while (running) {
            running = showMainMenu();
        }

        System.out.println("\n╔═══════════════════════════════════════════════╗");
        System.out.println("║   THANK YOU FOR USING SMARTSTUDY PLANNER!     ║");
        System.out.println("║         Stay organized, Stay successful!      ║");
        System.out.println("╚═══════════════════════════════════════════════╝\n");

        scanner.close();
    }


    private static boolean handleRegistrationAndLogin() {
        while (true) {
            System.out.println("========== WELCOME ==========");
            System.out.println("1. Register New Account");
            System.out.println("2. Login");
            System.out.print("Choose option (1-2): ");

            int choice = getIntInput();

            if (choice == 1) {
                handleRegistration();
                return handleLogin();
            } else if (choice == 2) {
                return handleLogin();
            } else {
                System.out.println("\n*** Invalid option! Please enter 1 or 2. ***\n");
            }
        }
    }


    private static void handleRegistration() {
        System.out.println("\n========== REGISTRATION ==========");

        // Create Student
        System.out.print("Enter Student ID: ");
        String studentId = scanner.nextLine();

        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Student student = new Student(studentId, name, email);


        System.out.print("Create Username: ");
        String username = scanner.nextLine();

        System.out.print("Create Password: ");
        String password = scanner.nextLine();

        UserAccount account = new UserAccount(username, password, student);
        authService.registerAccount(account);

        System.out.println("\n*** Registration Successful! ***");
        System.out.println("You can now login with your credentials.\n");
    }


    private static boolean handleLogin() {
        System.out.println("========== LOGIN ==========");

        for (int attempts = 0; attempts < 3; attempts++) {
            System.out.print("Enter username: ");
            String username = scanner.nextLine();

            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (authService.login(username, password)) {
                return true;
            }

            if (attempts < 2) {
                System.out.println("Try again. (" + (2 - attempts) + " attempts remaining)\n");
            }
        }

        System.out.println("Too many failed attempts.");
        return false;
    }


    private static boolean showMainMenu() {
        System.out.println("\n========== MAIN MENU ==========");
        System.out.println("  1. Manage Subjects");
        System.out.println("  2. Manage Tasks");
        System.out.println("  3. Manage Schedules");
        System.out.println("  4. Record Study Session");
        System.out.println("  5. View Progress");
        System.out.println("  6. Generate Reports");
        System.out.println("  7. View Student Profile");
        System.out.println("  8. Logout");
        System.out.println("===============================");
        System.out.print("Enter your choice (1-8): ");

        int choice = getIntInput();

        switch (choice) {
            case 1: manageSubjects(); break;
            case 2: manageTasks(); break;
            case 3: manageSchedules(); break;
            case 4: recordStudySession(); break;
            case 5: viewProgress(); break;
            case 6: generateReports(); break;
            case 7: viewProfile(); break;
            case 8:
                authService.logout();
                return false;
            default:
                System.out.println("Invalid choice. Please try again.");
        }

        return true;
    }


    private static void manageSubjects() {
        System.out.println("\n--- Subject Management ---");
        System.out.println("1. Add Subject");
        System.out.println("2. View All Subjects");
        System.out.print("Choice: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter subject code: ");
            String code = scanner.nextLine();

            System.out.print("Enter subject name: ");
            String name = scanner.nextLine();

            System.out.print("Enter credit hours: ");
            int credits = getIntInput();

            Subject subject = new Subject(code, name, credits);
            allSubjects.add(subject);
            subject.displaySubject();


            reportGenerator.showNotification("Subject '" + name + "' added successfully! Start planning your study sessions.");

        } else if (choice == 2) {
            if (allSubjects.isEmpty()) {
                System.out.println("No subjects available.");
            } else {
                System.out.println("\n=== All Subjects ===");
                for (int i = 0; i < allSubjects.size(); i++) {
                    System.out.println("\n" + (i + 1) + ".");
                    allSubjects.get(i).displaySubject();
                }
            }
        }
    }


    private static void manageTasks() {
        System.out.println("\n--- Task Management ---");
        System.out.println("1. Add Task");
        System.out.println("2. View All Tasks");
        System.out.println("3. Mark Task as Completed");
        System.out.print("Choice: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter task name: ");
            String name = scanner.nextLine();

            System.out.print("Enter deadline (e.g., 2025-12-25): ");
            String deadline = scanner.nextLine();

            System.out.print("Enter priority (HIGH/MEDIUM/LOW): ");
            String priority = scanner.nextLine();


            String id = "T" + String.format("%03d", taskCounter++);

            Task task = new Task(id, name, deadline, priority);
            allTasks.add(task);
            task.displayTask();


            if (priority.equalsIgnoreCase("HIGH")) {
                reportGenerator.showNotification("HIGH PRIORITY task added! Don't forget: '" + name + "' due on " + deadline);
            } else {
                reportGenerator.showNotification("Task '" + name + "' added successfully! Deadline: " + deadline);
            }

        } else if (choice == 2) {
            if (allTasks.isEmpty()) {
                System.out.println("No tasks available.");
            } else {
                System.out.println("\n=== All Tasks ===");
                for (int i = 0; i < allTasks.size(); i++) {
                    System.out.println("\n" + (i + 1) + ".");
                    allTasks.get(i).displayTask();
                }
            }

        } else if (choice == 3) {
            if (allTasks.isEmpty()) {
                System.out.println("No tasks available.");
                return;
            }

            System.out.println("\n=== Select Task to Complete ===");
            for (int i = 0; i < allTasks.size(); i++) {
                System.out.println((i + 1) + ". " + allTasks.get(i).getTaskName() +
                        " [" + (allTasks.get(i).isCompleted() ? "COMPLETED" : "PENDING") + "]");
            }

            System.out.print("Enter task number: ");
            int taskNum = getIntInput() - 1;

            if (taskNum >= 0 && taskNum < allTasks.size()) {
                Task completedTask = allTasks.get(taskNum);
                completedTask.markCompleted();


                int pendingCount = 0;
                for (Task t : allTasks) {
                    if (!t.isCompleted()) pendingCount++;
                }


                if (pendingCount > 0) {
                    reportGenerator.showNotification("Excellent work! You completed: '" + completedTask.getTaskName() + "'. You still have " + pendingCount + " pending task(s). Keep going!");
                } else {
                    reportGenerator.showNotification("Amazing! You completed: '" + completedTask.getTaskName() + "'. All tasks are now done! You're a productivity champion!");
                }
            } else {
                System.out.println("Invalid task number.");
            }
        }
    }


    private static void manageSchedules() {
        System.out.println("\n--- Schedule Management ---");
        System.out.println("1. Create New Schedule");
        System.out.println("2. Add Task to Schedule");
        System.out.println("3. View All Schedules");
        System.out.print("Choice: ");

        int choice = getIntInput();

        if (choice == 1) {
            System.out.print("Enter date/period (e.g., Week 1, Dec 15-20): ");
            String date = scanner.nextLine();


            String id = "SCH" + String.format("%03d", scheduleCounter++);

            Schedule schedule = new Schedule(id, date);
            allSchedules.add(schedule);


            reportGenerator.showNotification("Schedule created for '" + date + "'. Start adding tasks to organize your study time!");

        } else if (choice == 2) {
            if (allSchedules.isEmpty()) {
                System.out.println("No schedules available. Create one first.");
                return;
            }

            if (allTasks.isEmpty()) {
                System.out.println("No tasks available. Create tasks first.");
                return;
            }

            System.out.println("\n=== Select Schedule ===");
            for (int i = 0; i < allSchedules.size(); i++) {
                System.out.println((i + 1) + ". " + allSchedules.get(i).getDate());
            }
            System.out.print("Enter schedule number: ");
            int schedNum = getIntInput() - 1;

            if (schedNum >= 0 && schedNum < allSchedules.size()) {
                System.out.println("\n=== Select Task ===");
                for (int i = 0; i < allTasks.size(); i++) {
                    System.out.println((i + 1) + ". " + allTasks.get(i).getTaskName());
                }
                System.out.print("Enter task number: ");
                int taskNum = getIntInput() - 1;

                if (taskNum >= 0 && taskNum < allTasks.size()) {
                    allSchedules.get(schedNum).addTask(allTasks.get(taskNum));
                }
            }

        } else if (choice == 3) {
            if (allSchedules.isEmpty()) {
                System.out.println("No schedules available.");
            } else {
                for (Schedule schedule : allSchedules) {
                    schedule.viewSchedule();
                }
            }
        }
    }


    private static void recordStudySession() {
        if (allSubjects.isEmpty()) {
            System.out.println("No subjects available. Add subjects first.");
            return;
        }

        System.out.println("\n--- Record Study Session ---");


        String id = "SS" + String.format("%03d", sessionCounter++);

        System.out.println("\n=== Select Subject ===");
        for (int i = 0; i < allSubjects.size(); i++) {
            System.out.println((i + 1) + ". " + allSubjects.get(i).getSubjectName());
        }
        System.out.print("Enter subject number: ");
        int subNum = getIntInput() - 1;

        if (subNum >= 0 && subNum < allSubjects.size()) {
            System.out.print("Enter duration in hours: ");
            double duration = getDoubleInput();

            StudySession session = new StudySession(id, allSubjects.get(subNum), duration);
            allSessions.add(session);
            session.displaySession();


            double totalHours = 0;
            for (StudySession s : allSessions) {
                totalHours += s.getDurationInHours();
            }


            reportGenerator.showNotification("Study session recorded! Total study time: " + String.format("%.1f", totalHours) + " hours. Great dedication!");
        }
    }


    private static void viewProgress() {
        if (allTasks.isEmpty()) {
            System.out.println("No tasks available to track progress.");
            return;
        }

        reportGenerator.setTasks(allTasks);
        reportGenerator.displayProgress();
    }


    private static void generateReports() {
        System.out.println("\n--- Generate Reports ---");
        System.out.println("1. Task Report");
        System.out.println("2. Study Session Report");
        System.out.println("3. Both Reports");
        System.out.print("Choice: ");

        int choice = getIntInput();

        reportGenerator.setTasks(allTasks);
        reportGenerator.setSessions(allSessions);

        if (choice == 1) {
            if (allTasks.isEmpty()) {
                System.out.println("No tasks available for report.");
            } else {
                reportGenerator.generateTaskReport();
            }
        } else if (choice == 2) {
            if (allSessions.isEmpty()) {
                System.out.println("No study sessions available for report.");
            } else {
                reportGenerator.generateStudyReport();
            }
        } else if (choice == 3) {
            if (allTasks.isEmpty() && allSessions.isEmpty()) {
                System.out.println("No data available for reports.");
            } else {
                reportGenerator.generateReport();
            }
        }
    }


    private static void viewProfile() {
        if (authService.getCurrentUser() != null) {
            authService.getCurrentUser().getStudent().displayDetails();
        }
    }


    private static int getIntInput() {
        while (true) {
            try {
                int value = Integer.parseInt(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }


    private static double getDoubleInput() {
        while (true) {
            try {
                double value = Double.parseDouble(scanner.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}