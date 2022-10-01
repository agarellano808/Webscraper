public class SearchResult {
	/* String variable that represents the item's productName */
	private String productLink;
	private String productName;
	/* String variable that represents the item's inventory number */
	private String internetNumber;
	/* String variable that represents the item's inventory number */
	private String modelNumber;
	/* String variable that represents the item's inventory number */
	private String stockKeepingUnitNumber;
	/* String variable that represents the item's brand */
	private String brand;
	/* String variable that represents the item's price */
	private String price;
	/*String variable that represents the username of the user who recoeved this item as a search result*/
	private String imageName;
	private String imageLocation;
	private String username;
	/*The time when this item was added to the database*/
	private String timestamp;

	/*
	 * A Constructor for the item class.
	 * @param productName: The inputed productName for the item
	 * 		  stockKeepingUnitNumber: The inputed inventory number for the item
	 *        brand: The inputed brand for the item
	 *        price: The inputed price for the item
	 */
	public SearchResult(String productLink, String productName, String internetNumber, String modelNumber,String stockKeepingUnitNumber,String brand, 
			String price, String imageName,String imageLocation,String username,String timestamp) {
		this.productLink=productLink;
		this.productName = productName;
		this.internetNumber = internetNumber;
		this.modelNumber = modelNumber;
		this.stockKeepingUnitNumber =stockKeepingUnitNumber;
		this.brand = brand;
		this.price = price;
		this.imageName=imageName;
		this.imageLocation=imageLocation;
		this.username=username;
		this.timestamp=timestamp;
	}

	/*
	 * A method that takes a String input and stores it as the item's productName
	 * 
	 * @param productName: The inputed productName for the item
	 */
	public void setProductLink(String productLink) {
		this.productLink = productLink;
	}
	
	/*
	 * A method that takes a String input and stores it as the item's productName
	 * 
	 * @param productName: The inputed productName for the item
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}
	/*
	 * A method that takes a String input and stores it as the item's inventory number
	 * 
	 * @param inventoryNumber: The inputed inventory number for the item
	 */
	public void setInternetNumber(String internetNumber) {
		this.internetNumber = internetNumber;
	}
	
	/*
	 * A method that takes a String input and stores it as the item's inventory number
	 * 
	 * @param inventoryNumber: The inputed inventory number for the item
	 */
	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	/*
	 * A method that takes a String input and stores it as the item's inventory number
	 * 
	 * @param inventoryNumber: The inputed inventory number for the item
	 */
	public void setstockKeepingUnitNumber(String stockKeepingUnitNumber) {
		this.stockKeepingUnitNumber = stockKeepingUnitNumber;
	}
	
	/*
	 * A method that takes a String input and stores it as the item's brand
	 * 
	 * @param Brand: The inputed brand productName for the item
	 */
	public void setBrand(String brand) {
		this.brand = brand;
	}

	/*
	 * A method that takes a String input and stores it as the item's price
	 * 
	 * @param price: The inputed price for the item
	 */
	public void setPrice(String price) {
		this.price = price;
	}


	/*
	 * A method that returns the item's productName
	 * 
	 * @return The item's productLink
	 */
	public String getProductLink() {
		return productLink;
	}
	
	/*
	 * A method that returns the item's productName
	 * 
	 * @return The item's productName
	 */
	public String getProductName() {
		return productName;
	}

	/*
	 * A method that returns the item's inventory number
	 * 
	 * @return The item's inventory number
	 */
	public String getInternetNumber() {
		return internetNumber;
	}
	
	/*
	 * A method that returns the item's inventory number
	 * 
	 * @return The item's inventory number
	 */
	public String getModelNumber() {
		return modelNumber;
	}

	/*
	 * A method that returns the item's inventory number
	 * 
	 * @return The item's inventory number
	 */
	public String getStockKeepingUnitNumber() {
		return stockKeepingUnitNumber;
	}
	
	/*
	 * A method that returns the item's brand
	 * 
	 * @return the brand of the item
	 */
	public String getBrand() {
		return brand;
	}

	/*
	 * A method that returns the item's price
	 * 
	 * @return the price of the item
	 */
	public String getPrice() {
		return price;
	}


	/*
	 * A method that returns the age of the person the item is intended for(Baby
	 * Child Adult etc)
	 * 
	 * @return the age of the person the item is intended for
	 */
	public String getImageName() {
		return imageName;
	}
	/*
	 * A method that returns the age of the person the item is intended for(Baby
	 * Child Adult etc)
	 * 
	 * @return the age of the person the item is intended for
	 */
	public String getImageLocation() {
		return imageLocation;
	}
	/*
	 * A method that returns the age of the person the item is intended for(Baby
	 * Child Adult etc)
	 * 
	 * @return the age of the person the item is intended for
	 */
	public String getUsername() {
		return username;
	}
	
	/*
	 * A method that returns the user productName of the user who recived this result
	 * 
	 * @return the age of the person the item is intended for
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/*
	 * A method that returns a string containing all the information of the item
	 * This method is to be used when all the information needs to retrieved at
	 * once.
	 * 
	 * @return A string containing all the item's information
	 */
	public String getAllInfo() {
		return productLink+"|"+productName + "|"+internetNumber+ "|" + modelNumber + "|" +stockKeepingUnitNumber+ "|" + brand + "|" + price + "|"+imageName+"|"+imageLocation+"|"+username+"|"+timestamp;
	}
}//End of Item class
