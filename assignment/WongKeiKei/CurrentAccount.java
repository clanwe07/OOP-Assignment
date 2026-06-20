// subclass of Account
public class CurrentAccount extends Account {
    
    // data members
    private double overdraftLimit = 1000.0; 
    private Overdraft overdraft; 
    private Fee fee;
    
    // constructors
    public CurrentAccount() {
        super();
        this.overdraft = new Overdraft(); 
    }

    public CurrentAccount(String accId, double balance, String status, double overdraftLimit) {
        super(accId, balance, status);
        this.overdraftLimit = overdraftLimit;
        this.overdraft = new Overdraft(); 
    }

    // get methods
    public double getOverdraftLimit() { return overdraftLimit; }
    public Overdraft getOverdraft() { return overdraft; }
    public Fee getFee() { return fee; }

    // set methods
    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }

    // methods 
    public boolean withdraw(double amount) {
        if (amount <= (balance + overdraftLimit)) {
            balance -= amount; 
            return true; 
        }
        return false;
    }

    public void chargeFee(String type, double amount) {
        this.fee = new Fee(type, amount);
        this.withdraw(amount);
    }

    // toString method
    public String toString() {
        String output = super.toString() + 
                        String.format("\nOverdraft Limit: RM%.2f", overdraftLimit);
        
        if (fee != null) {
            output += "\n----------------------" +
            	      "\nRecent Charge: " + fee.toString();
        }
        
        return output;
    }
}
