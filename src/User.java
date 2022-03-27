import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;
public class User {
    // firstname,lastname,id,md5hash of user's pin,
    // list of accounts for this user
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[];
    private ArrayList<Account> accounts;


    public User(String firstName, String lastName, String pin, Bank theBank) {
        //set user's first and last name
        this.firstName = firstName;
        this.lastName = lastName;

        //md5hash for pin storing (security)
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());

        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error , no such algorithm exception");
            e.printStackTrace();
            System.exit(1);
        }

        //get a new , unique universal id for the user
        this.uuid = theBank.getNewUserUUId();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message
        System.out.printf("New user %s, %s with ID %s created. \n",lastName,firstName,this.uuid);
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(pin.getBytes()),this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Error , no such algorithm exception");
            e.printStackTrace();
            System.exit(1);        }
        return false;
    }

    public void printAccountsSummary() {
        System.out.printf("\n \n %s's accounts summary",this.firstName);
        for (int i=0;i<this.accounts.size();i++)
        {
            System.out.printf("%d) %s\n",i+1,
                    this.accounts.get(i).getSummaryLine());
        }
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAcctTransHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();

    }

    public double getAcctBalance(int accIdx) {
        return this.accounts.get(accIdx).getBalance();

    }

    public String getAccUUID(int accIdx) {
        return this.accounts.get(accIdx).getUUID();

    }

    public void addAcctTransaction(int accIdx, double amount, String memo) {
        this.accounts.get(accIdx).addTransaction(amount,memo);

    }
}