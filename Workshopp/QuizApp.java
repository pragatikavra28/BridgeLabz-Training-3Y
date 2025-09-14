import java.util.*;

class QuizPlatform {
    private String[] correctAnswers;

    // Constructor to initialize correct answers
    public QuizPlatform(String[] correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    // Compare answers and calculate score
    public int calculateScore(String[] userAnswers) {
        int score = 0;
        for (int i = 0; i < correctAnswers.length; i++) {
            if (i < userAnswers.length && correctAnswers[i].equalsIgnoreCase(userAnswers[i])) {
                score++;
            }
        }
        return score;
    }

    // Return grade based on score
    public String getGrade(int score) {
        double percentage = (score * 100.0) / correctAnswers.length;
        if (percentage >= 90) return "A";
        else if (percentage >= 75) return "B";
        else if (percentage >= 50) return "C";
        else return "D";
    }
}

public class QuizApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Example correct answers
        String[] correct = {"A", "B", "C", "D", "A"};

        // Create QuizPlatform object
        QuizPlatform quiz = new QuizPlatform(correct);

        // List to store multiple users' scores
        List<Integer> scores = new ArrayList<>();

        System.out.print("Enter number of users: ");
        int users = sc.nextInt();
        sc.nextLine(); // consume newline

        for (int u = 1; u <= users; u++) {
            System.out.println("\n--- User " + u + " ---");
            String[] userAnswers = new String[correct.length];

            for (int i = 0; i < correct.length; i++) {
                System.out.print("Enter answer for Q" + (i + 1) + ": ");
                userAnswers[i] = sc.nextLine().trim();
            }

            int score = quiz.calculateScore(userAnswers);
            scores.add(score);

            System.out.println("User " + u + " Score: " + score + "/" + correct.length);
            System.out.println("Grade: " + quiz.getGrade(score));
        }

        System.out.println("\nAll Users' Scores: " + scores);
    }
}
