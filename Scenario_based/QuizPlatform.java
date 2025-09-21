import java.util.*;

class InvalidQuizSubmissionException extends Exception {
    public InvalidQuizSubmissionException(String message) {
        super(message);
    }
}

public class QuizPlatform {
    
    public static int calculateScore(String[] correctAnswers, String[] studentAnswers) 
            throws InvalidQuizSubmissionException {
        if (correctAnswers.length != studentAnswers.length) {
            throw new InvalidQuizSubmissionException(
                "Number of answers doesn't match. Expected: " + 
                correctAnswers.length + ", Got: " + studentAnswers.length
            );
        }
        
        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (correctAnswers[i].equalsIgnoreCase(studentAnswers[i])) {
                score++;
            }
        }
        return score;
    }
    
    public static String assignGrade(int score, int totalQuestions) {
        double percentage = (double) score / totalQuestions * 100;
        
        if (percentage >= 90) return "A";
        if (percentage >= 80) return "B";
        if (percentage >= 70) return "C";
        if (percentage >= 60) return "D";
        return "F";
    }
    
    public static void main(String[] args) {
        String[] correctAnswers = {"A", "B", "C", "D", "A"};
        String[] studentAnswers = {"A", "B", "D", "D", "A"};
        
        List<Integer> scores = new ArrayList<>();
        
        try {
            int score = calculateScore(correctAnswers, studentAnswers);
            scores.add(score);
            
            String grade = assignGrade(score, correctAnswers.length);
            
            System.out.println("Score: " + score + "/" + correctAnswers.length);
            System.out.println("Grade: " + grade);
            System.out.println("Percentage: " + (score * 100 / correctAnswers.length) + "%");
            
        } catch (InvalidQuizSubmissionException e) {
            System.err.println("Error processing quiz: " + e.getMessage());
        }
    }
}