import java.util.ArrayList;
import java.util.List;

public class driver {
    public static void main(String[] args) {
        // Initialize accounts
        Account1 savings = new SavingsAccount1("SAV-101", 500.0);
        Account1 current = new CurrentAccount1("CUR-202", 100.0);

        // Create transaction list
        List<Transaction> list = new ArrayList<>();
        list.add(new DepositTransaction("T1", 200.0, savings));
        list.add(new WithdrawTransaction("T2", 600.0, current));
        list.add(new TransferTransaction("T3", 100.0, savings, current));

        System.out.println("--- System Running ---");
        for (Transaction t : list) {
            t.execute();
        }

        System.out.println("\n--- Final Balances ---");
        System.out.println("Savings: RM" + savings.getBalance());
        System.out.println("Current: RM" + current.getBalance());
    }
}
