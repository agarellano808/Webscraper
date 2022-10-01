public class UserAccount {
	/*String variable used to represent the user name of the account*/
	private String username;
	/*String variable used to represent the password of the account*/
	private String password;
	/*Integer variable used to represent the acount number of the account*/
	private int accountNumber;
	/*String variable used to represent the user name of the account*/
	protected String accountType;
	
	/*
	 * Constructor for a User Account
	 * @param userName: The user name inputed by the user
	 *        password: The password inputed by the user
	 *        typeOfAccount: The type of account inputed by the system
	 */
	public UserAccount(String username, String password,int accountNumber) {
		/*The username is initialized to the username parameter*/
		this.username=username;
		/*The password is initialized to the password parameter*/
		this.password=password;
		/*The accountNumber is initialized to the accountNumber parameter*/
		this.accountNumber=accountNumber;
		/*The account type is initialized to "standard"*/
		this.accountType="Standard";
	}
	
	/*
	 * Constructor for a User Account
	 * 
	 * @param userName: The user name inputed by the user
	 */
	public UserAccount(String username) {
		/*The username is initialized to the username parameter*/
		this.username=username;
		/*The account type is initialized to "standard"*/
		this.accountType="Standard";
	}
	
	/*
	 * A method that returns the username string
	 * 
	 * @return username: The username of the account
	 */
	public String getUserName() {
		return username;
	}
	/*
	 * A method that returns the password string.
	 * 
	 * @return password: The password of the account
	 */
    public String getPassword() {
		return password;
	}
    
	/*
	 * A method that returns the accountNumber integer
	 * 
	 * @return accountNumber: The account number of the account
	 */
    public int getAccountNumber() {
		return accountNumber;
	}
	
	/*
	 * A method that returns the accountType string
	 * 
	 * @return accountType: The type of account
	 */
    public String getAccountType() {
		return accountType;
	}
    
	/*
	 * A method that returns the accountType string
	 * 
	 * @return accountType: The type of account
	 */
    public void setPassword(String password) {
    	this.password=password;
	}
	/*
	 * A method that returns the accountType string
	 * 
	 * @return accountType: The type of account
	 */
    public void setUsername(String username) {
    	this.username=username;
	}
	/*
	 * A method that returns the accountType string
	 * 
	 * @return accountType: The type of account
	 */
    public void setAccountNumber(int accountNumber) {
    	this.accountNumber=accountNumber;
	}
}
