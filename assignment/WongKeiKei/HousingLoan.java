// sub class
public class HousingLoan extends Loan {

    // auto generate ID
    private static int count = 2000;

    private String propertyAddress;
    private double propertyValue;

    // constructor 
    public HousingLoan(double amount, double interestRate, int duration,
                       Customer customer, String propertyAddress, double propertyValue) {

        super("HL" + (++count), amount, interestRate, duration, customer);

        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }

    // getter methods
    public String getPropertyAddress() {
        return propertyAddress;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    // setter methods
    public void setPropertyAddress(String propertyAddress) {
        this.propertyAddress = propertyAddress;
    }

    public void setPropertyValue(double propertyValue) {
        if (propertyValue >= 0) {
            this.propertyValue = propertyValue;
        } else {
            System.out.println("Invalid property value.");
        }
    }

    // eligibility check
    public boolean checkEligibility() {
        int age = customer.getAge();

        return age >= 21 && age <= 65
                && customer.isMalaysian() 
                && customer.hasValidMyKad()
                && customer.meetsMinimumIncome(3000);
    }

    // calculate interest
    public double calculateInterest() {
        return amount * (interestRate - 0.01) * duration / 12;
    }

    // toString
    @Override
    public String toString() {

        return super.toString() +
               "\nProperty Address: " + propertyAddress +
               String.format("\nProperty Value : RM %.2f", propertyValue);
    }
}