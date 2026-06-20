import java.util.Date;

public abstract class Transaction {

    protected String transId;
    protected Date transDate;
    protected double amount;
    protected Account account;   

    public Transaction(String transId, double amount, Account account) {
        this.transId = transId;
        this.amount = amount;
        this.account = account;
        this.transDate = new Date();
    }

    public abstract void execute();

    public String getTransDetails() {
        return "ID: " + transId +
               " | Date: " + transDate +
               " | Amount: RM" + amount +
               " | Account: " + account.getAccId();
    }
}