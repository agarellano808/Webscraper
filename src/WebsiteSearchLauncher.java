
public class WebsiteSearchLauncher {
	/* This is the method were the program will be launched from */
	public static void main(String[] args) {
		/* A try statement where a login screen will be initialized and set to visible */
		if(args.length!=0) {
			CommandLineParser parser= new CommandLineParser();
			parser.parseCommandLine(args);
		}
		else {
		try {
			LoginPanel frame = new LoginPanel();
			frame.setVisible(true);
		}
		
		/*
		 * If an error occurs the exception is passed to the catch statement 
		 * and then the printStackTrace method is called to print out information
		 * about the error
		 */
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	}
}
