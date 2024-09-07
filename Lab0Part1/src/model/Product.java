package model;

/* 
 * Products for apple refurbished store 
 * BY Abdulkadir Ahmed
 */


public class Product {
	/* Attributes */
	private String model; // type of model
	private String finish; // color of model
	private int storage; // storage of model
	private boolean hasCellularConnectivity; // false (wifi), true (wifi + data)
	private double originalPrice; // Price of model e.g 1254.99
	private double discountValue; // Discount of model e.g 250.00
	
	/* Constructors 
	 * if no constructor are declared a implicit default constructor is there*/
	
	public Product() {
		// all attributes will be stored with their default values after an object is created
	}
	
	/* Accessors */
	
	/* Mutators */
}
