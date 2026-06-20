import java.util.Scanner;

public class CreditCardApp {

    //arrays to store cards and auto debits
    static CreditCard[] cards = new CreditCard[20];
    static AutoDebit[]  debits = new AutoDebit[20];
    static int cardCount = 0;
    static int debitCount = 0;
    static Scanner sc = new Scanner(System.in);
    static String currentUser = "AHMAD BIN RAZALI";

    //main
    public static void main(String[] args) {
        printHeader();
        login();
        mainMenu();
        System.out.println("\n===============================");
        System.out.println("  Goodbye, " + currentUser.split(" ")[0] + "!");
        System.out.println("===============================");
        sc.close();
    }//end of main

    //login
    public static void printHeader() {
        System.out.println("===============================");
        System.out.println("       MAYBANK CREDIT CARD SERVICES");
        System.out.println("           Digital Banking Portal");
        System.out.println("===============================");
    }//end of printHeader()

    public static void login() {
        System.out.print("\n Enter IC Number : ");
        sc.nextLine();
        System.out.print(" Enter Password  : ");
        sc.nextLine();
        System.out.println("\n Login successful! Welcome, " + currentUser.split(" ")[0] + ".");
    }//end of login()

    //main menu
    public static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n===============================");
            System.out.println("\tMain Menu");
            System.out.println("===============================");
            System.out.println("1. Manage Credit Cards");
            System.out.println("2. View Transactions");
            System.out.println("3. Make Payment");
            System.out.println("4. View Statement");
            System.out.println("5. Auto Debit Settings");
            System.out.println("0. Logout");
            System.out.print("\nEnter a choice: ");

