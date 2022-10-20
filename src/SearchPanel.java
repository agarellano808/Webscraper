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

public class SearchPanel extends JFrame {
	/*Panel where everything is placed*/
	private JPanel contentPane;
	/*Text field used to input the query*/
	private JTextField searchField;
	private JTextField changeOutputLocationField;
	private ResultsFilePrinter resultsPrinter= new ResultsFilePrinter();
	private JScrollPane scrollPane;
	private JTextArea resultArea;
	private JLabel messageLabel;
	private JButton searchButton;
	private JButton hashTableButton;
	private JButton printButton;
	private JButton logoutButton;
	private JButton changeOutputLocationButton;
	private JButton userAccountButton;
	private JButton accountDatabaseButton;
	
	/* This method creates the GUI*/
	
	public SearchPanel(UserAccount u) {
		setTitle("Search Screen");
		/*Setting the contentPane*/
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 448);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		addComponents();
		addActionListeners(u);
	}
	
	private void addComponents() {
		/*Placing the text screen on the cotent panel and setting the bounds and columns*/
		searchField = new JTextField();
		searchField.setBounds(122, 36, 408, 20);
		contentPane.add(searchField);
		searchField.setColumns(10);
		
		/*The text area where the results are displayed*/
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(123, 101, 407, 178);
		contentPane.add(scrollPane);
		
		resultArea = new JTextArea();
		scrollPane.setViewportView(resultArea);
		resultArea.setEditable(false);
		
		messageLabel = new JLabel("");
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setBounds(61, 11, 503, 14);
		contentPane.add(messageLabel);
		/*A button for searching*/
		searchButton = new JButton("Search");
		searchButton.setBounds(283, 67, 89, 23);
		contentPane.add(searchButton);
		
		/*A button to access the users stored data*/
		hashTableButton = new JButton("Hash Table");
		hashTableButton.setBounds(9, 375, 104, 23);
		contentPane.add(hashTableButton);
		
		/*A button to print search results*/
		printButton = new JButton("Print Results");
		printButton.setBounds(278, 292, 105, 23);
		contentPane.add(printButton);
		
		/*A button to logout from curretnt account*/
		logoutButton = new JButton("Logout");
		logoutButton.setBounds(533, 375, 89, 23);
		contentPane.add(logoutButton);
		
		changeOutputLocationField = new JTextField();
		changeOutputLocationField.setColumns(10);
		changeOutputLocationField.setBounds(297, 376, 212, 20);
		contentPane.add(changeOutputLocationField);
		
		changeOutputLocationButton = new JButton("Change Output Location");
		changeOutputLocationButton.setBounds(132, 375, 155, 23);
		contentPane.add(changeOutputLocationButton);
		
		userAccountButton = new JButton("User Account");
		userAccountButton.setBounds(9, 307, 104, 23);
		contentPane.add(userAccountButton);
		
		accountDatabaseButton = new JButton("Account DB");
		accountDatabaseButton.setBounds(9, 273, 104, 23);
		contentPane.add(accountDatabaseButton);
	}
	
	private void addActionListeners(UserAccount u) {
		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 long startTime = System.nanoTime();
				String results=WebsiteSearcher.guiSearch(searchField.getText(),u.getUserName());
				 long endTime = System.nanoTime()-startTime;
				 System.out.println("Elapsed Time: "+(endTime/1000000)+" MS");
				resultArea.append(results);
			}
		});
		hashTableButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			OfflineHashtablePanel frame = new OfflineHashtablePanel(u);
			frame.setVisible(true);
		    dispose();
				
			}
			});
		

		printButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				resultsPrinter.writeToFile(resultArea.getText());
				messageLabel.setText("The results have been sucessfully written to "+resultsPrinter.getOutputLocation());
			}
		});

		

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			LoginPanel frame = new LoginPanel();
			frame.setVisible(true);
		    dispose();
			}
			});
		


		changeOutputLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultsPrinter.setOutputLocation(changeOutputLocationField.getText());
				messageLabel.setText("The file location has successfully been changed to "+resultsPrinter.getOutputLocation());
			}
			});
		

		userAccountButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserAccountPanel frame = new UserAccountPanel(u);
				frame.setVisible(true);
			    dispose();
			}
		});

		

		accountDatabaseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AccountDatabasePanel frame = new AccountDatabasePanel(u);
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
