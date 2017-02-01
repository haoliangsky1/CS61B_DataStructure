package editor;

import java.util.Stack;
import java.util.ArrayList;

public class MyStack<Object> extends Stack<Object> {
    private static final int DEFAULT_MAX  = 100;
    // To account for the maximun size of 100:
    private int maxSize;
    // The stack
    private ArrayList<Object> stackArray;
    // Do know what this is yet
    private int top;
    private int currentPointer;
    private int counter;

    // Constructor:
    public MyStack() {
        // Set up the maximun size
    maxSize = DEFAULT_MAX;
    stackArray = new ArrayList<Object>();
    top = -1;
    currentPointer = -1;
    counter = 1;
    }
    // return the current pos:
    public int getTop() {
        return top;
    }
    // The method that takes in stuff
    // @Override
    public void putIn(Object action) {
        if (counter == maxSize) {
            // when the counter reaches the maxsize
            // We remove the earliest entry;
            stackArray.remove(0);
        }
        currentPointer += 1;
        stackArray.add(action);
        counter += 1;
        top++;
    }
    // Replace the element
    public void replace(Object action) {
        stackArray.set(currentPointer, action);
    }
    // The method that pops out stuff
    // @Override
    public Object popOut() {
        currentPointer -= 1;
        top--;
        return stackArray.get(currentPointer + 1);
    }
    // Have a look at the
    @Override
    public Object peek() {
        return stackArray.get(0);
    }
    public boolean isEmpty() {
        return (top == maxSize - 1);
    }
    public boolean isFull() {
        return (top == maxSize - 1);
    }
}






