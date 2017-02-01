public class ArrayDeque<Item> {
    // Create an array to store item
    private Item[] items;
    // Create the size object the store the size of the array
    private int size;
    private int front;
    private int back;
    // Set the resize factor to save time
    private static int RFACTOR  = 2;

    // Create and empty list
    public ArrayDeque() {
        size = 0;
        items = (Item[]) new Object[8];
        front = 0;
        back = 1;

    }
    // Add an item to the front of the Deque
    // Use a circular structure
    public void addFirst(Item x) {
        if (size == 0) {
            items[front] = x;
            front = minusOne(front);
        } else {
            // Test to see whether we would resize
            if (size == items.length - 1) {
                // Use the resize function as helper
                resize(size * RFACTOR);
                // Then reassign the front and back pointer
                front = items.length - 1;
                back = size;
                // Now add the item at the front
                items[front] = x;
                front = minusOne(front);
            } else {
                // If no need to resize
                items[front] = x;
                front = minusOne(front);
            }
        }
        size += 1;
    }
    //  Write the helper function to resize the array
    private void resize(int capacity) {
        // Create a new array, doubling the size
        Item[] helper = (Item[]) new Object[capacity];
        // Copy the inputs from the original array to the new one
        // Start with the front pointer
        if (front == items.length - 1) {
            System.arraycopy(items, 0, helper, 0, size);
        } else {
        	// When resizing up, size >= front
        	// When resizing down, size could be smaller than front.
        	// When reducing the size need to adjust again, out of bound otherwise
        	// To decide whether resize up ot down
            int part1 = 0;
            if (front < back) {
                part1 = size;
            } else {
                part1 = items.length - front - 1;
            }
        	int part2 = size - part1;
        	System.arraycopy(items, plusOne(front), helper, 0, part1);
        	System.arraycopy(items, 0, helper, part1, part2);
        }
        items = helper;
    }
    // Write the helper function to find the index immediately before a given index
    // Takes in the front variable and return the one in the front
    private int minusOne(int index) {
        if (index == 0) {
            return items.length - 1;
        } else {
            return index - 1;
        }
    }
    // Write the helper function to find the index immediately before a given index
    // Takes in the front variable and return the one in the front
    private int plusOne(int index) {
        if (index == items.length - 1) {
            return 0;
        } else {
            return index + 1;
        }
    }
    // Add an item to the back of the Deque
    public void addLast(Item x) {
        if (size == 0) {
            items[back] = x;
            back = plusOne(back);
        } else {
            // Test if we need to resize
            // When the two pointer collide, we resize
            if (size == items.length - 1) {
                resize(size * RFACTOR);
                front = items.length - 1;
                back = size;
                // Now add the new item
                items[back] = x;
                back = plusOne(back);
            } else {
                items[back] = x;
                back = plusOne(back);
            }
        }
        size += 1;
    }

    // Return true if deque is empty, false otherwise
    public boolean isEmpty() {
        return size == 0;
    }
    // Return the number of items in the Deque
    public int size() {
        return size;
    }
    // Prints the items in the Deque from front to back, separated by a space
    public void printDeque() {
    	if (front < back) {
    		for (int i = front + 1; i < back; i++) {
    			System.out.print(items[i] + " ");
    		}
    	} else {
    		for (int i = front + 1; i < items.length; i++) {
    			System.out.print(items[i] + " ");
    		}
    		for (int j = 0; j < back; j++) {
    			System.out.print(items[j] + " ");
    		}
    	}
    }
        // Remove and return the item at the front of the Deque. If no, return null
    public Item removeFirst() {
        if (size == 0) {
            front = 0;
            back = 1;
            return null;
        } else {
            // Consider the amount of memory must be proportional to the number of items.
            // For arrays of length 16 or more, the usage factor should always be at least 25%.
            if (items.length >= 16) {
                double proportion = size * 1.0 / items.length;
                // Check the proportion to see whether we should resize again.
                if (proportion < 0.25) {
                    resize(items.length / RFACTOR);
                    // Take out the first item
                    Item helper = items[0];
                    front = 0;
                    back = size - 1;
                    size -= 1;
                    return helper;
                }
            }
            front = plusOne(front);
            Item helper = items[front];
            size -= 1;
            return helper;
        }
    }

    // Remove and return the item at the back of the Deque. If no, return null
    public Item removeLast() {
        if (size == 0) {
            front = 0;
            back = 1;
            return null;
        } else {
            if (items.length >= 16) {
                double proportion = size * 1.0 / items.length;
                if (proportion < 0.25) {
                    resize(items.length / RFACTOR);
                    // Take out the last item
                    Item helper = items[size - 1];
                    front = items.length - 1;
                    back = size - 1;
                    size -= 1;
                    return helper;
                }
            }
            back = minusOne(back);
            Item helper = items[back];
            size -= 1;
            return helper;
        }
    }
    // Get the item at the given index, where 0 is frontmost item, 1 is the next item, and so forth.
    // If no, returns null.
    public Item get(int index) {
        if (size == 0) {
            return null;
        } else {
            if (plusOne(front) + index <= items.length - 1) {
                return items[plusOne(front) + index];
            } else {
            	int helper = plusOne(front) + index - items.length;
            	return items[helper];
            }
        }
    }
}
