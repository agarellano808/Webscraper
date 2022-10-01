
public class AdminAccount extends UserAccount {
	/*
	 * Constructor for a Admin Account
	 * @param userName: The user name inputed by the user
	 *        password: The password inputed by the user
	 *        typeOfAccount: The type of account inputed by the system
	 */
	public AdminAccount(String userName, String password, int AccountNumber) {
		/*User account constructor is invoked using the parameters*/
		super(userName, password,AccountNumber);
		/*The account type is initialized to "admin"*/
		this.accountType="admin";
	}
}
