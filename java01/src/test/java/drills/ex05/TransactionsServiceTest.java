package drills.ex05;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.BeforeEach;

import drills.ex05.TransactionsService.IllegalTransactionException;


public class TransactionsServiceTest {
    TransactionsService transactionsService;

    @BeforeEach
    public void setUp() {
        transactionsService = new TransactionsService();
    }

    @Test
    public void userCreationTest() {
        String name = "John Doe";
        long balance = 1000;
        User user = transactionsService.addUser(name, balance);
        assertEquals(user.getName(), name);
        assertEquals(user.getBalance(), balance);
    }

    @Test
    public void userBalanceTest() {
        User user = transactionsService.addUser("John Doe", 4000);
        assertEquals(transactionsService.getUserBalance(user.getId()), 4000);
    }

    @Test
    public void transactionCreationTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        Transaction transaction = transactionsService.getUserTransactions(alice.getId())[0];

        assertEquals(alice.getBalance(), 2000);
        assertEquals(bob.getBalance(), 2000);

        assertEquals(transaction.getSender(), alice);
        assertEquals(transaction.getReceiver(), bob);
        assertEquals(transaction.getAmount(), -1000);
        assertEquals(transaction.getType(), TransactionType.DEBIT);

        Transaction counterPart = bob.getTransactions()[0];
        assertEquals(counterPart.getSender(), bob);
        assertEquals(counterPart.getReceiver(), alice);
        assertEquals(counterPart.getAmount(), 1000);
        assertEquals(counterPart.getType(), TransactionType.CREDIT);
    }

    @Test
    public void transactionUUIDMatchTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        Transaction[] aliceTransactions = transactionsService.getUserTransactions(alice.getId());
        Transaction[] bobTransactions = transactionsService.getUserTransactions(bob.getId());

        assertEquals(aliceTransactions[0].getId(), bobTransactions[0].getId());
    }

    @Test
    public void userNotFoundThrowsTest() {
        assertThrows(UserList.UserNotFoundException.class, () -> {
            transactionsService.getUserBalance(0);
        });

        assertThrows(UserList.UserNotFoundException.class, () -> {
            transactionsService.getUserTransactions(0);
        });
    }

    @Test
    public void noreceiverThrowsTest() {
        User alice = transactionsService.addUser("Alice", 1000);
        assertThrows(IllegalTransactionException.class, () -> {
            transactionsService.makeTransfer(alice.getId(), 4, 1);
        });
    }

    @Test
    public void insufficientFundsThrowsTest() {
        User alice = transactionsService.addUser("Alice", 1000);
        User bob = transactionsService.addUser("Bob", 1000);
        assertThrows(IllegalTransactionException.class, () -> {
            transactionsService.makeTransfer(alice.getId(), bob.getId(), 1001);
        });
    }

    @Test
    public void negativeAmountThrowsTest() {
        User alice = transactionsService.addUser("Alice", 1000);
        User bob = transactionsService.addUser("Bob", 1000);
        assertThrows(IllegalTransactionException.class, () -> {
            transactionsService.makeTransfer(alice.getId(), bob.getId(), -1);
        });
    }

    @Test
    public void multiTransactionsTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        
        assertEquals(transactionsService.getUserTransactions(alice.getId()).length, 3);
        assertEquals(transactionsService.getUserTransactions(bob.getId()).length, 3);
        
        assertEquals(transactionsService.getUserBalance(alice.getId()), 0);
        assertEquals(transactionsService.getUserBalance(bob.getId()), 4000);
    }

    @Test
    public void removeFirstTransactionTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        Transaction[] transactions = transactionsService.getUserTransactions(alice.getId());
        transactionsService.removeTransaction(alice.getId(), transactions[0].getId());

        assertEquals(transactionsService.getUserTransactions(alice.getId()).length, 2);
        Transaction[] uncompleteTransactions = transactionsService.getUserTransactions(alice.getId());
        assertEquals(uncompleteTransactions[0].getId(), transactions[1].getId());
        assertEquals(uncompleteTransactions[1].getId(), transactions[2].getId());
    }

    @Test
    public void removeFinalTransactionTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        Transaction[] transactions = transactionsService.getUserTransactions(alice.getId());
        transactionsService.removeTransaction(alice.getId(), transactions[2].getId());

        assertEquals(transactionsService.getUserTransactions(alice.getId()).length, 2);
        Transaction[] uncompleteTransactions = transactionsService.getUserTransactions(alice.getId());
        assertEquals(uncompleteTransactions[0].getId(), transactions[0].getId());
        assertEquals(uncompleteTransactions[1].getId(), transactions[1].getId());
    }

    @Test
    public void removeMidleTransactionTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        Transaction[] transactions = transactionsService.getUserTransactions(alice.getId());
        transactionsService.removeTransaction(alice.getId(), transactions[1].getId());

        assertEquals(transactionsService.getUserTransactions(alice.getId()).length, 2);
        Transaction[] uncompleteTransactions = transactionsService.getUserTransactions(alice.getId());
        assertEquals(uncompleteTransactions[0].getId(), transactions[0].getId());
        assertEquals(uncompleteTransactions[1].getId(), transactions[2].getId());
    }

    @Test
    public void removeAllTransactionTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        transactionsService.removeAllTransactions(alice.getId());

        assertEquals(transactionsService.getUserTransactions(alice.getId()).length, 0);
    }

    @Test
    public void removeAllTransactionWithNoTransactionsTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        assertDoesNotThrow(() -> {
            transactionsService.removeTransaction(alice.getId(), UUID.randomUUID());
        });

        assertDoesNotThrow(() -> {
            transactionsService.removeTransaction(10, UUID.randomUUID());
        });

        assertDoesNotThrow(() -> {
            transactionsService.removeTransaction(alice.getId(), null);
        });

        assertDoesNotThrow(() -> {
            transactionsService.removeAllTransactions(alice.getId());
        });

        assertDoesNotThrow(() -> {
            transactionsService.removeAllTransactions(10);
        });
    }

    @Test
    public void checkValidTransactionsTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        Transaction[] unpaireTransactions =  transactionsService.checkTransactionsValidity();
        assertEquals(unpaireTransactions.length, 0);
    }

    @Test
    public void checkUncompleteTransactionsTest() {
        User alice = transactionsService.addUser("Alice", 3000);
        User bob = transactionsService.addUser("Bob", 1000);

        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);
        transactionsService.makeTransfer(alice.getId(), bob.getId(), 1000);

        Transaction[] aliceTransactions = transactionsService.getUserTransactions(alice.getId());
        transactionsService.removeTransaction(alice.getId(), aliceTransactions[0].getId());

        Transaction[] unpaireTransactions =  transactionsService.checkTransactionsValidity();
        assertEquals(unpaireTransactions.length, 1);
        assertEquals(unpaireTransactions[0].getId(), aliceTransactions[0].getId());
    }
}
