import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AdminMain {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // ---------------------------------------------------------
        // 1. ADMIN ?????? (Input)
        // ---------------------------------------------------------
        System.out.println("==================================================");
        System.out.println("          BANK LOAN MANAGEMENT SYSTEM             ");
        System.out.println("                [ ADMIN PANEL ]                   ");
        System.out.println("==================================================");
        
        System.out.print("Enter Customer Name       : ");
        String name = input.nextLine();
        
        System.out.print("Enter Customer IC/ID      : ");
        String id = input.nextLine();

        System.out.print("Enter Loan Amount (RM)    : ");
        double initialBalance = input.nextDouble();

        System.out.print("Annual Interest Rate (%)  : ");
        double annualRate = input.nextDouble();

        System.out.print("Monthly Repayment (RM)    : ");
        double monthlyPayment = input.nextDouble();

        // ---------------------------------------------------------
        // 2. ????????? (Processing & Output)
        // ---------------------------------------------------------
        double monthlyRate = (annualRate / 100) / 12;
        double balance = initialBalance;
        int month = 1;

        // ?????? (? Customer ??)
        System.out.println("\n\n" + "=".repeat(70));
        System.out.println("               OFFICIAL REPAYMENT SCHEDULE");
        System.out.println("=".repeat(70));
        System.out.printf("CUSTOMER : %-20s | IC: %s\n", name.toUpperCase(), id);
        System.out.printf("LOAN AMT : RM %-17.2f | RATE: %.2f%%\n", initialBalance, annualRate);
        System.out.println("-".repeat(70));

        // ???? (??????)
        System.out.printf("%-8s %-18s %-15s %-18s\n", 
                          "Month", "Balance", "Interest", "Repaid Principal");
        System.out.println("-".repeat(70));

        // ??????????
        while (balance > 0.01) {
            double interest = balance * monthlyRate;
            double repaidPrincipal = monthlyPayment - interest;

            // ???????:??????????,????????
            if (repaidPrincipal > balance) {
                repaidPrincipal = balance;
                monthlyPayment = repaidPrincipal + interest;
            }

            // ???????
            System.out.printf("%-8d RM %-15.2f RM %-12.2f RM %-15.2f\n", 
                              month, balance, interest, repaidPrincipal);

            // ????
            balance -= repaidPrincipal;

            // ????????????,???????
            if (month > 600) { 
                System.out.println("\n[SYSTEM ERROR]: Monthly payment is too low to settle the debt.");
                break;
            }
            month++;
        }

        // ---------------------------------------------------------
        // 3. ???? (Summary)
        // ---------------------------------------------------------
        System.out.println("-".repeat(70));
        System.out.printf("Remaining Balance in month %d = RM %.2f\n", month - 1, (balance < 0 ? 0 : balance));
        System.out.printf("(Note: The loan will be fully settled in month %d!)\n", month - 1);
        System.out.println("=".repeat(70));
        System.out.println("        Generated on: " + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
        
        input.close();
    }
}