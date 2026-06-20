import java.util.List;
import java.util.ArrayList;

public abstract class User {
    protected String customerId;
    protected String phoneNum;
    protected String email;

    // Constructor
    public User(String customerId, String phoneNum, String email) {
        this.customerId = customerId;
        this.phoneNum = phoneNum;
        this.email = email;
    }

    // Link account
    public void linkAccount(Account account) {
        System.out.println("Linking Account: " + account.getAccId());
    }

    // Unlink account
    public void unlinkAccount(Account account) {
        System.out.println("Unlinking Account: " + account.getAccId());
    }

    // Show profile info
    public void showInfo() {
        System.out.println("User ID: " + customerId + " | Phone: " + phoneNum + " | Email: " + email);
    }
}