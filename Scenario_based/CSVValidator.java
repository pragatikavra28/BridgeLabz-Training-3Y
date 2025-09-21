import java.io.*;

class InvalidFileFormatException extends Exception {
    public InvalidFileFormatException(String message) {
        super(message);
    }
}

public class CSVValidator {
    
    public static void validateCSV(String filePath, String[] requiredHeaders) 
            throws InvalidFileFormatException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String firstLine = reader.readLine();
            if (firstLine == null) {
                throw new InvalidFileFormatException("File is empty");
            }
            
            String[] headers = firstLine.split(",");
            Set<String> headerSet = new HashSet<>(Arrays.asList(headers));
            
            for (String required : requiredHeaders) {
                if (!headerSet.contains(required)) {
                    throw new InvalidFileFormatException("Missing required header: " + required);
                }
            }
            
            System.out.println("CSV file is valid");
            
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        String[] requiredHeaders = {"Name", "Age", "Email"};
        
        try {
            // Create a sample CSV file for testing
            String sampleContent = "Name,Age,Email\nJohn Doe,25,john@example.com\nJane Smith,30,jane@example.com";
            
            try (FileWriter writer = new FileWriter("sample.csv")) {
                writer.write(sampleContent);
            }
            
            validateCSV("sample.csv", requiredHeaders);
        } catch (InvalidFileFormatException | IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}