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
	
	/* Overloaded version of Product */
	
	public Product(String model, double orignalPrice) {
		this.model = model;
		this.originalPrice = orignalPrice;
	}
	
	/* Accessors and Mutators */
	
	public String getModel() {
		return this.model;
	}
	
	
	public void setModel(String model) {
		this.model = model;
	}

	public String getFinish() {
		return this.finish;
	}

	public void setFinish(String finish) {
		this.finish = finish;
	}

	public int getStorage() {
		return this.storage;
	}

	public void setStorage(int storage) {
		this.storage = storage;
	}

	public boolean hasCellularConnectivity() {
		return this.hasCellularConnectivity;
	}

	public void setHasCellularConnectivity(boolean hasCellularConnectivity) {
		this.hasCellularConnectivity = hasCellularConnectivity;
	}

	public double getOriginalPrice() {
		return this.originalPrice;
	}

	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}

	public double getDiscountValue() {
		return this.discountValue;
	}

	public void setDiscountValue(double discountValue) {
		this.discountValue = discountValue;
	}
	
	public double getPrice() {
		return (getOriginalPrice() - getDiscountValue());
	}
	
	public String toString() {
		/*
		 * StringBuilder sb = new StringBuilder();
		 * 
		 * sb.append(getModel() + " " + getFinish() + " " + getStorage() + "GB " +
		 * "(cellular connectivity: " + isHasCellularConnectivity() + "): $(" +
		 * String.format("%.2f", getOriginalPrice()) + " - " + String.format("%.2f", +
		 * getDiscountValue()) + ")");
		 * 
		 * return sb.toString();
		 */
		
		/*
		 * return (getModel() + " " + getFinish() + " " + getStorage() + "GB " +
		 * "(cellular connectivity: " + isHasCellularConnectivity() + "): $(" +
		 * String.format("%.2f", getOriginalPrice()) + " - " + String.format("%.2f",
		 * getDiscountValue()) + ")");
		 */
		
		String s = String.format("%s %s %dGB (cellular connectivity: %s): $(%.2f - %.2f)", 
				getModel(), getFinish(), getStorage(), hasCellularConnectivity(), 
				getOriginalPrice(), getDiscountValue());
		
		return s;		
	}
}
