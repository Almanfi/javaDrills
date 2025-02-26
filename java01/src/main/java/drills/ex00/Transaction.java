package drills.ex00;

import java.util.UUID;

public class Transaction {
    private UUID id;
    private User sender;
    private User receiver;
    private long amount;
    private TransactionType type;
    

    public Transaction(User sender, User receiver, long amount, TransactionType type) throws IllegalArgumentException {
        if (type == TransactionType.DEBIT && amount > 0) {
            throw new IllegalArgumentException("Debit amount must be negative");
        }
        if (type == TransactionType.CREDIT && amount < 0) {
            throw new IllegalArgumentException("Credit amount must be positive");
        }
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.type = type;
        this.id = UUID.randomUUID();
    }
    
    public UUID getId() {
        return id;
    }

    public User getSender() {
        return sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public long getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public Transaction createConterPart() {
        TransactionType conterPartType = type == TransactionType.DEBIT ? TransactionType.CREDIT : TransactionType.DEBIT;
        return new Transaction(receiver, sender, -amount, conterPartType);
    }
}
