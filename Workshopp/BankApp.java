class BankAccount {
    private String accountNumber;
    private double balance;

    // Constructor
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        if (initialBalance >= 0) {
            this.balance = initialBalance;
        } else {
            this.balance = 0;
            System.out.println("Initial balance can't be negative. Setting balance to 0.");
        }
    }

    // Deposit method
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Deposit amount must be positive.");
        }
    }

    // Withdraw method (with overdraft prevention)
    public void withdraw(double amount) {
        if (amount > 0) {
            if (amount <= balance) {
                balance -= amount;
                System.out.println("Withdrawn: " + amount);
            } else {
                System.out.println("Insufficient balance! Withdrawal failed.");
            }
        } else {
            System.out.println("Withdrawal amount must be positive.");
        }
    }

    // Check balance method
    public void checkBalance() {
        System.out.println("Account Number: " + accountNumber + " | Current Balance: " + balance);
    }
}

// Testing the BankAccount class
public class BankApp {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("ACC12345", 1000);

        account.checkBalance();
        account.deposit(500);
        account.checkBalance();
        account.withdraw(200);
        account.checkBalance();
        account.withdraw(2000); // will fail
        account.checkBalance();
    }
}
