package drills.ex05;

import java.util.UUID;

public class TransactionsService {
    UserList userList;

    public TransactionsService() {
        this.userList = new UserArrayList();
    }

    public User addUser(String name, long balance) {
        if (balance < 0) {
            return null;
        }
        User newUser = new User(name, balance);
        userList.addUser(newUser);
        return newUser;
    }

    public String getUserName(int id) throws UserList.UserNotFoundException {
        return userList.getUserById(id).getName();
    }

    public long getUserBalance(int id) throws UserList.UserNotFoundException {
        return userList.getUserById(id).getBalance();
    }

    public void makeTransfer(int senderId, int receiverId, long amount) throws IllegalTransactionException {
        if (amount <= 0) {
            throw new IllegalTransactionException("Amount must be positive");
        }
        try {
            User sender = userList.getUserById(senderId);
            User receiver = userList.getUserById(receiverId);
            if (sender.getBalance() < amount) {
                throw new IllegalTransactionException("Insufficient funds");
            }
            Transaction transaction = new Transaction(sender, receiver, -amount, TransactionType.DEBIT);
            Transaction counterPart = transaction.createConterPart();

            sender.executeTransaction(transaction);
            receiver.executeTransaction(counterPart);
        }
        catch (UserList.UserNotFoundException e) {
            throw new IllegalTransactionException("User not found");
        }
    }

    public Transaction[] getUserTransactions(int id) throws UserList.UserNotFoundException {
        User user = userList.getUserById(id);
        return user.getTransactions();
    }

    public void removeTransaction(int UserId, UUID transactionId) {
        try {
            User user = userList.getUserById(UserId);
            user.removeTransaction(transactionId);
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public void removeAllTransactions(int UserId) {
        try {
            User user = userList.getUserById(UserId);
            user.removeAllTransactions();
        }
        catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }

    public Transaction[] checkTransactionsValidity() {
        TransactionList unpairTransactions = new TransactionLinkedList();
        UserList userList = this.userList;
        for (int i = 0; i < userList.size(); i++) {
            User user = userList.getUserByIndex(i);
            for (Transaction transaction : user.getTransactions()) {
                User receiver = transaction.getReceiver();
                if (isTransactionValid(transaction, receiver)) {
                    continue;
                }
                unpairTransactions.add(transaction);
            }
        }
        return unpairTransactions.toArray();
    }

    boolean isTransactionValid(Transaction transaction, User otherUser) {
        for (Transaction otherTransaction : otherUser.getTransactions()) {
            if (transaction.getId().equals(otherTransaction.getId())) {
                return true;
            }
        }
        return false;
    }

    public class IllegalTransactionException extends RuntimeException {
        public IllegalTransactionException(String message) {
            super(message);
        }
    }
}
