import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ScheduleManager implements Subject {
    private List<Task> tasks;
    private static ScheduleManager instance;
    private List<Observer> observers;

    private ScheduleManager() {
        tasks = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public static ScheduleManager getInstance() {
        if (instance == null) {
            instance = new ScheduleManager();
        }
        return instance;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    public boolean addTask(Task task) {
        if (!isValidTime(task.getStartTime()) || !isValidTime(task.getEndTime())) {
            System.err.println("Error: Invalid time format.");
            return false;
        }
        if (hasConflict(task)) {
            Task conflictingTask = getConflict(task);
            String conflictMessage = "Error: Task conflicts with existing task: " + conflictingTask.getDescription();
            
            notifyObservers(conflictMessage); 
            return false;
        }
        tasks.add(task);
        String successMessage = "Task added successfully: " + task.getDescription();
       
        notifyObservers(successMessage); 
        return true;
    }

    public boolean removeTask(String description) {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                tasks.remove(task);
                String successMessage = "Task removed successfully: " + task.getDescription();
                
                notifyObservers(successMessage); 
                return true;
            }
        }
        String errorMessage = "Error: Task not found: " + description;
        
        notifyObservers(errorMessage);  
        return false;
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks scheduled for the day.");
            return;
        }
        Collections.sort(tasks, Comparator.comparing(Task::getStartTime));
        for (Task task : tasks) {
            System.out.println(task);
        }
    }

    public void viewTasksByPriority(String priority) {
        boolean found = false;
        for (Task task : tasks) {
            if (task.getPriority().equals(priority)) {
                System.out.println(task);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No tasks found with priority level: " + priority);
        }
    }

    public boolean editTask(String description, String newDescription, String newStartTime, String newEndTime, String newPriority) {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                if (newStartTime != null && !newStartTime.isEmpty()) task.setStartTime(newStartTime);
                if (newEndTime != null && !newEndTime.isEmpty()) task.setEndTime(newEndTime);
                if (newDescription != null && !newDescription.isEmpty()) task.setDescription(newDescription);
                if (newPriority != null && !newPriority.isEmpty()) task.setPriority(newPriority);
                String successMessage = "Task edited successfully: " + description;
                
                notifyObservers(successMessage);  
                return true;
            }
        }
        String errorMessage = "Error: Task not found: " + description;
       
        notifyObservers(errorMessage);  
        return false;
    }

    public boolean markTaskAsCompleted(String description) {
        for (Task task : tasks) {
            if (task.getDescription().equals(description)) {
                task.setCompleted(true);
                String successMessage = "Task marked as completed: " + description;
               
                notifyObservers(successMessage);  
                return true;
            }
        }
        String errorMessage = "Error: Task not found: " + description;
        
        notifyObservers(errorMessage);  
        return false;
    }

    private boolean hasConflict(Task newTask) {
        return getConflict(newTask) != null;
    }
    private Task getConflict(Task newTask) {
        for (Task task : tasks) {
            if (timeOverlap(task,newTask)) {
                return task;
            }
        }
        return null;
    }
    private boolean timeOverlap(Task t1, Task t2) {
        return !(t1.getEndTime().compareTo(t2.getStartTime()) <= 0 || t1.getStartTime().compareTo(t2.getEndTime()) >= 0);
    }

    private boolean isValidTime(String time) {
        return time.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]");
    }
}
