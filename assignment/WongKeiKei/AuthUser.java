public class AuthUser {
    String username;
    String password;
    String role; 
    User linkedUser;

    public AuthUser(String username, String password, String role, User linkedUser) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.linkedUser = linkedUser;
    }
}