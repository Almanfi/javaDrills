package drills.ex04;

import java.util.UUID;

public class User {
    private final int id;
    private String name;
    private long balance;
    private TransactionList transactions;

    public User(String name, long balance) {
        this.name = name;
        this.balance = balance;
        this.id = UserIdsGenerator.getInstance().generateId() ;
        this.transactions = new TransactionLinkedList();
    }

    public String getName() {
        return name;
    }

    public long getBalance() {
        return balance;
    }

    public int getId() {
        return id;
    }

    public void setBalance(long balance) {
        this.balance = balance;
    }

    void executeTransaction(Transaction transaction) {
        balance += transaction.getAmount();
        transactions.add(transaction);
    }

    public Transaction[] getTransactions() {
        return transactions.toArray();
    }

    void removeTransaction(UUID transactionId) {
        transactions.removeById(transactionId);
    }

    void removeAllTransactions() {
        transactions = new TransactionLinkedList();
    }
}