package drills.ex02;

import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int coffeeRequestCount = 0;
        while (sc.hasNextInt())
        {
            int n = sc.nextInt();
            if (n == 42) {
                break;
            }
            if (isSumOfDigitPrime(n)) {
                coffeeRequestCount++;
            }
        }
        sc.close();
        System.out.println("Count of coffee-request : " + coffeeRequestCount);
    }

    public static boolean isSumOfDigitPrime(int n) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n = n / 10;
        }
        if (drills.ex01.Program.isPrime(sum)) {
            return true;
        }
        return false;
    }
}