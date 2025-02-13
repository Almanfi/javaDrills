import drills.ex00.Program;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ProgramTest {
    @Test
    public void testAdd() {
        int a = 10;
        int b = 32;
        int sum = Program.add(a, b);
        assertEquals(sum, 42);
    }

    @ParameterizedTest
    @CsvSource({
        "10, 32, 42",
        "5, 15, 20",
        "-5, 5, 0",
        "0, 0, 0"
    })
    public void rigorousTestAdd(int a, int b, int expected) {
        int sum = Program.add(a, b);
        assertEquals(sum, expected);
    }
}
