import java.util.Scanner;

public class SentenceFormatter {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a paragraph:");
        String text = sc.nextLine();
        text = text.trim().replaceAll("\\s+", " ");
        text = text.replaceAll("([.!?])\\s*", "$1 ");
        String result = "";
        boolean capital  = true; 

        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);

            if (capital && Character.isLetter(ch)) {
                result += Character.toUpperCase(ch);
                capital = false;
            } else {
                result += ch;
            }
            if (ch == '.' || ch == '?' || ch == '!') {
                capital = true;
            }
        }

        System.out.println("Corrected paragraph:");
        System.out.println(result.trim());

        sc.close();
    }
}