import java.util.Date;

public abstract class Transaction {
    protected String transId;
    protected Date transDate;
    protected double amount;
    protected String accountId;

    public Transaction(String transId, double amount, String accountId) {
        this.transId = transId;
        this.amount = amount;
        this.accountId = accountId;
        this.transDate = new Date();
    }

    public abstract void execute();

    public String getTransDetails() {
        return "ID: " + transId + " | Date: " + transDate + " | Amount: RM" + amount;
    }
}
