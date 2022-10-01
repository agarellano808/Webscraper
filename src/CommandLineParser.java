/*
 * All imports are taken from the standard java library.java.io.File is
 * imported to handle various files and java.io.IOException is imported 
 * to handle exceptions with the files.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CommandLineParser {
	
	/*
	 * A string used to contain the name of the input file which by default is 
	 * initialized to "input.txt".
	 */
	private String inputFileName = "CommandLineQueryInput.txt";

	/*
	 * A string used to contain the name of the output file which by default is 
	 * initialized to "output.txt".
	 */
	private String outputFileName = "CommandLineQueryOutput.txt";
	
	/*
	 * A string used to contain the path of both input and output which by default
	 * is initialized to System.getProperty("user.dir")+"\\" which is the path
	 * the this java file is located in.
	 */
	private String dictionaryPath = System.getProperty("user.dir")+"\\";
	
	/*
	 * A string used to contain the dictionary path of the input file. This string 
	 * will be passed as a parameter for the URLProcessor constructor. By default it 
	 * is initialized to ""
	 */
	private String inputFilePath = "";
	
	/*
	 * A string used to contain the dictionary path of the output file. This string 
	 * will be passed as a parameter for the URLProcessor constructor. By default it 
	 * is initialized to ""
	 */
	private static String outputFilePath = "";
	private String databaseChoice="ht";


	/*
	 * The CommandLineParser method will process any arguments entered by the user
	 * and then call the method checkFilePath() to make sure the path exists. Then
	 * the methods setInputFile(), and setOutputFile() are called to assign new 
	 * values to inputFilePath and outputFilePath.
	 * @param args: A string array that holds any additional inputs entered by
	 * 				user in the command line. These will be processed to see if
	 * 				they are valid commands and then carry them out.
	 */
	public void parseCommandLine(String[] args) {
	
		/* An integer variable used to iterate through the arguments */
		int i = 0;
		/*
		 * A string variable used to temporally store the arguments to check what they
		 * contain
		 */
		String currentArgument;

		/*
		 * A while loop used to parse and process all the command line arguments in the 
		 * args string array.
		 */
		while (i < args.length) {
			/*
			 * An argument is assigned to currentArgument and then i is iterated so the next
			 * argument can be read. currentArgument is then checked to see if it matches
			 * any flag.
			 */
			currentArgument = args[i++];

			/* 
			 * If currentArgument contains the -i flag and the following argument(args[i]) is not another flag 
			 * then args[i] is assigned to inputFileName then i is iterated.
			 */
			if (currentArgument.equalsIgnoreCase("-i") && !args[i].startsWith("-")) {
				inputFileName = args[i++];
				if(!inputFileName.endsWith(".txt")) {
					inputFileName+=".txt";
				}
			} // End of if statement for -i flag

			/* 
			 * If currentArgument contains the -d flag and the following argument(args[i]) is not another flag 
			 * then args[i] is assigned to path then i is iterated.
			 */
			if (currentArgument.equalsIgnoreCase("-d") && !args[i].startsWith("-")) {
				dictionaryPath  = args[i++];
				
				/*If the file path does not end with \ it will be added to it */
				if(!dictionaryPath.endsWith("\\")) {
					dictionaryPath+="\\";
				}
			} // End of if statement for -d flag

			/* 
			 * If currentArgument contains the -o flag and the following argument(args[i]) is not another flag 
			 * then args[i] is assigned to outputFileName then i is iterated.
			 */
			if (currentArgument.equalsIgnoreCase("-o") && !args[i].startsWith("-")) {
				outputFileName = args[i++];
				if(!outputFileName.endsWith(".txt")) {
					outputFileName+=".txt";
				}
			} // End of if statement for -o flag
			
			if (currentArgument.equalsIgnoreCase("-h") && !args[i].startsWith("-")) {
				databaseChoice=args[i++];
				System.out.println(databaseChoice);
				if(!databaseChoice.equals("ht")&&!databaseChoice.equals("db")&&!databaseChoice.equals("bo")) {
					System.err.println("An invalid parameter for command -h has been inputed. The default (ht) will be used instead");
					databaseChoice="ht";
				}
				
			} // End of if statement for -h flag

		if (currentArgument.equalsIgnoreCase("-p") && !args[i].startsWith("-")) {
			checkDictionaryPath();
			setInputFile();
			setOutputFile();
			String field = args[i++];
			String derbyResults="DERBY RESULTS"+"\n";;
			String hashtableResults="HASHTABLE RESULTS"+"\n";
			if(databaseChoice.equalsIgnoreCase("bo")) {
				ResultsDatabase resultsDatabaes=new ResultsDatabase();
				BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(inputFilePath));
					for (String line = reader.readLine(); line != null; line = reader.readLine()) {
						hashtableResults=hashtableResults+resultsDatabaes.search(line,"Parser")+"\n";
					}
					printToOutputFile(hashtableResults+derbyResults);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
	
			}
			else if(databaseChoice.equalsIgnoreCase("db")) {
				BufferedReader reader;
			
				try {
					System.out.println(field);
					reader = new BufferedReader(new FileReader(inputFilePath));
					for (String line = reader.readLine(); line != null; line = reader.readLine()) {
					//	derbyResults=derbyResults+derbyResultsDatabase.search(field, line,"Parser")+"\n";
					}
					printToOutputFile(derbyResults);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else {
				ResultsDatabase resultsDatabaes=new ResultsDatabase();
				BufferedReader reader;
				try {
					reader = new BufferedReader(new FileReader(inputFilePath));
					for (String line = reader.readLine(); line != null; line = reader.readLine()) {
						hashtableResults=hashtableResults+resultsDatabaes.search(line,"Parser")+"\n";
					}
					printToOutputFile(hashtableResults);
				} 
				catch (IOException e) {
					e.printStackTrace();
				}
			
	
			}
			}
		} // End of while loop

	}// End of CommandLineProcessor method
	
	/*A method used to check if the file path is valid*/
	private void checkDictionaryPath() {
		/*
		 * A new file is declared and initialized using dictionaryPath. This file will be used to check 
		 * if the path exists.
		 */
		File dictionary = new File(dictionaryPath);
		
		/* 
		 * dictionary is checked to see if it exists. If it does not dictionaryPath is reassigned to the
		 * default value. If it does the value of dictionaryPath will remain the same
		 */
		if (!dictionary.exists()) {
			System.err.println("Could not find dictionary path, the default will be used instead");
			dictionaryPath = System.getProperty("user.dir")+"\\";
		}//End of if statement
		
	}// End of checkDictionaryPath method
	
	/*A method used to assign inputFilePath a new value and then check if the input file exists*/
	private void setInputFile() {
		/*inputFileName will assigned dictionaryPath + inputFileName so it will have the complete path of the input file*/
		inputFilePath = dictionaryPath  + inputFileName;
		
		/*
		 *  A new file is declared and initialized using inputFileName. This file will be used to check 
		 * if they file exists at the designated path.
		 */
		File inputFile = new File(inputFilePath);
		
		/* 
		 * Inputfile is checked to see if it exists. If it does not, InputFileName is reassigned to the
		 * default value. If it does the inputFile name will remain the same
		 */
		if (!inputFile.exists()) {
			System.err.println("Input file does not exist, the default input file will be used instead");
			inputFilePath = "input.txt";
		} 
	}//End of setInputFile method
	
	/*A method used to assign outputFilePath a new value and then check if the output file exists*/
	private void setOutputFile() {
		/*outputFileName will assigned dictionaryPath + outputFileName so it will have the complete path of the output file*/
		outputFilePath = dictionaryPath  + outputFileName;
		/* A new file is declared and initialized using outputFileName */
		File outputFile = new File(outputFilePath);
		
		/*A try statement where the outPutFile is created at the file path at the outputFilePath if it does not already exist*/
		try {
			/*
			 * If the output file is created it is because it could not be found
			 * The user will be informed of this
			 */
			if(outputFile.createNewFile()) {;
				System.err.println("Could not find output file, a new one will be created");
			}
		}//End of try statement
		
		/*
		 * If an error occurs the exception is passed to the catch statement 
		 * and then the printStackTrace method is called to print out information
		 * about the error
		 */
		catch (IOException exception) {
			exception.printStackTrace();
		}//End of catch statement
		
	}//End of setOutputFile method
	
	
	/*
	 * A method that prints the URL information to an output text file. THis will
	 * only be used if the program is launched from the command line
	 * 
	 * @param outputFilePath: The complete file path of the output text file
	 * urlInformation: A list containing all the information of the URL
	 */
	public static void printToOutputFile(String results) throws IOException {
		/* A file writer used to write to the output file */
		FileWriter outputFileWriter = new FileWriter(outputFilePath);
		outputFileWriter.write(results);
		outputFileWriter.close();
	}// End of printToOutputFile method
}// End of *URLInfoRetrieverCommandLineParser class