
public class GuestAccount extends UserAccount {
	/*
	 * Constructor for a Guest Account
	 * 
	 * @param userName: The user name inputed by the user
	 */
	public GuestAccount(String userName) {
		/*User account constructor is invoked using the parameters*/
		super(userName);
		/*The account type is initialized to "guest"*/
		this.accountType="guest";
	}
}
