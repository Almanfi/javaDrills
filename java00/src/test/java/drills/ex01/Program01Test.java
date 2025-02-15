import drills.ex01.Program;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class Program01Test {
    @Test
    public void testIsPrimeOnSeven() {
        int n = 7;
        boolean res = Program.isPrime(n);
        assertEquals(res, true);
    }

    @ParameterizedTest
    @CsvSource({
        "10, false",
        "5, true",
        "13, true",
        "113, true",
        "1997, true",
        "2009, false"
    })
    public void rigorousTestIsPrime(int n, boolean expected) {
        assertEquals(Program.isPrime(n), expected);
    }

}
