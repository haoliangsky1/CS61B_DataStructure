public class StudentLinkedListDequeLauncher {
	public static void main(String[] args) {
		StudentLinkedListDequeLauncher<Integer> slld1 = new StudentLinkedListDequeLauncher<Integer>();
		slld1.addLast(5);
		slld1.addFirst(10);
		slld1.printDeque();
	}
}