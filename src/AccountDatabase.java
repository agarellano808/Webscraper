/*All imports come from the standard hava library*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Set;

public class AccountDatabase {
	/*
	 * The HashTable is the data structure in which the User Accounts are
	 * stored, acting as a database 
	 */
	Hashtable<Integer, UserAccount> database = new Hashtable<Integer, UserAccount>();
	/* A string containing the location of the database file */
	private String databaseLocation;

	/*A constructor for the account database */
	public AccountDatabase() {
		/*The databaseLocation is initialized to the path where the AccountDatabase.txt file is located*/
		databaseLocation = System.getProperty("user.dir") + "\\AccountDatabase.txt";
		/*The initializedDatabase is called to initialize the database*/
		initializedDatabase();
	}

	/*A method used to initialize the database*/
	public void initializedDatabase() {
		/* A file writer used to write to the output file */
		BufferedReader reader;
		/*An string array that will used to store the split information of an account*/
		String[] info = new String[4];
		
		try {
			/*A read to read through the databaseLocation file*/
			reader = new BufferedReader(new FileReader(databaseLocation));
			int j = 0;
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				int i = 0;

				for (String word : line.split("\\|")) {
				
					info[i] = word;
					i++;
				} // End of second for loop
				UserAccount t;
				
				/* A new item is constructed from the info string array */
				if(info[3].equals("admin")) {
					 t = new AdminAccount(info[0], info[1], Integer.parseInt(info[2]));
				}
				else {
					 t = new UserAccount(info[0], info[1], Integer.parseInt(info[2]));
				}

				/* The new item is put into the Hash Table */
				database.put(Integer.parseInt(info[2]), t);
				j++;

			}
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/*
	 *A method used to add an account to the database
	 * 
	 * @param userName: The user name inputed by the user
	 *        password: The password inputed by the user
	 *        typeOfAccount: The type of account inputed by the sys
	 */
	public boolean addAccount(String username, String password, String accountType) {
		boolean accountCreated=false;
		if (!UsernameExistsAlready(username)) {
			if(accountType=="admin") {
				AdminAccount u = new AdminAccount(username, password, database.size());
				database.put(database.size(), u);
				updateDatabase();
				accountCreated= true;
			}
			else {
			UserAccount u = new UserAccount(username, password, database.size());
			database.put(database.size(), u);
			updateDatabase();
			accountCreated= true;
			}
		} 
		return accountCreated;
	}
    /*
     * A method used to delete account from the database
     * 
     * @param: accountNumber: The account number of the account that is to be deleted
     */
	public void deleteAccount(int accountNumber) {
		int d=database.size();
	
		/*The account is removed from the Hash Table using its remove method and using the accountnubmer as a paramer*/
		database.remove(accountNumber);
		if(!database.isEmpty()) {
			for(int i=accountNumber;i<d-1;i++) {
				database.put(i,database.get(i+1));
				database.get(i).setAccountNumber(i);
			}
	
			database.remove(d-1);
		
			}
		/*The database file and the Hashtable are updated*/
		updateDatabase();
	}

	/*
	 * A method used to check if a user can login with the information
	 * they submitted
	 * 
	 * @param userName: The user name inputed by the user
	 *        password: The password inputed by the user
	 */
	public boolean checkLogIn(String username, String password) {
		UserAccount a=findUserAccount(username);
		Boolean logIn=false;
		if(a!=null&&checkPassword(password,a)) {
			logIn= true;
		}
		return logIn;
	}
	/*
	 *A method used to check if a username is already used by another account
	 * 
	 * @param userName: The user name inputed by the user
	 */
	public boolean UsernameExistsAlready(String username) {
		Set<Integer> keys = database.keySet();
		boolean found = false;

		for (Integer key : keys) {
			if (username.equals(database.get(key).getUserName())) {
				found = true;
				break;
			}
		}
		return found;
	}
	/*
	 * A method used to to find a user account
	 * 
	 * @param userName: The user name inputed by the user
	 */
	public UserAccount findUserAccount(String username) {
		Set<Integer> keys = database.keySet();
		UserAccount u = null;
		for (Integer key : keys) {
			if (username.equals(database.get(key).getUserName())) {
				u= database.get(key);
				break;
			}
		}	
		return u;
	}
/*
  	*A method used to see if a the inputed password and the account match 
	 * 
	 * @param  password: The password inputed by the user
 */
	public boolean checkPassword(String password,UserAccount account) {
		Boolean found=false;
			if (password.equals(account.getPassword())) {
				found=true;
		}
		return found;
	}

	/*
	 * A method used to update the database after adding,deleting or modifying an account
	 */
	public void updateDatabase() {
		/* A file writer used to write all the info into an output file */
		try {
			FileWriter fileWriter = new FileWriter(databaseLocation);
			/*
			 * In this loop each key represent one item. Each item's information is
			 * retrieved using their key and is written into the output file
			 */
			for (int i=0;i<database.size();i++) {
				
				fileWriter.write(database.get(i).getUserName() + "|" + database.get(i).getPassword() + "|"
						+ database.get(i).getAccountNumber() + "|"+ database.get(i).getAccountType()+"\n");
			}
			fileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void modifyUsername(UserAccount user, String newUsername) {
		UserAccount tempAccount=database.get(user.getAccountNumber());
		tempAccount.setUsername(newUsername);
		updateDatabase();
	}
	
	public void modifyPassword(UserAccount user, String newPassword) {
		UserAccount tempAccount=database.get(user.getAccountNumber());
		tempAccount.setPassword(newPassword);
		updateDatabase();
	}
	
	public void adminModifyPassword(int key, String newPassword) {
		UserAccount tempAccount=database.get(key);
		tempAccount.setPassword(newPassword);
		updateDatabase();
	}
	public void adminModifyUsername(int key, String newUsername) {
		UserAccount tempAccount=database.get(key);
		tempAccount.setUsername(newUsername);
		updateDatabase();
	}

	public String printDatabase() {
		String content="";
		/* A file writer used to write all the info into an output file */
		for(int i=0;i<database.size();i++) {
			content=content+database.get(i).getUserName() + "|" + database.get(i).getPassword() + "|"
		+ database.get(i).getAccountNumber() + "|"+ database.get(i).getAccountType()+"\n";
		}

	return content;
	}
}

