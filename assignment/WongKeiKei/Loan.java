public class Loan {
    protected String loanID;
    protected double amount;
    protected double interestRate;
    protected int duration;
    protected String status;
    protected Customer customer;

    protected LoanPayment[] payments = new LoanPayment[100];
    protected int paymentCount = 0;

    public Loan(String loanID, double amount, double interestRate,
                int duration, Customer customer) {
        this.loanID = loanID;
        this.amount = amount;
        this.interestRate = interestRate;
        this.duration = duration;
        this.customer = customer;
        this.status = "PENDING";
    }

    //get method
    public String getLoanID() { 
    	return loanID;
    }
    public double getAmount() { 
    	return amount;
    }
    public double getInterestRate() {
    	 return interestRate;
    }
    public int getDuration() { 
    	return duration;
    }
    public String getStatus() { 
    	return status; 
    }
    public Customer getCustomer() { 
    	return customer; 
    }

    //set method
    public void setAmount(double amount) {
    	 this.amount = amount; 
    }
    public void setInterestRate(double interestRate) { 
    	this.interestRate = interestRate;
    }
    public void setDuration(int duration) {
    	 this.duration = duration;
    }
    public void setStatus(String status) {
    	 this.status = status; 
    }

    //logic 
    public void approveLoan() {
        status = "APPROVED";
    }
    
    public void rejectLoan() {
    this.status = "REJECTED"; 
    }

    public double calculateInterest() {
        return amount * interestRate * duration / 12;
    }

    public double calculateMonthlyInstallment() {
        return (amount + calculateInterest()) / duration;
    }

    public void generateRepaymentSchedule() {
        double monthly = calculateMonthlyInstallment();
        for (int i = 1; i <= duration; i++) {
            System.out.println("Month " + i + ": RM" + monthly);
        }
    }

    public void repayLoan(double paymentAmount) {
        payments[paymentCount++] = new LoanPayment(paymentAmount);
    }

    public double getRemainingBalance() {
        double paid = 0;
        for (int i = 0; i < paymentCount; i++) {
            paid += payments[i].getAmount();
        }
        return (amount + calculateInterest()) - paid;
    }

    public void processAutoDebit() {
        double monthly = calculateMonthlyInstallment();
        repayLoan(monthly);
        System.out.println("Auto debit: RM" + monthly);
    }

    public void applyLatePaymentPenalty(int lateDays) {
        int grace = 7;
        if (lateDays > grace) {
            double penalty = getRemainingBalance() * 0.01;
            amount += penalty;
            System.out.println("Penalty: RM" + penalty);
        }
    }

    public void processEarlyRepayment(double amt) {
        double balance = getRemainingBalance();
        if (amt >= balance) {
            fullSettlement();
        } else {
            repayLoan(amt);
        }
    }

    public void fullSettlement() {
        double balance = getRemainingBalance();
        repayLoan(balance);
        status = "SETTLED";
    }

    //to string method
    @Override
    public String toString() {
        return "Loan ID: " + loanID +
               "\nCustomer: " + customer.getName() +
               "\nAmount: RM" + String.format("%.2f", amount) +
               "\nInterest Rate: " + String.format("%.2f%%", interestRate * 100) +
               "\nMonthly: RM" + String.format("%.2f", calculateMonthlyInstallment()) +
               "\nBalance: RM" + String.format("%.2f", getRemainingBalance()) +
               "\nStatus: " + status;
    }
}