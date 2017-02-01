// Defines a Deque interface for all deques.
public interface Deque<Item> {
	// Contain all of the methods that appear in both ArrayDeque and LinkedListDeque
	public void addFirst(Item x);
	public void addLast(Item x);
	public boolean isEmpty();
	public int size();
	public void printDeque();
	public Item removeFirst();
	public Item removeLast();
	public Item get(int index);
}
