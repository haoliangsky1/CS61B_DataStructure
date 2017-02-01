// Make sure to make this class a part of the synthesizer package
package synthesizer;
import java.util.Iterator;

// Make sure to make this class and all of its methods public
// Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        //       Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        // Array with length of capacity
        rb = (T[]) new Object[this.capacity];
        this.fillCount = 0;
        this.first = 0;
        this.last = 0;
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    @Override
    public void enqueue(T x) {
        // Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.isFull()) {
            throw new RuntimeException("Ring Buffer Overflow");
        } else {
            this.rb[this.last] = x;
            this.fillCount += 1;
            this.last = next(this.last);
        }
    }
    // Helper function to advance index
    private int next(int x) {
        if (x == this.capacity - 1) {
            return 0;
        } else {
            return (x + 1);
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    @Override
    public T dequeue() {
        // Dequeue the first item. Don't forget to decrease fillCount and update first
        if (this.isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            T ans  = this.rb[this.first];
            this.fillCount -= 1;
            this.first = next(this.first);
            return ans;
        }
    }

    /**
     * Return oldest item, but don't remove it.
     */
    @Override
    public T peek() {
        // Return the first item. None of your instance variables should change.
        if (this.isEmpty()) {
            throw new RuntimeException("Ring Buffer Underflow");
        } else {
            return this.rb[this.first];
        }
    }
    // We build the private class for iterator
    private class QueueIterator implements Iterator<T> {
        private int index;
        public QueueIterator() {
            index  = 0;
        }
        public boolean hasNext() {
            return (index < fillCount);
        }
        public T next() {
            T value  = rb[index];
            index += 1;
            return value;
        }
    }
    @Override
    public Iterator<T> iterator() {
        return new QueueIterator();
    }
    // When you get to part 5, implement the needed code to support iteration.
}
