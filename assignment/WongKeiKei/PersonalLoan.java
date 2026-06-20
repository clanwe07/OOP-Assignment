public class PersonalLoan extends Loan {
    // 1. ???????,PL ?????? 3000 ????
    private static int count = 3000; 
    
    private String purpose;
    private double processingFee;

    // 2. ??????:?? String loanID ??
    public PersonalLoan(double amount, double interestRate,
                        int duration, Customer customer,
                        String purpose, double processingFee) {

        // ???? ID,??: PL-3001, PL-3002
        super("PL-" + (++count), amount, interestRate, duration, customer);
        this.purpose = purpose;
        this.processingFee = processingFee;
    }

    // --- Getter & Setter Methods (????) ---
    public String getPurpose() { return purpose; }
    public double getProcessingFee() { return processingFee; }

    public void setPurpose(String purpose) { this.purpose = purpose; }
    public void setProcessingFee(double processingFee) {
        if (processingFee >= 0) {
            this.processingFee = processingFee;
        } else {
            System.out.println("Error: Processing fee cannot be negative.");
        }
    }

    // --- Business Logic ---

    public boolean checkEligibility() {
        return customer.isAdult()
            && customer.isMalaysian()
            && customer.hasValidMyKad()
            && customer.meetsMinimumIncome(1500);
    }

    @Override
    public double calculateInterest() {
        return super.calculateInterest() + processingFee;
    }

    // 3. ?? toString ?? Admin ????
    @Override
    public String toString() {
        return super.toString() +
               "\nLoan Purpose: " + purpose +
               String.format("\nProcessing Fee: RM %.2f", processingFee);
    }
}