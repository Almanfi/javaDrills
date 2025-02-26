package drills.ex00;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.*;

public class SignatureFinderTest {
    private SignatureFinder sf;

    @BeforeEach
    public void setUp() {
        sf = new SignatureFinder();
    }

    @AfterEach
    public void tearDown() {
        Path logPath = sf.getLogPath();
        sf.close();
        try {
            java.nio.file.Files.deleteIfExists(logPath);
        } catch (Exception e) {
            System.out.println("Error: tearDown - " + e.getMessage());
        }
    }

    @Test
    public void SearchSignatureOfZeroBytesTest() {
        byte[] buffer = new byte[16];
        String extention = sf.searchSignatures(buffer, 0);
        assertEquals(null, extention);
    }

    @Test
    public void SearchSignaturePNGTest() {
        byte[] buffer = { (byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A };
        String extention = sf.searchSignatures(buffer, 8);
        assertEquals("PNG", extention);
    }

    @Test
    public void SearchSignaturePNGPlusDataTest() {
        byte[] buffer = { (byte)0x89, 0x50, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
            , 0x11, 0x13, 0x15, 0x17, 0x19, 0x21, 0x23, 0x25 };
        String extention = sf.searchSignatures(buffer, 16);
        assertEquals("PNG", extention);
    }

    @Test
    public void SearchSignatureMP3Test() {
        byte[] buffer = { (byte)0xFF, (byte)0xFB};
        String extention = sf.searchSignatures(buffer, 8);
        assertEquals("MP3", extention);
    }

    @Test
    public void SearchSignatureMP3PlusDataTest() {
        byte[] buffer = { (byte)0xFF, (byte)0xFB, 0x4E, 0x47, 0x0D, 0x0A, 0x1A, 0x0A
            , 0x11, 0x13, 0x15, 0x17, 0x19, 0x21, 0x23, 0x25 };
        String extention = sf.searchSignatures(buffer, 16);
        assertEquals("MP3", extention);
    }
    
    @Test
    public void SearchSignatureMP3UncompleteTest() {
        byte[] buffer = { (byte)0xFF };
        String extention = sf.searchSignatures(buffer, 1);
        assertEquals(null, extention);
    }

}
