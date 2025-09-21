import java.util.*;

public class StudentAttendanceTracker {
    
    public static Map<String, Integer> calculateAttendance(List<List<String>> dailyAttendance) {
        Map<String, Integer> attendanceMap = new HashMap<>();
        
        for (List<String> day : dailyAttendance) {
            for (String student : day) {
                attendanceMap.put(student, attendanceMap.getOrDefault(student, 0) + 1);
            }
        }
        
        return attendanceMap;
    }
    
    public static void main(String[] args) {
        List<List<String>> weeklyAttendance = new ArrayList<>();
        
        List<String> day1 = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> day2 = Arrays.asList("Alice", "Charlie", "David");
        List<String> day3 = Arrays.asList("Bob", "Charlie", "Eve");
        
        weeklyAttendance.add(day1);
        weeklyAttendance.add(day2);
        weeklyAttendance.add(day3);
        
        Map<String, Integer> result = calculateAttendance(weeklyAttendance);
        System.out.println("Attendance count: " + result);
    }
}