            switch (readInt()) {
                case 1: manageCards();      
                	break;
                case 2: viewTransactions(); 
                	break;
                case 3: makePayment();      
                	break;
                case 4: viewStatement();    
                	break;
                case 5: autoDebitMenu();    
                	break;
                case 0: running = false;   
                	break;
                default: System.out.println(" Invalid choice.");
            }
        }
    }//end of mainMenu()

    //manage cards create read and update
    public static void manageCards() {
        boolean back = false;
        while (!back) {
            System.out.println("\n===============================");
            System.out.println("\tCredit Card Management");
            System.out.println("===============================");
            System.out.println("1. Apply New Card");
            System.out.println("2. View My Cards");
            System.out.println("3. Update Card Settings");
            System.out.println("0. Back");
            System.out.print("\n Enter choice: ");

            switch (readInt()) {
                case 1: applyNewCard();       
                	break;
                case 2: viewMyCards();        
                	break;
                case 3: updateCardSettings(); 
                	break;
                case 0: back = true;          
                	break;
                default: System.out.println("Invalid choice.");
            }
        }
    }//end of manageCards()

    //CREATE
    public static void applyNewCard() {
        if (cardCount >= cards.length) {
            System.out.println("Maximum cards reached.");
            return;
        }
        
        System.out.println("\n===============================");
        System.out.println(" Apply New Card");
        System.out.println("===============================");
        System.out.println("1. Standard Card");
        System.out.println("2. Premium Card");
        System.out.print("\n Enter choice: ");
        int type = readInt();
        if (type != 1 && type != 2) {
            System.out.println(" Invalid choice.");
            return;
        }

        System.out.print(" Enter Full Name: ");
        String name = sc.nextLine().trim().toUpperCase();
        System.out.print("Monthly Income(RM): ");
        double income = readDouble();

        double limit;
        if (income >= 5000) {
            limit = 15000;
        } else {
            limit = 5000;
        }

        System.out.println("\nGenerating card...");

        CreditCard card;
        if (type == 2) {
            card = new PremiumCard(name, limit);
        } else {
            card = new StandardCard(name, limit);
        }

        System.out.println("\nCard successfully created!");
        System.out.println(card);
        System.out.println("Status : PENDING ACTIVATION");

        System.out.print("\n Activate card now? (yes/no): ");
        if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
            card.activate();
        }

        cards[cardCount]  = card;
        debits[debitCount] = new AutoDebit(card.getCardId(), "Maybank Savings **7823", 3, "MINIMUM");
        cardCount++;
        debitCount++;
    }//end of applyNewCard()

    //Read credit card details
    public static void viewMyCards() {
        System.out.println("\n===============================");
        System.out.println("\t\tMy Cards");
        System.out.println("===============================");

        if (cardCount == 0) {
            System.out.println("No cards found. Please apply for a card first.");
            return;
        }

        cards = selectionSort(cards, cardCount);

        for (int i = 0; i < cardCount; i++) {
            if (cards[i] instanceof PremiumCard) {
                System.out.print("\n****** Premium Card ******");
            } else {
                System.out.print("\n****** Standard Card ******");
            }
            System.out.println(cards[i]);
            System.out.println("Total Charges: RM" + String.format("%.2f", cards[i].calculateTotalCharges()));
            System.out.println("--------------------------------------------------");
        }
    }//end of viewMyCards()

    //Update credit card status
    public static void updateCardSettings() {
        CreditCard card = selectCard();
        if (card == null) return;

        System.out.println("\n===============================");
        System.out.println("\tUpdate Card Settings");
        System.out.println("===============================");
        System.out.println("1. Block Card");
        System.out.println("2. Unblock / Activate Card");
        System.out.println("3. Freeze Card");
        System.out.println("0. Back");
        System.out.print("\nEnter choice: ");

        switch (readInt()) {
            case 1: card.blockCard();  
            	break;
            case 2: card.activate();   
            	break;
            case 3: card.freezeCard(); 
            	break;
            case 0: break;
            default: System.out.println(" Invalid choice.");
        }
    }//end of updateCardSettings()

    //view transactions
    public static void viewTransactions() {
        CreditCard card = selectCard();
        if (card == null) return;

        System.out.println("\n===============================");
        System.out.println("Transaction " + card.getMaskedNumber());
        System.out.println("===============================");

        //load sample data if empty
        if (card.getTxnCount() == 0) {
            System.out.println("No transactions yet. Loading sample data...\n");
            addSampleTransactions(card);
        }

        CardTransaction[] txns = card.getTransactions();
        int count = card.getTxnCount();
        double purchases = 0;
        double refunds = 0;

        for (int i = 0; i < count; i++) {
            System.out.println("\n [" + (i + 1) + "]");
            System.out.println(txns[i]);
            if (txns[i].getAmount() < 0) {
                refunds += Math.abs(txns[i].getAmount());
            } else {
                purchases += txns[i].getAmount();
            }
        }

        System.out.println("\n --------------------------------------------------");
        System.out.println("Total Purchases  : RM" + String.format("%.2f", purchases));
        System.out.println("Total Refunds    : RM" + String.format("%.2f", refunds));
        System.out.println("Net Spending     : RM" + String.format("%.2f", purchases - refunds));
        System.out.println("--------------------------------------------------");
        
    }//end of viewTransactions()

    //make payment for credit card
    public static void makePayment() {
        CreditCard card = selectCard();
        if (card == null) return;

        System.out.println("\n===============================");
        System.out.println("Make Payment  " + card.getMaskedNumber());
        System.out.println("===============================");
        System.out.println("Current Balance: RM" + String.format("%.2f", card.getCurrentBalance()));
        System.out.println("Minimum Payment: RM" + String.format("%.2f", card.calculateMinimumPayment()));

        if (card.getCurrentBalance() <= 0) {
            System.out.println("\nNo outstanding balance. Nothing to pay.");
            return;
        }

        System.out.println();
        System.out.println("1. Pay Minimum(RM" + String.format("%.2f", card.calculateMinimumPayment()) + ")");
        System.out.println("2. Pay Full(RM" + String.format("%.2f", card.getCurrentBalance()) + ")");
        System.out.println("3. Pay Custom Amount");
        System.out.println("0. Back");
        System.out.print("\nEnter choice: ");

        int choice = readInt();
        double amount = 0;

        if (choice == 1) {
            amount = card.calculateMinimumPayment();
        } else if (choice == 2) {
            amount = card.getCurrentBalance();
        } else if (choice == 3) {
            System.out.print(" Enter amount: RM");
            amount = readDouble();
        } else if (choice == 0) {
            return;
        } else {
            System.out.println(" Invalid choice.");
            return;
        }

        System.out.print("\nConfirm payment of RM" + String.format("%.2f", amount) + "? (yes/no): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("yes")) {
            System.out.println("Payment cancelled.");
            return;
        }

        Payment payment = new Payment(amount, "ONLINE BANKING", card.getCardId(), "Maybank Savings **7823");
        payment.processPayment(card);
        
    }//end of makePayment()

    //view statement
    public static void viewStatement() {
        CreditCard card = selectCard();
        if (card == null) return;

        System.out.println("\n===============================");
        System.out.println("Monthly Statement  " + card.getMaskedNumber());
        System.out.println("===============================");
        System.out.println("1. This month (April 2026)");
        System.out.println("2. Last month (March 2026)");
        System.out.println("0. Back ");
        System.out.print("\nEnter choice: ");

        int choice = readInt();
        if (choice == 0) return;

        String period;
        String dueDate;
        if (choice == 2) {
            period  = "March 2026";
            dueDate = "05 Apr 2026";
        } else {
            period  = "April 2026";
            dueDate = "05 May 2026";
        }

        CardTransaction[] allTxns = card.getTransactions();
        int allCount = card.getTxnCount();

        //filter transactions by selected month only
        CardTransaction[] filtered = new CardTransaction[allCount];
        int filteredCount = 0;
        for (int i = 0; i < allCount; i++) {
            if (allTxns[i].getMonth().equals(period)) {
                filtered[filteredCount++] = allTxns[i];
            }
        }

        Statement stmt = new Statement(period, dueDate, card.getCurrentBalance(), filtered, filteredCount, card.getInterestRate());
        stmt.generateStatement(card);
        
    }//end of viewStatement()

    //auto debit menu
    public static void autoDebitMenu() {
        CreditCard card = selectCard();
        if (card == null) return;

        AutoDebit ad = getAutoDebit(card.getCardId());
        if (ad == null) {
            System.out.println("No Auto Debit setup found.");
            return;
        }

        System.out.println("\n===============================");
        System.out.println("Auto Debit Settings  " + card.getMaskedNumber());
        System.out.println("===============================");
        ad.displaySettings();

        System.out.println();
        System.out.println("1. Enable Auto Debit");
        System.out.println("2. Disable Auto Debit");
        System.out.println("3. Change to Full Payment");
        System.out.println("4. Change to Minimum Payment");
        System.out.println("5. Execute Auto Debit Now");
        System.out.println("0. Back");
        System.out.print("\nEnter choice: ");

        switch (readInt()) {
            case 1: ad.enable(); 
            	break;
            case 2: ad.disable(); 
            	break;
            case 3:
                ad.setAmountType("FULL");
                System.out.println("\nAuto Debit changed to FULL PAYMENT.");
                break;
            case 4:
                ad.setAmountType("MINIMUM");
                System.out.println("\nAuto Debit changed to MINIMUM PAYMENT.");
                break;
            case 5:
                System.out.print("\nConfirm execute auto debit? (yes/no): ");
                if (sc.nextLine().trim().equalsIgnoreCase("yes")) {
                    ad.executeAutoDebit(card);
                }
                break;
            case 0: break;
            default: System.out.println(" Invalid choice.");
        }
    }//end of autoDebitMenu()

    //selection sort by card holder name
    public static CreditCard[] selectionSort(CreditCard[] arr, int size) {
        for (int i = 0; i < size; i++) {
            int indexOfSmallest = i;
            for (int j = i + 1; j < size; j++) {
                if (arr[j].compareTo(arr[indexOfSmallest]) < 0) {
                    indexOfSmallest = j;
                }
            }
            CreditCard temp= arr[indexOfSmallest];
            arr[indexOfSmallest] = arr[i];
            arr[i]= temp;
        }
        return arr;
    }//end of selectionSort()

    public static CreditCard selectCard() {
        if (cardCount == 0) {
            System.out.println("\nNo cards found. Please apply first.");
            return null;
        }
        if (cardCount == 1) return cards[0];

        System.out.println("\n Select card:");
        for (int i = 0; i < cardCount; i++) {
            String type;
            if (cards[i] instanceof PremiumCard) {
                type = "Premium";
            } else {
                type = "Standard";
            }
            System.out.println(" " + (i + 1) + ". " + cards[i].getMaskedNumber() + "  (" + type + ")");
        }
        System.out.print("Enter choice: ");
        int idx = readInt() - 1;
        if (idx < 0 || idx >= cardCount) {
            System.out.println("Invalid selection.");
            return null;
        }
        return cards[idx];
    }//end of selectCard()

    public static AutoDebit getAutoDebit(String cardId) {
        for (int i = 0; i < debitCount; i++) {
            if (debits[i].getLinkedCardId().equals(cardId)) {
                return debits[i];
            }
        }
        return null;
    }//end of getAutoDebit()

    public static void addSampleTransactions(CreditCard card) {
        //create an array for transactions
        CardTransaction[] txns = new CardTransaction[9];

        //April 2026 transactions
        txns[0] = new CardTransaction(128.50, "Jaya Grocer, Penang", "Groceries", "Gurney Plaza, Penang", "PURCHASE", "April 2026");
        txns[1] = new CardTransaction(80.00, "Shell Petrol Station", "Fuel", "Jalan Perak, Penang", "PURCHASE","April 2026");
        txns[2] = new CardTransaction(54.90, "Netflix", "Subscription", "Online", "AUTO_DEBIT", "April 2026");
        txns[3] = new CardTransaction(38.70, "McDonald's Gurney", "Dining","Gurney Plaza, Penang", "PURCHASE", "April 2026");

        //March 2026 transactions
        txns[4] = new CardTransaction(95.40,  "Jaya Grocer", "Groceries", "Penang", "PURCHASE", "March 2026");
        txns[5] = new CardTransaction(320.00, "Parkson", "Shopping","Penang", "PURCHASE","March 2026");
        txns[6] = new CardTransaction(54.90,  "Netflix", "Subscription", "Online", "AUTO_DEBIT", "March 2026");
        txns[7] = new CardTransaction(22.90,  "Spotify", "Subscription", "Online","AUTO_DEBIT", "March 2026");
        txns[8] = new CardTransaction(-45.00, "REFUND - Grab", "Transport","Online", "REFUND", "March 2026");

        //process each transaction of the card
        for (int i = 0; i < txns.length; i++) {
            txns[i].processTransaction(card);
        }
    }//end of addSampleTransactions()

    public static int readInt() {
        String input = sc.nextLine().trim();
        int result = -1;
        boolean valid = true;

        if (input.isEmpty()) {
            valid = false;
        } else {
            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    valid = false;
                }
            }
        }

        if (valid) {
            result = Integer.parseInt(input);
        }

        return result;
    }//end of readInt()

    public static double readDouble() {
        String input  = sc.nextLine().trim();
        double result = 0.0;
        boolean valid = true;
        int dotCount  = 0;

        if (input.isEmpty()) {
            valid = false;
        } else {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                if (c == '.') {
                    dotCount++;
                    if (dotCount > 1) {
                        valid = false;
                    }
                } else if (!Character.isDigit(c)) {
                    valid = false;
                }
            }
        }

        if (valid) {
            result = Double.parseDouble(input);
        }

        return result;
    }//end of readDouble()

}//end of CreditCardApp