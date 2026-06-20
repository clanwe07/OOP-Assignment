public class CurrentAccount1 extends Account1 {
    private double overdraftLimit = 1000.0;

    public CurrentAccount1(String accountId, double initialBalance) {
        super(accountId, initialBalance);
    }

    public boolean debit(double amount) {
        if (this.balance + overdraftLimit >= amount) {
            this.balance -= amount;
            return true;
        }
        System.out.println("Overdraft limit exceeded.");
        return false;
    }
}
