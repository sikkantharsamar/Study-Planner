import java.util.ArrayList;
import java.util.List;


public class AuthenticationService {

    private List<UserAccount> userAccounts;
    private UserAccount currentLoggedInUser;


    public AuthenticationService() {
        this.userAccounts = new ArrayList<>();
        this.currentLoggedInUser = null;
    }


    public void registerAccount(UserAccount account) {
        userAccounts.add(account);
        System.out.println("Account registered successfully for: " + account.getUsername());
    }


    public boolean login(String username, String password) {
        for (UserAccount account : userAccounts) {
            if (account.validateCredentials(username, password)) {
                currentLoggedInUser = account;
                System.out.println("\n*** Login Successful! ***");
                System.out.println("Welcome, " + account.getStudent().getName() + "!");
                return true;
            }
        }
        System.out.println("\n*** Login Failed! Invalid credentials. ***");
        return false;
    }


    public void logout() {
        if (currentLoggedInUser != null) {
            System.out.println("\n*** Logging out " + currentLoggedInUser.getUsername() + " ***");
            currentLoggedInUser = null;
            System.out.println("Logout successful!");
        } else {
            System.out.println("No user is currently logged in.");
        }
    }


    public UserAccount getCurrentUser() {
        return currentLoggedInUser;
    }


    public boolean isUserLoggedIn() {
        return currentLoggedInUser != null;
    }
}