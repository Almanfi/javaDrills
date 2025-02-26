package drills.ex05;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


public class Program01Test {

    @Test
    public void UserCreationTest() {
        String name = "John Doe";
        long balance = 1000;
        User user = new User(name, balance);
        assertEquals(user.getName(), name);
        assertEquals(user.getBalance(), balance);
    }

    @Test
    public void TransactionCreationTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);
        Transaction transaction = new Transaction(alice, bob, -1000, TransactionType.DEBIT);
        assertEquals(transaction.getSender(), alice);
        assertEquals(transaction.getReceiver(), bob);
        assertEquals(transaction.getAmount(), -1000);
        assertEquals(transaction.getType(), TransactionType.DEBIT);
    }

    @Test
    public void TransactionCreationUnmatchedTypeThwowTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);

        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(alice, bob, 1000, TransactionType.DEBIT);
        }, "Debit amount must be negative");

        assertThrows(IllegalArgumentException.class, () -> {
            new Transaction(alice, bob, -1000, TransactionType.CREDIT);
        }, "Credit amount must be positive");
    }
}
