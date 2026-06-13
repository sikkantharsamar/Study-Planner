import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


public class AuthenticationService {

        private static final Logger logger = Logger.getLogger(AuthenticationService.class.getName());


    private List<UserAccount> userAccounts;
    private UserAccount currentLoggedInUser;


    public AuthenticationService() {
        this.userAccounts = new ArrayList<>();
        this.currentLoggedInUser = null;
    }


    public void registerAccount(UserAccount account) {
        userAccounts.add(account);
        logger.info("Account registered successfully for: " + account.getUsername());
    }


    public boolean login(String username, String password) {
        for (UserAccount account : userAccounts) {
            if (account.validateCredentials(username, password)) {
                currentLoggedInUser = account;
                account.updateLastLogin();
                logger.info("\n*** Login Successful! ***");
                logger.info("Welcome back, " + account.getStudent().getName() + "! (Level " + account.getStudent().getLevel() + ")");
                return true;
            }
        }
        logger.info("\n*** Login Failed! Invalid credentials. ***");
        return false;
    }


    public void logout() {
        if (currentLoggedInUser != null) {
            logger.info("\n*** Logging out " + currentLoggedInUser.getUsername() + " ***");
            currentLoggedInUser = null;
            logger.info("Logout successful!");
        } else {
            logger.info("No user is currently logged in.");
        }
    }


    public UserAccount getCurrentUser() {
        return currentLoggedInUser;
    }


    public boolean isUserLoggedIn() {
        return currentLoggedInUser != null;
    }
}