/** Performs some basic linked list tests. */
public class LinkedListDequeTest {
	public static void main(String[] args) {
		LinkedListDeque<Integer> p = new LinkedListDeque<Integer> ();
		int count = 30;
		for (int i = 0; i < count; i++) {
			p.addFirst(i);
		}
		p.printDeque();
		System.out.println("look");
		for (int i = 0; i < count; i++) {
			System.out.println(p.removeLast());
		}
		p.printDeque();
	}
	
	// /* Utility method for printing out empty checks. */
	// public static boolean checkEmpty(boolean expected, boolean actual) {
	// 	if (expected != actual) {
	// 		System.out.println("isEmpty() returned " + actual + ", but expected: " + expected);
	// 		return false;
	// 	}
	// 	return true;
	// }

	// /* Utility method for printing out empty checks. */
	// public static boolean checkSize(int expected, int actual) {
	// 	if (expected != actual) {
	// 		System.out.println("size() returned " + actual + ", but expected: " + expected);
	// 		return false;
	// 	}
	// 	return true;
	// }

	// /* Prints a nice message based on whether a test passed. 
	//  * The \n means newline. */
	// public static void printTestStatus(boolean passed) {
	// 	if (passed) {
	// 		System.out.println("Test passed!\n");
	// 	} else {
	// 		System.out.println("Test failed!\n");
	// 	}
	// }

	// /** Adds a few things to the list, checking isEmpty() and size() are correct, 
	//   * finally printing the results. 
	//   *
	//   * && is the "and" operation. */
	// public static void addIsEmptySizeTest() {
	// 	System.out.println("Running add/isEmpty/Size test.");
		
	// 	LinkedListDeque<String> lld1 = new LinkedListDeque<String>();

	// 	boolean passed = checkEmpty(true, lld1.isEmpty());

	// 	lld1.addFirst("front");
	// 	passed = checkSize(1, lld1.size()) && passed;
	// 	passed = checkEmpty(false, lld1.isEmpty()) && passed;

	// 	lld1.addLast("middle");
	// 	passed = checkSize(2, lld1.size()) && passed;

	// 	lld1.addLast("back");
	// 	passed = checkSize(3, lld1.size()) && passed;

	// 	System.out.println("Printing out deque: ");
	// 	lld1.printDeque();

	// 	printTestStatus(passed);
		
	// }

	// /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
	// public static void addRemoveTest() {

	// 	System.out.println("Running add/remove test.");

		
	// 	LinkedListDeque<Integer> lld1 = new LinkedListDeque<Integer>();
	// 	// should be empty 
	// 	boolean passed = checkEmpty(true, lld1.isEmpty());

	// 	lld1.addFirst(10);
	// 	// should not be empty 
	// 	passed = checkEmpty(false, lld1.isEmpty()) && passed;

	// 	lld1.removeFirst();
	// 	// should be empty 
	// 	passed = checkEmpty(true, lld1.isEmpty()) && passed;

	// 	printTestStatus(passed);
		
	// 	//another test
	// 	LinkedListDeque<Integer> lld2 = new LinkedListDeque<Integer>();
	// 	lld2.addFirst(10);
	// 	lld2.addFirst(30);
	// 	lld2.addFirst(50);
	// 	lld2.printDeque();
	// 	System.out.println("Removing first.");
	// 	System.out.println(lld2.size());
	// 	Integer k = lld2.removeLast();
	// 	System.out.println(k);
	// 	System.out.println(lld2.size());
	// 	lld2.printDeque();

	// }

	// public static void main(String[] args) {
	// 	System.out.println("Running tests.\n");
	// 	addIsEmptySizeTest();
	// 	addRemoveTest();
	// }
} 