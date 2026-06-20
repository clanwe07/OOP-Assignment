public abstract class Account1 {
    protected String accountId;
    protected double balance;

    public Account1(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public void credit(double amount) {
        this.balance += amount;
    }

    public abstract boolean debit(double amount);

    public double getBalance() { return balance; }
    public String getAccountId() { return accountId; }
}
