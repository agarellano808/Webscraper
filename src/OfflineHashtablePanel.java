import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class OfflineHashtablePanel extends JFrame {
	/*Panel where everything is placed*/
	private JPanel contentPane;
	/*The text field for the query search bar*/
	private JTextField queryField;
	private ResultsDatabase resultDatabase;
	private JTextField deleteTextField;
	private JTextField timeEndField;
	private JTextField timeBeginField;
	private ResultsFilePrinter resultsPrinter= new ResultsFilePrinter();
	private JTextField usernameTextField;
	private Boolean isRegularUser=false;
	/* Create the frame. */
	public OfflineHashtablePanel(UserAccount u) {
		setTitle("Offline Hashtable GUI");	
			resultDatabase= new ResultsDatabase();
		/*Setting the contentPane*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		if(u.accountType.equals("Standard")) {
			resultDatabase.intializeUserTable(u.getUserName());
			isRegularUser=true;
		}
		setBounds(100, 100, 766, 454);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*The text field for the search bar is initialized and setup*/
		queryField = new JTextField();
		queryField.setBounds(211, 40, 326, 20);
		contentPane.add(queryField);
		queryField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(160, 92, 442, 234);
		contentPane.add(scrollPane);

		JTextArea resultArea = new JTextArea();
		scrollPane.setViewportView(resultArea);
		resultArea.setEditable(false);
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(69, 11, 622, 18);
		contentPane.add(messageLabel);
		
		usernameTextField = new JTextField();
		usernameTextField.setColumns(10);
		usernameTextField.setBounds(10, 168, 120, 20);
		contentPane.add(usernameTextField);
		
		deleteTextField = new JTextField();
		deleteTextField.setBounds(654, 303, 86, 20);
		contentPane.add(deleteTextField);
		deleteTextField.setColumns(10);
		
		timeEndField = new JTextField();
		timeEndField.setBounds(639, 168, 101, 20);
		contentPane.add(timeEndField);
		timeEndField.setColumns(10);
		
		timeBeginField = new JTextField();
		timeBeginField.setBounds(639, 137, 101, 20);
		contentPane.add(timeBeginField);
		timeBeginField.setColumns(10);
		
		/*The button to delete the stored data is initialized and setup*/
		JButton deleteButton = new JButton("Delete");
		deleteButton.setBounds(651, 269, 89, 23);
		contentPane.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!deleteTextField.getText().equals("")) {
				try {
					if(isRegularUser) {
						resultDatabase.deleteUserResults(Integer.parseInt(deleteTextField.getText()),u.getUserName());
					}
					else {
					resultDatabase.delete(Integer.parseInt(deleteTextField.getText()),u.getUserName());
					}
				} 
				catch (NumberFormatException | IOException e1) {	
					e1.printStackTrace();
				}
				}
			}
		});
	
		/*The button to go back to the website search screen is initialized and setup*/
		JButton backButton = new JButton("Back");
		backButton.setBounds(651, 381, 89, 23);
		contentPane.add(backButton);
		backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					SearchPanel frame = new SearchPanel(u);
					frame.setVisible(true);
				    dispose();
			}
		});
		/*The button to search the stored data is initialized and setup*/
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(332, 62, 89, 23);
		contentPane.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String results="";
				if(isRegularUser) {
					results = resultDatabase.userResultsSearch(queryField.getText(),u.getUserName());
				}
				else {
					results = resultDatabase.search(queryField.getText(),u.getUserName());

				}
				resultArea.setText(results);
			
			}
		});
		
		/*The button to print the stored data is initialized and setup*/
		JButton printButton = new JButton("Print");
		printButton.setBounds(651, 334, 89, 23);
		contentPane.add(printButton);
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultsPrinter.writeToFile(resultArea.getText());
				messageLabel.setText("The results have been sucessfully written to "+resultsPrinter.getOutputLocation());
			}
		});

		

		
		JButton transactionLogButton = new JButton("Transaction Log");
		transactionLogButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultArea.setText(resultDatabase.printTransactionLog());
			}
		});
		transactionLogButton.setBounds(10, 337, 120, 23);
		contentPane.add(transactionLogButton);
		

		

		
		JButton showAllButton = new JButton("Show All");
		showAllButton.setBounds(10, 303, 120, 23);
		contentPane.add(showAllButton);
		showAllButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(isRegularUser) {
				resultArea.setText(resultDatabase.printAllUserResults());
				}
				else {
				resultArea.setText(resultDatabase.printAll());
				}
			}
		});
		
		JButton timeSearchButton = new JButton("Time Search");
		timeSearchButton.setBounds(639, 94, 101, 23);
		contentPane.add(timeSearchButton);
		timeSearchButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				resultArea.setText(resultDatabase.timeSearch(timeBeginField.getText(),timeEndField.getText()));
			}
		
		});
		
		JButton usernameSearchButton = new JButton("Username Search");
		
		usernameSearchButton.setBounds(10, 136, 123, 23);
		contentPane.add(usernameSearchButton);
		usernameSearchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultArea.setText(resultDatabase.usernameResultsSearch(usernameTextField.getText(),u.getUserName()));
			}
		});


		
		JButton reconstructionButton = new JButton("Reconstruct DB");
		reconstructionButton.setBounds(10, 371, 120, 23);
		contentPane.add(reconstructionButton);
		reconstructionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(resultDatabase.reconstructDatabase()) {
					messageLabel.setText("Database has been reconstructed");
				}
			}
		});
		
		if(!u.getAccountType().equals("admin")) {
			transactionLogButton.setEnabled(false);
			reconstructionButton.setEnabled(false);
		    usernameSearchButton.setEnabled(false);
		}
		

	}
}
