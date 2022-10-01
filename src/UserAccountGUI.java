import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserAccountGUI extends JFrame {

	private JPanel contentPane;
	private JTextField usernameField;
	private JTextField passwordField;
	AccountDatabase accountDatabase=new AccountDatabase();

	public UserAccountGUI(UserAccount userAccount) {
		setTitle("User Account ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 322);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel usernameLabel = new JLabel("Username");
		usernameLabel.setBounds(78, 11, 292, 14);
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(usernameLabel);
		
		
		JLabel usernameDisplayLabel = new JLabel("");
		usernameDisplayLabel.setBounds(148, 34, 147, 14);
		usernameDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(usernameDisplayLabel);
		usernameDisplayLabel.setText(userAccount.getUserName());
		
		JLabel passwordLabel = new JLabel("Password");
		passwordLabel.setBounds(78, 71, 292, 14);
		passwordLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(passwordLabel);
		
		JLabel passwordDisplayLabel = new JLabel("");
		passwordDisplayLabel.setBounds(148, 109, 147, 14);
		passwordDisplayLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contentPane.add(passwordDisplayLabel);
		passwordDisplayLabel.setText(userAccount.getPassword());

		usernameField = new JTextField();
		usernameField.setBounds(160, 175, 243, 20);
		contentPane.add(usernameField);
		usernameField.setColumns(10);
		
		passwordField = new JTextField();
		passwordField.setBounds(160, 217, 243, 20);
		contentPane.add(passwordField);
		passwordField.setColumns(10);
		
		JButton usernameChangeButton = new JButton("Change Username");
		usernameChangeButton.setBounds(10, 174, 121, 23);
		contentPane.add(usernameChangeButton);
		usernameChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.modifyUsername(userAccount,usernameField.getText());
				userAccount.setUsername(usernameField.getText());
				usernameDisplayLabel.setText(userAccount.getUserName());
			}
		});
		
		JButton passwordChangeButton = new JButton("Change Password");
		passwordChangeButton.setBounds(10, 216, 121, 23);
		contentPane.add(passwordChangeButton);
		passwordChangeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.modifyPassword(userAccount,passwordField.getText());
				userAccount.setPassword(passwordField.getText());
				passwordDisplayLabel.setText(userAccount.getPassword());
			}
		});
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(10, 250, 121, 23);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SearchScreenGUI frame = new SearchScreenGUI(userAccount);
				frame.setVisible(true);
			    dispose();
			}
		});
	
	}
}
