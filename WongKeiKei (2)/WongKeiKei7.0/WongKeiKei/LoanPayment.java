public class LoanPayment {
    private double amount;
    
    //constructor
    public LoanPayment(double amount) {
        this.amount = amount;
    }

    // get method
    public double getAmount() {
        return amount;
    }

    //set method
    public void setAmount(double amount) {
        if (amount >= 0) {
            this.amount = amount;
        } else {
            System.out.println("Error: Payment amount cannot be negative.");
        }
    }

    //method overriding
    @Override
    public String toString() {
        return "LoanPayment{" +
                "amount=" + amount +
                '}';
    }
}