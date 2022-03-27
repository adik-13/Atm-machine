import java.util.ArrayList;

public class User {
    // firstname,lastname,id,md5hash of user's pin,
    // list of accounts for this user
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;
}
