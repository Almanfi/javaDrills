package drills.ex05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import drills.ex05.TransactionList.TransactionNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.UUID;


public class TransactionListTest {
    private TransactionList transactionList;

    @BeforeEach
    public void setUp() {
        transactionList = new TransactionLinkedList();
    }

    @Test
    public void TransactionListAddTransactionTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);;
        transactionList.add(transaction);
        assertEquals(transactionList.toArray()[0], transaction);
    }

    @Test
    public void TransactionListRemoveTransactionTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        transactionList.add(transaction);
        assertDoesNotThrow(() -> {
            transactionList.removeById(transaction.getId());
        });
        assertEquals(transactionList.toArray().length, 0);
    }

    @Test
    public void TransactionListRemoveTransactionThrowsOnTransactionNotFound() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        transactionList.add(transaction);

        while (true) {
            UUID newId = UUID.randomUUID();
            if (newId == transaction.getId()) {
                continue;
            }
            assertThrows(TransactionNotFoundException.class, () -> {
                transactionList.removeById(newId);
            });
            break;
        }
    }

    @Test
    public void TransactionListremoveFirstNodeTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = transaction.createConterPart();
        Transaction transaction3 = transaction.createConterPart();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.removeById(transaction3.getId());
        assertEquals(transactionList.toArray().length, 2);
        assertEquals(transactionList.toArray()[0], transaction2);
        assertEquals(transactionList.toArray()[1], transaction);
    }

    @Test
    public void TransactionListremoveLastNodeTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction1 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction3 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.removeById(transaction1.getId());
        assertEquals(transactionList.toArray().length, 2);
        assertEquals(transactionList.toArray()[0], transaction3);
        assertEquals(transactionList.toArray()[1], transaction2);
    }

    @Test
    public void TransactionListremoveMiddleNodeTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction1 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction3 = new Transaction(user, user2, 100, TransactionType.CREDIT);
        transactionList.add(transaction1);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.removeById(transaction2.getId());
        assertEquals(transactionList.toArray().length, 2);
        assertEquals(transactionList.toArray()[0], transaction3);
        assertEquals(transactionList.toArray()[1], transaction1);
    }

    @Test
    public void TransactionListremoveAllNodesTest() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = transaction.createConterPart();
        Transaction transaction3 = transaction.createConterPart();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        transactionList.removeById(transaction.getId());
        transactionList.removeById(transaction3.getId());
        transactionList.removeById(transaction2.getId());
        assertEquals(transactionList.toArray().length, 0);
    }

    @Test
    public void TransactionListToArrayEmpty() {
        assertEquals(transactionList.toArray().length, 0);
    }

    @Test
    public void TransactionListToArrayOneElement() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        transactionList.add(transaction);
        assertEquals(transactionList.toArray().length, 1);
    }

    @Test
    public void TransactionListToArray() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = transaction.createConterPart();
        Transaction transaction3 = transaction.createConterPart();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        assertEquals(transactionList.toArray().length, 3);
    }

    @Test
    public void TransactionListToArrayOrder() {
        User user = new User("John Doe", 1000);
        User user2 = new User("Smith Beth", 2000);
        Transaction transaction = new Transaction(user, user2, 100, TransactionType.CREDIT);
        Transaction transaction2 = transaction.createConterPart();
        Transaction transaction3 = transaction.createConterPart();
        transactionList.add(transaction);
        transactionList.add(transaction2);
        transactionList.add(transaction3);
        assertEquals(transactionList.toArray()[0], transaction3);
        assertEquals(transactionList.toArray()[1], transaction2);
        assertEquals(transactionList.toArray()[2], transaction);
    }
}
