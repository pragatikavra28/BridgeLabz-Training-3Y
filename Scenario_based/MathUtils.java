public class MathUtils {
    
    public static long factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Factorial is not defined for negative numbers");
        }
        if (n == 0 || n == 1) {
            return 1;
        }
        
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
    
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        if (n == 2) {
            return true;
        }
        if (n % 2 == 0) {
            return false;
        }
        
        for (int i = 3; i <= Math.sqrt(n); i += 2) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
    
    public static int gcd(int a, int b) {
        if (a == 0 && b == 0) {
            throw new IllegalArgumentException("Both numbers cannot be zero");
        }
        if (b == 0) {
            return Math.abs(a);
        }
        return gcd(b, a % b);
    }
    
    public static long fibonacci(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Fibonacci sequence is not defined for negative numbers");
        }
        if (n <= 1) {
            return n;
        }
        
        long a = 0, b = 1, c;
        for (int i = 2; i <= n; i++) {
            c = a + b;
            a = b;
            b = c;
        }
        return b;
    }
    
    public static void main(String[] args) {
        // Test all methods
        System.out.println("Factorial of 5: " + factorial(5));
        System.out.println("Is 17 prime? " + isPrime(17));
        System.out.println("GCD of 48 and 18: " + gcd(48, 18));
        System.out.println("10th Fibonacci number: " + fibonacci(10));
        
        // Test edge cases
        try {
            System.out.println("Factorial of -5: " + factorial(-5));
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}