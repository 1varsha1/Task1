import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Task {
    private String title;
    private LocalDate dueDate;
    private boolean isCompleted;

    public Task(String title, LocalDate dueDate) {
        this.title = title;
        this.dueDate = dueDate;
        this.isCompleted = false;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}

public class TaskReminderApp {
    private static List<Task> tasks = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n1. Create a new task");
            System.out.println("2. View tasks");
            System.out.println("3. Mark a task as completed");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createTask(scanner);
                    break;
                case 2:
                    viewTasks();
                    break;
                case 3:
                    markTaskAsCompleted(scanner);
                    break;
                case 4:
                    System.out.println("Thank you for using the Task Reminder App. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createTask(Scanner scanner) {
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the task title: ");
        String title = scanner.nextLine();
        System.out.print("Enter the due date (yyyy-MM-dd): ");
        String dueDateString = scanner.next();

        try {
            LocalDate dueDate = LocalDate.parse(dueDateString);
            Task task = new Task(title, dueDate);
            tasks.add(task);
            System.out.println("Task created successfully!");
        } catch (Exception e) {
            System.out.println("Invalid date format. Task creation failed.");
        }
    }

    private static void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks found.");
        } else {
            System.out.println("\nTasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". " + task.getTitle() + " - Due: " + task.getDueDate());
            }
        }
    }

    private static void markTaskAsCompleted(Scanner scanner) {
        viewTasks();
        System.out.print("Enter the task number you want to mark as completed: ");
        int taskNumber = scanner.nextInt();

        if (taskNumber >= 1 && taskNumber <= tasks.size()) {
            Task task = tasks.get(taskNumber - 1);
            task.setCompleted(true);
            System.out.println("Task marked as completed: " + task.getTitle());
        } else {
            System.out.println("Invalid task number. Please try again.");
        }
    }
}
