public class TransferTransaction extends Transaction {
    private Account1 sourceAccount;
    private Account1 targetAccount;
    private double transFee = 2.0;

    public TransferTransaction(String transId, double amount, Account1 src, Account1 dest) {
        super(transId, amount, src.getAccountId());
        this.sourceAccount = src;
        this.targetAccount = dest;
    }

    public void execute() {
        double totalAmount = amount + transFee;
        if (sourceAccount.debit(totalAmount)) {
            targetAccount.credit(amount);
            System.out.println("Success: Transferred RM" + amount + " to " + targetAccount.getAccountId() + " (Fee: RM" + transFee + ")");
        }
    }
}
