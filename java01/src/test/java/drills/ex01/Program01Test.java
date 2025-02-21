import drills.ex01.User;
import drills.ex01.UserIdsGenerator;
import drills.ex01.Transaction;
import drills.ex01.TransactionType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTimeout;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


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
    public void UserCreationThrowsOnNegativeBalance() {
        String name = "John Doe";
        long balance = -1000;
        try {
            User user = new User(name, balance);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Balance cannot be negative");
        }
    }

    @Test
    public void UserSendMoneyTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);
        boolean isOperationSuccessful = alice.sendMoney(bob, 1000);
        assertEquals(isOperationSuccessful, true);
        assertEquals(alice.getBalance(), 2000);
        assertEquals(bob.getBalance(), 2000);
    }

    @Test
    public void UserSendMoneyFailsOnInsufficientBalance() {
        User alice = new User("Alice", 500);
        User bob = new User("Bob", 1000);
        boolean isOperationSuccessful = alice.sendMoney(bob, 1000);
        assertEquals(isOperationSuccessful, false);
        assertEquals(alice.getBalance(), 500);
        assertEquals(bob.getBalance(), 1000);
    }

    @Test
    public void UserSendMoneyFailsOnNegativeAmount() {
        User alice = new User("Alice", 500);
        User bob = new User("Bob", 1000);
        boolean isOperationSuccessful = alice.sendMoney(bob, -1000);
        assertEquals(isOperationSuccessful, false);
        assertEquals(alice.getBalance(), 500);
        assertEquals(bob.getBalance(), 1000);
    }

    @Test
    public void UserSendMoneyFailsOnZeroAmount() {
        User alice = new User("Alice", 500);
        User bob = new User("Bob", 1000);
        boolean isOperationSuccessful = alice.sendMoney(bob, 0);
        assertEquals(isOperationSuccessful, false);
        assertEquals(alice.getBalance(), 500);
        assertEquals(bob.getBalance(), 1000);
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
        boolean isThrown = false;
        try {
            Transaction transaction = new Transaction(alice, bob, 1000, TransactionType.DEBIT);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Debit amount must be negative");
            isThrown = true;
        }
        assertEquals(isThrown, true);

        isThrown = false;
        try {
            Transaction transaction = new Transaction(alice, bob, -1000, TransactionType.CREDIT);
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), "Credit amount must be positive");
            isThrown = true;
        }
        assertEquals(isThrown, true);
    }

    @Test
    public void TransactionAddedToUserTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);
        alice.sendMoney(bob, 100);
        assertEquals(alice.getTransactions().size(), 1);
        Transaction transaction = alice.getTransactions().get(0);
        assertEquals(transaction.getSender(), alice);
        assertEquals(transaction.getReceiver(), bob);
        assertEquals(transaction.getAmount(), -100);
        assertEquals(transaction.getType(), TransactionType.DEBIT);
    }

    @Test
    public void TransactionCounterPartAddedTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);
        alice.sendMoney(bob, 100);
        assertEquals(bob.getTransactions().size(), 1);
        Transaction transaction = bob.getTransactions().get(0);
        assertEquals(transaction.getSender(), bob);
        assertEquals(transaction.getReceiver(), alice);
        assertEquals(transaction.getAmount(), 100);
        assertEquals(transaction.getType(), TransactionType.CREDIT);
    }

    @Test
    public void TransactionCounterPartAddedOnFailedSendTest() {
        User alice = new User("Alice", 500);
        User bob = new User("Bob", 1000);
        alice.sendMoney(bob, 1000);
        assertEquals(alice.getTransactions().size(), 0);
        assertEquals(bob.getTransactions().size(), 0);
    }

    @Test
    public void TwoTransactionsAddedTest() {
        User alice = new User("Alice", 3000);
        User bob = new User("Bob", 1000);
        alice.sendMoney(bob, 1000);
        alice.sendMoney(bob, 1000);
        assertEquals(alice.getTransactions().size(), 2);
        assertEquals(bob.getTransactions().size(), 2);
        assertEquals(alice.getBalance(), 1000);
        assertEquals(bob.getBalance(), 3000);
    }
}
