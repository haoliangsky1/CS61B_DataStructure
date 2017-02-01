package editor;

import java.util.Iterator;
import java.util.LinkedList;

import java.io.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.util.Duration;
import java.lang.Math;
import java.util.ArrayList;


public class LinkedListDeque<Object> extends LinkedList<Object> { 
    // Create a sentinel pointer
    private Node sentinel;
    // Create a current position pointer:
    // Current node to be inserted after
    // private Node currentPositionPointer;
    // Character number in the text:
    private int currentPos;
    // Create a size pointer
    private int size;
    // Cursor Node
    private Node cursorNode;
    // Create the Node
    public class Node {
        // Points to the previous node
        public Node prev;
        // The actual item of the node
        public Object item;
        // Points to the next node
        public Node next;
        // The Text for the current node
        public Node nodeText;
        // Create the Node
        public Node(Node p, Object i, Node n) {
            prev = p;
            item = i;
            next = n;
        }
    }
    //Create an empty list
    public LinkedListDeque() {
        this.size = 0;
        currentPos = 0;
        sentinel = new Node(null, (Object) new Text(5, 0, ""), null);
        Text starter = new Text(0,0, "Cursor");
        cursorNode = new Node(sentinel, (Object) starter, null);
        sentinel.prev = cursorNode;
        sentinel.next = cursorNode;
    }
    // Set the cursor according to the hash function
    public void setCursor(Node pointer) {
        // System.out.println("reset the cursor node");
        cursorNode.prev = pointer;
        // also take care of the currentPos here, if possible
    }
    public Node getCursorNode() {
        return cursorNode;
    }
    public Node getCurrentNode() {
        return cursorNode.prev;
    }
    public Node getNextNode() {
        return cursorNode.prev.next;
    }
    // Get the node of the index:
    public Node getNode(int index) {
        if(index > this.size ) {
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
            return helper.prev;
        }
    }
    public String getCursorPosition() {
        String pos = "";
        pos = Integer.toString(this.getCursorX());
        pos += ", ";
        pos += Integer.toString(this.getCursorY());
        return pos;
    }

    // Return the X coordinate for the cursor
    public int getCursorX() {
        if (size == 0) {
            return 5;
        } else {
            Text temp = (Text) cursorNode.prev.item;
            if (temp.getText().equals("\n") || temp.getText().equals("\r\n") || temp.getText().equals("\r")) {
                return 5;
            } else {
                return (int) Math.round(temp.getX() + temp.getLayoutBounds().getWidth());
            }
        }
    }
    // Return the Y coordinate for the cursor
    public int getCursorY() {
        if (size == 0) {
            return 0;
        } else {
            Text temp = (Text) cursorNode.prev.item;
            return (int) Math.round(temp.getY());
        }
    }
    // Return the height of the last character
    public int getCursorHeight() {
        if (size == 0) {
            return 12;
        } else {
            Text temp = (Text) cursorNode.prev.item;
            return (int) Math.round(temp.getLayoutBounds().getHeight());
        }
    }
    // Return the width of the next character
    public int getTextWidth() {
        if (size == 0) {
            return 5;
        } else {
            Text temp = (Text) cursorNode.prev.next.item;
            return (int) Math.round(temp.getLayoutBounds().getWidth());
        }
    }

    // The method that adds the given Text to the linked list after the current Node
    public void addText(Text newText) {
        // System.out.println("adding newText");
        if (size == 0) {
            Node insert = new Node(sentinel, (Object) newText, sentinel);
            sentinel.prev = insert;
            sentinel.next = insert;
            cursorNode.prev = insert;
        } else {
            Node insert = new Node(cursorNode.prev, (Object) newText, cursorNode.prev.next);
            cursorNode.prev.next.prev = insert;
            cursorNode.prev.next = insert;
            cursorNode.prev = insert;
        }
        size += 1;
        currentPos += 1; 
        
        // Node insert = new Node(cursorNode.prev, (Object) newText, cursorNode);
        // cursorNode.prev.next = insert;
        // cursorNode.prev = insert;
    }
    // The method that deletes the currentNode
    public Object deleteText() {
        // System.out.println("deleteing Text");
        try {
            if (size > 0) {
                size -= 1;
                currentPos -= 1;
                Node helper = cursorNode.prev;
                cursorNode.prev.prev.next = cursorNode.prev.next;
                cursorNode.prev.next.prev = cursorNode.prev.prev;
                cursorNode.prev = cursorNode.prev.prev;
                return helper.item;
            } else {
                return null;
            }
        } catch (NullPointerException nullPointerExcetion) {
            return null;
        }
    }
    // Move cursor node to the left
    public void cursorMoveLeft() {
        if (currentPos > 0) {
            currentPos -= 1;
            cursorNode.prev = cursorNode.prev.prev;
        }
    }
    // Move cursor node to the right
    public void cursorMoveRight() {
        currentPos += 1;
        cursorNode.prev = cursorNode.prev.next;
    }
    // Return the number of items in the Deque
    // Must take constant time
    @Override
    public int size() {
        return this.size + 1;
    }
    // Get the item at the given index, where 0 is the front. 
    // Return null if no such item exits
    // Do not alter the deque: non-destructive, use new
    // Use iteration, not recursion
    @Override
    public Object get(int index) {
        if(index > this.size ) {
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

    // Return the current position
    public int currentPos() {
        return currentPos;
    }
}

