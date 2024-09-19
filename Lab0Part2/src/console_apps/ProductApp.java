package console_apps;

import model.Entry;
import model.Product;
//import java.util.Scanner;

public class ProductApp {

	public static void main(String[] args) {
		//Scanner input = new Scanner(System.in);
		
		Product p = new Product();

		System.out.println(p); 
		
		Product p2 = new Product("Aipad Pro",1289.00);
		System.out.println(p2);

//		int[] entries1 = {1,2,3};
//		int[] entries2 = new int[3];
//
//		for (int i = 0; i < entries1.length - 1; i++) {
//			entries2[i] = entries1[i];
//		}
		
//		System.out.println("Enter a model:");
//		String model = input.nextLine();
//		
//		System.out.println("Enter the original price:");
//		double price = input.nextDouble();
//		
//		Product p3 = new Product(model,price);	
//		System.out.println(p3);
//		
//		input.close();
	}

}
