package model;

/*Template of a Collection of Entries*/

public class RefurbishedStore {
	private Entry[] entries;
	//private int[] sers;
	private final int MAX_CAPACITY = 5;
	private int noe;

	public RefurbishedStore() {
		this.entries = new Entry[MAX_CAPACITY];
		this.noe = 0;
	}
	
	public int getNumberOfEntries() {
		return this.noe;
	}
	
	public Entry[] getPrivateEntriesArray() {
		return this.entries;
	}
	
	public Entry[] getEntries() {
		Entry[] entries = new Entry[this.noe];
		
		for (int i = 0; i < this.noe; i++) {
			entries[i] = this.entries[i];
		}
		
		return entries;
	}
	
	//assume entry serial number here doesn't exist in the collection 
	public void addEntry(Entry entries) {
		this.entries[this.noe] = entries;
		this.noe++;
	}
	
	public void addEntry(String serialNumber,Product product) {
//		Entry newEntry = new Entry(serialNumber,product);
		
//		this.entries[this.noe] = newEntry;
//		this.noe++;
		
		this.addEntry(new Entry(serialNumber,product));
	}
	
	
	public void addEntry(String serialNumber,String model,double originalPrice) {
//		Product p = new Product(model,originalPrice);	
//		Entry newEntry = new Entry(serialNumber,new Product(model,originalPrice));
		
//		this.entries[this.noe] = newEntry;
//		this.noe++;
		
		this.addEntry(new Entry(serialNumber,new Product(model,originalPrice)));
	}
}
