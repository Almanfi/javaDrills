package drills.ex05;

import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Pattern;

public class Menu {
    Scanner sc = new Scanner(System.in);
    TransactionsService transactionsServiceInstance;
    Mode mode = Mode.PROD;
    Runnable[] actions;
    
    void setUpRunnables() {
        if (mode == Mode.DEV) {
            actions = new Runnable[7];
        }
        else {
            actions = new Runnable[5];
        }
        actions[0] = this::addUser;
        actions[1] = this::viewUserBalances;
        actions[2] = this::performTransfer;
        actions[3] = this::viewUserTransactions;
        if (mode == Mode.DEV) {
            actions[4] = this::removeTransaction;
            actions[5] = this::checkTransferValidity;
            actions[6] = this::finishExecution;
        }
        else {
            actions[4] = this::finishExecution;
        }
    }

    enum Mode {
        DEV, PROD
    }

    public Menu(TransactionsService transactionsServiceInstance) {
        this.transactionsServiceInstance = transactionsServiceInstance;
        setUpRunnables();
    }

    public Menu(TransactionsService transactionsServiceInstance, String mode) {
        this.transactionsServiceInstance = transactionsServiceInstance;
        setMode(mode);
    }

    public Menu setMode(String mode) {
        if (mode == "dev") {
            this.mode = Mode.DEV;
        }
        else {
            this.mode = Mode.PROD;
        }
        setUpRunnables();
        return this;
    }

    public void run() {
        while (true) {
            DisplayMenu();
            if (!sc.hasNextLine())
                break;
            int choice = actions.length;
            try {
                String line = sc.nextLine();
                if (!Pattern.matches("\\d", line)) {
                    throw new IllegalArgumentException();
                }
                choice = Integer.parseInt(line);
                if (choice <= 0 || choice > actions.length) {
                    throw new IllegalArgumentException();
                }
            }
            catch (Exception e) {
                System.out.println("Invalid choice");
                System.out.println("---------------------------------------------------------");
                continue;
            }
            try {
                actions[choice - 1].run();
            }
            catch (Exception e) {
                System.err.println(e.getMessage());
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    public void DisplayMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        if (mode == Mode.DEV) {
            System.out.println("5. DEV - remove a transfer by ID");
            System.out.println("6. DEV - check transfer validity");
            System.out.println("7. Finish execution");
        }
        else {
            System.out.println("5. Finish execution");
        }
    }

    private void addUser() {
        System.out.println("Enter a user name and a balance");

        String line = sc.nextLine();
        
        if (!Pattern.matches("\\w+ \\d+", line)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        String[] input = line.split(" ");
        String name = input[0];
        long balance = Long.parseLong(input[1]);

        User user = transactionsServiceInstance.addUser(name, balance);
        System.out.printf("User with id %d added\n", user.getId());
    }

    private void viewUserBalances() {
        System.out.println("Enter a user ID");
        String line = sc.nextLine();

        if (!Pattern.matches("\\d+", line)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        int id = Integer.parseInt(line);
        System.out.print(transactionsServiceInstance.getUserName(id) + " - ");
        System.out.println(transactionsServiceInstance.getUserBalance(id));
    }

    private void performTransfer() {
        System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
        
        String line = sc.nextLine();
        
        if (!Pattern.matches("\\d+ \\d+ \\d+", line)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Scanner newSc = new Scanner(line);
        int senderId, receiverId;
        long amount;
        try {
            senderId = newSc.nextInt();
            receiverId = newSc.nextInt();
            amount = newSc.nextLong();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        finally {
            newSc.close();
        }

        transactionsServiceInstance.makeTransfer(senderId, receiverId, amount);
    }

    private void viewUserTransactions() {
        System.out.println("Enter a user ID");

        String line = sc.nextLine();

        if (!Pattern.matches("\\d+", line)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Scanner newSc = new Scanner(line);
        int id;
        try {
            id = newSc.nextInt();
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        finally {
            newSc.close();
        }

        Transaction[] transactions = transactionsServiceInstance.getUserTransactions(id);
        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }

    private void removeTransaction() {
        System.out.println("Enter a user ID and a transfer ID");

        String line = sc.nextLine();

        if (!Pattern.matches("\\d+ [a-f0-9-]+", line)) {
            throw new IllegalArgumentException("Invalid user ID");
        }

        Scanner newSc = new Scanner(line);
        int userId;
        UUID transactionId;
        try {
            userId = newSc.nextInt();
            transactionId = UUID.fromString(newSc.next());
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Invalid arguments");
        }
        finally {
            newSc.close();
        }
        
        transactionsServiceInstance.removeTransaction(userId, transactionId);
    }

    private void checkTransferValidity() {
        Transaction[] unacknowledged = transactionsServiceInstance.checkTransactionsValidity();
        
        System.out.println("Check results:");
        for (Transaction transaction : unacknowledged) {
            System.out.printf(
                "%s(id = %d) has an unacknowledged transfer id = %s from %s(id = %d) for %ld\n",
                transaction.getSender().getName(),
                transaction.getSender().getId(),
                transaction.getId().toString(),
                transaction.getReceiver().getName(),
                transaction.getReceiver().getId(),
                transaction.getAmount()
            );
        }
    }

    private void finishExecution() {
        System.exit(0);
    }
}
