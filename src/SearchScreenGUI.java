import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class SearchScreenGUI extends JFrame {
	/*Panel where everything is placed*/
	private JPanel contentPane;
	/*Text field used to input the query*/
	private JTextField searchField;
	private JTextField changeOutputLocationField;
	private ResultsFilePrinter resultsPrinter= new ResultsFilePrinter();
	/* This method creates the GUI*/
	public SearchScreenGUI(UserAccount u) {
		setTitle("Search Screen");
		/*Setting the contentPane*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Placing the text screen on the cotent panel and setting the bounds and columns*/
		searchField = new JTextField();
		searchField.setBounds(122, 36, 408, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		/*The text area where the results are displayed*/
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(123, 101, 407, 178);
		contentPane.add(scrollPane);
		JTextArea resultArea = new JTextArea();
		scrollPane.setViewportView(resultArea);
		resultArea.setEditable(false);
		
		JLabel messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(61, 11, 503, 14);
		contentPane.add(messageLabel);
		/*A button for searching*/
		JButton searchButton = new JButton("Search");
		searchButton.setBounds(283, 67, 89, 23);
		contentPane.add(searchButton);
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 long startTime = System.nanoTime();
				String results=WebsiteSearcher.guiSearch(searchField.getText(),u.getUserName());
				 long endTime = System.nanoTime()-startTime;
				 System.out.println("Elapsed Time: "+(endTime/1000000)+" MS");
				resultArea.append(results);
			}
		});
		/*A button to access the users stored data*/
		JButton hashTableButton = new JButton("Hash Table");
		hashTableButton.setBounds(9, 375, 104, 23);
		contentPane.add(hashTableButton);
		hashTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			OfflineHashtableGUI frame = new OfflineHashtableGUI(u);
			frame.setVisible(true);
		    dispose();
				
			}
			});
		/*A button to print search results*/
		JButton printButton = new JButton("Print Results");
		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultsPrinter.writeToFile(resultArea.getText());
				messageLabel.setText("The results have been sucessfully written to "+resultsPrinter.getOutputLocation());
			}
		});
		printButton.setBounds(278, 292, 105, 23);
		contentPane.add(printButton);
		
		/*A button to logout from curretnt account*/
		JButton logoutButton = new JButton("Logout");
		logoutButton.setBounds(533, 375, 89, 23);
		contentPane.add(logoutButton);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			LoginGUI frame = new LoginGUI();
			frame.setVisible(true);
		    dispose();
			}
			});
		

		changeOutputLocationField = new JTextField();
		changeOutputLocationField.setColumns(10);
		changeOutputLocationField.setBounds(297, 376, 212, 20);
		contentPane.add(changeOutputLocationField);
		
		JButton changeOutputLocationButton = new JButton("Change Output Location");
		changeOutputLocationButton.setBounds(132, 375, 155, 23);
		contentPane.add(changeOutputLocationButton);
		changeOutputLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultsPrinter.setOutputLocation(changeOutputLocationField.getText());
				messageLabel.setText("The file location has successfully been changed to "+resultsPrinter.getOutputLocation());
			}
			});
		
		JButton userAccountButton = new JButton("User Account");
		userAccountButton.setBounds(9, 307, 104, 23);
		contentPane.add(userAccountButton);
		userAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserAccountGUI frame = new UserAccountGUI(u);
				frame.setVisible(true);
			    dispose();
			}
		});

		
		JButton accountDatabaseButton = new JButton("Account DB");
		accountDatabaseButton.setBounds(9, 273, 104, 23);
		contentPane.add(accountDatabaseButton);
		accountDatabaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountDatabaseGUI frame = new AccountDatabaseGUI(u);
				frame.setVisible(true);
			    dispose();				
			}
		});
	
		


if(u.getAccountType().equals("guest")) {
	changeOutputLocationField.setEnabled(false);
	changeOutputLocationButton.setEnabled(false);
	printButton.setEnabled(false);
	hashTableButton.setEnabled(false);
	accountDatabaseButton.setEnabled(false);
	userAccountButton.setEnabled(false);
}

if(!u.getAccountType().equals("admin")) {
	accountDatabaseButton.setEnabled(false);

}
	}
}
