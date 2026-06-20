public class SavingsAccount1 extends Account1 {
    public SavingsAccount1(String accountId, double initialBalance) {
        super(accountId, initialBalance);
    }

    public boolean debit(double amount) {
        if (this.balance >= amount) {
            this.balance -= amount;
            return true;
        }
        System.out.println("Insufficient funds in Savings.");
        return false;
    }
}
