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
	// e.g
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
	
	
	public Product getProduct(String sn) {
		int index = -1;
		
		for (int i = 0; i < this.noe; i++) {
			if (this.entries[i].getSerialNumber().equals(sn)) {
				index = i;
			}
		}
		
		if (index < 0) {
			return null;
		} else {
			return this.entries[index].getProduct();
		}
	}
	
	public String[] getSpaceGreyOrPro() {
		int count = 0;
		int[] indices = new int[this.noe];
		
		for (int i = 0; i < this.noe; i++) {
			Product p = this.entries[i].getProduct();
			
			if ((p.getModel().contains("Pro") || p.getFinish().contains("Space Grey"))  && p.getFinish() != null) {
				indices[count] = i;
				count++;
			}			
		}
		
		String[] sns = new String[count];
		
		for (int i = 0; i < count; i++) {
			sns[i] = this.entries[indices[i]].getSerialNumber();
		}
		
		return sns;
	}
	
	public String[] getSpaceGreyPro() {
		int count = 0;
		int[] indices = new int[this.noe];
		
		for (int i = 0; i < this.noe; i++) {
			Product p = this.entries[i].getProduct();
			
			if (p.getModel().contains("Pro") && p.getFinish() != null && p.getFinish().contains("Space Grey")) {
				indices[count] = i;
				count++;
			}			
		}
		
		String[] sns = new String[count];
		
		for (int i = 0; i < count; i++) {
			sns[i] = this.entries[indices[i]].getSerialNumber();
		}
		
		return sns;
	}
}
