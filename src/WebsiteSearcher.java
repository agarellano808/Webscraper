import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;

public class WebsiteSearcher {
	/* The user agent that will be used to access the websites */
	public static final String[] USER_AGENT = {
			"Mozilla/5.0  AppleWebKit/602.1.50 (KHTML, like Gecko) CriOS/56.0.2924.75 Mobile/14E5239e Safari/602.1 RuxitSynthetic/1.0 v4872520098 t1099441676816697146",
			"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-US)", };

	/*
	 * A method used to add an account to the database
	 * 
	 * @param userQuery: The query inputed by the user 
	 *        username: The username of the  account used by the user
	 */
	public static String guiSearch(String userQuery, String username) {
		String results = "";
		try {
			String query = URLEncoder.encode(userQuery, "UTF-8");
			// THe URL is hardcoded to be initialized to my chosen website

		    URL url = new URL("https://www.homedepot.com/s/"+query);
		    URI oURL = new URI("https://www.homedepot.com/s/"+query);
		    Desktop desktop = java.awt.Desktop.getDesktop();
		    desktop.browse(oURL);
		//	System.out.println(url.toString());


			/* A string that contains the content of the website */
			File htmlFile = downloadFileByLine(url);
			
			 
			/* The regex that will used to parse the links from the initial search page */
			String initialSearchRegex = "<a class=\"\"[^>]+href=[\\\"]?(/p[^\\\"]+)";
			ArrayList<String> resultLinks = linkParser(htmlFile, initialSearchRegex);
			Hashtable<String, ArrayList<String>> resultTable = new Hashtable<String, ArrayList<String>>();

			fillResults(resultTable, resultLinks);
			//htmlFile.delete();
			addToDatabase(resultTable, username);
			results = displayResults(resultTable);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return results;
	}


	/*
	 * A method used to parse the links in an html page
	 * 
	 * @param html: The content of the html page regex: The regex that will used to
	 * parse the html contetn
	 */
	public static ArrayList<String> linkParser(File html, String regex) {
		String HTMLPage = openFile(html);
		String linkRegex = regex;
		Pattern linkPattern = Pattern.compile(linkRegex, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(HTMLPage);
		ArrayList<String> links = new ArrayList<String>();
		while (pageMatcher.find()) {
			links.add(pageMatcher.group(1));
		} // End of while loop

		return links;
	}

	/*
	 * A method used to fill the results into a hashtable
	 * 
	 * @param resultList: The hashtable where the results will be stored links: The
	 * list of links retrieved from the results page
	 */
	public static void fillResults(Hashtable<String, ArrayList<String>> resultList, ArrayList<String> links) {
		for(int i=0; i<links.size();i++) {
			ArrayList<String> data = resultProcessor(links.get(i));
			resultList.put(links.get(i), data);
		
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/*
	 * A method that will retrieve various information from the url depending on the
	 * regex
	 * 
	 * @param html: The text that will be parsed regex: The regex used to find the
	 * data
	 * 
	 */
	public static String resultParser(String html, String regex) {
		String HTMLPage = html;
		String r = regex;
		Pattern linkPattern = Pattern.compile(r, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
		Matcher pageMatcher = linkPattern.matcher(HTMLPage);
		String data = "";
		if (pageMatcher.find()) {
			data = pageMatcher.group(1);
		} else {
			data = "N/A";
		}
		return data;
	}

	/*
	 * A method used to retrieve all the data needed from the link
	 * 
	 * @param link: The link to the html page where the info will be retrieved from
	 */
	public static ArrayList<String> resultProcessor(String link) {
		ArrayList<String> data = new ArrayList<String>();
		try {
			URL url = new URL("https://www.homedepot.com" + link);
			
		//	long startTime = System.nanoTime();
			File HTMLfile = downloadFileByLine(url);
		//	long endTime = System.nanoTime()-startTime;
		//	 System.out.println("Elapsed Time to download html: "+(endTime/1000000)+" MS");
			
			String HTMLPage = openFile(HTMLfile);
		//	HTMLfile.delete();
			data.add("https://www.homedepot.com" + link);
			/* The string the represent the regex to find the name of the product */
			String nameRegex = "<h1 class=\"product-title__title\">([^\\s][^<]+)";
			data.add(resultParser(HTMLPage, nameRegex));
			
			/* The string the represent the regex to find the brand of the product */			
			String internetNumberRegex = "Internet #[^\\d]+([^<]+)";
			data.add(resultParser(HTMLPage, internetNumberRegex));
			
			/* The string the represent the regex to find the id of the product */		
			String modelNumberRegex = "<h2 class=\"product_details modelNo\">[\\s]+Model # ([^\\s]+)";
			data.add(resultParser(HTMLPage, modelNumberRegex));
			
			String stockKeepingUnitRegex = "Store SKU #[^\\d]+([^<]+)";
			data.add(resultParser(HTMLPage, stockKeepingUnitRegex));
					
			String brandRegex="<div class=\"sticky_brand_info\">([^<]+)";

			data.add(resultParser(HTMLPage, brandRegex));
			
			/* The string the represent the regex to find the price of the product */
			String dollarRegex = "<span class=\"price__dollars\">[\\s]+(\\d+)";
			String centsRegex= "<span class=\"price__cents\">([^<]+)";
			String price="$"+resultParser(HTMLPage, dollarRegex)+":"+resultParser(HTMLPage, centsRegex);
			data.add(price);

			String imageRegex = "<img id=\"mainImage\" itemprop=\"image\" src=\"([^\"]+)";
			String imageLocation = resultParser(HTMLPage, imageRegex);
	
			if(imageLocation!="N/A") {
			URL imageURL = new URL(imageLocation);
			String[] split = imageLocation.split("/");
			String imageFileName = split[split.length - 1];
		//	long startTime1 = System.nanoTime();
			downloadImageFile(imageURL, imageFileName);
		//	long endTime1 = System.nanoTime()-startTime1;
		//	 System.out.println("Elapsed Time to download image: "+(endTime1/1000000)+" MS");
			 
			data.add(imageFileName);

		      
			data.add(System.getProperty("user.dir")+"\\image folder" + "\\" + imageFileName);
			}	
			else {
				data.add("N/A");
				data.add("N/A");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}

	/*
	 * A method used to add the results to the result database
	 * 
	 * @param results: The hashtable containing all the information about the
	 * results username: The username of the account the user is using
	 */
	public static void addToDatabase(Hashtable<String, ArrayList<String>> results, String username) {
		Set<String> keys = results.keySet();
		ResultsDatabase r = new ResultsDatabase();
		for (String key : keys) {
			try {
				ArrayList<String> arr = results.get(key);
		
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				
				SearchResult i = new SearchResult(arr.get(0), arr.get(1), arr.get(2), arr.get(3), arr.get(4), arr.get(5),
					 arr.get(6), arr.get(7),arr.get(8), username, timestamp.toString());
				r.insert(i,username);
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			r.updateDatabase();
		}
	}

	/*
	 * A method used to retrieve the sizes fromt he html
	 * 
	 * @param resultList: The hashtable containing all the information about the
	 * results
	 */
	public static String displayResults(Hashtable<String, ArrayList<String>> resultList) {
		Set<String> keys = resultList.keySet();
		String results = "";
		int i=1;
		for (String key : keys) {
			ArrayList<String> arr = resultList.get(key);
		
			results = results +i+":"+" Link: "+"https://www.homedepot.com" +key+ "\n"  + arr.get(1) + "|" + arr.get(2) + "|" + arr.get(3) + "|"
					+ arr.get(4) + "|" + arr.get(5) + "|" +  arr.get(6)+"|" +arr.get(7)+"\n\n";
			i++;
		}
		return results;
	}

	/*
	 * A method used to retirve contnet from url and put it into the string
	 * 
	 */
	private static String parseHTML(InputStreamReader stream) throws IOException {
		BufferedReader URLFileReader = new BufferedReader(stream);
		String inputLine="";
		StringBuilder sb=new StringBuilder();

		/*
		 * A while loop used to iterate through a website
		 */
	
		while ((inputLine = URLFileReader.readLine()) != null) {
			sb.append(inputLine + "\n");
		} // End of while loop
		return sb.toString();
	}

	/*
	 * A method used to download the html content
	 * 
	 * @param url: The url where the html content will be retrieved from
	 * 
	 */
	private static String downloadHTML( URL url) throws IOException {
		String agent = USER_AGENT[0];
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", agent);
		 int responseCode=((HttpURLConnection) connection).getResponseCode();
		System.out.println(responseCode);
	    InputStreamReader stream = new InputStreamReader(connection.getInputStream());
		String content = parseHTML(stream);
		stream.close();
		return content;
	}

	/*
	 * This method downloads a file by reading the image file the URL points to and
	 * then writing that image into the parameter file
	 * 
	 * @param url: The URL that is currently being processed downloadedURLFile: The
	 * currently empty file that will be filled with the URLfile content
	 */
	public static void downloadImageFile(URL url, String filename) {
		String imageFolder=System.getProperty("user.dir")+"\\image folder";
	      File downloadedUrlFile = new File(imageFolder);
	      downloadedUrlFile.mkdir();
		downloadedUrlFile = new File(imageFolder + "\\" + filename + ".png");

		/*
		 * A try statement where the URL content is read and then written into
		 * downloadedURLFile
		 */
		try {
			downloadedUrlFile.createNewFile();
			/* A reader used to read the image the URL points to */
			BufferedImage urlImageReader = ImageIO.read(url);
			/* A writer that writes the image into the parameter file */
			ImageIO.write(urlImageReader, "png", downloadedUrlFile);
		} // End of try statement

		/*
		 * If an error occurs the exception is passed to the catch statement and then
		 * the printStackTrace method is called to print out information about the error
		 */
		catch (IOException exception) {
			exception.printStackTrace();
		} // End of catch of statement
	}// End of downloadFileImage Method



	public static File downloadFileByLine(URL url) {
		/*
		 * A try statement where the URL content is read and then written into
		 * downloadedURLFile
		 */
		String htmlContent = "";
		String filename = "\\temp.html";
		File downloadedURLFile = new File(System.getProperty("user.dir") + filename);
		try {
			htmlContent = downloadHTML(url);
			downloadedURLFile.createNewFile();
			/* A writer to write into the parameter file */
			FileWriter newFileWriter = new FileWriter(downloadedURLFile);
			newFileWriter.write(htmlContent);
			newFileWriter.close();
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return downloadedURLFile;
	}// End of downloadFileByLine method

	public static String openFile(File f) {
		StringBuilder fileContent = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			for (String line = reader.readLine(); line != null; line = reader.readLine()) {
				fileContent.append( line + "\n");
			}
			reader.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		return fileContent.toString();
	}
	

}
