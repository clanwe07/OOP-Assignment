public abstract class CreditCard implements Comparable {

    // data members
    protected String cardId;
    protected String cardHolderName;
    protected double creditLimit;
    protected double availableCredit;
    protected double currentBalance;
    protected String status;
    protected String cardNumber;
    protected String expiryDate;
    protected double interestRate;
    private static int lastAssignedID = 1001;

    private CardTransaction[] transactions = new CardTransaction[100];
    private int txnCount = 0;

    // constructor
    public CreditCard() {
        this("", 0.0, 0.0);
    }

    public CreditCard(String cardHolderName, double creditLimit, double interestRate) {
        this.cardId = "CC" + lastAssignedID;
        this.cardHolderName = cardHolderName;
        this.creditLimit = creditLimit;
        this.availableCredit = creditLimit;
        this.currentBalance = 0.0;
        this.interestRate= interestRate;
        this.status = "PENDING";
        this.cardNumber= generateCardNumber(lastAssignedID);
        this.expiryDate = "04/30";
        lastAssignedID++;
    }
    
    // getter
    public String getCardId() {
        return cardId;
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public double getCreditLimit() {
        return creditLimit;
    }

    public double getAvailableCredit() {
        return availableCredit;
    }

    public double getCurrentBalance() {
        return currentBalance;
    }

    public String getStatus() {
        return status;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public double getInterestRate() {
        return interestRate;
    }

	//setters
    public void setStatus(String status) {
        this.status= status;
    }

    public void setCreditLimit(double limit) {
        this.creditLimit = limit;
        this.availableCredit= limit - currentBalance;
    }

    public void setInterestRate(double rate) {
        this.interestRate = rate;
    }

    public void setCardHolderName(String name) {
        this.cardHolderName = name;
    }

    // generate a fake masked card number
    private String generateCardNumber(int id) {
        return "5123 4567 8910" + String.format("%04d", id);
    }

    public String getMaskedNumber() {
        return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
    }

    //activate credit card
    public void activate() {
        if (status.equals("PENDING") || status.equals("FROZEN")) {
            status = "ACTIVE";
            System.out.println("\n Card " + getMaskedNumber() + " has been ACTIVATED.");
            System.out.println(" Status: ACTIVE");
        } else {
            System.out.println("\n Cannot activate. Current status: " + status);
        }
    }

    // block credit card
    public void blockCard() {
        if (status.equals("ACTIVE") || status.equals("FROZEN")) {
            status = "BLOCKED";
            System.out.println("\nCard " + getMaskedNumber() + " has been BLOCKED.");
            System.out.println(" Status: BLOCKED");
        } else {
            System.out.println("\nCannot block. Current status: " + status);
        }
    }

    // freeze credit card
    public void freezeCard() {
        if (status.equals("ACTIVE")) {
            status = "FROZEN";
            System.out.println("\nCard " + getMaskedNumber() + " has been FROZEN.");
            System.out.println(" Status: FROZEN");
        } else {
            System.out.println("\nCannot freeze. Current status: " + status);
        }
    }

    public double calculateMinimumPayment() {
        return Math.round(currentBalance * 0.05 * 100.0) / 100.0;
    }

    // add transaction to credit card
    public void addTransaction(CardTransaction txn) {
        if (txnCount < transactions.length) {
            transactions[txnCount++] = txn;
        }
        currentBalance  += txn.getAmount();
        availableCredit -= txn.getAmount();
    }

    //add payment to card
    public void addPayment(double amount) {
        currentBalance  -= amount;
        availableCredit += amount;
        if (availableCredit > creditLimit) availableCredit = creditLimit;
        if (currentBalance < 0) currentBalance = 0;
    }

    public CardTransaction[] getTransactions() { 
    	return transactions; 
    }
    
    public int getTxnCount(){ 
    	return txnCount; 
    }

    //abstract methods implemented in subclass
    public abstract double getAnnualFee();
    public abstract double calculateRewards(double spentAmount);
    public abstract double calculateTotalCharges();

    // toString method
    public String toString() {
        return "\nCard ID          : " + cardId + "\nCard Holder      : " + cardHolderName +
               "\nCard Number      : " + getMaskedNumber() + "\nExpiry Date      : " + expiryDate + "\nStatus           : " + status +
               "\nInterest Rate    : " + String.format("%.2f", interestRate) + "% per annum" +
               "\nCredit Limit     : RM" + String.format("%.2f", creditLimit) +
               "\nCurrent Balance  : RM" + String.format("%.2f", currentBalance) +
               "\nAvailable Credit : RM" + String.format("%.2f", availableCredit) +
               "\nMinimum Payment  : RM" + String.format("%.2f", calculateMinimumPayment());
    }

    // compareTo sort by card holder name
    public int compareTo(Object o) {
        CreditCard other = (CreditCard) o;
        return cardHolderName.compareTo(other.cardHolderName);
    }

    
}