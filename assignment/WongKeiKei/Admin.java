import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Admin extends User {

    private String adminLevel;
    private static double penaltyRate = 0.05;

    // Shared loan list (IMPORTANT)
    private static List<Loan> loanApplications = new ArrayList<>();

    public Admin(String customerId, String phoneNum, String email, String adminLevel) {
        super(customerId, phoneNum, email);
        this.adminLevel = adminLevel;
    }

    // ================= ADD LOAN =================
    public void addLoanApplication(Loan loan) {
        loanApplications.add(loan);
    }

    // ================= VIEW PENDING =================
    public void viewPendingLoans() {
        boolean found = false;

        for (Loan loan : loanApplications) {
            if (loan.getStatus().equalsIgnoreCase("PENDING")) {
                System.out.println(loan);
                System.out.println("-------------------------");
                found = true;
            }
        }

        if (!found) {
            System.out.println("No pending loan applications.");
        }
    }

    // ================= VIEW ALL =================
    public void viewAllLoanStatus() {

        if (loanApplications.isEmpty()) {
            System.out.println("No loan applications found.");
            return;
        }

        for (Loan loan : loanApplications) {
            System.out.println("Loan ID: " + loan.getLoanID());
            System.out.println("Customer: " + loan.getCustomer().getName());
            System.out.println("Status: " + loan.getStatus());
            System.out.println("-------------------------");
        }
    }

    // ================= CHOOSE LOAN =================
    public Loan chooseLoan() {

        if (loanApplications.isEmpty()) {
            System.out.println("No loans available.");
            return null;
        }

        Scanner input = new Scanner(System.in);

        for (int i = 0; i < loanApplications.size(); i++) {
            Loan l = loanApplications.get(i);
            System.out.println((i + 1) + ". " + l.getLoanID() + " (" + l.getStatus() + ")");
        }

        System.out.print("Select loan number: ");

        try {
            int choice = Integer.parseInt(input.nextLine());
            return loanApplications.get(choice - 1);
        } catch (Exception e) {
            System.out.println("Invalid selection.");
            return null;
        }
    }

    // ================= APPROVE =================
    public void approveLoan() {

        Loan loan = chooseLoan();

        if (loan == null) return;

        loan.approveLoan();
        System.out.println("Loan APPROVED: " + loan.getLoanID());
    }

    // ================= REJECT =================
    public void rejectLoan() {

        Loan loan = chooseLoan();

        if (loan == null) return;

        loan.rejectLoan();
        System.out.println("Loan REJECTED: " + loan.getLoanID());
    }

    // ================= PENALTY =================
    public void setPenaltyRate(double newRate) {
        if (newRate >= 0) {
            penaltyRate = newRate;
            System.out.println("Penalty rate updated to: " + (penaltyRate * 100) + "%");
        } else {
            System.out.println("Invalid penalty rate.");
        }
    }

    public static double getPenaltyRate() {
        return penaltyRate;
    }

    // ================= GET LIST =================
    public static List<Loan> getLoanApplications() {
        return loanApplications;
    }

    // ================= TO STRING =================
    @Override
    public String toString() {
        return "Admin ID: " + customerId +
               "\nLevel: " + adminLevel +
               "\nPenalty Rate: " + (penaltyRate * 100) + "%";
    }
}