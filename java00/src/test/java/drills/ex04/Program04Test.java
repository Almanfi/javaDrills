import drills.ex04.Program;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import org.junit.jupiter.api.Test;

public class Program04Test {
    @Test
    public void testAnalysisOnEmptyString() {
        String s = "";
        Program.analyseInput(s);
        int len = Program.getFinalLen();
        assertEquals(0, len);
    }

    @Test
    public void testAnalysisOnSingleCharacterString() {
        String s = "a";
        Program.analyseInput(s);
        int len = Program.getFinalLen();
        assertEquals(1, len);
        char code = Program.getFinalCodes()[0];
        assertEquals('a', code);
        int freq = Program.getFinalFreqs()[0];
        assertEquals(1, freq);
    }

    @Test
    public void testAnalysisOnSimpleString() {
        String s = "bcbaaa";
        Program.analyseInput(s);
        int len = Program.getFinalLen();
        assertEquals(3, len);
        char[] codes = Program.getFinalCodes();
        assertEquals('a', codes[0]);
        assertEquals('b', codes[1]);
        assertEquals('c', codes[2]);
        int[] freqs = Program.getFinalFreqs();
        assertEquals(3, freqs[0]);
        assertEquals(2, freqs[1]);
        assertEquals(1, freqs[2]);
    }

    @Test
    public void testAnalysisOnGigString() {
        String s = "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAASSSSSSSSSSSSSSSSSSSSSSSSDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDWEWWKFKKDKKDSKAKLSLDKSKALLLLLLLLLLRTRTETWTWWWWWWWWWWOOOOOOO42";
        Program.analyseInput(s);
        int len = Program.getFinalLen();
        assertEquals(10, len);
        char[] codes = Program.getFinalCodes();
        assertEquals('D', codes[0]);
        assertEquals('A', codes[1]);
        assertEquals('E', codes[8]);
        int[] freqs = Program.getFinalFreqs();
        assertEquals(36, freqs[0]);
        assertEquals(35, freqs[1]);
        assertEquals(2, freqs[8]);
    }
}

