public class CardTransaction {

    // data members
    private String transactionId;
    private String date;
    private String month;           //e.g. "April 2026" used for statement filtering
    private double amount;
    private String merchantName;
    private String merchantCategory;
    private String location;
    private String status;
    private String txnType;
    private static int lastAssignedID = 3001;

    // constructor
    public CardTransaction() {
        this(0.0, "", "", "", "PURCHASE", "April 2026");
    }

    public CardTransaction(double amount, String merchantName, String merchantCategory, String location,
                           String txnType, String month) {
        this.transactionId = "TXN-" + lastAssignedID;
        this.date= java.time.LocalDate.now().toString();
        this.amount = amount;
        this.merchantName= merchantName;
        this.merchantCategory = merchantCategory;
        this.location = location;
        this.txnType = txnType;
        this.month= month;
        this.status = "APPROVED";
        lastAssignedID++;
    }
    
    //getters
    public String getTransactionId() {
        return transactionId;
    }

    public String getDate() {
        return date;
    }

    public String getMonth() {
        return month;
    }

    public double getAmount() {
        return amount;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantCategory() {
        return merchantCategory;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getTxnType() {
        return txnType;
    }

	//setters
    public void setStatus(String s) {
        this.status = s;
    }

    // process transaction against a card
    public void processTransaction(CreditCard card) {
        if (!card.getStatus().equals("ACTIVE")) {
            this.status = "DECLINED";
            System.out.println("\nTransaction DECLINED. Card is not active.");
            return;
        }
        if (amount > card.getAvailableCredit()) {
            this.status = "DECLINED";
            System.out.println("\n Transaction DECLINED. Insufficient credit.");
            System.out.println(" Available Credit : RM" + String.format("%.2f", card.getAvailableCredit()));
            System.out.println(" Transaction Amt  : RM" + String.format("%.2f", amount));
            return;
        }
        this.status = "APPROVED";
        card.addTransaction(this);
        double rewards = card.calculateRewards(amount);

        System.out.println("\nTransaction APPROVED!");
        System.out.println(this);
        if (card instanceof PremiumCard) {
            System.out.println("Reward Points    : +" + (int) rewards + "pts earned");
        } else {
            System.out.println("Cashback Earned  : RM" + String.format("%.2f", rewards));
        }
    }

    // refund transaction
    public void refund(CreditCard card) {
        if (!status.equals("APPROVED")) {
            System.out.println("\n Cannot refund. Status: " + status);
            return;
        }
        this.status = "REFUNDED";
        CardTransaction refundTxn = new CardTransaction(
            -amount, "REFUND - " + merchantName, merchantCategory, location, "REFUND", month
        );
        refundTxn.status = "REFUNDED";
        card.addTransaction(refundTxn);

        System.out.println("\nRefund APPROVED!");
        System.out.println("Ref No           : " + refundTxn.transactionId);
        System.out.println("Refund Amount    : RM" + String.format("%.2f", amount));
        System.out.println("Available Credit : RM" + String.format("%.2f", card.getAvailableCredit()));
        System.out.println("Reflects within 3-5 business days.");
    }

    // toString
    public String toString() {
        String sign = (amount < 0) ? "+RM" : "-RM";
        return "\nRef No           : " + transactionId +
               "\nDate             : " + date +
               "\nMerchant         : " + merchantName +
               "\nCategory         : " + merchantCategory +
               "\nLocation         : " + location +
               "\nAmount           : " + sign + String.format("%.2f", Math.abs(amount)) +
               "\nStatus           : " + status;
    }
}