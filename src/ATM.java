import java.util.Scanner;

public class ATM {
	
	public static void main(String[] args) {
		
		//init Scanner
		Scanner sc = new Scanner(System.in);
		
		//init Bank
		Bank theBank= new Bank("Bank of John");
		
		/*
			Add User to bank
			Also creates a Chequing Account
			Hard-coded pin for easier testing
			Not recommended IRL
		*/
		User initUser = theBank.addUser("John", "Bao", "1234");
		Account initAcc = new Account("Chequing", initUser, theBank);
		
		//Add initAccount to User and Bank
		initUser.addAccount(initAcc);
		theBank.addAccount(initAcc);
		
		//Make Login prompt
		User curUser;
		while(true) {
			
			//Always pass the scanner because only 1 instance can be on
			
			//Stay in the login prompt
			curUser = ATM.mainMenuPrompt(theBank, sc);
			
			//Stay in menu until user quits
			ATM.printUserMenu(curUser, sc);
		}
	}
	
	public static User mainMenuPrompt(Bank theBank, Scanner sc) {
		
		//init
		String userID, pin;
		User authUser;
		
		//Prompt user for userID and pin until the correct one is reached
		do {
			System.out.printf("\n\nWelcome to the %s\n\n", theBank.getName());
			System.out.print("Enter userID: ");
			userID = sc.nextLine();
			System.out.printf("Enter pin: ");
			pin = sc.nextLine();
			
			//Get the corresponding User
			authUser = theBank.userLogin(userID, pin);
			if (authUser == null) {
				System.out.println("Incorrect userID or pin. Please try again.");
			}
		} while (authUser == null); //Keep looping until success
		
		//Once outside, the User is not null > Successful Login
		
		return authUser;
	}
	
	public static void printUserMenu(User user, Scanner sc) {
		
		//Print a summary of user's account
		user.printAccountSummary();
		
		//init
		int choice;
		
		//user menu
		do {
			System.out.printf("Welcome [%s]. What would you like to do?\n", user.getFirstName());
			System.out.println("Options:");
			System.out.println("\t1: Show account transaction history");
			System.out.println("\t2: Withdraw");
			System.out.println("\t3: Deposit");
			System.out.println("\t4: Transfer");
			System.out.println("\t5: Quit");
			System.out.println();
			System.out.println("Enter option: ");
			choice = ((int)sc.nextFloat());
			
			//We have to check input is within bounds
			if (choice < 1 || 5 < choice) {
				System.out.printf("\n[%s] is an invalid option\n", choice);
			}
		} while (choice < 1 || 5 < choice);
		
		//Process choice
		switch (choice) {
		
		case 1:
			System.out.println("You selected [Show account transaction history]");
			ATM.showTransactionHistory(user, sc);
			break;
		case 2:
			System.out.println("You selected [Withdraw]");
			ATM.withdraw(user, sc);
			break;
		case 3:
			System.out.println("You selected [Deposit]");
			ATM.deposit(user, sc);
			break;
		case 4:
			System.out.println("You selected [Transfer]");
			ATM.transfer(user, sc);
			break;			
		}
		
		//Redisplay the menu > Recursion
		if (choice != 5) {
			ATM.printUserMenu(user, sc);
		}
	}

	/**
	 	Show the transaction history
	 	@param	user	the user
	 	@param	sc		scanner to get inputs from user
	*/
	public static void showTransactionHistory(User user, Scanner sc) {
		
		int account;
		
		do {
			System.out.printf("Enter the number (1 - %d) of the account:", user.getNumAccounts());
			account = ((int)sc.nextFloat()) - 1;
			
			if ((account < 0) || (user.getNumAccounts() < account)) {
				System.out.println("That is an invalid choice. Please try again.");
			}
		} while ((account < 0) || (user.getNumAccounts() < account));
		
		//print transaction history
		user.printAccTransactionHistory(account);
	}
	
