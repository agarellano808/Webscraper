import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class RegistrationGUI extends JFrame {
	/*The panel where everything is placed*/
	private JPanel contentPanel;
	/*Text field used to input the user name*/
	private JTextField usernameTextField;
	/*Text field used to input the password*/
	private JTextField passwordTextField;
	/* 
	 * An instance of AccountDatabse is initialized which is used to check if
	 * an account already exists and to insert a new account.
	 */
	private AccountDatabase accountDatabase = new AccountDatabase();
	
	/* This method creates the GUI*/
	public RegistrationGUI() {
		setTitle("Registration Screen");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		contentPanel.setLayout(null);
		
		/*A label that serves as the title of the screen*/
		JLabel registrationLabel = new JLabel("Registration");
		registrationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		registrationLabel.setBounds(173, 12, 89, 14);
		contentPanel.add(registrationLabel);
		
		/*The text field for the username is initialized and set up*/
		usernameTextField = new JTextField();
		usernameTextField.setBounds(149, 57, 132, 20);
		contentPanel.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		/*The text field for the password is initialized and set up*/
		passwordTextField = new JTextField();
		passwordTextField.setBounds(149, 88, 134, 20);
		contentPanel.add(passwordTextField);
		passwordTextField.setColumns(10);
		

		
		/*The button to cancel registration is set up*/
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(116, 186, 89, 23);
		contentPanel.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
		LoginGUI frame = new LoginGUI();
		frame.setVisible(true);
	    dispose();
		}
		});
		
		/*Label that shows what to put into the text filed*/
		JLabel enterUsernameLabel = new JLabel("Enter Username:");
		enterUsernameLabel.setBounds(149, 37, 123, 14);
		contentPanel.add(enterUsernameLabel);
		
		/*Label that shows what to put into the text filed*/
		JLabel enterPasswordLabel = new JLabel("Enter Password:");
		enterPasswordLabel.setBounds(149, 76, 132, 14);
		contentPanel.add(enterPasswordLabel);
		
		JCheckBox adminCheckBox = new JCheckBox("Admin");
		adminCheckBox.setBounds(183, 115, 97, 23);
		contentPanel.add(adminCheckBox);
		
		/*The button to confime registration is set up*/
		JButton registorButton = new JButton("Registor");
		registorButton.setBounds(226, 186, 89, 23);
		contentPanel.add(registorButton);
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(136, 146, 163, 29);
		contentPanel.add(messageLabel);
		registorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Boolean accountCreationSuccess;
				if(adminCheckBox.isSelected()) {;
				accountCreationSuccess=accountDatabase.addAccount(usernameTextField.getText(),passwordTextField.getText(),"admin");
				}
				else {
					accountCreationSuccess=accountDatabase.addAccount(usernameTextField.getText(),passwordTextField.getText(),"Standard");
				}
				if(accountCreationSuccess) {
					messageLabel.setText("Account creation was a success");
					usernameTextField.setEditable(false);
					passwordTextField.setEditable(false);
					adminCheckBox.setEnabled(false);
					registorButton.setEnabled(false);
					cancelButton.setEnabled(false);

					LoginGUI frame = new LoginGUI();
					frame.setVisible(true);
				    dispose();
				}
				else {
					messageLabel.setText("User name was already taken, try something else");
				}
			}
			});
	}
}
