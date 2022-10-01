/*The entire import section is from the standard Java library*/
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class ResultsDatabase {
	/*
	 * The HashTable is the data structure in which all the data for items sold 
	 * are stored acting like a database
	 */
	Hashtable<Integer, SearchResult> database = new Hashtable<Integer, SearchResult>();
	Hashtable<Integer, SearchResult> userDatabase = new Hashtable<Integer, SearchResult>();
	/*A string containing the location of the input file*/
	private String inputLocation=System.getProperty("user.dir")+"\\ResultsDatabase.txt";
	/*A string containing the location of the transaction log file*/
	private String transactionLogLocation=System.getProperty("user.dir")+"\\ResultsDatabaseTransactionLog.txt";
	/*
	 * A constructor for the table class. It initialized the outputLocation,inputLocation
	 * and transactionLogLocation. The constructor then calls the initializeTable method
	 * to insert the information from the input file to the Hash Table
	 */
	public ResultsDatabase() {
		try {
			initializeTable();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	/*
	 * A method that sets the location of the input file
	 * 
	 * @param inputLocation: A string containing the input file location
	 */
	public void setInputLocation(String inputLocation) {
		this.inputLocation = inputLocation;
	}
	
	/*
	 * A method that sets the location of the transaction log file
	 * 
	 * @param outputLocation: A string containing the transaction log file location
	 */
	public void setTransactionLogLocation(String transactionLogLocation) {
		this.transactionLogLocation = transactionLogLocation;
	}

	/*
	 * A method used to initialize the Hash table. Each line in the text file
	 * is turned into an item which is then stored into the Hash Table with the Inventory
	 * number as the key
	 */
	public void initializeTable() throws IOException {

		database.clear();
		/*A string array used to store all the inputed fields*/
		String[] info = new String[11];

		/*A reader to read the input text file*/
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(inputLocation));
			/*
			 * This loop is where the text file is read and a new item is constructed for
			 * each line in the text file and then inserted into the Hash table with the
			 * inventory number being used as a key
			 */
			int j=0;
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				/*An iterator for the info string array*/
				int i = 0;
				
				/*A loop used to split information in each line of the text file by | 
				 *The split information is then stored into the info string array and
				 * i is then iterated
				 */
				for (String word : line.split("\\|")) {
					info[i] = word;
					i++;
				}//End of second for loop
				
				/*A new item is constructed from the info string array*/
				SearchResult t = new SearchResult(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7], info[8],info[9],info[10]);
				/*The new item is put into the Hash Table*/
				database.put(j++, t);
			}//End of first for loop
			
			reader.close();
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}//End of initializeTable function
 
	public void intializeUserTable(String username) {

		userDatabase.clear();
		for(int i=0;i<database.size();i++) {
	
			if(username.equals(database.get(i).getUsername())) {
				
				userDatabase.put(i,database.get(i));
			}
		}
	}
	
	/*
	 * A method used to insert a new item into the database 
	 * @param inventoryNumber: A string input that doubles as the item's inventoryNumber and Key for the Hash Table. 
	 * 		  searchResult: The searchResutl that is to be inserted
	 */
	public void insert(SearchResult searchResult,String username) throws IOException {
		int newkey=database.size();
		database.put(newkey, searchResult);
		/*
		 * After the item is inserted into the database its information is retrieved to
		 * print in the transaction log
		 */
		String info = database.get(newkey).getAllInfo();
		/*The command and the item info is passed to the writeToTransactionLog function*/
		writeToTransactionLog("INSERT", info, username);

	}//End of insert function

	/*
	 * A method used to delete an item from the database 
	 * 
	 * @param inventoryNumber: The string input is the inventory number and Hash Table key of the item that is to be deleted.
	 */
	public void delete(int inventoryNumber,String username) throws IOException {
	int d=database.size();
		/*A if statement to see if the item exists in the hash table*/
		if(database.containsKey(inventoryNumber)) {
		/*The item is then remove from the Hash Table*/
		database.remove(inventoryNumber);
		/*The command and the item info is passed to the writeToTransactionLog function*/
		if(!database.isEmpty()) {
			for(int i=inventoryNumber;i<d-1;i++) {
				database.put(i,database.get(i+1));
			}
			database.remove(d-1);
			}
		writeToTransactionLog("DELETE", Integer.toString(inventoryNumber) , username);
		}// End of the if statement
		else
			System.err.println("Deletion cannot be done because item does not exist");
		updateDatabase();
	}//End of delete function

	public boolean deleteUserResults(int inventoryNumber,String username) {
		  Set<Integer> keys = userDatabase.keySet();
		for (Integer key : keys) {
			if(inventoryNumber==key) {
				try {
					userDatabase.remove(key);
					delete(key,username);
				} 
				catch (IOException e) {
					
					e.printStackTrace();
				}
				return true;
			}
		}
		return false;
	}
	/*
	 * A method used to search though the Hash Table The string input is a word or
	 * phrase that is used to search for an item which is entered by the user
	 * 
	 * @return
	 */
	public String search(String searchTerms,String username){
	/*A list used to store all the search terms*/
		List<String> searchWords = new ArrayList<String>();
		/*A list used to store the results of the search*/
		List<SearchResult> results = new ArrayList<SearchResult>();
		/* The inputed search phrase is split into singular lower case words and stored into the searchWords list */
		for (String word : searchTerms.split(" ")) {
			searchWords.add(word.toLowerCase());
		}
		/* Converts the list of search words into an array */
		Object[] sWords = searchWords.toArray();
		/* Retrieve all the keys from the Hash Table */
		List<Integer> keys = new ArrayList<Integer>();
		
		/*
		 * Checking each item in the Hash Table for the searched word by using the keys
		 * in the this outer loop
		 */
		for (int i=0;i<database.size();i++) {

			/*A list used to store the items split information*/
			List<String> infoSplit = new ArrayList<String>();
		
			/* The info for each item in the Hash Table is split in the first inner loop */
			for (String w : database.get(i).getAllInfo().split("\\|")) {
				infoSplit.add(w.toLowerCase());
			}//End of first inner loop
			
			/*
			 * A second inner loop to check if the searched word is contained anywhere in the items name
			 */
			for (int j = 0; j < searchWords.size(); j++) {
				for (int k=0;k<infoSplit.size();k++) {
				if (infoSplit.get(k).contains((CharSequence) sWords[j])) {
					results.add(database.get(i));
					keys.add(i);
					break;
				}
				}//End of second inner loop
			}
		}// End of outer loop

		StringBuilder finalResult= new StringBuilder();
		/* A loop used to print the results into the output text file */
		for (int i = 0; i < results.size(); i++) {
			finalResult.append(keys.get(i)+"|"+(results.get(i).getAllInfo() + "\n"));
		}//End of file writer loop
		
		/* The Transaction information is written into the transaction text file */
		try {
			writeToTransactionLog("SEARCH", searchTerms,username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return finalResult.toString();
	}// End of Search Function
	
	
	public String userResultsSearch(String searchTerms,String username) {
		  Set<Integer> keys = userDatabase.keySet();
		/*A list used to store all the search terms*/
			List<String> searchWords = new ArrayList<String>();
			/*A list used to store the results of the search*/
			List<SearchResult> results = new ArrayList<SearchResult>();
			/* The inputed search phrase is split into singular lower case words and stored into the searchWords list */
			for (String word : searchTerms.split(" ")) {
				searchWords.add(word.toLowerCase());
			}
			/* Converts the list of search words into an array */
			Object[] sWords = searchWords.toArray();
			/* Retrieve all the keys from the Hash Table */
			List<Integer> pkeys = new ArrayList<Integer>();
			
			/*
			 * Checking each item in the Hash Table for the searched word by using the keys
			 * in the this outer loop
			 */
			for (Integer key : keys) {

				/*A list used to store the items split information*/
				List<String> infoSplit = new ArrayList<String>();
			
				/* The info for each item in the Hash Table is split in the first inner loop */
				for (String w : userDatabase.get(key).getAllInfo().split("\\|")) {
			
					infoSplit.add(w.toLowerCase());
				}//End of first inner loop
				
				/*
				 * A second inner loop to check if the searched word is contained anywhere in the items name
				 */
				for (int i = 0; i < searchWords.size(); i++) {
					for (int j=0;j<infoSplit.size();j++) {
					if (infoSplit.get(j).contains((CharSequence) sWords[i])) {
						results.add(userDatabase.get(key));
						pkeys.add(key);
						break;
					}
					}//End of second inner loop
				}
			}// End of outer loop

			StringBuilder finalResult= new StringBuilder();
			/* A loop used to print the results into the output text file */
			for (int i = 0; i < results.size(); i++) {
				finalResult.append(pkeys.get(i)+"|"+(results.get(i).getAllInfo() + "\n"));
			}//End of file writer loop
			
			/* The Transaction information is written into the transaction text file */
			try {
				writeToTransactionLog("SEARCH", searchTerms,username);
			} 
			catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return finalResult.toString();
		
	}
	
	public String usernameResultsSearch(String searchTerms,String username) {

		  Set<Integer> keys = database.keySet();
		/*A list used to store all the search terms*/
			List<String> searchWords = new ArrayList<String>();
			/*A list used to store the results of the search*/
			List<SearchResult> results = new ArrayList<SearchResult>();
			/* The inputed search phrase is split into singular lower case words and stored into the searchWords list */
			for (String word : searchTerms.split(" ")) {
				searchWords.add(word);
			}
			/* Converts the list of search words into an array */
			Object[] sWords = searchWords.toArray();
			/* Retrieve all the keys from the Hash Table */
			List<Integer> pkeys = new ArrayList<Integer>();
			
			/*
			 * Checking each item in the Hash Table for the searched word by using the keys
			 * in the this outer loop
			 */
			for (Integer key : keys) {

				/*
				 * A second inner loop to check if the searched word is contained anywhere in the items name
				 */
				for (int i = 0; i < searchWords.size(); i++) {
					if (database.get(key).getUsername().contains((CharSequence) sWords[i])) {
						results.add(database.get(key));
						pkeys.add(key);
						break;
					}
				
				}
			}// End of outer loop

			String finalResult="";
			/* A loop used to print the results into the output text file */
			for (int i = results.size()-1; i >= 0; i--) {
				finalResult=finalResult+pkeys.get(i)+"|"+(results.get(i).getAllInfo() + "\n");
			}//End of file writer loop
			
			/* The Transaction information is written into the transaction text file */
			try {
				writeToTransactionLog("SEARCH", searchTerms,username);
			}
			catch (IOException e) {
				
				e.printStackTrace();
			}
			return finalResult;
		
	
	}
	
	
	public String timeSearch(String begin, String end) {
		String results="";
		for(int i=0;i<database.size();i++) {
			Timestamp timestamp=Timestamp.valueOf(database.get(i).getTimestamp());
			if(!begin.equals("")&&timestamp.after(Timestamp.valueOf(begin))&&!end.equals("")&&timestamp.before(Timestamp.valueOf(end))) {
				results=results+i+"|"+(database.get(i).getAllInfo() + "\n");
			}
		
		}

		return results;
	}
	
	public void updateDatabase() {
		/* A file writer used to write all the info into an output file */
		try {
			FileWriter fileWriter = new FileWriter(inputLocation);
			/*
			 * In this loop each key represent one item. Each item's information is
			 * retrieved using their key and is written into the output file
			 */
			for (int i=0;i<database.size();i++) {
				fileWriter.write(database.get(i).getAllInfo()+ "\n");
			}

			fileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}
/*
 * A method that prints the entire database to the GUI
 */
	public String printAll() {
		StringBuilder content= new StringBuilder();
		for(int i=0;i<database.size();i++) {
			content.append(i+"|"+database.get(i).getAllInfo()+"\n");
		}
		return content.toString();
	}
	
	/*
	 * A method that prints the entire database to the GUI
	 */
		public String printAllUserResults() {
			Set<Integer> keys = userDatabase.keySet();
			StringBuilder content= new StringBuilder();
			for(Integer key : keys) {
				content.append(key+"|"+userDatabase.get(key).getAllInfo()+"\n");
			}
			return content.toString();
		}
	/*
	 * A method used to write any actions to the Transaction Log text file
	 * 
	 * @param 	command: The command that was used such as insert,search, delete etc
	 * 		    info: Any additional info that needs to be written into the log file
	 */
	public void writeToTransactionLog(String command, String info, String username) throws IOException {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		FileWriter writer = new FileWriter(transactionLogLocation,true);
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.println(timestamp +"|"+username+ "|" + command + "|" + info);
		printWriter.close();
	}// End of writeToTransactionLog function
	
	public String printTransactionLog() {
		String fileContent = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(transactionLogLocation));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				fileContent = fileContent + line + "\n";
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent;
	}
	
	public boolean reconstructDatabase() {
		database.clear();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(transactionLogLocation));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				String []split=line.split("\\|");
			
				String command=split[2];
				switch(command){
				case("INSERT"):
					database.put(database.size(), new SearchResult(split[3],split[4],split[5],split[6],split[7],split[8],split[9],split[10],split[11],split[12],split[13]));
					break;
				
				case("DELETE"):
					int d=database.size();
					int invenotryNumber=Integer.parseInt(split[3]);
					database.remove(invenotryNumber);
					if(!database.isEmpty()) {
					for(int i=invenotryNumber;i<database.size()-1;i++) {
						database.put(i,database.get(i+1));
					}
					database.remove(d-1);
					}
					break;
				default:
					break;
				}
			}
			reader.close();
			FileWriter outputFileWriter = new FileWriter(inputLocation);
			for(int i=0;i<database.size();i++) {
				outputFileWriter.write(database.get(i).getAllInfo()+"\n");
			}
			outputFileWriter.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	
		return true;
	}
}//End of Table class
