import java.util.Date;

public class Fee {
    // data members 
    private String feeType;
    private double amount;
    private Date chargeDate;

    // constructors
    public Fee() {
        this("", 0.0);
    }

    public Fee(String feeType, double amount) {
        this.feeType = feeType;
        this.amount = amount;
        this.chargeDate = new Date(); 
    }

    // get methods
    public double getAmount() {
        return amount;
    }

    public String getFeeType() {
        return feeType;
    }

    public Date getChargeDate() {
        return chargeDate;
    }

    // toString method
    public String toString() {
        return String.format("\n\t- Type  : %s" +
                         "\n\t- Amount: RM%.2f" +
                         "\n\t- Date  : %s", 
                         feeType, amount, chargeDate);

    }
}
