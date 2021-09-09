import java.util.ArrayList;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {

	//User's first name
	private String firstName;
	
	//User's last name
	private String lastName;
	
	//User's universal unique id
	private String uuid;
	
	//User's pin number in MD5 Hash
	private byte pinHash[];
	
	//List of user's accounts
	private ArrayList<Account> accounts;
	
	/**
		Create User
		@param	firstName 	User's first name
		@param	lastName	User's last name
		@param	pin			User's account pin code
		@param 	theBank		User's bank
	*/
	
	//Constructor
	public User(String firstName, String lastName, String pin, Bank theBank) {
		
		//Set names
		this.firstName = firstName;
		this.lastName = lastName;
		
		/*
			Hash pin and set hash
			Security
			Try-Catch surrounds the problem then does something when it catches an error
			We know MD5 is valid, but we do this to appease Java
		*/
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			//Gets the memory of pin and turns that into hash
			//This works because the bytes of pin is the location
			this.pinHash = md.digest(pin.getBytes());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: NoSuchAlgorithmException");;
			e.printStackTrace();
			System.exit(1);
		}
		
		//Java has some libraries to generate a uuid
		//For this, I make it myself
		this.uuid = theBank.getNewUserUUID();
		
		//Create list of accounts
		this.accounts = new ArrayList<Account>();
		
		//Send message that this is done
		System.out.printf("User [%s, %s] created with ID: [%s].\n", this.lastName, this.firstName, this.uuid);
	}
	
	/**
		Add an Account to User
		@param 	account		the Account to add
	*/
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	
	/*
		Get the User's UUID
	*/
	public String getUUID() {
		
		return this.uuid;
	}
	
	/**	
		Validate the pin
		@param	pin			Pin the user is using to login
		@return				Whether the pin is valid or not
	*/
	
	public boolean validatePin(String pin) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			
			//Compares the pinHash and the hash of the @param pin
			return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			System.err.println("Error: NoSuchAlgorithmException");;
			e.printStackTrace();
			System.exit(1);
		}
		
		return false;
	}
	
	/*
		Get the User's UUID
	*/
	public String getFirstName() {
		
		return this.firstName;
	}
	
	/*
		Output the User's transaction history
	*/
	public void printAccountSummary() {
		
		System.out.printf("\n\n[%s]'s Accounts Summary\n", this.firstName);
		
		for (int i = 0; i < this.accounts.size(); i++) {
			/*
				%d		number
				%s		summary of account
			*/
			System.out.printf("\t%d: %s\n", i + 1, this.accounts.get(i).getSummary());
		}
		
		System.out.println();
	}
	
	/**
		Get the number of accounts 
		@return 		the number of accounts the user has
	*/
	public int getNumAccounts() {
		
		return this.accounts.size();
	}
	
	/** 
		Print transaction history for an account
		@param	accIdx	the index of the account to use
	*/
	public void printAccTransactionHistory(int accIdx) {
		
		this.accounts.get(accIdx).printTransactionHistory();
	}
	
	public double getAccBalance(int accIdx) {
		return this.accounts.get(accIdx).getBalance();
	}
	
	/**
		Get the UUID of the account
		@param accIdx	id of the account
	 	@return			the UUID of the account
	*/
	public String getAccUUID(int accIdx) {
		return this.accounts.get(accIdx).getUUID();
	}

	public void addAccountTransaction(int accIdx, double amount, String memo) {
		this.accounts.get(accIdx).addTransaction(amount, memo);
	}
}
