import drills.ex02.Program;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Program02Test {
    @Test
    public void isSumOfDigitPrimeTestOne() {
        int n = 11;
        assertFalse(Program.isSumOfDigitPrime(n));
    }

    @Test
    public void isSumOfDigitPrimeTestTwo() {
        int n = 2;
        assertFalse(Program.isSumOfDigitPrime(n));
    }

    @Test
    public void isSumOfDigitPrimeTestThree() {
        int n = 111;
        assertTrue(Program.isSumOfDigitPrime(n));
    }
}

