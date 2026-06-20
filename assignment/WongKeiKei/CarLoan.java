public class CarLoan extends Loan {
    private static int count = 1000; 
    
    private String carModel;
    private double carValue;
    private double downPayment;
    
    public CarLoan(double amount, double interestRate,
                   int duration, Customer customer,
                   String carModel, double carValue, double downPayment) {

        super("CL" + (++count), amount, interestRate, duration, customer);
        
        this.carModel = carModel;
        this.carValue = carValue;
        this.downPayment = downPayment;
    }
    
    //get method
    public String getCarModel() {
    	 return carModel;
    }
    
    public double getCarValue() { 
    	return carValue;
    }
    
    public double getDownPayment() { 
    	return downPayment;
    }
    
    //set method
    public void setCarModel(String carModel) { 
    	this.carModel = carModel;
    }
    public void setCarValue(double carValue) { 
      this.carValue = carValue;
    }
    public void setDownPayment(double downPayment) { 
    	this.downPayment = downPayment;
    }

    public boolean checkEligibility() {
        return customer.isAdult()
            && customer.isMalaysian()
            && customer.hasValidMyKad()
            && customer.isHasLicense()
            && customer.meetsMinimumIncome(2000);
    }
    
    //method overriding
    @Override
    public String toString() {
        return super.toString() +
               "\nCar Model: " + carModel +
               String.format("\nCar Value: RM %.2f", carValue);
    }
}