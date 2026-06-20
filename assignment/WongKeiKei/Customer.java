public class Customer extends User {
    private String name;
    private String myKad;
    private String nationality;
    private int age;
    private boolean hasLicense;
    private double monthlyIncome;

    private Loan myLoan;   // ??????????,???

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

    public Loan getMyLoan() {
        return myLoan;
    }

    public void setMyLoan(Loan myLoan) {
        this.myLoan = myLoan;
    }

    public boolean isAdult() {
        return age >= 18;
    }

    public boolean isMalaysian() {
        return nationality != null && nationality.equalsIgnoreCase("Malaysian");
    }

    public boolean hasValidMyKad() {
        return myKad != null && !myKad.trim().isEmpty() && !myKad.equalsIgnoreCase("N/A");
    }

    public boolean meetsMinimumIncome(double minIncome) {
        return monthlyIncome >= minIncome;
    }

    // ??????
    public void submitLoanApplication(Loan loan) {
        this.myLoan = loan;
        System.out.println("Loan application submitted successfully.");
    }

    // ?????????
    public void viewMyLoanDetails() {
        if (myLoan == null) {
            System.out.println("No loan application found.");
        } else {
            System.out.println(myLoan);
        }
    }

    // ???????
    public void viewRepaymentSchedule() {
        if (myLoan == null) {
            System.out.println("No loan found.");
        } else {
            myLoan.generateRepaymentSchedule();
        }
    }

    // ????
    public void makeRepayment(double amount) {
        if (myLoan == null) {
            System.out.println("No loan found.");
        } else {
            System.out.println("Repayment of RM" + amount + " submitted.");
        }
    }

    // ????
    public void earlySettlement() {
        if (myLoan == null) {
            System.out.println("No loan found.");
        } else {
            System.out.println("Early settlement request submitted.");
        }
    }

    // ??????
    public void receiveLoanDecision() {
        if (myLoan == null) {
            System.out.println("No loan application found.");
        } else {
            System.out.println("Loan Status: " + myLoan.getStatus());
        }
    }

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