public class DepositTransaction extends Transaction {

    public DepositTransaction(String transId, double amount, Account account) {
        super(transId, amount, account);
    }

    @Override
    public void execute() {
        account.deposit(amount);
        System.out.println("Deposit successful: RM" + amount);
    }
}