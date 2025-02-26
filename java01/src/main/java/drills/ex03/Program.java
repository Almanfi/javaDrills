package drills.ex03;

public class Program {
    public static void main(String[] args) {
        boolean isOperationSuccessful = false;
        User alice = new User("Alice", 1000);
        System.out.printf("created user: " + alice.getName(), "with balance: " + alice.getBalance() + "\n");
        User bob = new User("Bob", 1000);
        System.out.printf("created user: " + bob.getName(), "with balance: " + bob.getBalance() + "\n");

        isOperationSuccessful =  alice.sendMoney(bob, 100);
        System.out.println("alice sending 100 to bob");
        System.out.println("Operation successful: " + isOperationSuccessful);
        System.out.println("Alice balance: " + alice.getBalance());
        System.out.println("Bob balance: " + bob.getBalance());

        isOperationSuccessful =  alice.sendMoney(bob, 1000);
        System.out.println("alice sending 1000 to bob");
        System.out.println("Operation successful: " + isOperationSuccessful);
        System.out.println("Alice balance: " + alice.getBalance());
        System.out.println("Bob balance: " + bob.getBalance());

        isOperationSuccessful =  alice.sendMoney(bob, 100);
        System.out.println("alice sending 100 to bob");
        System.out.println("Operation successful: " + isOperationSuccessful);
        System.out.println("Alice balance: " + alice.getBalance());
        System.out.println("Bob balance: " + bob.getBalance());

        isOperationSuccessful = bob.sendMoney(alice, 1100);
        System.out.println("bob sending 1100 to alice");
        System.out.println("Operation successful: " + isOperationSuccessful);
        System.out.println("Alice balance: " + alice.getBalance());
        System.out.println("Bob balance: " + bob.getBalance());

        isOperationSuccessful = bob.sendMoney(alice, 1);
        System.out.println("bob sending 1 to alice");
        System.out.println("Operation successful: " + isOperationSuccessful);
        System.out.println("Alice balance: " + alice.getBalance());
        System.out.println("Bob balance: " + bob.getBalance());

        System.out.println("Alice transactions:");
        for (Transaction transaction : alice.getTransactions()) {
            System.out.println(transaction.getId() + " " + transaction.getSender().getName() + " => " + transaction.getReceiver().getName() + " " + transaction.getAmount() + " " + transaction.getType());
        }

        System.out.println("Bob transactions:");
        for (Transaction transaction : bob.getTransactions()) {
            System.out.println(transaction.getId() + " " + transaction.getSender().getName() + " => " + transaction.getReceiver().getName() + " " + transaction.getAmount() + " " + transaction.getType());
        }

    }
}
