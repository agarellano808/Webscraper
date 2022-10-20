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
	AccountDatabase accountDatabase=new AccountDatabase();
	
	public AccountDatabasePanel(UserAccount u) {

		setTitle("Account Database GUI");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 394);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(115, 44, 319, 227);
		contentPane.add(scrollPane);
		
		JTextArea displayArea = new JTextArea();
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
		
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(67, 11, 416, 14);
		contentPane.add(messageLabel);
		
		JButton backButton = new JButton("Back");
		backButton.setBounds(435, 321, 89, 23);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					SearchPanel frame = new SearchPanel(u);
					frame.setVisible(true);
				    dispose();
			}
		});
		
		JButton changePasswordButton = new JButton("Change Password");
		changePasswordButton.setBounds(125, 282, 140, 23);
		contentPane.add(changePasswordButton);
		changePasswordButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.adminModifyPassword(Integer.parseInt(accountNumberTextField.getText()),passwordTextField.getText());
				displayArea.setText(accountDatabase.printDatabase());
			}
		});

		
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(435, 282, 89, 23);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.deleteAccount(Integer.parseInt(accountNumberTextField.getText()));
				displayArea.setText(accountDatabase.printDatabase());
			}
		});

		
		JLabel accountNumberLabel = new JLabel("AccountNumber");
		accountNumberLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accountNumberLabel.setBounds(10, 286, 95, 14);
		contentPane.add(accountNumberLabel);
		

		/*The button used to change the username*/
		JButton changeUsernameButton = new JButton("Change Username");
		changeUsernameButton.setBounds(124, 316, 141, 23);
		contentPane.add(changeUsernameButton);
		changeUsernameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				accountDatabase.adminModifyUsername(Integer.parseInt(accountNumberTextField.getText()),usernameTextField.getText());
				displayArea.setText(accountDatabase.printDatabase());
			}
		});


	}
}
