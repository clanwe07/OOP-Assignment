public class Statement {

    // data members
    private String statementId;
    private String period;
    private String dueDate;
    private double previousBalance;
    private double totalPurchases;
    private double totalRefunds;
    private double financeCharge;
    private double closingBalance;
    private double minimumPayment;
    private String status;
    private CardTransaction[] transactions;
    private int txnCount;
    private static int lastAssignedID = 9001;

    // constructor
    public Statement() {
        this("", "", 0.0, new CardTransaction[0], 0, 0.0);
    }

    public Statement(String period, String dueDate, double previousBalance, CardTransaction[] txns, int txnCount, double interestRate) {
        this.statementId = "STMT-" + lastAssignedID;
        this.period = period;
        this.dueDate = dueDate;
        this.previousBalance = previousBalance;
        this.status = "GENERATED";
        this.txnCount = txnCount;
        this.transactions = new CardTransaction[txnCount];
        for (int i = 0; i < txnCount; i++) {
            this.transactions[i] = txns[i];
        }
        calculate(interestRate);
        lastAssignedID++;
    }
    
    // getters 
    public String getStatementId(){ 
    	return statementId; 
    }
    
    public String getPeriod(){ 
    	return period; 
    }
    
    public String getDueDate(){ 
    	return dueDate; 
    }
    
    public double getClosingBalance(){ 
    	return closingBalance;
    }
    
    public double getMinimumPayment(){ 
    	return minimumPayment; 
    }
    
    public double getFinanceCharge()  { 
    	return financeCharge; 
    }
    
    public String getStatus(){ 
    	return status; 
    }
    
    // setters
    public void setStatus(String s){ 
    	this.status = s; 
    }

	//methods
    // calculate totals
    private void calculate(double annualRate) {
        totalPurchases = 0;
        totalRefunds   = 0;

        for (int i = 0; i < txnCount; i++) {
            double amt = transactions[i].getAmount();
            if (amt < 0) totalRefunds   += Math.abs(amt);
            else         totalPurchases += amt;
        }

        double monthlyRate = (annualRate / 100.0) / 12.0;
        financeCharge = Math.round(previousBalance * monthlyRate * 100.0) / 100.0;
        closingBalance= previousBalance + totalPurchases - totalRefunds + financeCharge;
        if (closingBalance < 0) closingBalance = 0;
        minimumPayment = Math.round(closingBalance * 0.05 * 100.0) / 100.0;
    }

    // print credit card statement
    public void generateStatement(CreditCard card) {
        System.out.println("\n --------------------------------------------------");
        System.out.println("        STATEMENT  " + period.toUpperCase());
        System.out.println(" --------------------------------------------------");
        System.out.println(this);

        System.out.println("\n Date          Merchant                    Amount");
        System.out.println(" " + "-".repeat(50));

        if (txnCount == 0){
            System.out.println(" No transactions this period.");
        } else {
            for (int i = 0; i < txnCount; i++) {
                CardTransaction t = transactions[i];
                
                String sign;
                if (t.getAmount() < 0) {
                    sign = "+RM";
                } else {
                    sign = "-RM";
                }
 
                String name;
                if (t.getMerchantName().length() > 24) {
                    name = t.getMerchantName().substring(0, 21) + "...";
                } else {
                    name = t.getMerchantName();
                }
                
                System.out.printf(" %-12s  %-24s %s%.2f%n",
                    t.getDate(), name, sign, Math.abs(t.getAmount()));
            }
        }

        System.out.println("\n --------------------------------------------------");
        System.out.println("Previous Balance  : RM" + String.format("%.2f", previousBalance));
        System.out.println("Total Purchases   : RM" + String.format("%.2f", totalPurchases));
        System.out.println("Total Refunds     : RM" + String.format("%.2f", totalRefunds));
        System.out.println("Finance Charge    : RM" + String.format("%.2f", financeCharge)
            							+ "  (" + String.format("%.1f", card.getInterestRate() / 12.0) + "%/month)");
        System.out.println("--------------------------------------------------");
        System.out.println("Closing Balance   : RM" + String.format("%.2f", closingBalance));
        System.out.println("--------------------------------------------------");
        System.out.println("Minimum Payment   : RM" + String.format("%.2f", minimumPayment) + "  (5%)");
        System.out.println("Full Payment      : RM" + String.format("%.2f", closingBalance));
        System.out.println("Due Date          : " + dueDate);
        System.out.println("--------------------------------------------------");

        if (card instanceof PremiumCard) {
            PremiumCard p = (PremiumCard) card;
            System.out.println(" Points Earned: +" + (int) totalPurchases + " pts");
            System.out.println(" Points Balance: " + p.getRewardPoints() + " pts");
            System.out.println(" --------------------------------------------------");
        }
    }

    public double calculateDue() {
        return minimumPayment;
    }

    // toString methods
    public String toString() {
        return " Statement ID  : " + statementId +
               "\n Period        : " + period +
               "\n Due Date      : " + dueDate +
               "\n Status        : " + status;
    }
}