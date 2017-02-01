import java.util.Arrays;
// Performs some basic linked array
public class ArrayDequeTest{
	// Utility mehtod for printing out empty checks
	public static void main(String[] args) {
		ArrayDeque<Integer> q = new ArrayDeque<Integer>();
		int count = 500;
		for (int i = 0; i < count; i++) {
			q.addFirst(i);
		}
		q.printDeque();
		System.out.println("haha");
		for (int i = count; i >= 0; i--) {
			System.out.println(q.removeFirst());

		}
		System.out.println("haha");
		q.printDeque();
		// System.out.println("hehe");
		// for (int i = 0; i <= count; i++) {
		// 	q.addFirst(i);
		// }
		// q.addFirst(0);
  //       q.addFirst(1);
  //       q.addLast(2);
  //       int k  = q.get(1);
  //       System.out.println(k);      
		// q.printDeque();
	}
}