import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account> accounts;

    //generate a new uuid for a user
    public String getNewUserUUId() {
        //inits
        String uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;
        //continue looping until we get a unique id
        do{
            uuid = "";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //check to make sure its unique
            nonUnique=false;
            for(User u:this.users){
                if(uuid.compareTo(u.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }
        }while(nonUnique);
        return uuid;
    }
    //generate a new uuid for a account
    public String getNewAccountUUID() {
        //inits
        String uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;
        //continue looping until we get a unique id
        do{
            uuid = "";
            for(int i=0;i<len;i++){
                uuid += ((Integer)rng.nextInt(10)).toString();
            }

            //check to make sure its unique
            nonUnique=false;
            for(Account a:this.accounts){
                if(uuid.compareTo(a.getUUID())==0){
                    nonUnique=true;
                    break;
                }
            }
        }while(nonUnique);
        return uuid;
    }

    public void addAccount(Account account) {
        this.accounts.add(account);
    }

    public User addUser(String firstName,String lastName,String pin){
        //create a new user object and add it to our list
        User newUser = new User(firstName,lastName,pin,this);
        this.users.add(newUser);
        //create a savings account for the user
        Account newAccount = new Account("Savings",newUser,this);
        //add to holder and bank lists
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;
    }

    public String getName() {
        return name;
    }

    public Bank(String name) {
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public User userLogin(String userID, String pin){
        //search for user in list

        for(User u:this.users){
            //check userid is correct
            if(u.getUUID().compareTo(userID)==0 && u.validatePin(pin)){
                return u;
            }
        }
        //if userid or pin is wrong
        return null;
    }
}
