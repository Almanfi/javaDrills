package drills.ex01;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


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
