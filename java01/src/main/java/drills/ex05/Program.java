package drills.ex05;

public class Program {
    public static void main(String[] args) {
        String profile = "prod";
        for (String arg : args) {
            if (arg.startsWith("--profile=")) {
                profile = arg.substring("--profile=".length());
            }
        }
        TransactionsService transactionsService = new TransactionsService();
        Menu menu = new Menu(transactionsService, profile);
        menu.run();
    }
}