	/**
		Transfer funds
	 	@param user		the user that wants to transfer
	 	@param sc		scanner to get inputs from user
	*/
	public static void transfer(User user, Scanner sc) {
		//init
		int fromAcc, toAcc;
		double amount;
		double accBalance;
		
		//Get the acc to transfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer from:", user.getNumAccounts());
			fromAcc = ((int)sc.nextFloat()) - 1;
			
			if ((fromAcc < 0) || (user.getNumAccounts() < fromAcc)) {
				System.out.println("That is an invalid choice. Please try again.");
			}
		} while ((fromAcc < 0) || (user.getNumAccounts() <= fromAcc));
		
		accBalance = user.getAccBalance(fromAcc);
		
		//Get the acc to transfer to
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer to:", user.getNumAccounts());
			toAcc = ((int)sc.nextFloat()) - 1;
			
			if ((toAcc < 0) || (user.getNumAccounts() < toAcc)) {
				System.out.println("That is an invalid choice. Please try again.");
			}
		} while ((toAcc < 0) || (user.getNumAccounts() <= toAcc));
		
		do {
			System.out.printf("Enter amount to transfer (max $%.02f): $", accBalance);
			amount = sc.nextDouble();
			
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (accBalance < amount) {
				System.out.printf("Amount must be less than max ($%.02f)\n", accBalance);
			}
		} while ((amount < 0) || (accBalance < amount));
		
		//Do transfer
		
		//Subtract from the first account
		user.addAccountTransaction(fromAcc, -1 * amount, String.format("Transfer to account %s", user.getAccUUID(toAcc)));
		
		//Add to the second account
		user.addAccountTransaction(toAcc, amount, String.format("Transfer from account %s", user.getAccUUID(fromAcc)));
	}
	
	/**
	 	Withdraw money
	 	@param user		the user that wants to withdraw
	 	@param sc		scanner to get inputs from user
	*/
	public static void withdraw(User user, Scanner sc) {
		//init
		int fromAcc;
		double amount;
		double accBalance;
		String memo;
		
		//Get the acc to transfer from
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer from:", user.getNumAccounts());
			fromAcc = ((int)sc.nextFloat()) - 1;
			
			if ((fromAcc < 0) || (user.getNumAccounts() < fromAcc)) {
				System.out.println("That is an invalid choice. Please try again.");
			}
		} while ((fromAcc < 0) || (user.getNumAccounts() <= fromAcc));
		
		accBalance = user.getAccBalance(fromAcc);
		
		//Get the amount to withdraw
		do {
			System.out.printf("Enter amount to transfer (max $%.02f): $", accBalance);
			amount = (double)sc.nextFloat();
			
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			} else if (accBalance < amount) {
				System.out.printf("Amount must be less than max ($%.02f)\n", accBalance);
			}
		} while ((amount < 0) || (accBalance < amount));
		
		//Gobble up the previous input line
		sc.nextLine();
		
		//Get the memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//Do the withdraw
		user.addAccountTransaction(fromAcc, -1*amount, memo);
	}
	
	/**
	 	Deposit money
	 	@param user		the user that wants to deposit
	 	@param sc		scanner to get inputs from user
	*/
	public static void deposit(User user, Scanner sc) {
		//init
		int toAcc;
		double amount;
		String memo;
		
		//Get the acc to transfer to
		do {
			System.out.printf("Enter the number (1 - %d) of the account to transfer to:", user.getNumAccounts());
			//Runs an error if the input is not an int
			toAcc = ((int)sc.nextFloat()) - 1;
			
			if ((toAcc < 0) || (user.getNumAccounts() < toAcc)) {
				System.out.println("That is an invalid choice. Please try again.");
			}
		} while ((toAcc < 0) || (user.getNumAccounts() <= toAcc));
				
		do {
			System.out.printf("Enter amount to transfer: $");
			amount = (double)sc.nextFloat();
			
			if (amount < 0) {
				System.out.println("Amount must be greater than 0");
			}
			
			//Removed check for when the deposit is greater than accBalance
			//Reason: it should be possible to put more than the accBalance
		} while (amount < 0);
		
		//Gobble up the previous input line
		sc.nextLine();
		
		//Get the memo
		System.out.println("Enter a memo: ");
		memo = sc.nextLine();
		
		//Do the withdraw
		user.addAccountTransaction(toAcc, amount, memo);
		
	}

}
