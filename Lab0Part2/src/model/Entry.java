package model;
//import model.Product; // this is redundant since Entry and Product are in the same package 
/*
 * Template of unit of storage in apple refurbished shop (kind of like a database)
 */

public class Entry {
	private String serialNumber; // Unique as in no two products shall have the same serialNumber
	private Product product; /* the type of attributes is a Reference type 
	 * at RunTime this attribute will store the address of 
	 * SOME PRODUCT OBJECT
	 */
	
	//public Entry() {}
	
	public Entry(String serialNumber,Product product) {
		this.serialNumber = serialNumber;
		this.product = product;
	}
	
	
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
	
	/* An overloaded version of setProduct
	 * This version of setProduct doesn't expect 
	 * the user to create a Product object and pass it as an argument
	 * Instead we expect the user to pass a string model and a value or original price 
	 */
	public void setProudct(String model,double originalPrice) {
		this.product = new Product(model,originalPrice);
	}
	
	public String toString() {
		//String s = String.format("[%s]%s", this.serialNumber,this.product.toString()); 
		
		//return s;
		
		return "[" + this.serialNumber +  "] " + this.product.toString();
	}
}
