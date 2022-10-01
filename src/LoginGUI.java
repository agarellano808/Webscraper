import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class LoginGUI extends JFrame {

	/* Panel where everything is placed */
	private JPanel contentPane;
	/*Text field used to input the user name*/
	private JTextField usernameField;
	/*Text field used to input the password*/
	private JPasswordField passwordField;
	/* An instance of AccountDatabse is initialized which is used to check if
	 * an account exists.
	 */
	private AccountDatabase database = new AccountDatabase();

	/* Creates the frame. */
	public LoginGUI() {
		setTitle("Login Screen");
		/* Setting the contentPane */
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		/* The text field for the username is initialized and set up */
		usernameField = new JTextField();
		usernameField.setBounds(106, 59, 213, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);

		/* The text field for the password is initialized and set up */
		passwordField = new JPasswordField();
		passwordField.setBounds(106, 115, 213, 20);
		contentPane.add(passwordField);

		/* Label that shows what to put into the text filed */
		JLabel usernameLabel = new JLabel("USERNAME:");
		usernameLabel.setBounds(106, 46, 107, 14);
		contentPane.add(usernameLabel);

		/* Label that shows what to put into the text filed */
		JLabel passwordLabel = new JLabel("PASSWORD:");
		passwordLabel.setBounds(106, 100, 107, 14);
		contentPane.add(passwordLabel);

		/* Button to access registration screen */
		JButton createAccountButton = new JButton("Registor");
		createAccountButton.setBounds(106, 146, 114, 23);
		contentPane.add(createAccountButton);
		createAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RegistrationGUI frame = new RegistrationGUI();
				frame.setVisible(true);
				dispose();
			}
		});


		JCheckBox guestCheckbox = new JCheckBox("Guest");
		guestCheckbox.setBounds(325, 58, 62, 23);
		contentPane.add(guestCheckbox);
		guestCheckbox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(passwordField.isEditable()) {
				passwordField.setEditable(false);
				}
				else {
					passwordField.setEditable(true);
				}
			}
		});
		/* Button to log in */
		JButton logInButton = new JButton("Log In");
		logInButton.setBounds(230, 146, 89, 23);
		contentPane.add(logInButton);
		logInButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserAccount userAccount;
				if(guestCheckbox.isSelected()) {
					userAccount= new GuestAccount(usernameField.getText());
					SearchScreenGUI frame = new SearchScreenGUI(userAccount);
						frame.setVisible(true);
						dispose();
				}
				else {
				if (database.checkLogIn(usernameField.getText(), passwordField.getText())) {
					userAccount=database.findUserAccount(usernameField.getText());
					SearchScreenGUI frame = new SearchScreenGUI(userAccount);
					frame.setVisible(true);
					dispose();
				}
				}
			}
		});
	}
}
