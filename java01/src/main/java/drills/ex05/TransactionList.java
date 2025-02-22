package drills.ex05;
import java.util.UUID;

public interface TransactionList {
    public void add(Transaction transaction);
    public void removeById(UUID id) throws TransactionNotFoundException;
    public Transaction[] toArray();

    public class TransactionNotFoundException extends RuntimeException {
        public TransactionNotFoundException(String message) {
            super(message);
        }
    }
}
