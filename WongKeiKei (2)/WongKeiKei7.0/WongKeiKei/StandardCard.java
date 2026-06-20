public class StandardCard extends CreditCard {

    // data members
    private double cashbackRate;
    private int rewardPoints;
    private static double annualFee = 0.0;   // free for first year

    // constructor
    public StandardCard() {
        super();
    }

    public StandardCard(String cardHolderName, double creditLimit) {
        super(cardHolderName, creditLimit, 18.0);
        this.cashbackRate = 0.005;   // 0.5% cashback
        this.rewardPoints = 0;
    }
    
     // getters 
    public double getCashbackRate(){ 
    	return cashbackRate; 
    }
    
    public int getRewardPoints(){ 
    	return rewardPoints; 
    }
    
    //setters
    public void setCashbackRate(double r) { 
    	this.cashbackRate = r; 
    }
    
    //method
    public void addRewardPoints(int pts) {
    	this.rewardPoints += pts; 
    }

    // abstract methods implementation
    public double getAnnualFee() {
        return annualFee;
    }

    public double calculateRewards(double spentAmount) {
        double cashback = spentAmount * cashbackRate;
        rewardPoints += (int)(spentAmount / 2);  // 1 pt per RM2 spent
        return Math.round(cashback * 100.0) / 100.0;
    }

    public double calculateTotalCharges() {
        return currentBalance + annualFee;
    }

    // toString
    public String toString() {
        return super.toString() +
               "\nCard Type        : StandardCard (Visa Classic)" +
               "\nAnnual Fee       : RM" + String.format("%.2f", annualFee) + " (waived 1st year)" +
               "\nCashback Rate    : " + (cashbackRate * 100) + "%" +
               "\nReward Points    : " + rewardPoints + " pts";
    }
}