package drills.ex01;

import java.util.Scanner;

public class Program {
    static int steps = 0;
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n;
        try {
            n = sc.nextInt();
            if (n <= 1)
                throw new IllegalArgumentException();
            boolean isPrime = isPrime(n);
            System.out.println(isPrime + " " + steps);
        }
        catch (Exception e) {
            sc.close();    
            System.err.println("llegalArgument");
            System.exit(-1);
        }
        finally {
            sc.close();
        }
    }

    public static boolean isPrime(int n) {
        steps = 0;
        steps++;
        if (n % 2 == 0) {
            return false;
        }
        int i = 3;
        while(i < Math.sqrt(n)) {
            steps++;
            if (n % i == 0) {
                return false;
            }
            i += 2;
        }
        return true;
    }
}