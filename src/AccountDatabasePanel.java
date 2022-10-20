import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class AccountDatabasePanel extends JFrame {
    /*The panel where everything is held*/
	private JPanel contentPane;
	private JTextField usernameTextField;
	private JTextField accountNumberTextField;
	private JTextField passwordTextField;
	private JScrollPane scrollPane;
	private JTextArea displayArea;
	private JLabel messageLabel;
	private JButton backButton;
	private JButton changePasswordButton;
	private JButton deleteButton;
	private JLabel accountNumberLabel;
	private JButton changeUsernameButton;
	AccountDatabase accountDatabase=new AccountDatabase();
	
	public AccountDatabasePanel(UserAccount u) {

		setTitle("Account Database GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		addComponents();
		addActionListeners(u);

	}
	
	public void addComponents() {
		scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 44, 319, 227);
		contentPane.add(scrollPane);
		
		displayArea = new JTextArea();
		displayArea.setEditable(false);
		scrollPane.setViewportView(displayArea);
		displayArea.setText(accountDatabase.printDatabase());
		
		usernameTextField = new JTextField();
		usernameTextField.setBounds(275, 317, 107, 20);
		contentPane.add(usernameTextField);
		usernameTextField.setColumns(10);
		
		accountNumberTextField = new JTextField();
		accountNumberTextField.setColumns(10);
		accountNumberTextField.setBounds(10, 317, 95, 20);
		contentPane.add(accountNumberTextField);
		
		passwordTextField = new JTextField();
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(275, 283, 107, 20);
		contentPane.add(passwordTextField);
		
		
		messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(67, 11, 416, 14);
		contentPane.add(messageLabel);
		
		backButton = new JButton("Back");
		backButton.setBounds(435, 321, 89, 23);
		contentPane.add(backButton);
		
		changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(125, 282, 140, 23);
		contentPane.add(changePasswordButton);
		
		deleteButton = new JButton("Delete");
		deleteButton.setBounds(435, 282, 89, 23);
		contentPane.add(deleteButton);
		
		accountNumberLabel = new JLabel("AccountNumber");
		accountNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accountNumberLabel.setBounds(10, 286, 95, 14);
		contentPane.add(accountNumberLabel);
		
		changeUsernameButton = new JButton("Change Username");
		changeUsernameButton.setBounds(124, 316, 141, 23);
		contentPane.add(changeUsernameButton);
	}

	private void addActionListeners(UserAccount u) {
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					SearchPanel frame = new SearchPanel(u);
					frame.setVisible(true);
				    dispose();
			}
		});
		

		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.adminModifyPassword(Integer.parseInt(accountNumberTextField.getText()),passwordTextField.getText());
				displayArea.setText(accountDatabase.printDatabase());
			}
		});

		

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.deleteAccount(Integer.parseInt(accountNumberTextField.getText()));
				displayArea.setText(accountDatabase.printDatabase());
			}
		});

		/*The button used to change the username*/

		changeUsernameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.adminModifyUsername(Integer.parseInt(accountNumberTextField.getText()),usernameTextField.getText());
				displayArea.setText(accountDatabase.printDatabase());
			}
		});
	}
}
