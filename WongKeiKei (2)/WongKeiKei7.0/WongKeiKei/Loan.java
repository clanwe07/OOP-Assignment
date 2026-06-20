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

    // ================= GET METHODS =================
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

    // ================= SET METHODS =================
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

    // ================= LOAN STATUS =================
    public void approveLoan() {
        status = "APPROVED";
    }

    public void rejectLoan() {
        status = "REJECTED";
    }

    // ================= CALCULATION =================
    public double calculateInterest() {
        return amount * interestRate * duration / 12;
    }

    public double calculateMonthlyInstallment() {
        return (amount + calculateInterest()) / duration;
    }

    public void generateRepaymentSchedule() {
        double monthly = calculateMonthlyInstallment();

        for (int i = 1; i <= duration; i++) {
            System.out.println("Month " + i + ": RM" + String.format("%.2f", monthly));
        }
    }

    // ================= REPAYMENT =================
    public void repayLoan(double paymentAmount) {
        if (paymentAmount <= 0) {
            System.out.println("Invalid repayment amount.");
            return;
        }

        if (paymentCount >= payments.length) {
            System.out.println("Payment record is full. Cannot add more payments.");
            return;
        }

        double balance = getRemainingBalance();

        if (balance <= 0) {
            status = "SETTLED";
            System.out.println("Loan already settled.");
            return;
        }

        if (paymentAmount > balance) {
            paymentAmount = balance;
        }

        payments[paymentCount++] = new LoanPayment(paymentAmount);

        if (getRemainingBalance() <= 0) {
            status = "SETTLED";
        }
    }

    public double getRemainingBalance() {
        double paid = 0;

        for (int i = 0; i < paymentCount; i++) {
            paid += payments[i].getAmount();
        }

        double balance = (amount + calculateInterest()) - paid;

        if (balance < 0) {
            return 0;
        }

        return balance;
    }

    public void processAutoDebit() {
        if (!status.equalsIgnoreCase("APPROVED")) {
            System.out.println("Auto debit failed. Loan is not approved.");
            return;
        }

        double monthly = calculateMonthlyInstallment();
        double balance = getRemainingBalance();

        if (balance <= 0) {
            status = "SETTLED";
            System.out.println("Loan already settled.");
            return;
        }

        if (monthly > balance) {
            monthly = balance;
        }

        repayLoan(monthly);
        System.out.println("Auto debit: RM" + String.format("%.2f", monthly));
    }

    public void applyLatePaymentPenalty(int lateDays) {
        int grace = 7;

        if (lateDays > grace) {
            double penalty = getRemainingBalance() * 0.01;
            amount += penalty;
            System.out.println("Penalty: RM" + String.format("%.2f", penalty));
        }
    }

    public void processEarlyRepayment(double amt) {
        if (amt <= 0) {
            System.out.println("Invalid early repayment amount.");
            return;
        }

        double balance = getRemainingBalance();

        if (balance <= 0) {
            status = "SETTLED";
            System.out.println("Loan already settled.");
            return;
        }

        if (amt >= balance) {
            fullSettlement();
        } else {
            repayLoan(amt);
        }
    }

    public void fullSettlement() {
        double balance = getRemainingBalance();

        if (balance <= 0) {
            status = "SETTLED";
            return;
        }

        repayLoan(balance);
        status = "SETTLED";
    }

    // ================= TO STRING =================
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