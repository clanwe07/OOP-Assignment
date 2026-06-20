import java.util.*;

public class Main {

    // ================= LOGIN =================
    static Map<String, String> users = new HashMap<>();
    static boolean loggedIn = false;
    static boolean isAdmin = false;

    static Customer currentCustomer = null;
    static String currentUsername = "";
    static Map<String, Customer> customerProfiles = new HashMap<>();

    static Admin systemAdmin = new Admin("ADMIN-001", "012", "admin@mail.com", "SUPER");

    // ================= CREDIT CARD =================
    static CreditCard[] cards = new CreditCard[20];
    static AutoDebit[] debits = new AutoDebit[20];
    static int cardCount = 0;
    static int debitCount = 0;

    // ================= EARLY SETTLEMENT =================
    static List<Loan> earlySettlementRequests = new ArrayList<>();

    // ================= BANK ACCOUNTS =================
    static CurrentAccount currentAcc =
            new CurrentAccount("CA-Test01", 500.0, "Active", 1000.0);

    static SavingAccount savingAcc =
            new SavingAccount("SA-Test01", 1000.0, "Active", 0.015);

    // ================= BANNER =================
    public static void showBanner() {
        System.out.println("====================================================");
        System.out.println("              JUPITER BANK SYSTEM");
        System.out.println("====================================================");
        System.out.println("        Secure   Reliable   Smart Banking");
        System.out.println("====================================================");
    }

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        users.put("ahmad", "1234");

        showBanner();

