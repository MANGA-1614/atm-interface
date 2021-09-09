import java.util.ArrayList;

public class Account {

	//Account name
	private String name;
	
	//Account's balance
	//Removed because Transaction can do it
	//private double balance;
	
	//Account's uuid, different from User's uuid
	private String uuid;
	
	//Link to User of the account
	private User holder;
	
	//List of transactions
	private ArrayList<Transaction> transactions;
	
	//Constructor
	public Account(String name, User holder, Bank theBank) {
		
		//Set name
		this.name = name;
		
		//Set holder
		this.holder = holder;
		
		//Get new account UUID
		this.uuid = theBank.getNewAccountUUID();
		
		//Init transactions
		this.transactions = new ArrayList<Transaction>();

	}
	
	/**
		Get the User's UUID
		@return			the uuid
	*/
	public String getUUID() {
		
		return this.uuid;
	}
	
	/**
		Get Summary of account
		@return 		the string summary
	*/
	
	public String getSummary() {
		
		//Get balance
		double balance = this.getBalance();
		
		//format summary line, depending if the balance is negative or not
		if (balance >= 0) {
			//Name, then ID, then balance, 
			return String.format("%s\t|\t%s\t|\t$%.02f", this.name, this.uuid, balance);
		} else {
			return String.format("%s\t|\t%s\t|\t$(%.02f)", this.name, this.uuid, balance);
		}
	}
	
	/**
		Get the total balance from all transactions
		@return 	balance
	*/
	public double getBalance() {
		
		double balance = 0;
		
		for (Transaction t : this.transactions) {
			balance += t.getAmount();
		}
		
		return balance;
	}
	
	/**
		Print the transaction history
	*/
	public void printTransactionHistory() {
		
		System.out.printf("\nTransaction History for account [%s]\n", this.uuid);
		
		for (int i = this.transactions.size() - 1; i >= 0; i--) {
			System.out.printf(this.transactions.get(i).getSummaryLine());
		}
		
		System.out.println();
	}

	/**
		Add transaction to account history
	 	@param amount		Amount to transact
	 	@param memo			Note
	*/
	public void addTransaction(double amount, String memo) {
		//Create new transaction then add to list
		Transaction transaction = new Transaction(amount, memo, this);
		this.transactions.add(transaction);
	}
}
