package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    // Return size of the buffer
    int capacity();
    // Return number of items currently in the buffer
    int fillCount();
    // Add item x to the end
    void enqueue(T x);
    // Delete and return item from the front
    T dequeue();
    // Return (but do not delete) item from the front
    T peek();
    // Return whether the buffer is empty
    default boolean isEmpty() {
        return (this.fillCount() == 0);
    }
    // Return whether the buffer is full
    default boolean isFull() {
        return (this.fillCount() == this.capacity());
    }
    // Iterator
    Iterator<T> iterator();
}
