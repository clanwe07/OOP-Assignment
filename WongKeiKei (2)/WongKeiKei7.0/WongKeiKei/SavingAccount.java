//sub class
public class SavingAccount extends Account {

    private double interestRate = 0.015;
    

    public SavingAccount(String accId, double balance, String status, double interestRate) {
        super(accId, balance, status);
        this.interestRate = interestRate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double calculateAnnualInterest() {
        return getBalance() * interestRate;
    }

    @Override
    public String toString() {
        return super.toString() +
               String.format("\nInterest Rate: %.2f%%", interestRate * 100) +
               String.format("\nAnnual Interest: RM%.2f", calculateAnnualInterest());
    }
}