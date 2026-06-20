import java.util.Date;

public abstract class Account {
    private String accId;
    protected double balance;
    private Date createDate;
    private String status;

    public Account() {
        this("", 0, "Active");
    }

    public Account(String accId, double balance, String status) {
        this.accId = accId;
        this.balance = balance;
        this.status = status;
        this.createDate = new Date();
    }

    public String getAccId() { return accId; }
    public double getBalance() { return balance; }
    public Date getCreateDate() { return createDate; }
    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public void deposit(double amount) {
        if (amount > 0) balance += amount;
    }

    public boolean withdraw(double amount) {
        if (amount > balance) return false;
        balance -= amount;
        return true;
    }

    public String toString() {
        return "\nAccount ID: " + accId +
               String.format("\nBalance: RM%.2f", balance) +
               "\nStatus: " + status +
               "\nDate Created: " + createDate;
    }
}