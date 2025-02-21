import drills.ex01.User;
import drills.ex01.UserIdsGenerator;
import drills.ex01.Transaction;
import drills.ex01.TransactionType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class UserIdsGeneratorTest {

    @BeforeEach
    public void setUp() {
        UserIdsGenerator._rest();
    }

    @AfterEach
    public void tearDown() {
        UserIdsGenerator._rest();
    }

    @Test
    public void UserIdsGeneratorSingletonTest() {
        UserIdsGenerator generator = UserIdsGenerator.getInstance();
        UserIdsGenerator generator2 = UserIdsGenerator.getInstance();
        assertEquals(generator, generator2);
    }

    @Test
    public void UserIdsGeneratorIdGenerationTest() {
        UserIdsGenerator generator = UserIdsGenerator.getInstance();
        assertEquals(generator.generateId(), 0);
        assertEquals(generator.generateId(), 1);
        assertEquals(generator.generateId(), 2);
    }

    @Test
    public void UserIdsGeneratorIdGenerationTest2() {
        UserIdsGenerator generator = UserIdsGenerator.getInstance();
        UserIdsGenerator generator2 = UserIdsGenerator.getInstance();
        assertEquals(generator.generateId(), 0);
        assertEquals(generator2.generateId(), 1);
        assertEquals(generator.generateId(), 2);
    }
}
