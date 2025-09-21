public class BankAccount {
    private String accountNumber;
    private double balance;
    
    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Deposit amount must be positive");
            return;
        }
        balance += amount;
        System.out.println("Deposited: $" + amount + ". New balance: $" + balance);
    }
    
    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Withdrawal amount must be positive");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient funds. Withdrawal canceled.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrew: $" + amount + ". New balance: $" + balance);
    }
    
    public double checkBalance() {
        return balance;
    }
    
    public String getAccountNumber() {
        return accountNumber;
    }
    
    public static void main(String[] args) {
        BankAccount account = new BankAccount("123456789", 1000.0);
        
        System.out.println("Initial balance: $" + account.checkBalance());
        account.deposit(500.0);
        account.withdraw(200.0);
        account.withdraw(2000.0);
        System.out.println("Final balance: $" + account.checkBalance());
    }
}