public class DepositTransaction extends Transaction {
    private Account1 targetAccount;

    public DepositTransaction(String transId, double amount, Account1 target) {
        super(transId, amount, target.getAccountId());
        this.targetAccount = target;
    }

    public void execute() {
        targetAccount.credit(amount);
        System.out.println("Success: Deposited RM" + amount + " to " + accountId);
    }
}
