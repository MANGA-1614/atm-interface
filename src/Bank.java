import java.util.ArrayList;
import java.util.Random;

public class Bank {

	//Name of bank
	private String name;
	
	//List of customers
	private ArrayList<User> users;
	
	//Bank keeps track of the accounts it has
	//A big list of accounts
	//More direct that customers.accounts
	private ArrayList<Account> accounts;
	
	/*
		Constructor
		Create a new Bank obj with empty lists of users and accounts
		@param	name	Name of the bank
	*/
	public Bank(String name) {
		
		this.name = name;
		this.users = new ArrayList<User>();
		this.accounts = new ArrayList<Account>();
	}
	
	/*
		Generate a new universally-unique ID for a User
	*/
	public String getNewUserUUID() {
		
		String output = "";
		
		Random rng = new Random();
		
		int length = 6;
		boolean nonUnique = false;
		
		//Continue looping while we have a nonUnique
		do {
			
			//Generate the number
			for (int i = 0; i < length; i++) {
				//0-inclusive, so it's 0-9
				output += ((Integer)rng.nextInt(10)).toString();
			}
			
			//Check if unique
			for (User u : this.users) {
				if (output.compareTo(u.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return output;
	}
	
	/*
		Generate a new universally-unique ID for an Account
	*/
	public String getNewAccountUUID() {
		
		String output = "";
		
		Random rng = new Random();
		
		int length = 10;
		boolean nonUnique = false;
		
		//Continue looping while we have a nonUnique
		do {
			
			//Generate the number
			for (int i = 0; i < length; i++) {
				//0-inclusive, so it's 0-9
				output += ((Integer)rng.nextInt(10)).toString();
			}
			
			//Check if unique
			for (Account acc : this.accounts) {
				if (output.compareTo(acc.getUUID()) == 0) {
					nonUnique = true;
					break;
				}
			}
			
		} while (nonUnique);
		
		return output;
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}
	public User addUser(String firstName, String lastName, String pin) {
		
		//Create new User obj and add to list of users
		User newUser = new User(firstName, lastName, pin, this);
		this.users.add(newUser);
		
		//Create a savings account
		Account newAccount =  new Account("Savings", newUser, this);
		
		
		//Add Account to User and Bank's list
		//User and Bank point to the same object
		newUser.addAccount(newAccount);
		this.addAccount(newAccount);
			
		return newUser;
	}
	
	/**
		User's Login
		If wrong credentials, then return null
		
		@param	userID	the UUID of the user logging in
		@param	pin		the pin the user is using
		@return			Return the User obj if credentials are correct, otherwise null
	*/
	public User userLogin(String userID, String pin) {
		
		//Search to users list
		for (User u : this.users) {
			
			//Check for user
			if ((u.getUUID().compareTo(userID) == 0) && u.validatePin(pin)) {
				return u;
			}
		}
		
		return null;
	}
	
	/*
		Get the name of the bank
		@return			tne name of the bank
	*/
	public String getName() {
		return this.name;
	}
}
