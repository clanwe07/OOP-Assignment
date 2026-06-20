public class Staff extends User {
    private String name;

    public Staff(String name, String phone, String email) {
        super("STAFF-" + System.currentTimeMillis(), phone, email);
        this.name = name;
    }

    public String toString() {
        return "Staff: " + name + " (" + customerId + ")";
    }
}