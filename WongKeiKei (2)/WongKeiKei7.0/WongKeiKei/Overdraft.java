public class Overdraft {
    
    // data members
    private double overdraftAmount;
    private double interestRate = 0.05; 

    // constructors
    public Overdraft() {
        this(0.0, 0.05);
    }

    public Overdraft(double overdraftAmount, double interestRate) {
        this.overdraftAmount = overdraftAmount;
        this.interestRate = interestRate;
    }

    // get methods
    public double getOverdraftAmount() {
        return overdraftAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }
    
    // set methods
     public void setOverdraftAmount(double overdraftAmount) {
        this.overdraftAmount = overdraftAmount;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    // methods
    // calculation
    public double calculateInterest() {
        return overdraftAmount * interestRate;
    }

    // clear
    public void clearOverdraft() {
        this.overdraftAmount = 0.0;
    }

    // toString method
    public String toString() {
        return String.format("\nOverdraft Amount: RM%.2f" +
                             "\nOverdraft Interest Rate: %.2f%%" +
                             "\nCalculated Interest: RM%.2f", 
                             overdraftAmount, interestRate * 100, calculateInterest());
    }
}
