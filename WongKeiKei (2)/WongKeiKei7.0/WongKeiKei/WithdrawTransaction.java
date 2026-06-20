public class WithdrawTransaction extends Transaction {

    public WithdrawTransaction(String transId, double amount, Account account) {
        super(transId, amount, account);
    }

    @Override
    public void execute() {
        if (account.withdraw(amount)) {
            System.out.println("Withdraw successful: RM" + amount);
        } else {
            System.out.println("Withdraw failed: Insufficient balance");
        }
    }
}