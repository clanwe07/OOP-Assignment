public class Payment {

    // data members
    private String paymentId;
    private double amount;
    private String paymentDate;
    private String method;
    private String status;
    private String linkedCardId;
    private String linkedAccount;
    private static int lastAssignedID = 5001;

    // constructor
    public Payment() {
        this(0.0, "", "", "");
    }

    public Payment(double amount, String method, String linkedCardId, String linkedAccount) {
        this.paymentId = "PAY" + lastAssignedID;
        this.paymentDate= java.time.LocalDate.now().toString();
        this.amount= amount;
        this.method= method;
        this.status = "PENDING";
        this.linkedCardId = linkedCardId;
        this.linkedAccount = linkedAccount;
        lastAssignedID++;
    }
    
    // getters 
    public String getPaymentId() {
        return paymentId;
    }

    public double getAmount() {
        return amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public String getMethod() {
        return method;
    }

    public String getStatus() {
        return status;
    }

    public String getLinkedCardId() {
        return linkedCardId;
    }

    public String getLinkedAccount() {
        return linkedAccount;
    }

	//setters
    public void setAmount(double a) {
        this.amount = a;
    }

    public void setStatus(String s) {
        this.status = s;
    }

    // process payment
    public void processPayment(CreditCard card) {
        if (amount <= 0) {
            System.out.println("\n Invalid payment amount.");
            this.status = "FAILED";
            return;
        }
        
        if (amount > card.getCurrentBalance()) {
            amount = card.getCurrentBalance();
        }
        
        this.status = "SUCCESS";
        card.addPayment(amount);
        displayReceipt(card);
    }

    // display the receipt
    public void displayReceipt(CreditCard card) {
        System.out.println("\nPayment successful!");
        System.out.println(this);
        System.out.println("New Balance      : RM" + String.format("%.2f", card.getCurrentBalance()));
        System.out.println("Available Credit : RM" + String.format("%.2f", card.getAvailableCredit()));
    }

    // toString
    public String toString() {
        return "\nPayment ID       : " + paymentId + "\nDate             : " + paymentDate +
               "\nAmount Paid      : RM" + String.format("%.2f", amount) +
               "\nMethod           : " + method + "\nStatus           : " + status;
    }//end of toString method
}//end of main