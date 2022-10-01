import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ResultsFilePrinter {
	/* A file writer used to write all the info into an output file */
	private String outputLocation;
	public ResultsFilePrinter() {
		outputLocation=System.getProperty("user.dir") + "\\SearchResults.txt";
	}
	public void writeToFile(String text) {
	try {
		FileWriter outputFileWriter = new FileWriter(outputLocation);
		outputFileWriter.write(text);
		outputFileWriter.close();
	} 
	/*
	 * If an error occurs the exception is passed to the catch statement 
	 * and then the printStackTrace method is called to print out information
	 * about the error
	 */
	catch (IOException e) {
		e.printStackTrace();
	}
	}

	public void setOutputLocation(String n) {
		String newFileLocation=n;
		if(!newFileLocation.endsWith(".txt")) {
			newFileLocation=newFileLocation+"\\SearchResults.txt";
		}
		File outputFile = new File(newFileLocation);
		/*A try statement where the outPutFile is created at the file path at the outputFilePath if it does not already exist*/
		try {
			/*
			 * If the output file is created it is because it could not be found
			 * The user will be informed of this
			 */
			if(outputFile.createNewFile()) {;
				System.err.println("Could not find output file, a new one will be created");
			}
	
			outputLocation=newFileLocation;
		}//End of try statement
		
		/*
		 * If an error occurs the exception is passed to the catch statement 
		 * and then the printStackTrace method is called to print out information
		 * about the error
		 */
		catch (IOException exception) {
			exception.printStackTrace();
		}//End of catch statement
	}
	public String getOutputLocation() {
		return outputLocation;
	}
}
