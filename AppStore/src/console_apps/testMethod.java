package console_apps;
import model.Log;

public class testMethod {

	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		String[] s1 = {"1", "2","3"};
//		String result = "[" + s1[0];
//
//		for (int i = 1; i < s1.length; i++) {
//			result += "," + s1[i];
//		}
//
//		result += "]";
//
//		System.out.println(result);

		// Example array with null values
		String[] array = { "apple",null,null, null, "banana", null, "orange", null };

		// Step 1: Count the non-null elements
		int nonNullCount = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				nonNullCount++;
			}
		}

		// Step 2: Create a new array to hold only non-null elements
		String[] result = new String[nonNullCount];

		// Step 3: Copy non-null elements to the new array
		int index = 0;
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null) {
				result[index++] = array[i];
			}
		}

		// Print the result
		for (int i = 0; i < result.length; i++) {
			System.out.print(result[i] + " ");
		}

	}

}
