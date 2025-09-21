import java.util.*;

public class TodoList {
    private List<String> tasks;
    
    public TodoList() {
        tasks = new ArrayList<>();
    }
    
    public void addTask(String task) {
        if (task != null && !task.trim().isEmpty()) {
            tasks.add(task.trim());
            System.out.println("Task added: " + task);
        } else {
            System.out.println("Cannot add empty task");
        }
    }
    
    public void removeTask(String task) {
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty");
            return;
        }
        
        boolean removed = tasks.removeIf(t -> t.equalsIgnoreCase(task.trim()));
        if (removed) {
            System.out.println("Task removed: " + task);
        } else {
            System.out.println("Task not found: " + task);
        }
    }
    
    public void displayTasks() {
        if (tasks.isEmpty()) {
            System.out.println("No tasks in the list");
            return;
        }
        
        List<String> sortedTasks = new ArrayList<>(tasks);
        Collections.sort(sortedTasks);
        
        System.out.println("Tasks (sorted alphabetically):");
        for (int i = 0; i < sortedTasks.size(); i++) {
            System.out.println((i + 1) + ". " + sortedTasks.get(i));
        }
    }
    
    public static void main(String[] args) {
        TodoList todo = new TodoList();
        
        todo.addTask("Study for exam");
        todo.addTask("Buy groceries");
        todo.addTask("Complete assignment");
        
        todo.displayTasks();
        
        todo.removeTask("Buy groceries");
        todo.removeTask("Non-existent task");
        
        todo.displayTasks();
    }
}