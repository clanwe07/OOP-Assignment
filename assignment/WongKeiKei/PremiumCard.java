public class PremiumCard extends CreditCard {

    // data members
    private int rewardPoints;
    private String rewardTier;
    private boolean loungeAccess;
    private int loungeVisitsLeft;
    private static double annualFee = 800.0;

    // constructor
    public PremiumCard() {
        super();
    }

    public PremiumCard(String cardHolderName, double creditLimit) {
        super(cardHolderName, creditLimit, 15.0);
        this.rewardPoints = 0;
        this.rewardTier = "Silver";
        this.loungeAccess = true;
        this.loungeVisitsLeft= 2;
    }
    
    // getters and setters
    public int getRewardPoints(){ 
    	return rewardPoints; 
    }
    public String getRewardTier(){ 
    	return rewardTier; 
    }
    public boolean isLoungeAccess(){ 
    	return loungeAccess; 
    }
    
    public int getLoungeVisitsLeft(){ 
    	return loungeVisitsLeft; 
    }
    
    public void addRewardPoints(int pts){ 
    	rewardPoints += pts; updateTier(); 
    }
    
    public void setLoungeAccess(boolean a) { 
    	this.loungeAccess = a; 
    }
    
    public void resetMonthlyLounge() { 
    	this.loungeVisitsLeft = 2; 
    }

    // abstract methods implementation
    public double getAnnualFee() {
        return annualFee;
    }

    public double calculateRewards(double spentAmount) {
        int earned = (int) spentAmount;   // 1 pt per RM1 spent
        rewardPoints += earned;
        updateTier();
        return earned;
    }

    public double calculateTotalCharges() {
        return currentBalance + annualFee;
    }

    // update reward tier based on points
    private void updateTier() {
        if (rewardPoints >= 50000) rewardTier = "Platinum";
        else if (rewardPoints >= 20000) rewardTier = "Gold";
        else rewardTier = "Silver";
    }

    // redeem points
    public void redeemPoints(int points) {
        if (points > rewardPoints) {
            System.out.println(" Insufficient points. Balance: " + rewardPoints + " pts");
            return;
        }
        
        rewardPoints -= points;
        double cashValue = (points / 5000.0) * 50.0;
        System.out.println("\n Redemption successful!");
        System.out.println(" Points deducted  : " + points + " pts");
        System.out.println(" Remaining points : " + rewardPoints + " pts");
        System.out.println(" Cash value       : RM" + String.format("%.2f", cashValue) + " credited to card");
    }

    // toString
    public String toString() {
        return super.toString() +
               "\nCard Type         : PremiumCard(Visa Platinum)" +
               "\nAnnual Fee       : RM" + String.format("%.2f", annualFee) + " (waived if spend >= RM12,000/yr)" +
               "\nReward Tier      : " + rewardTier + "\n Reward Points   : " + rewardPoints + " pts" +
               "\nLounge Access  : " + (loungeAccess ? "YES (" + loungeVisitsLeft + " visits/month left)" : "NO");
    }
}