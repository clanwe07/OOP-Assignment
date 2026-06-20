import java.util.Scanner;
import java.util.InputMismatchException; 

public class DriverProgram {
	// main
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // initial account
        CurrentAccount current = new CurrentAccount("CA-Test01", 500.0, "Active", 1000.0);
        
        System.out.println("===== Welcome to Digital Banking System =====");
        
        int choice = 0;
        // while method
        while (choice != 4) {
        	//try and catch to prevent bug
            try { 
                System.out.println("\n--- Account Menu ---");
                System.out.println("1. Deposit Money");
                System.out.println("2. Withdraw Money (Overdraft allowed)");
                System.out.println("3. View Account Summary & Fees");
                System.out.println("4. Exit");
                System.out.print("Select an option (1-4): ");
                
                choice = input.nextInt(); 
                
                // switch case to let user choice
                switch (choice) {
                    case 1:
                        System.out.print("Enter amount to deposit: RM");
                        current.deposit(input.nextDouble());
                        System.out.println("Deposit Successful! Balance: RM" + current.getBalance());
                        break;
                        
                    case 2:
                        System.out.print("Enter amount to withdraw: RM");
                        double amount = input.nextDouble();
                        if (current.withdraw(amount)) {
                            System.out.println("Withdrawal Successful! Balance: RM" + current.getBalance());
                        } else {
                            System.out.println("Failed: Exceeds Overdraft Limit!");
                        }
                        break;
                        
                    case 3:
                        current.chargeFee("Maintenance Fee", 5.0);
                        System.out.println(current.toString());
                        break;
                        
                    case 4:
                        System.out.println("Thank you for using our system. Goodbye!");
                        break;
                        
                    default:
                        System.out.println("Invalid choice! Please select 1-4.");
                }// end of choice
            } catch (InputMismatchException e) {
            	// not integer become error 
                System.out.println(">> ERROR: Invalid input! Please enter a whole number (1-4).");
                input.nextLine(); 
                choice = 0; 
            }
        }// end of while method
        
        input.close();
        
    }//end of main
}//end of program


