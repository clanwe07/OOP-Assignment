import java.util.ArrayList;

public class Customer extends User {
    private String name;
    private String myKad;
    private String nationality;
    private int age;
    private boolean hasLicense;
    private double monthlyIncome;

    // Store multiple loans instead of only one loan
    private ArrayList<Loan> myLoans = new ArrayList<>();

    private static int idCounter = 1000;

    public Customer(String name, String myKad, String nationality,
                    int age, boolean hasLicense, double monthlyIncome,
                    String phoneNum, String email) {

        super("CUST-" + (++idCounter), phoneNum, email);

        this.name = name;
        this.myKad = myKad;
        this.nationality = nationality;
        this.age = age;
        this.hasLicense = hasLicense;
        this.monthlyIncome = monthlyIncome;
    }

    public Customer(String name, String myKad, String nationality,
                    int age, boolean hasLicense, double monthlyIncome) {

        super("CUST-" + (++idCounter), "N/A", "N/A");

        this.name = name;
        this.myKad = myKad;
        this.nationality = nationality;
        this.age = age;
        this.hasLicense = hasLicense;
        this.monthlyIncome = monthlyIncome;
    }

    // ================= GET METHODS =================

    public String getName() {
        return name;
    }

    public String getMyKad() {
        return myKad;
    }

    public String getNationality() {
        return nationality;
    }

    public int getAge() {
        return age;
    }

    public boolean hasLicense() {
        return hasLicense;
    }

    public boolean isHasLicense() {
        return hasLicense;
    }

    public double getMonthlyIncome() {
        return monthlyIncome;
    }

    public ArrayList<Loan> getMyLoans() {
        return myLoans;
    }

    // Return latest loan only
    public Loan getMyLoan() {
        if (myLoans.isEmpty()) {
            return null;
        }
        return myLoans.get(myLoans.size() - 1);
    }

    // Add loan without replacing old loans
    public void setMyLoan(Loan myLoan) {
        if (myLoan != null && !myLoans.contains(myLoan)) {
            myLoans.add(myLoan);
        }
    }

    // ================= ELIGIBILITY METHODS =================

    public boolean isAdult() {
        return age >= 18;
    }

    public boolean isMalaysian() {
        if (nationality == null) {
            return false;
        }

        String n = nationality.trim().toLowerCase();

        return n.equals("malaysian") || n.equals("malaysia");
    }

    // Fixed: MyKad must not be empty, not N/A, and must be numbers only
    public boolean hasValidMyKad() {
        if (myKad == null || myKad.trim().isEmpty() || myKad.equalsIgnoreCase("N/A")) {
            return false;
        }

        String id = myKad.trim();

        for (int i = 0; i < id.length(); i++) {
            if (!Character.isDigit(id.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public boolean meetsMinimumIncome(double minIncome) {
        return monthlyIncome >= minIncome;
    }

    // ================= LOAN METHODS =================

    public void submitLoanApplication(Loan loan) {
        if (loan != null) {
            myLoans.add(loan);
            System.out.println("Loan application submitted successfully.");
        }
    }

    public void viewMyLoanDetails() {
        if (myLoans.isEmpty()) {
            System.out.println("No loan application found.");
            return;
        }

        for (int i = 0; i < myLoans.size(); i++) {
            System.out.println("\nLoan #" + (i + 1));
            System.out.println(myLoans.get(i));
            System.out.println("-------------------------");
        }
    }

    public void viewRepaymentSchedule() {
        Loan latestLoan = getMyLoan();

        if (latestLoan == null) {
            System.out.println("No loan found.");
        } else {
            latestLoan.generateRepaymentSchedule();
        }
    }

    public void makeRepayment(double amount) {
        Loan latestLoan = getMyLoan();

        if (latestLoan == null) {
            System.out.println("No loan found.");
        } else {
            System.out.println("Repayment of RM" + amount + " submitted.");
            latestLoan.repayLoan(amount);
        }
    }

    public void earlySettlement() {
        Loan latestLoan = getMyLoan();

        if (latestLoan == null) {
            System.out.println("No loan found.");
        } else {
            System.out.println("Early settlement request submitted.");
        }
    }

    public void receiveLoanDecision() {
        if (myLoans.isEmpty()) {
            System.out.println("No loan application found.");
            return;
        }

        for (int i = 0; i < myLoans.size(); i++) {
            Loan loan = myLoans.get(i);

            System.out.println("Loan #" + (i + 1));
            System.out.println("Loan ID: " + loan.getLoanID());
            System.out.println("Loan Status: " + loan.getStatus());
            System.out.println("-------------------------");
        }
    }

    // ================= TO STRING =================

    @Override
    public String toString() {
        return "--- Customer Profile ---" +
                "\nSystem ID: " + customerId +
                "\nName: " + name +
                "\nIC/MyKad: " + myKad +
                "\nNationality: " + nationality +
                "\nAge: " + age +
                "\nIncome: RM" + String.format("%.2f", monthlyIncome) +
                "\nPhone: " + phoneNum +
                "\nEmail: " + email;
    }
}