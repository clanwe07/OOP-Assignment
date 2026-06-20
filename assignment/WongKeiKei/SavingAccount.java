// ??? SavingAccount,?? Account
public class SavingAccount extends Account {
    
    private double interestRate = 0.015; 

    // ????:???? B ?????
    public SavingAccount(String accId, double balance, String status, double interestRate) {
        super(accId, balance, status); 
        this.interestRate = interestRate;
    }

    // ??:??? A ? debit ?????(??????!)
    @Override
    public boolean debit(double amount) {
        if (this.getBalance() >= amount) { // ????? getBalance
            // ??:???? balance ? protected,?????;??? private,????? setBalance
            this.balance -= amount; 
            return true;
        }
        System.out.println("????!");
        return false;
    }

    // ???????(???? B ???)
    public double calculateAnnualInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public String toString() {
        return super.toString() + 
               String.format("\n??: %.2f%%", interestRate * 100) +
               String.format("\n???: RM%.2f", calculateAnnualInterest());
    }
}