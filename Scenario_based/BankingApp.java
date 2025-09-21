import java.util.InputMismatchException;
import java.util.Scanner;

class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message) {
        super(message);
    }
}

public class BankingApp {
    private double balance;
    
    public BankingApp(double initialBalance) {
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance += amount;
        System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
    }
    
    public void withdraw(double amount) throws InsufficientFundsException, IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > balance) {
            throw new InsufficientFundsException(
                "Insufficient funds. Available: $" + balance + ", Requested: $" + amount
            );
        }
        balance -= amount;
        System.out.println("Withdrew: $" + amount + ". New balance: $" + balance);
    }
    
    public double getBalance() {
        return balance;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankingApp account = null;
        
        try {
            System.out.print("Enter initial balance: $");
            double initialBalance = scanner.nextDouble();
            
            if (initialBalance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }
            
            account = new BankingApp(initialBalance);
            System.out.println("Account created with balance: $" + account.getBalance());
            
            boolean running = true;
            while (running) {
                System.out.println("\n1. Deposit");
                System.out.println("2. Withdraw");
                System.out.println("3. Check Balance");
                System.out.println("4. Exit");
                System.out.print("Choose an option: ");
                
                int choice = scanner.nextInt();
                
                switch (choice) {
                    case 1:
                        System.out.print("Enter deposit amount: $");
                        double depositAmount = scanner.nextDouble();
                        account.deposit(depositAmount);
                        break;
                        
                    case 2:
                        System.out.print("Enter withdrawal amount: $");
                        double withdrawAmount = scanner.nextDouble();
                        account.withdraw(withdrawAmount);
                        break;
                        
                    case 3:
                        System.out.println("Current balance: $" + account.getBalance());
                        break;
                        
                    case 4:
                        running = false;
                        System.out.println("Thank you for using our banking service!");
                        break;
                        
                    default:
                        System.out.println("Invalid option. Please try again.");
                }
            }
            
        } catch (InputMismatchException e) {
            System.err.println("Error: Invalid input. Please enter numeric values.");
        } catch (IllegalArgumentException | InsufficientFundsException e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}