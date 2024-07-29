import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApp {
    private static ScheduleManager manager = ScheduleManager.getInstance();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to the Astronaut Daily Schedule Organizer!");

        
        manager.addObserver(new Observer() {
            @Override
            public void update(String message) {
                System.out.println("Notification: " + message);
            }
        });

        Map<String, Runnable> commandMap = new HashMap<>();
        commandMap.put("add", ConsoleApp::addTask);
        commandMap.put("remove", ConsoleApp::removeTask);
        commandMap.put("view", ConsoleApp::viewTasks);
        commandMap.put("edit", ConsoleApp::editTask);
        commandMap.put("complete", ConsoleApp::completeTask);
        commandMap.put("priority", ConsoleApp::viewTasksByPriority);
        commandMap.put("exit", ConsoleApp::exitApp);

        processCommands(commandMap);
    }

    private static void processCommands(Map<String, Runnable> commandMap) {
        System.out.print("Enter command (add, remove, view, edit, complete, priority, exit): ");
        String command = scanner.nextLine().trim().toLowerCase();
        Runnable action = commandMap.get(command);

        if (action != null) {
            action.run();
            if (!command.equals("exit")) {
                processCommands(commandMap);
            }
        } else {
            System.out.println("Invalid command. Please try again.");
            processCommands(commandMap);
        }
    }

    private static void addTask() {
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter start time (HH:mm): ");
        String startTime = scanner.nextLine();
        System.out.print("Enter end time (HH:mm): ");
        String endTime = scanner.nextLine();
        System.out.print("Enter priority (High, Medium, Low): ");
        String priority = scanner.nextLine();
        Task task = TaskFactory.createTask(description, startTime, endTime, priority);
        manager.addTask(task);
    }

    private static void removeTask() {
        System.out.print("Enter description of the task to remove: ");
        String description = scanner.nextLine();
        manager.removeTask(description);
    }

    private static void viewTasks() {
        manager.viewTasks();
    }

    private static void editTask() {
        System.out.print("Enter description of the task to edit: ");
        String description = scanner.nextLine();
        System.out.print("Enter new description (leave empty to keep unchanged): ");
        String newDescription = scanner.nextLine();
        System.out.print("Enter new start time (leave empty to keep unchanged): ");
        String newStartTime = scanner.nextLine();
        System.out.print("Enter new end time (leave empty to keep unchanged): ");
        String newEndTime = scanner.nextLine();
        System.out.print("Enter new priority (leave empty to keep unchanged): ");
        String newPriority = scanner.nextLine();
        manager.editTask(description, newDescription, newStartTime, newEndTime, newPriority);
    }

    private static void completeTask() {
        System.out.print("Enter description of the task to mark as completed: ");
        String description = scanner.nextLine();
        manager.markTaskAsCompleted(description);
    }

    private static void viewTasksByPriority() {
        System.out.print("Enter priority level to view (High, Medium, Low): ");
        String priority = scanner.nextLine();
        manager.viewTasksByPriority(priority);
    }

    private static void exitApp() {
        System.out.println("Exiting the application.");
        scanner.close();
    }
}
