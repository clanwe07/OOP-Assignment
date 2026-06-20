public class TransferTransaction extends Transaction {

    private Account targetAccount;

    public TransferTransaction(String transId, double amount, Account source, Account target) {
        super(transId, amount, source);
        this.targetAccount = target;
    }

    @Override
    public void execute() {
        if (account.withdraw(amount)) {
            targetAccount.deposit(amount);
            System.out.println("Transfer successful: RM" + amount +
                               " to " + targetAccount.getAccId());
        } else {
            System.out.println("Transfer failed: Insufficient balance");
        }
    }
}