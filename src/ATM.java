import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        //init bank
        Bank theBank = new Bank("Bank of Adi");
        //add a user , which also create a savings acc
        User aUser = theBank.addUser("John","Doe","1234");
        //add checking account for our user
        Account newAccount = new Account("Checking",aUser,theBank);
        theBank.addAccount(newAccount);

        User curUser;
        while(true){
            //stay in the login prompt until successful login
            curUser = ATM.mainMenuPrompt(theBank,sc);

            //stay in the main menu until user quits
            ATM.printUserMenu(curUser,sc);
        }
    }

    private static void printUserMenu(User theUser, Scanner sc) {
        // print a summary of user's accounts
        theUser.printAccountsSummary();

        //inits
        int choice;

        //user menu
        do{
            System.out.printf("Welcome %s , what would you like to do ?",theUser.getFirstName());
            System.out.println("\n 1) Show account transaction history");
            System.out.println("2) Withdrawal");
            System.out.println("3) Deposit");
            System.out.println("4) Transfer");
            System.out.println("5) Quit");
            System.out.println();
            System.out.println("Enter choice : ");
            choice = sc.nextInt();
            if(choice<1 || choice >5){
                System.out.println("Invalid choice , Enter correct option 1:5");
            }


        }while (choice<1 || choice>5);

        switch (choice){
            case 1 -> ATM.showTransactionHistory(theUser,sc);
            case 2 -> ATM.withdrawFunds(theUser,sc);
            case 3 -> ATM.depositFunds(theUser,sc);
            case 4 -> ATM.transferFunds(theUser,sc);

        }
        //redisplay this menu unless the user wants to quite
        if(choice!=5)
            ATM.printUserMenu(theUser,sc);
    }

    private static void depositFunds(User theUser, Scanner sc) {
        //inits
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        //get acc to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of" +
                    " the account \n"+"to deposit to : ");
            toAcct = sc.nextInt()-1;
            if(toAcct <0 || toAcct>=theUser.numAccounts() ){
                System.out.println("Invalid account ..... Please try again");
            }

        }while (toAcct <0 || toAcct>=theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);
        //get the amount to deposit
        do{
            System.out.printf("Enter the amount to deposit (max Rs%.02f): Rs"
                    ,acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than zero .");
            }else if(amount > acctBal){
                System.out.printf("Amount must not be greater than \n"+
                        "balance of Rs%.02f.\n ",acctBal);
            }
        }while(amount<0 || amount>acctBal);

        //gobble up rest of previous input
        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(toAcct,amount,memo);

    }

    private static void withdrawFunds(User theUser, Scanner sc) {
        //inits
        int fromAcct;
        double amount;
        double acctBal;
        String memo;

        //get acc to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of" +
                    " the account \n"+"to transfer from : ");
            fromAcct = sc.nextInt()-1;
            if(fromAcct <0 || fromAcct>=theUser.numAccounts() ){
                System.out.println("Invalid account ..... Please try again");
            }

        }while (fromAcct <0 || fromAcct>=theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);
        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max Rs%.02f): Rs"
                    ,acctBal);
            amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than zero .");
            }else if(amount > acctBal){
                System.out.printf("Amount must not be greater than \n"+
                        "balance of Rs%.02f.\n ",acctBal);
            }
        }while(amount<0 || amount>acctBal);

        //gobble up rest of previous input
        sc.nextLine();

        //get a memo
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();

        //do the withdrawal
        theUser.addAcctTransaction(fromAcct,-1*amount,memo);

    }

    private static void transferFunds(User theUser, Scanner sc) {
        //inits
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        //get acc to transfer from
        do{
            System.out.printf("Enter the number (1-%d) of" +
                    " the account \n"+"to transfer from : ");
            fromAcct = sc.nextInt()-1;
            if(fromAcct <0 || fromAcct>=theUser.numAccounts() ){
                System.out.println("Invalid account ..... Please try again");
            }

        }while (fromAcct <0 || fromAcct>=theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        //get the acc to transfer to
        do{
            System.out.printf("Enter the number (1-%d) of" +
                    " the account \n"+"to transfer to : ");
            toAcct = sc.nextInt()-1;
            if(toAcct <0 || toAcct>=theUser.numAccounts() ){
                System.out.println("Invalid account ..... Please try again");
            }

        }while (toAcct <0 || toAcct>=theUser.numAccounts());

        //get the amount to transfer
        do{
            System.out.printf("Enter the amount to transfer (max Rs%.02f): Rs"
            ,acctBal);
        amount = sc.nextDouble();
            if(amount<0){
                System.out.println("Amount must be greater than zero .");
            }else if(amount > acctBal){
                System.out.printf("Amount must not be greater than \n"+
                        "balance of Rs%.02f.\n ",acctBal);
            }
        }while(amount<0 || amount>acctBal);

        //finally , do the transfer
        theUser.addAcctTransaction(fromAcct,-1*amount,String.format(
                "transfer to account %s",theUser.getAccUUID(toAcct)));

    }

    private static void showTransactionHistory(User theUser, Scanner sc) {
        int theAcct;
        //get acc whose transaction history to look at
        do{
            System.out.printf("Enter the number (1-%d) of the account "
                    +"whose transactions you want to see : ",theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if(theAcct <0 || theAcct >= theUser.numAccounts()){
                System.out.println("Invalid account , please try again");
            }
        }while(theAcct>0 || theAcct >=theUser.numAccounts());
        theUser.printAcctTransHistory(theAcct);
    }

    private static User mainMenuPrompt(Bank theBank, Scanner sc) {
        //inits
        String userID;
        String pin;
        User authUser;

        //prompt user for user id/pin combo until it is correct
        do{
            System.out.printf("\n Welcome to %s \n\n",theBank.getName());
            System.out.println("Enter user id : ");
            userID = sc.nextLine();
            System.out.println("Enter the pin : ");
            pin = sc.nextLine();

            //try to get user object corresponding to id and pin combo
            authUser = theBank.userLogin(userID,pin);
            if(authUser==null)
                System.out.println("Incorrect user id/pin combination ... Try again");

        }while(authUser==null); //continue looping until successful login
        return authUser;
    }
}
