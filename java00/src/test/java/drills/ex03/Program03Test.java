import drills.ex03.Program;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Program03Test {
    @Test
    public void findMinOfScoresTestNoThrow() {
        String s = "1 2 3 4 5";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertFalse(isThrowing);
    }

    @Test
    public void findMinOfScoresTestThrowOnEmpty() {
        String s = "";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertTrue(isThrowing);
    }

    @Test
    public void findMinOfScoresTestThrowOnLessThan5() {
        String s = "1 2 3";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertTrue(isThrowing);
    }

    @Test
    public void findMinOfScoresTestThrowOnMoreThan5() {
        String s = "1 2 3 4 5 6";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertTrue(isThrowing);
    }

    @Test
    public void findMinOfScoresTestThrowOnNonSingleDigit() {
        String s = "1 2 3 4 56";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertTrue(isThrowing);
    }

    @Test
    public void findMinOfScoresTestNoThrowOnMultySpaces() {
        String s = "  1   2 3 4 5   ";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertFalse(isThrowing);
    }

    @Test
    public void findMinOfScoresTestThrowOnNonSpaceSeperators() {
        String s = "1-2 3+4 5";
        boolean isThrowing = false;
        try {
            Program.findMinOfScores(s);
        }
        catch (IllegalArgumentException e) {
            isThrowing = true;
        }
        assertTrue(isThrowing);
    }

    @Test
    public void findMinOfScoresTest() {
        String s = "4 3 8 7 9";
        int min = Program.findMinOfScores(s);
        assertEquals(min, 3);
    }
}

