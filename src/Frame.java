import java.awt.CardLayout;
import java.sql.SQLException;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private CardLayout cl;
	private LoginPanel loginPanel;
	private AccountDatabasePanel accountDatabasePanel;
	private OfflineHashtablePanel offlineHashTablePanel;
	private RegistrationPanel RegistrationPanel;
	private SearchPanel searchPanel;
	private UserAccountPanel UserAccountPanel;
	public Frame() throws SQLException {

		//setTitle("Space Invaders");
		cl = new CardLayout(100, 160);
		setLayout(cl);
		//setSize(640, 480);
		setResizable(false);
		addPanels();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
	}
	
	private void addPanels() throws SQLException {
		loginPanel = new ButtonPanel();
		getContentPane().add(loginPanel, "1");
		accountDatabasePanel=new Board();
		getContentPane().add(accountDatabasePanel, "2");
		offlineHashTablePanel = new HighScoreScreen();
		getContentPane().add(offlineHashTablePanel, "3");
		RegistrationPanel = new EnterInitialsPanel();
		getContentPane().add(RegistrationPanel, "4");
	}

	public void changePanel(String s) {
		cl.show(getContentPane(), s);
	}

	public void setVgap(int i) {
		cl.setVgap(i);
	}

	public void setHgap(int i) {
		cl.setHgap(i);
	} 
}
