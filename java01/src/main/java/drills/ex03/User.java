package drills.ex03;


public class User {
    private final int id;
    private String name;
    private long balance;
    private TransactionList transactions;

    public User(String name, long balance) throws IllegalArgumentException {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
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

    private void executeTransaction(Transaction transaction) {
        balance += transaction.getAmount();
        transactions.add(transaction);
    }

    public boolean sendMoney(User receiver, long amount) {
        if (amount <= 0 || balance < amount) {
            return false;
        }

        Transaction transaction = new Transaction(this, receiver, -amount, TransactionType.DEBIT);
        this.executeTransaction(transaction);

        Transaction conterPart = transaction.createConterPart();
        receiver.executeTransaction(conterPart);
        return true;
    }

    public Transaction[] getTransactions() {
        return transactions.toArray();
    }
}