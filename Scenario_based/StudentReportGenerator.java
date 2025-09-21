import java.util.*;

class InvalidMarkException extends Exception {
    public InvalidMarkException(String message) {
        super(message);
    }
}

class Student {
    private String name;
    private int[] marks;
    private String[] subjects;
    
    public Student(String name, String[] subjects, int[] marks) throws InvalidMarkException {
        this.name = name;
        this.subjects = subjects;
        
        // Validate marks
        for (int mark : marks) {
            if (mark < 0 || mark > 100) {
                throw new InvalidMarkException("Marks must be between 0 and 100");
            }
        }
        
        this.marks = marks;
    }
    
    public double calculateAverage() {
        int sum = 0;
        for (int mark : marks) {
            sum += mark;
        }
        return (double) sum / marks.length;
    }
    
    public String assignGrade() {
        double average = calculateAverage();
        
        if (average >= 90) return "A";
        if (average >= 80) return "B";
        if (average >= 70) return "C";
        if (average >= 60) return "D";
        return "F";
    }
    
    public void displayReport() {
        System.out.println("Student Name: " + name);
        System.out.println("Subjects and Marks:");
        
        for (int i = 0; i < subjects.length; i++) {
            System.out.println("  " + subjects[i] + ": " + marks[i]);
        }
        
        System.out.println("Average: " + String.format("%.2f", calculateAverage()));
        System.out.println("Grade: " + assignGrade());
        System.out.println("----------------------------");
    }
}

public class StudentReportGenerator {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        
        String[] subjects = {"Math", "Science", "English", "History"};
        
        try {
            int[] marks1 = {85, 92, 78, 88};
            Student student1 = new Student("Alice", subjects, marks1);
            students.add(student1);
            
            int[] marks2 = {95, 89, 92, 94};
            Student student2 = new Student("Bob", subjects, marks2);
            students.add(student2);
            
            // This will throw an exception
            int[] invalidMarks = {105, 90, 85, 80};
            Student invalidStudent = new Student("Charlie", subjects, invalidMarks);
            students.add(invalidStudent);
            
        } catch (InvalidMarkException e) {
            System.err.println("Error: " + e.getMessage());
        }
        
        // Display reports for all valid students
        for (Student student : students) {
            student.displayReport();
        }
    }
}