public class WithdrawTransaction extends Transaction {
    private Account1 sourceAccount;

    public WithdrawTransaction(String transId, double amount, Account1 source) {
        super(transId, amount, source.getAccountId());
        this.sourceAccount = source;
    }

    public void execute() {
        if (sourceAccount.debit(amount)) {
            System.out.println("Success: Withdrew RM" + amount + " from " + accountId);
        }
    }
}
