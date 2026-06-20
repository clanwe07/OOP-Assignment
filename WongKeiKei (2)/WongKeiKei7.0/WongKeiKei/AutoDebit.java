public class AutoDebit {

    // data members
    private String  autoDebitId;
    private boolean enabled;
    private int deductionDate;
    private String  amountType;    // "FULL" or "MINIMUM"
    private String  linkedCardId;
    private String  linkedAccount;
    private static int lastAssignedID = 7001;

    // constructor
    public AutoDebit() {
        this("", "", 3, "MINIMUM");
    }

    public AutoDebit(String linkedCardId, String linkedAccount, int deductionDate, String amountType) {
        this.autoDebitId = "AD" + lastAssignedID;
        this.linkedCardId = linkedCardId;
        this.linkedAccount = linkedAccount;
        this.deductionDate = deductionDate;
        this.amountType = amountType;
        this.enabled = true;
        lastAssignedID++;
    }
    
    //getter
    public String getAutoDebitId() {
        return autoDebitId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getDeductionDate() {
        return deductionDate;
    }

    public String getAmountType() {
        return amountType;
    }

    public String getLinkedCardId() {
        return linkedCardId;
    }

    public String getLinkedAccount() {
        return linkedAccount;
    }

	//setter
    public void setEnabled(boolean e) {
        this.enabled = e;
    }

    public void setDeductionDate(int d) {
        this.deductionDate = d;
    }

    public void setAmountType(String t) {
        this.amountType = t;
    }

    //enable credit card auto debit
    public void enable() {
        this.enabled = true;
        System.out.println("\nAuto Debit ENABLED.");
        System.out.println("Deduction Type   : " + amountType + " PAYMENT");
        System.out.println("Deduction Date   : " + deductionDate + ordinal(deductionDate) + " of every month");
        System.out.println("Linked Account   : " + linkedAccount);
    }

    //disable auto debit
    public void disable() {
        this.enabled = false;
        System.out.println("\n Auto Debit DISABLED.");
    }

    //execute auto debit
    public void executeAutoDebit(CreditCard card) {
        if (!enabled) {
            System.out.println("\nAuto Debit is disabled.");
            return;
        }
        double deductAmount;
		if (amountType.equals("FULL")) {
    		deductAmount = card.getCurrentBalance();
		} else {
    		deductAmount = card.calculateMinimumPayment();
		}

        if (deductAmount <= 0) {
            System.out.println("\nNo outstanding balance. Auto Debit skipped.");
            return;
        }

        Payment payment = new Payment(deductAmount, "AUTO DEBIT", linkedCardId, linkedAccount);
        payment.processPayment(card);

        System.out.println("\nAuto Debit executed!");
        System.out.println("Auto Debit ID    : " + autoDebitId);
        System.out.println("Amount Deducted  : RM" + String.format("%.2f", deductAmount)
            + "  (" + amountType + "PAYMENT)");
        System.out.println("From Account     : " + linkedAccount);
        System.out.println("Status           : SUCCESS");
        System.out.println("New Balance      : RM" + String.format("%.2f", card.getCurrentBalance()));
    }

    // display settings
    public void displaySettings() {
        System.out.println("Auto Debit       : " + (enabled ? "ENABLED" : "DISABLED"));
        System.out.println("Deduction Type   : " + amountType + " PAYMENT");
        System.out.println("Deduction Date   : " + deductionDate + ordinal(deductionDate) + " of every month");
        System.out.println("Linked Account   : " + linkedAccount);
    }

    // toString method
    public String toString() {
        return "\nAuto Debit ID    : " + autoDebitId +
               "\nStatus           : " + (enabled ? "ENABLED" : "DISABLED") +
               "\nDeduction Type   : " + amountType + " PAYMENT" +
               "\nDeduction Date   : " + deductionDate + ordinal(deductionDate) + " of every month" +
               "\nLinked Account   : " + linkedAccount;
    }

    private String ordinal(int n) {
        if (n >= 11 && n <= 13) return "th";
        switch (n % 10) {
            case 1:  return "st";
            case 2:  return "nd";
            case 3:  return "rd";
            default: return "th";
        }
    }
}