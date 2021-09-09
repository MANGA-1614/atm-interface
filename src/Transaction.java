import java.util.Date;

public class Transaction {
	//Amount of the transaction
	private double amount;
	
	//When the transaction happened
	private Date timestamp;
	
	//Note of the transaction
	private String memo;
	
	//Account linked to transaction
	private Account inAccount;
	
	public Transaction(double amount, Account account) {
		this.amount = amount;
		this.inAccount = account;
		this.timestamp = new Date();
	}
	
	public Transaction(double amount, String memo, Account account) {
		//Call the previous Constructor so that change there happens here
		this(amount, account);
		
		this.memo = memo;
	}
	
	/**
		Get the amount of the transaction
		@return		amount
	*/
	public double getAmount() {
		return this.amount;
	}
	
	/**
		Get the summary of the transaction
		@return		Formatted text of timestamp, amount, and memo
	*/
	public String getSummaryLine() {
		if (this.getAmount() >= 0) {
			//Timestamp > Amount > Memo
			return String.format("%s | $%.02f | %s\n", this.timestamp.toString(), this.getAmount(), this.memo);
		} else {
			return String.format("%s | $(%.02f) | %s\n", this.timestamp.toString(), this.getAmount(), this.memo);
		}
		
	}
}
