package junit_tests;

import static org.junit.Assert.*;

import org.junit.Test;

import model.Entry;
import model.Product;
import model.RefurbishedStore;

public class TestRefurbishedStore {

	@Test
	public void test_refurbished_store_1() {
		//Create an empty store 
		
		RefurbishedStore rs = new RefurbishedStore();
		
		assertTrue(rs.getNumberOfEntries() == 0);
		assertTrue(rs.getPrivateEntriesArray().length == 5);
		assertTrue(rs.getPrivateEntriesArray()[0] == null);
		assertTrue(rs.getPrivateEntriesArray()[1] == null);
		assertTrue(rs.getPrivateEntriesArray()[2] == null);
		assertTrue(rs.getPrivateEntriesArray()[3] == null);
		assertTrue(rs.getPrivateEntriesArray()[4] == null);
		assertTrue(rs.getEntries().length == 0);
		
		Product p1 = new Product("iPad Pro 12.9",1709.00);
		p1.setFinish("Space Grey");
		p1.setStorage(1000);
		p1.setHasCellularConnectivity(true);
		p1.setDiscountValue(220.00);
		
		Entry e1 = new Entry("F9FND4NKQ1GC",p1);
		
		rs.addEntry(e1);
		
		assertTrue(rs.getNumberOfEntries() == 1);
		assertTrue(rs.getPrivateEntriesArray().length == 5);
		assertTrue(rs.getPrivateEntriesArray()[0] == e1);
		assertTrue(rs.getPrivateEntriesArray()[1] == null);
		assertTrue(rs.getPrivateEntriesArray()[2] == null);
		assertTrue(rs.getPrivateEntriesArray()[3] == null);
		assertTrue(rs.getPrivateEntriesArray()[4] == null);
		assertTrue(rs.getEntries().length == 1);
		assertTrue(rs.getEntries()[0] == e1);
		
		Product p2 = new Product("iPad Air",649.00);
		p2.setFinish("Gold");
		p2.setStorage(64);
		p2.setHasCellularConnectivity(false);
		p2.setDiscountValue(100.00);
		
		rs.addEntry("C9FZN4J8QC82", p2);
		
		assertTrue(rs.getNumberOfEntries() == 2);
		assertTrue(rs.getPrivateEntriesArray().length == 5);
		assertTrue(rs.getPrivateEntriesArray()[0] == e1);
		assertTrue(rs.getPrivateEntriesArray()[1].getSerialNumber().equals("C9FZN4J8QC82"));
		assertTrue(rs.getPrivateEntriesArray()[1].getProduct() == p2);
		assertTrue(rs.getPrivateEntriesArray()[2] == null);
		assertTrue(rs.getPrivateEntriesArray()[3] == null);
		assertTrue(rs.getPrivateEntriesArray()[4] == null);
		assertTrue(rs.getEntries().length == 2);
		assertTrue(rs.getEntries()[0] == e1);
		assertTrue(rs.getEntries()[1].getSerialNumber().equals("C9FZN4J8QC82"));
		assertTrue(rs.getEntries()[1].getProduct() == p2);
		
		rs.addEntry("7YM4PFZ779UB", "iPad Pro 10.5", 929.00); 
		
		
		assertTrue(rs.getNumberOfEntries() == 3);
		assertTrue(rs.getPrivateEntriesArray().length == 5);
		assertTrue(rs.getPrivateEntriesArray()[0] == e1);
		assertTrue(rs.getPrivateEntriesArray()[1].getSerialNumber().equals("C9FZN4J8QC82"));
		assertTrue(rs.getPrivateEntriesArray()[1].getProduct() == p2);
		assertTrue(rs.getPrivateEntriesArray()[2].getSerialNumber().equals("7YM4PFZ779UB"));
		assertTrue(rs.getPrivateEntriesArray()[2].getProduct().getModel().equals("iPad Pro 10.5"));
		assertEquals(929.00,rs.getPrivateEntriesArray()[2].getProduct().getOriginalPrice(),0.1);
		assertTrue(rs.getPrivateEntriesArray()[3] == null);
		assertTrue(rs.getPrivateEntriesArray()[4] == null);
		assertTrue(rs.getEntries().length == 3);
		assertTrue(rs.getEntries()[0] == e1);
		assertTrue(rs.getEntries()[1].getSerialNumber().equals("C9FZN4J8QC82"));
		assertTrue(rs.getEntries()[1].getProduct() == p2);
		assertTrue(rs.getEntries()[2].getSerialNumber().equals("7YM4PFZ779UB"));
		assertTrue(rs.getEntries()[2].getProduct().getModel().equals("iPad Pro 10.5"));
		assertEquals(929.00,rs.getEntries()[2].getProduct().getOriginalPrice(),0.1);
		
	}

}
