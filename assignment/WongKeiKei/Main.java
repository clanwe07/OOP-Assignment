import java.util.*;

public class Main {

    // ================= LOGIN SYSTEM =================
    static Map<String, String> users = new HashMap<>();
    static final String ADMIN_PASSWORD = "admin123";
    static final String ADMIN_USERNAME = "admin";

    static boolean loggedIn = false;
    static boolean isAdmin = false;
    static String currentUser = "";

    // ================= NEW OBJECTS =================
    static Customer currentCustomer = null;
    static Admin systemAdmin = new Admin("ADMIN-001", "012", "admin@mail.com", "SUPER");

    // ================= CREDIT CARD DATA =================
    static CreditCard[] cards = new CreditCard[10];
    static AutoDebit[] debits = new AutoDebit[10];
    static int cardCount = 0;
    static int debitCount = 0;

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        users.put("ahmad", "1234");

        while (true) {
            if (!loggedIn) authMenu(input);
            else mainSystem(input);
        }
    }

    // ================= AUTH =================
    public static void authMenu(Scanner input) {
        System.out.println("\n1. Register\n2. Login\n3. Exit");
        int choice = readInt(input);

        switch (choice) {
            case 1 -> register(input);
            case 2 -> loginMenu(input);
            case 3 -> System.exit(0);
        }
    }

    public static void loginMenu(Scanner input) {
        System.out.println("1. User\n2. Admin");
        int choice = readInt(input);

        if (choice == 1) loginUser(input);
        else if (choice == 2) loginAdmin(input);
    }

    public static void register(Scanner input) {
        System.out.print("Username: ");
        String u = input.nextLine();
        System.out.print("Password: ");
        String p = input.nextLine();
        users.put(u, p);
    }

    public static void loginUser(Scanner input) {
        System.out.print("Username: ");
        String u = input.nextLine();
        System.out.print("Password: ");
        String p = input.nextLine();

        if (users.containsKey(u) && users.get(u).equals(p)) {
            loggedIn = true;
            isAdmin = false;
            currentUser = u;

            currentCustomer = new Customer(u, "N/A", "Malaysian", 18, false, 0);

            System.out.println("User login success!");
        }
    }

    public static void loginAdmin(Scanner input) {
        System.out.print("Admin username: ");
        String u = input.nextLine();
        System.out.print("Password: ");
        String p = input.nextLine();

        if (u.equals("admin") && p.equals("admin123")) {
            loggedIn = true;
            isAdmin = true;
            currentUser = "ADMIN";
        }
    }

    // ================= MAIN SYSTEM =================
    public static void mainSystem(Scanner input) {

        while (loggedIn) {

            if (isAdmin) {

                System.out.println("\n--- ADMIN MENU ---");
                System.out.println("1. View Pending Loans");
                System.out.println("2. Approve Loan");
                System.out.println("3. Reject Loan");
                System.out.println("4. View All Loans");
                System.out.println("5. Set Penalty");
                System.out.println("6. Run Transaction Batch");
                System.out.println("7. Logout");

                int c = readInt(input);

                switch (c) {
                    case 1 -> systemAdmin.viewPendingLoans();
                    
                    
                    case 2 -> systemAdmin.approveLoan();

					case 3 -> systemAdmin.rejectLoan();


                    case 4 -> systemAdmin.viewAllLoanStatus();

                    case 5 -> {
                        System.out.print("New penalty rate: ");
                        double r = Double.parseDouble(input.nextLine());
                        systemAdmin.setPenaltyRate(r);
                    }

                    case 6 -> handleTransactionProcessing(
                            new SavingsAccount1("SAV-101", 500.0),
                            new CurrentAccount1("CUR-202", 100.0)
                    );

                    case 7 -> logout();
                }

            } else {

                System.out.println("\n--- USER MENU ---");
                System.out.println("1. Personal Banking");
                System.out.println("2. Credit Card");
                System.out.println("3. Apply Loan");
                System.out.println("4. View My Loan");
                System.out.println("5. View Repayment");
                System.out.println("6. Repay Loan");
                System.out.println("7. Early Settlement");
                System.out.println("8. Check Status");
                System.out.println("9. Logout");

                int c = readInt(input);

                switch (c) {
                    case 1 -> handlePersonalBanking(input,
                            new CurrentAccount("CA-Test01", 500.0, "Active", 1000.0));

                    case 2 -> creditCardMenu(input);

                    case 3 -> handleLoanApplication(input);

                    case 4 -> currentCustomer.viewMyLoanDetails();

                    case 5 -> currentCustomer.viewRepaymentSchedule();

                    case 6 -> {
                        System.out.print("Amount: ");
                        double amt = Double.parseDouble(input.nextLine());
                        currentCustomer.makeRepayment(amt);
                    }

                    case 7 -> currentCustomer.earlySettlement();

                    case 8 -> currentCustomer.receiveLoanDecision();

                    case 9 -> logout();
                }
            }
        }
    }

    // ================= LOAN APPLY =================
    public static void handleLoanApplication(Scanner input) {

        System.out.println("1. Car 2. Housing 3. Personal");
        int type = Integer.parseInt(input.nextLine());

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("IC: ");
        String id = input.nextLine();

        System.out.print("Nationality: ");
        String nat = input.nextLine();

        System.out.print("Age: ");
        int age = Integer.parseInt(input.nextLine());

        System.out.print("Income: ");
        double income = Double.parseDouble(input.nextLine());

        boolean license = false;
        if (type == 1) {
            System.out.print("Has License: ");
            license = Boolean.parseBoolean(input.nextLine());
        }

        Customer temp = new Customer(name, id, nat, age, license, income);

        Loan loan = null;

        if (type == 1) {
            System.out.print("Car model: ");
            String m = input.nextLine();
            System.out.print("Amount: ");
            double amt = Double.parseDouble(input.nextLine());
            loan = new CarLoan(amt, 0.035, 12, temp, m, amt + 5000, 5000);
        }

        if (type == 2) {
            System.out.print("Address: ");
            String a = input.nextLine();
            System.out.print("Amount: ");
            double amt = Double.parseDouble(input.nextLine());
            loan = new HousingLoan(amt, 0.04, 240, temp, a, amt + 100000);
        }

        if (type == 3) {
            System.out.print("Purpose: ");
            String p = input.nextLine();
            System.out.print("Amount: ");
            double amt = Double.parseDouble(input.nextLine());
            loan = new PersonalLoan(amt, 0.06, 24, temp, p, 200);
        }

        if (loan != null) {
            currentCustomer.submitLoanApplication(loan);
            systemAdmin.addLoanApplication(loan);

            System.out.println("Submitted. Waiting for admin approval.");
        }
    }

    // ================= HELPER =================
    public static Loan chooseLoan() {

        List<Loan> list = Admin.getLoanApplications();

        if (list.isEmpty()) {
            System.out.println("No loans.");
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getLoanID());
        }

        Scanner input = new Scanner(System.in);
        int c = Integer.parseInt(input.nextLine());

        return list.get(c - 1);
    }

    public static int readInt(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine());
        } catch (Exception e) {
            return -1;
        }
    }

    public static void logout() {
        loggedIn = false;
        isAdmin = false;
        currentUser = "";
        currentCustomer = null;
    }
}