public class LinkedListDeque<Item> {
    // Create a sentinel pointer
    private Node sentinel;
    // Create a size pointer
    private int size;
    // Create the Node
    public class Node {
        // Points to the previous node
        public Node prev;
        // The actual item of the node
        public Item item;
        // Points to the next node
        public Node next;
        // Create the Node
        public Node(Node p, Item i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    //Create an empty list
    public LinkedListDeque() {
        this.size = 0;
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
    }
    // Add an item to the front of the Deque.
    // Do not involve any looping or recursion.
    // use Circular Sentinel
    public void addFirst(Item x) {
        if(this.size == 0) {
            Node insert = new Node(sentinel, x, sentinel);
            sentinel.next = insert;
            sentinel.prev = insert;
        } else {
            Node insert = new Node(sentinel, x, sentinel.next);
            sentinel.next.prev = insert;
            sentinel.next = insert;
        }
        this.size += 1;
    }
    // Add an item to the back of the Deque.
    // Do not involve any looping or recursion.
    // use Circular Sentinel
    public void addLast(Item x) {
        if(this.size == 0) {
            Node insert = new Node(sentinel, x, sentinel);
            sentinel.next = insert;
            sentinel.prev = insert;
        } else {
            Node helper = sentinel.prev;
            Node insert = new Node(helper, x, sentinel);
            helper.next = insert;
            sentinel.prev = insert;
        }
        this.size += 1;
    }
    // Return true if deque is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }
    // Return the number of items in the Deque
    // Must take constant time
    public int size() {
        return this.size;
    }
    // Print the items in the Deque from first to last, separated by a space
    public void printDeque() {
        int counter = 0;
        Node helper = sentinel;
        while(counter < this.size) {
            System.out.print(helper.next.item + " ");
            helper = helper.next;
            counter += 1;
        }
    }
    // Remove and return the item at the front of the Deque. If no, return null
    public Item removeFirst() {
        if(this.size == 0) {
            return null;
        } else {
            Item helper = sentinel.next.item;
            sentinel.next = sentinel.next.next;
            sentinel.next.prev = sentinel;
            this.size -= 1;
            return helper;
        }
    }
    // Remove and return the item at the last of the Deque. If no, return null
    public Item removeLast() {
        if(this.size == 0) {
            return null;
        } else {
            Item helper = sentinel.prev.item;
            sentinel.prev.prev.next = sentinel;
            sentinel.prev = sentinel.prev.prev;
            this.size -= 1;
            return helper;
        }

    }
    // Get the item at the given index, where 0 is the front. 
    // Return null if no such item exits
    // Do not alter the deque: non-destructive, use new
    // Use iteration, not recursion
    public Item get(int index) {
        if(index > this.size - 1) {
            return null;
        }
        if(this.size == 0) {
            return null;
        } else {
            int counter = 0;
            Node helper = sentinel;
            while(counter <= index) {
                helper = helper.next;
                counter += 1;
            }
            return helper.item;
        }
    }
    // The recursive version of the get item method
    public Item getRecursive(int index) {
        if(index > size - 1) {
            return null;
        }
        if(size == 0) {
            return null;
        } else {
            if(index == 0) {
                return sentinel.next.item;
            } else {
                LinkedListDeque<Item> helper = new LinkedListDeque<Item>();
                helper.sentinel = sentinel.next;
                helper.size = this.size - 1;
                return helper.getRecursive(index - 1);
            }
        }
    }
}
