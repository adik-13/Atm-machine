import java.util.Date;

public class Transaction {
    //amount,timestamp,memo of transaction
    // and acc in which transaction is performed
    private double amount;
    private Date timeStamp;
    private String memo;
    private Account inAccount;

    public Transaction(double amount,Account account){
        this.amount=amount;
        this.inAccount = account;
        this.timeStamp = new Date();
    }
    public Transaction(double amount,String memo,Account account){
        // call the two arg constructor first

        //set the memo
        this(amount,account);
        this.memo = memo;
    }


    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if(this.amount>=0)
        {
            return String.format("%s : Rs%.02f : %s",
                    this.timeStamp.toString(),this.amount,this.memo);
        }
        else{
            return String.format("%s : Rs(%.02f) : %s",
                    this.timeStamp.toString(),this.amount,this.memo);
        }
    }
}
