package junit_tests;

import static org.junit.Assert.*;
import model.*;

import org.junit.Test;

public class TestEntry {

	@Test
	public void test_entry_1() {
		Product p1 = new Product("iPad Pro 12.9",1709.00);
		p1.setFinish("Space Grey");
		p1.setStorage(1000);
		p1.setHasCellularConnectivity(true);
		p1.setDiscountValue(220.00);

		Entry e = new Entry("F9DN4NKQ1GC",p1);

		//assertTrue(e.getSerialNumber() == "F9DN4NKQ1GC"); will not work sometimes and not work sometimes 
		assertEquals("F9DN4NKQ1GC",e.getSerialNumber());
		assertTrue(e.getProduct() == p1);
		assertSame(e.getProduct(),p1);
		assertEquals("[F9DN4NKQ1GC] iPad Pro 12.9 Space Grey 1000GB (cellular connectivity: true): $(1709.00 - 220.00)",e.toString());

		assertEquals("iPad Pro 12.9",e.getProduct().getModel());
		assertTrue(e.getProduct().getModel().equals("iPad Pro 12.9"));
		assertEquals("Space Grey",e.getProduct().getFinish());
		assertTrue(1000 == e.getProduct().getStorage());
		assertTrue(e.getProduct().hasCellularConnectivity());
		assertEquals(1709.00,e.getProduct().getOriginalPrice(),0.1);
		assertEquals(220.00,e.getProduct().getDiscountValue(),0.1);
		assertEquals(1489.00,e.getProduct().getPrice(),0.1);
		assertEquals("iPad Pro 12.9 Space Grey 1000GB (cellular connectivity: true): $(1709.00 - 220.00)",e.getProduct().toString());
	}

	@Test
	public void test_entry_2() {
		Product p1 = new Product("iPad Pro 12.9",1709.00);
		p1.setFinish("Space Grey");
		p1.setStorage(1000);
		p1.setHasCellularConnectivity(true);
		p1.setDiscountValue(220.00);

		Entry e = new Entry(new String("F9DN4NKQ1GC"),p1);

		//assertTrue(e.getSerialNumber() == "F9DN4NKQ1GC"); will not work sometimes and not work sometimes 
		assertEquals("F9DN4NKQ1GC",e.getSerialNumber());
		//assertTrue(e.getSerialNumber().equals("F9DN4NKQ1GC"));
		assertTrue(e.getProduct() == p1);
		assertSame(e.getProduct(),p1);
		assertEquals("[F9DN4NKQ1GC] iPad Pro 12.9 Space Grey 1000GB (cellular connectivity: true): $(1709.00 - 220.00)",e.toString());

		assertEquals("iPad Pro 12.9",e.getProduct().getModel());
		assertTrue(e.getProduct().getModel().equals("iPad Pro 12.9"));
		assertEquals("Space Grey",e.getProduct().getFinish());
		assertTrue(1000 == e.getProduct().getStorage());
		assertTrue(e.getProduct().hasCellularConnectivity());
		assertEquals(1709.00,e.getProduct().getOriginalPrice(),0.1);
		assertEquals(220.00,e.getProduct().getDiscountValue(),0.1);
		assertEquals(1489.00,e.getProduct().getPrice(),0.1);
		assertEquals("iPad Pro 12.9 Space Grey 1000GB (cellular connectivity: true): $(1709.00 - 220.00)",e.getProduct().toString());

		Product p2 = new Product("iPad Air",649.00);
		p2.setFinish("Gold");
		p2.setStorage(64);
		p2.setHasCellularConnectivity(false); // redundant 
		p2.setDiscountValue(100.00);

		// change associated product from p1 to p2
		e.setProduct(p2);
		
		assertEquals("F9DN4NKQ1GC",e.getSerialNumber());
		assertFalse(e.getProduct() == p1);
		assertNotSame(e.getProduct(),p1);
		assertTrue(e.getProduct() == p2); 
		assertSame(e.getProduct(),p2);
		assertEquals("[F9DN4NKQ1GC] iPad Air Gold 64GB (cellular connectivity: false): $(649.00 - 100.00)",e.toString());
		
		// change associated product from p2 to another object
		e.setProudct("iPad Air", 649.00);
		
		assertEquals("F9DN4NKQ1GC",e.getSerialNumber());
		assertFalse(e.getProduct() == p1);
		assertNotSame(e.getProduct(),p1);
		assertFalse(e.getProduct() == p2);
		assertNotSame(e.getProduct(),p2);
		assertTrue(e.getProduct() == e.getProduct()); 
		assertSame(e.getProduct(),e.getProduct());
		assertEquals("[F9DN4NKQ1GC] iPad Air null 0GB (cellular connectivity: false): $(649.00 - 0.00)",e.toString());
	}

}