        while (true) {
            if (!loggedIn) {
                authMenu(input);
            } else {
                mainSystem(input);
            }
        }
    }

    // ================= AUTH =================
    public static void authMenu(Scanner input) {

        System.out.println("\n================ LOGIN PORTAL ================");
        System.out.println("1. Register");
        System.out.println("2. Login");
        System.out.println("3. Exit");
        System.out.println("==============================================");
        System.out.print("Choice: ");

        int c = readInt(input);

        switch (c) {
            case 1 -> register(input);
            case 2 -> loginMenu(input);
            case 3 -> {
                System.out.println("Exiting system...");
                System.exit(0);
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public static void register(Scanner input) {
        System.out.print("Username: ");
        String u = input.nextLine();

        System.out.print("Password: ");
        String p = input.nextLine();

        if (users.containsKey(u)) {
            System.out.println("User already exists.");
            return;
        }

        users.put(u, p);
        System.out.println("Registered successfully.");
    }

    public static void loginMenu(Scanner input) {
        System.out.println("\n1. User");
        System.out.println("2. Admin");
        System.out.print("Choice: ");

        int c = readInt(input);

        if (c == 1) {
            loginUser(input);
        } else if (c == 2) {
            loginAdmin(input);
        } else {
            System.out.println("Invalid choice.");
        }
    }

    public static void loginUser(Scanner input) {
        System.out.print("Username: ");
        String u = input.nextLine();

        System.out.print("Password: ");
        String p = input.nextLine();

        if (users.containsKey(u) && users.get(u).equals(p)) {
            loggedIn = true;
            isAdmin = false;
            currentUsername = u;

            if (customerProfiles.containsKey(u)) {
                currentCustomer = customerProfiles.get(u);
            } else {
                currentCustomer = new Customer(u, "N/A", "Malaysian", 18, false, 0);
                customerProfiles.put(u, currentCustomer);
            }

            System.out.println("User login success.");
        } else {
            System.out.println("Wrong username or password.");
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
            currentUsername = "ADMIN";
            System.out.println("Admin login success.");
        } else {
            System.out.println("Wrong admin login.");
        }
    }

    // ================= MAIN =================
    public static void mainSystem(Scanner input) {

        while (loggedIn) {

            if (isAdmin) {
                System.out.println("\n================ ADMIN MENU ================");
                System.out.println("1. View Pending Loans");
                System.out.println("2. Approve Loan");
                System.out.println("3. Reject Loan");
                System.out.println("4. View All Loans");
                System.out.println("5. Delete / Cancel Credit Card");
                System.out.println("6. View Early Settlement Requests");
                System.out.println("7. Approve Early Settlement");
                System.out.println("8. Logout");
                System.out.println("============================================");
                System.out.print("Choice: ");

                int c = readInt(input);

                switch (c) {
                    case 1 -> systemAdmin.viewPendingLoans();
                    case 2 -> systemAdmin.approveLoan();
                    case 3 -> systemAdmin.rejectLoan();
                    case 4 -> systemAdmin.viewAllLoanStatus();
                    case 5 -> deleteCard(input);
                    case 6 -> viewEarlySettlementRequests();
                    case 7 -> approveEarlySettlement(input);
                    case 8 -> logout();
                    default -> System.out.println("Invalid choice.");
                }

            } else {
                System.out.println("\n================ USER MENU ================");
                System.out.println("1. Personal Banking");
                System.out.println("2. Credit Card Services");
                System.out.println("3. Apply Loan");
                System.out.println("4. View My Loan / Status");
                System.out.println("5. View Repayment Schedule");
                System.out.println("6. Make Repayment");
                System.out.println("7. Request Early Settlement");
                System.out.println("8. Logout");
                System.out.println("===========================================");
                System.out.print("Choice: ");

                int c = readInt(input);

                switch (c) {
                    case 1 -> handlePersonalBanking(input);

                    case 2 -> creditCardMenu(input);

                    case 3 -> handleLoanApplication(input);

                    case 4 -> viewMyLoanAndStatus();

                    case 5 -> viewRepaymentScheduleMenu(input);

                    case 6 -> makeRepaymentMenu(input);

                    case 7 -> requestEarlySettlementMenu(input);

                    case 8 -> logout();

                    default -> System.out.println("Invalid choice.");
                }
            }
        }
    }

    // ================= LOAN =================
    public static void handleLoanApplication(Scanner input) {

        System.out.println("\n================ APPLY LOAN ================");
        System.out.println("1. Car Loan");
        System.out.println("2. Housing Loan");
        System.out.println("3. Personal Loan");
        System.out.println("============================================");
        System.out.print("Choice: ");

        int type = readInt(input);

        if (type < 1 || type > 3) {
            System.out.println("Invalid loan type.");
            return;
        }

        System.out.print("Name: ");
        String name = input.nextLine();

        System.out.print("IC/MyKad: ");
        String id = input.nextLine();

        System.out.print("Nationality: ");
        String nat = input.nextLine();

        System.out.print("Age: ");
        int age = readInt(input);

        System.out.print("Monthly Income: RM");
        double income = readDouble(input);

        boolean license = false;

        if (type == 1) {
            System.out.print("Has Driving License? (true/false): ");
            license = Boolean.parseBoolean(input.nextLine());
        }

        Customer applicant = new Customer(name, id, nat, age, license, income);

        if (!applicant.isMalaysian()) {
            System.out.println("\n[SYSTEM]: Loan application rejected.");
            System.out.println("[SYSTEM]: Only Malaysia/Malaysian customers are allowed to apply for loans.");
            return;
        }

        if (!applicant.hasValidMyKad()) {
            System.out.println("\n[SYSTEM]: Loan application rejected.");
            System.out.println("[SYSTEM]: Valid MyKad/IC is required.");
            return;
        }

        Loan loan = null;

        if (type == 1) {
            System.out.print("Car Model: ");
            String model = input.nextLine();

            System.out.print("Loan Amount: RM");
            double amount = readDouble(input);

            loan = new CarLoan(amount, 0.035, 12, applicant, model, amount + 5000, 5000);

        } else if (type == 2) {
            System.out.print("Property Address: ");
            String address = input.nextLine();

            System.out.print("Loan Amount: RM");
            double amount = readDouble(input);

            loan = new HousingLoan(amount, 0.04, 240, applicant, address, amount + 100000);

        } else if (type == 3) {
            System.out.print("Loan Purpose: ");
            String purpose = input.nextLine();

            System.out.print("Loan Amount: RM");
            double amount = readDouble(input);

            loan = new PersonalLoan(amount, 0.06, 24, applicant, purpose, 200);
        }

        if (loan != null) {
            currentCustomer.submitLoanApplication(loan);
            customerProfiles.put(currentUsername, currentCustomer);
            systemAdmin.addLoanApplication(loan);

            System.out.println("\n[SYSTEM]: Loan submitted successfully.");
            System.out.println("[SYSTEM]: Waiting for admin approval.");
        }
    }

    public static void viewMyLoanAndStatus() {
        ArrayList<Loan> loans = currentCustomer.getMyLoans();

        if (loans == null || loans.isEmpty()) {
            System.out.println("No loan application found.");
            return;
        }

        System.out.println("\n================ MY LOAN DETAILS ================");

        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);

            System.out.println("\nLoan #" + (i + 1));
            System.out.println(loan);
            System.out.println("------------------------------------------------");
            System.out.println("Loan Status: " + loan.getStatus());

            if (loan.getStatus().equalsIgnoreCase("PENDING")) {
                System.out.println("Message: Your loan is waiting for admin approval.");
            } else if (loan.getStatus().equalsIgnoreCase("APPROVED")) {
                System.out.println("Message: Your loan has been approved.");
            } else if (loan.getStatus().equalsIgnoreCase("REJECTED")) {
                System.out.println("Message: Your loan has been rejected.");
            } else if (loan.getStatus().equalsIgnoreCase("SETTLED")) {
                System.out.println("Message: Your loan has been fully settled.");
            }

            System.out.println("================================================");
        }
    }

    public static Loan selectMyLoan(Scanner input) {
        ArrayList<Loan> loans = currentCustomer.getMyLoans();

        if (loans == null || loans.isEmpty()) {
            System.out.println("No loan found.");
            return null;
        }

        if (loans.size() == 1) {
            return loans.get(0);
        }

        System.out.println("\nSelect Loan:");

        for (int i = 0; i < loans.size(); i++) {
            Loan loan = loans.get(i);
            System.out.println((i + 1) + ". " + loan.getLoanID()
                    + " | " + loan.getCustomer().getName()
                    + " | " + loan.getStatus()
                    + " | Balance RM" + String.format("%.2f", loan.getRemainingBalance()));
        }

        System.out.print("Choice: ");
        int index = readInt(input) - 1;

        if (index < 0 || index >= loans.size()) {
            System.out.println("Invalid loan selection.");
            return null;
        }

        return loans.get(index);
    }

    public static void viewRepaymentScheduleMenu(Scanner input) {
        Loan loan = selectMyLoan(input);

        if (loan == null) return;

        loan.generateRepaymentSchedule();
    }

    public static void makeRepaymentMenu(Scanner input) {
        Loan loan = selectMyLoan(input);

        if (loan == null) return;

        System.out.print("Enter repayment amount: RM");
        double amt = readDouble(input);

        loan.repayLoan(amt);
        System.out.println("Repayment of RM" + String.format("%.2f", amt) + " submitted.");
        System.out.println("Remaining Balance: RM" + String.format("%.2f", loan.getRemainingBalance()));
    }

    // ================= EARLY SETTLEMENT =================
    public static void requestEarlySettlementMenu(Scanner input) {

        Loan loan = selectMyLoan(input);

        if (loan == null) return;

        if (!loan.getStatus().equalsIgnoreCase("APPROVED")) {
            System.out.println("Early settlement can only be requested for APPROVED loans.");
            System.out.println("Current loan status: " + loan.getStatus());
            return;
        }

        if (earlySettlementRequests.contains(loan)) {
            System.out.println("Early settlement request already submitted.");
            return;
        }

        earlySettlementRequests.add(loan);

        System.out.println("\n[SYSTEM]: Early settlement request submitted.");
        System.out.println("[SYSTEM]: Waiting for admin approval.");
    }

    public static void requestEarlySettlement() {
        requestEarlySettlementMenu(new Scanner(System.in));
    }

    public static void viewEarlySettlementRequests() {

        if (earlySettlementRequests.isEmpty()) {
            System.out.println("No early settlement requests.");
            return;
        }

        System.out.println("\n================ EARLY SETTLEMENT REQUESTS ================");

        for (int i = 0; i < earlySettlementRequests.size(); i++) {
            Loan loan = earlySettlementRequests.get(i);

            System.out.println((i + 1) + ". Loan ID: " + loan.getLoanID());
            System.out.println("   Customer: " + loan.getCustomer().getName());
            System.out.println("   Balance: RM" + String.format("%.2f", loan.getRemainingBalance()));
            System.out.println("   Status: " + loan.getStatus());
            System.out.println("----------------------------------------------------------");
        }
    }

    public static void approveEarlySettlement(Scanner input) {

        if (earlySettlementRequests.isEmpty()) {
            System.out.println("No early settlement requests to approve.");
            return;
        }

        viewEarlySettlementRequests();

        System.out.print("Select request to approve: ");
        int index = readInt(input) - 1;

        if (index < 0 || index >= earlySettlementRequests.size()) {
            System.out.println("Invalid selection.");
            return;
        }

        Loan loan = earlySettlementRequests.get(index);

        loan.fullSettlement();

        earlySettlementRequests.remove(index);

        System.out.println("\n[SYSTEM]: Early settlement approved.");
        System.out.println("[SYSTEM]: Loan " + loan.getLoanID() + " is now SETTLED.");
    }

    // ================= BANK =================
    public static void handlePersonalBanking(Scanner input) {

        boolean back = false;

        while (!back) {
            System.out.println("\n================ PERSONAL BANKING ================");
            System.out.println("1. Current Account");
            System.out.println("2. Saving Account");
            System.out.println("3. Transfer Money");
            System.out.println("0. Back");
            System.out.println("==================================================");
            System.out.print("Choice: ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> currentAccountMenu(input);
                case 2 -> savingAccountMenu(input);
                case 3 -> transferMoneyMenu(input);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void currentAccountMenu(Scanner input) {

        boolean back = false;

        while (!back) {
            System.out.println("\n================ CURRENT ACCOUNT ================");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money (Overdraft allowed)");
            System.out.println("3. View Account Summary & Fees");
            System.out.println("4. Charge Maintenance Fee");
            System.out.println("0. Back");
            System.out.println("=================================================");
            System.out.print("Choice: ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: RM");
                    double amount = readDouble(input);

                    currentAcc.deposit(amount);
                    System.out.println("Deposit successful.");
                    System.out.println("Balance: RM" + String.format("%.2f", currentAcc.getBalance()));
                }

                case 2 -> {
                    System.out.print("Enter amount to withdraw: RM");
                    double amount = readDouble(input);

                    if (currentAcc.withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                        System.out.println("Balance: RM" + String.format("%.2f", currentAcc.getBalance()));
                    } else {
                        System.out.println("Failed: Exceeds overdraft limit.");
                    }
                }

                case 3 -> System.out.println(currentAcc);

                case 4 -> {
                    currentAcc.chargeFee("Maintenance Fee", 5.0);
                    System.out.println("Maintenance fee charged: RM5.00");
                    System.out.println(currentAcc);
                }

                case 0 -> back = true;

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void savingAccountMenu(Scanner input) {

        boolean back = false;

        while (!back) {
            System.out.println("\n================ SAVING ACCOUNT ================");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. View Account Summary");
            System.out.println("4. View Annual Interest");
            System.out.println("0. Back");
            System.out.println("================================================");
            System.out.print("Choice: ");

            int choice = readInt(input);

            switch (choice) {
                case 1 -> {
                    System.out.print("Enter amount to deposit: RM");
                    double amount = readDouble(input);

                    savingAcc.deposit(amount);
                    System.out.println("Deposit successful.");
                    System.out.println("Balance: RM" + String.format("%.2f", savingAcc.getBalance()));
                }

                case 2 -> {
                    System.out.print("Enter amount to withdraw: RM");
                    double amount = readDouble(input);

                    if (savingAcc.withdraw(amount)) {
                        System.out.println("Withdrawal successful.");
                        System.out.println("Balance: RM" + String.format("%.2f", savingAcc.getBalance()));
                    } else {
                        System.out.println("Failed: Insufficient balance.");
                    }
                }

                case 3 -> System.out.println(savingAcc);

                case 4 -> {
                    System.out.println("Annual Interest: RM" +
                            String.format("%.2f", savingAcc.calculateAnnualInterest()));
                }

                case 0 -> back = true;

                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void transferMoneyMenu(Scanner input) {

        final double TRANSFER_FEE = 2.0;

        System.out.println("\n================ TRANSFER MONEY ================");
        System.out.println("1. Saving Account -> Current Account");
        System.out.println("2. Current Account -> Saving Account");
        System.out.println("0. Back");
        System.out.println("================================================");
        System.out.print("Choice: ");

        int choice = readInt(input);

        if (choice == 0) {
            return;
        }

        if (choice != 1 && choice != 2) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Enter transfer amount: RM");
        double amount = readDouble(input);

        if (amount <= 0) {
            System.out.println("Invalid transfer amount.");
            return;
        }

        double totalAmount = amount + TRANSFER_FEE;

        if (choice == 1) {
            if (savingAcc.withdraw(totalAmount)) {
                currentAcc.deposit(amount);

                System.out.println("Transfer successful.");
                System.out.println("Transferred RM" + String.format("%.2f", amount));
                System.out.println("Transfer Fee RM" + String.format("%.2f", TRANSFER_FEE));
                System.out.println("Saving Balance: RM" + String.format("%.2f", savingAcc.getBalance()));
                System.out.println("Current Balance: RM" + String.format("%.2f", currentAcc.getBalance()));
            } else {
                System.out.println("Transfer failed: Insufficient saving account balance.");
                System.out.println("Required amount including fee: RM" + String.format("%.2f", totalAmount));
            }

        } else {
            if (currentAcc.withdraw(totalAmount)) {
                savingAcc.deposit(amount);

                System.out.println("Transfer successful.");
                System.out.println("Transferred RM" + String.format("%.2f", amount));
                System.out.println("Transfer Fee RM" + String.format("%.2f", TRANSFER_FEE));
                System.out.println("Current Balance: RM" + String.format("%.2f", currentAcc.getBalance()));
                System.out.println("Saving Balance: RM" + String.format("%.2f", savingAcc.getBalance()));
            } else {
                System.out.println("Transfer failed: Exceeds current account overdraft limit.");
                System.out.println("Required amount including fee: RM" + String.format("%.2f", totalAmount));
            }
        }
    }

    // ================= CREDIT CARD MAIN MENU =================
    public static void creditCardMenu(Scanner input) {

        boolean running = true;

        while (running) {
            System.out.println("\n================ CREDIT CARD SERVICES ================");
            System.out.println("1. Manage Credit Cards");
            System.out.println("2. View Transactions");
            System.out.println("3. Make Payment");
            System.out.println("4. View Statement");
            System.out.println("5. Auto Debit Settings");
            System.out.println("0. Back");
            System.out.println("======================================================");
            System.out.print("Choice: ");

            int c = readInt(input);

            switch (c) {
                case 1 -> manageCards(input);
                case 2 -> viewTransactions(input);
                case 3 -> makePayment(input);
                case 4 -> viewStatement(input);
                case 5 -> autoDebitMenu(input);
                case 0 -> running = false;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void manageCards(Scanner input) {

        boolean back = false;

        while (!back) {
            System.out.println("\n================ CREDIT CARD MANAGEMENT ================");
            System.out.println("1. Apply New Card");
            System.out.println("2. View My Cards");
            System.out.println("3. Update Card Settings");
            System.out.println("0. Back");
            System.out.println("========================================================");
            System.out.print("Choice: ");

            int c = readInt(input);

            switch (c) {
                case 1 -> applyNewCard(input);
                case 2 -> viewMyCards();
                case 3 -> updateCardSettings(input);
                case 0 -> back = true;
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    public static void applyNewCard(Scanner input) {

        if (cardCount >= cards.length) {
            System.out.println("Maximum cards reached.");
            return;
        }

        System.out.println("\n================ APPLY NEW CARD ================");
        System.out.println("1. Standard Card");
        System.out.println("2. Premium Card");
        System.out.println("================================================");
        System.out.print("Choice: ");

        int type = readInt(input);

        if (type != 1 && type != 2) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Full Name: ");
        String name = input.nextLine().trim().toUpperCase();

        System.out.print("Monthly Income: RM");
        double income = readDouble(input);

        double limit = (income >= 5000) ? 15000 : 5000;

        CreditCard card;

        if (type == 2) {
            card = new PremiumCard(name, limit);
        } else {
            card = new StandardCard(name, limit);
        }

        System.out.println("\nCard successfully created.");
        System.out.println(card);
        System.out.println("Status: PENDING ACTIVATION");

        System.out.print("\nActivate card now? (yes/no): ");
        if (input.nextLine().trim().equalsIgnoreCase("yes")) {
            card.activate();
        }

        cards[cardCount] = card;
        debits[debitCount] = new AutoDebit(card.getCardId(), "Jupiter Savings **7823", 3, "MINIMUM");

        cardCount++;
        debitCount++;
    }

    public static void viewMyCards() {

        System.out.println("\n================ MY CARDS ================");

        if (cardCount == 0) {
            System.out.println("No cards found. Please apply for a card first.");
            return;
        }

        selectionSort(cards, cardCount);

        for (int i = 0; i < cardCount; i++) {
            if (cards[i] instanceof PremiumCard) {
                System.out.println("\n****** Premium Card ******");
            } else {
                System.out.println("\n****** Standard Card ******");
            }

            System.out.println(cards[i]);
            System.out.println("Total Charges    : RM" + String.format("%.2f", cards[i].calculateTotalCharges()));
            System.out.println("------------------------------------------");
        }
    }

    public static void updateCardSettings(Scanner input) {

        CreditCard card = selectCard(input);

        if (card == null) return;

        System.out.println("\n================ UPDATE CARD SETTINGS ================");
        System.out.println("1. Block Card");
        System.out.println("2. Unblock / Activate Card");
        System.out.println("3. Freeze Card");
        System.out.println("0. Back");
        System.out.println("======================================================");
        System.out.print("Choice: ");

        int c = readInt(input);

        switch (c) {
            case 1 -> card.blockCard();
            case 2 -> card.activate();
            case 3 -> card.freezeCard();
            case 0 -> { }
            default -> System.out.println("Invalid choice.");
        }
    }

    public static void deleteCard(Scanner input) {

        if (cardCount == 0) {
            System.out.println("No cards available to delete.");
            return;
        }

        System.out.println("\n================ DELETE / CANCEL CREDIT CARD ================");

        for (int i = 0; i < cardCount; i++) {
            String type = (cards[i] instanceof PremiumCard) ? "Premium" : "Standard";
            System.out.println((i + 1) + ". " + cards[i].getMaskedNumber() + " (" + type + ")");
        }

        System.out.print("Select card to delete: ");
        int index = readInt(input) - 1;

        if (index < 0 || index >= cardCount) {
            System.out.println("Invalid card selection.");
            return;
        }

        System.out.print("Confirm delete this card? (yes/no): ");
        String confirm = input.nextLine();

        if (!confirm.equalsIgnoreCase("yes")) {
            System.out.println("Delete cancelled.");
            return;
        }

        String deletedCardId = cards[index].getCardId();

        for (int i = index; i < cardCount - 1; i++) {
            cards[i] = cards[i + 1];
        }

        cards[cardCount - 1] = null;
        cardCount--;

        for (int i = 0; i < debitCount; i++) {
            if (debits[i] != null && debits[i].getLinkedCardId().equals(deletedCardId)) {

                for (int j = i; j < debitCount - 1; j++) {
                    debits[j] = debits[j + 1];
                }

                debits[debitCount - 1] = null;
                debitCount--;
                break;
            }
        }

        System.out.println("Credit card deleted successfully by admin.");
    }

    public static void viewTransactions(Scanner input) {

        CreditCard card = selectCard(input);

        if (card == null) return;

        System.out.println("\n================ TRANSACTIONS " + card.getMaskedNumber() + " ================");

        if (card.getTxnCount() == 0) {
            System.out.println("No transactions yet. Loading sample data...");
            addSampleTransactions(card);
        }

        CardTransaction[] txns = card.getTransactions();
        int count = card.getTxnCount();

        double purchases = 0;
        double refunds = 0;

        for (int i = 0; i < count; i++) {
            System.out.println("\n[" + (i + 1) + "]");
            System.out.println(txns[i]);

            if (txns[i].getAmount() < 0) {
                refunds += Math.abs(txns[i].getAmount());
            } else {
                purchases += txns[i].getAmount();
            }
        }

        System.out.println("\n------------------------------------------");
        System.out.println("Total Purchases : RM" + String.format("%.2f", purchases));
        System.out.println("Total Refunds   : RM" + String.format("%.2f", refunds));
        System.out.println("Net Spending    : RM" + String.format("%.2f", purchases - refunds));
        System.out.println("------------------------------------------");
    }

    public static void makePayment(Scanner input) {

        CreditCard card = selectCard(input);

        if (card == null) return;

        System.out.println("\n================ MAKE PAYMENT " + card.getMaskedNumber() + " ================");
        System.out.println("Current Balance: RM" + String.format("%.2f", card.getCurrentBalance()));
        System.out.println("Minimum Payment: RM" + String.format("%.2f", card.calculateMinimumPayment()));

        if (card.getCurrentBalance() <= 0) {
            System.out.println("No outstanding balance. Nothing to pay.");
            return;
        }

        System.out.println("\n1. Pay Minimum");
        System.out.println("2. Pay Full");
        System.out.println("3. Pay Custom Amount");
        System.out.println("0. Back");
        System.out.print("Choice: ");

        int choice = readInt(input);
        double amount = 0;

        if (choice == 1) {
            amount = card.calculateMinimumPayment();
        } else if (choice == 2) {
            amount = card.getCurrentBalance();
        } else if (choice == 3) {
            System.out.print("Amount: RM");
            amount = readDouble(input);
        } else if (choice == 0) {
            return;
        } else {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.print("Confirm payment of RM" + String.format("%.2f", amount) + "? (yes/no): ");

        if (!input.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        Payment payment = new Payment(amount, "ONLINE BANKING", card.getCardId(), "Jupiter Savings **7823");
        payment.processPayment(card);
    }

    public static void viewStatement(Scanner input) {

        CreditCard card = selectCard(input);

        if (card == null) return;

        System.out.println("\n================ MONTHLY STATEMENT " + card.getMaskedNumber() + " ================");
        System.out.println("1. This month (April 2026)");
        System.out.println("2. Last month (March 2026)");
        System.out.println("0. Back");
        System.out.print("Choice: ");

        int choice = readInt(input);

        if (choice == 0) return;

        String period;
        String dueDate;

        if (choice == 2) {
            period = "March 2026";
            dueDate = "05 Apr 2026";
        } else {
            period = "April 2026";
            dueDate = "05 May 2026";
        }

        CardTransaction[] allTxns = card.getTransactions();
        int allCount = card.getTxnCount();

        CardTransaction[] filtered = new CardTransaction[allCount];
        int filteredCount = 0;

        for (int i = 0; i < allCount; i++) {
            if (allTxns[i].getMonth().equals(period)) {
                filtered[filteredCount++] = allTxns[i];
            }
        }

        Statement stmt = new Statement(
                period,
                dueDate,
                card.getCurrentBalance(),
                filtered,
                filteredCount,
                card.getInterestRate()
        );

        stmt.generateStatement(card);
    }

    public static void autoDebitMenu(Scanner input) {

        CreditCard card = selectCard(input);

        if (card == null) return;

        AutoDebit ad = getAutoDebit(card.getCardId());

        if (ad == null) {
            System.out.println("No Auto Debit setup found.");
            return;
        }

        System.out.println("\n================ AUTO DEBIT SETTINGS " + card.getMaskedNumber() + " ================");
        ad.displaySettings();

        System.out.println("\n1. Enable Auto Debit");
        System.out.println("2. Disable Auto Debit");
        System.out.println("3. Change to Full Payment");
        System.out.println("4. Change to Minimum Payment");
        System.out.println("5. Execute Auto Debit Now");
        System.out.println("0. Back");
        System.out.print("Choice: ");

        int c = readInt(input);

        switch (c) {
            case 1 -> ad.enable();
            case 2 -> ad.disable();

            case 3 -> {
                ad.setAmountType("FULL");
                System.out.println("Auto Debit changed to FULL PAYMENT.");
            }

            case 4 -> {
                ad.setAmountType("MINIMUM");
                System.out.println("Auto Debit changed to MINIMUM PAYMENT.");
            }

            case 5 -> {
                System.out.print("Confirm execute auto debit? (yes/no): ");
                if (input.nextLine().trim().equalsIgnoreCase("yes")) {
                    ad.executeAutoDebit(card);
                }
            }

            case 0 -> { }

            default -> System.out.println("Invalid choice.");
        }
    }

    public static CreditCard selectCard(Scanner input) {

        if (cardCount == 0) {
            System.out.println("No cards found. Please apply first.");
            return null;
        }

        if (cardCount == 1) {
            return cards[0];
        }

        System.out.println("\nSelect card:");

        for (int i = 0; i < cardCount; i++) {
            String type = (cards[i] instanceof PremiumCard) ? "Premium" : "Standard";
            System.out.println((i + 1) + ". " + cards[i].getMaskedNumber() + " (" + type + ")");
        }

        System.out.print("Choice: ");
        int idx = readInt(input) - 1;

        if (idx < 0 || idx >= cardCount) {
            System.out.println("Invalid selection.");
            return null;
        }

        return cards[idx];
    }

    public static AutoDebit getAutoDebit(String cardId) {

        for (int i = 0; i < debitCount; i++) {
            if (debits[i].getLinkedCardId().equals(cardId)) {
                return debits[i];
            }
        }

        return null;
    }

    public static void addSampleTransactions(CreditCard card) {

        CardTransaction[] txns = new CardTransaction[9];

        txns[0] = new CardTransaction(128.50, "Jaya Grocer, Penang", "Groceries", "Gurney Plaza, Penang", "PURCHASE", "April 2026");
        txns[1] = new CardTransaction(80.00, "Shell Petrol Station", "Fuel", "Jalan Perak, Penang", "PURCHASE", "April 2026");
        txns[2] = new CardTransaction(54.90, "Netflix", "Subscription", "Online", "AUTO_DEBIT", "April 2026");
        txns[3] = new CardTransaction(38.70, "McDonald's Gurney", "Dining", "Gurney Plaza, Penang", "PURCHASE", "April 2026");

        txns[4] = new CardTransaction(95.40, "Jaya Grocer", "Groceries", "Penang", "PURCHASE", "March 2026");
        txns[5] = new CardTransaction(320.00, "Parkson", "Shopping", "Penang", "PURCHASE", "March 2026");
        txns[6] = new CardTransaction(54.90, "Netflix", "Subscription", "Online", "AUTO_DEBIT", "March 2026");
        txns[7] = new CardTransaction(22.90, "Spotify", "Subscription", "Online", "AUTO_DEBIT", "March 2026");
        txns[8] = new CardTransaction(-45.00, "REFUND - Grab", "Transport", "Online", "REFUND", "March 2026");

        for (int i = 0; i < txns.length; i++) {
            txns[i].processTransaction(card);
        }
    }

    public static CreditCard[] selectionSort(CreditCard[] arr, int size) {

        for (int i = 0; i < size; i++) {
            int indexOfSmallest = i;

            for (int j = i + 1; j < size; j++) {
                if (arr[j].compareTo(arr[indexOfSmallest]) < 0) {
                    indexOfSmallest = j;
                }
            }

            CreditCard temp = arr[indexOfSmallest];
            arr[indexOfSmallest] = arr[i];
            arr[i] = temp;
        }

        return arr;
    }

    public static int readInt(Scanner input) {
        try {
            return Integer.parseInt(input.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static double readDouble(Scanner input) {
        try {
            return Double.parseDouble(input.nextLine().trim());
        } catch (Exception e) {
            return 0.0;
        }
    }

    public static void logout() {
        loggedIn = false;
        isAdmin = false;
        currentCustomer = null;
        currentUsername = "";
        System.out.println("Logged out.");
    }
}