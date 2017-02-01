// Package
package editor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.control.ScrollBar;
import javafx.util.Duration;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import javafx.scene.shape.Rectangle;
import javafx.event.EventType;
import java.lang.String;
import java.util.ArrayList;
import java.util.Arrays;
// Consider using LinkedListDeque<Object> as the data structure that store the input
// ArrayList is resizable, and pertmits all elements.
// The size, isEmpty, get operations run in constant time


public class Editor extends Application {
    private final Rectangle textBoundingBox;
    // The list that saves all the Text input
    private static LinkedListDeque<Object> listOfWords;
    // The list that saves the line starting node
    // private static ArrayList<Node> linePointer;
    // private static LinkedListDeque<Object> linePointer;
    private LinkedListDeque<Object> stackList;

    private static Object[] linePointer;
    private static int[] numberOfCharacter;

    private static Group root;
    private static Clipboard clipboard;

    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    private static final int DEFAULT_FONT_SIZE = 12;
    private static final String DEFAULT_FONT_NAME = "Verdana";
    private final int MARGIN = 5;

    private static int fontSize;
    private static String fontName;
    private static int fontHeight;
    // Give the cursor position
    private static int cursorX;
    private static int cursorY;
    private static int currentPos;
    private static CursorDisplay cursor;
    private static ScrollBar scrollBar;
    private static int currentWindowWidth;
    private static int currentWindowHeight;

    private static int numberOfRows;

    private static ArrayList<Rectangle> selectionBoxes;

    private static final String NEW_CLIPBOARD_CONTENT = "";

    private static MyStack<Object> actionSaver;
    private static MyStack<Object> undoSaver;
    private static boolean flag;




    public int currentPos() {
        return currentPos;
    }
    public static int getFontSize() {
        return fontSize;
    }
    public static String getFontName() {
        return fontName;
    }
    public static int getFontHeight() {
        return fontHeight;
    }
    public static Group getRoot() {
        return root;
    }
    public static int getWindowWidth() {
        return (int) currentWindowWidth;
    }
    public static int getWindowHeight() {
        return (int) currentWindowHeight;
    }
    public static int getNumberOfRows() {
        return numberOfRows;
    }
    public static int[] getNumberOfCharacter() {
        return numberOfCharacter;
    }
    // Constructor: set up all the default values and position:
    public Editor() {
        currentWindowWidth = WINDOW_WIDTH;
        currentWindowHeight = WINDOW_HEIGHT;
        root = new Group();
        textBoundingBox = new Rectangle(0, 0);
        currentWidth = 5;
        fontSize = DEFAULT_FONT_SIZE;
        fontName = DEFAULT_FONT_NAME;
        fontHeight = 15;
        // System.out.println("Construct a new Editor");
        scrollBar = new ScrollBar();
        listOfWords = new LinkedListDeque<Object>();
        linePointer = new Object[1];
        numberOfCharacter = new int[1];
        // linePointer = new LinkedListDeque<Object> ();
        // System.out.println(listOfWords.getCursorNode());
        linePointer[0] = (Object) listOfWords.getCursorNode();
        numberOfCharacter[0] = 0;
        numberOfRows = 1;
        flag = false;

        // System.out.println(linePointer);
        actionSaver = new MyStack<Object>();
        undoSaver = new MyStack<Object>();
        selectionBoxes = new ArrayList<Rectangle>();

        stackList = new LinkedListDeque<Object>();

    }

    private static int currentWidth;
    private static int currentHeight;
    private static Text current = new Text("");
    private static Text previous = new Text("");
    private static int length;
    private static int characterHeight;

    // The render method that recalculates the X, Y coordinate:
    public void render(LinkedListDeque<Object> list, int windowWidth, int windowHeight) {
        scrollBar = new ScrollBar();
        windowWidth -= (int) scrollBar.getLayoutBounds().getWidth();
        currentWidth = 5;
        currentHeight = 0;
        characterHeight = 15;
        length = list.size();
        // System.out.println("Into the rendering process");
        int rowCount = 0;
        linePointer = new Object[length];
        numberOfCharacter = new int[length];
        // System.out.println(list.get(rowCount));
        linePointer[rowCount] = list.getNode(rowCount);
        rowCount += 1;
        numberOfRows = 1;
        flag = false;
        int extraWidth = 0;
        for (int i = 0; i < length - 1; i++) {
            if (i > 0) {
                previous = (Text) list.get(i - 1);
            } else {
                previous = new Text(currentWidth, currentHeight, "");
            }
            previous.setFont(Font.font(Editor.getFontName(), Editor.getFontSize()));
            current = (Text) list.get(i);
            current.setFont(Font.font(Editor.getFontName(), Editor.getFontSize()));
            characterHeight = Math.min((int) current.getLayoutBounds().getHeight(), characterHeight);
            numberOfCharacter[rowCount - 1] += 1;
            // This is for enter
            if (current.getText().equals("\n") || current.getText().equals("\r\n") || current.getText().equals("\r")) {
                // System.out.println("This is a new line");
                // Everytime we change a line, we add the first node of the line to the 
                // rowCount position
                // And increment the rowCount
                linePointer[rowCount] = (Object) list.getNode(i);
                rowCount += 1;

                currentWidth = 5;
                // The new line should only increment the Y-coordinate by 
                // currentHeight += (int) Math.round(previous.getLayoutBounds().getHeight());
                currentHeight += characterHeight;
                numberOfRows += 1;
            }

            // This is for wordwrapping
            if (currentWidth + (int) previous.getLayoutBounds().getWidth() > windowWidth - 5) {
                flag = true;
                // System.out.println("Word Wrap");
                // write an inner for loop to find the previous one
                for (int j = i; j > 1; j--) {
                    // go over from the last to find the nearest " " and starts from there
                    Text tempJ = (Text) listOfWords.get(j - 1);
                    if (tempJ.getText().equals(" ")) {
                        // System.out.println("detect the empty space");
                        int tempX = 5;
                        // int tempY = (int) tempJ.getLayoutBounds().getHeight() + characterHeight;
                        currentHeight += 2 * characterHeight;
                        linePointer[rowCount] = (Object) list.getNode(j);
                        for (int k = j; k < i; k++) {
                            Text tempK = (Text) listOfWords.get(k);
                            int widthK = (int) tempK.getLayoutBounds().getWidth();
                            int heightK = (int) tempK.getLayoutBounds().getHeight();
                            tempK.setX(tempX);
                            tempK.setY(currentHeight);
                            tempK.setTextOrigin(VPos.TOP);
                            tempK.setFont(Font.font(Editor.getFontName(), Editor.getFontSize()));
                            tempX += widthK;
                            extraWidth = (int) tempK.getX();
                        }
                        break;
                    }
                }
                // And increment the rowCount
                rowCount += 1;
                numberOfRows += 1;
                currentWidth = 0;
                current.setTextOrigin(VPos.TOP);
                current.setFont(Font.font(Editor.getFontName(), Editor.getFontSize()));
            }
            // The X coordinate for the next character
            if (flag == true) {
                currentWidth += ((int) Math.round(previous.getLayoutBounds().getWidth()) + extraWidth);
            } else {
                currentWidth += (int) Math.round(previous.getLayoutBounds().getWidth());
            }
            current.setTextOrigin(VPos.TOP);
            current.setFont(Font.font(Editor.getFontName(), Editor.getFontSize()));
            current.setX(currentWidth);
            current.setY(currentHeight);
        }

    }
    public void setScrollBar() {
        if (listOfWords.getCursorY() > getWindowHeight()) {
            // In this situation, move the scrollBar down
            scrollBar.setValue(listOfWords.getCursorY());
        }
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(getWindowHeight());
        root.getChildren().add(scrollBar);
        scrollBar.setLayoutX(getWindowWidth() - scrollBar.getLayoutBounds().getWidth());
    }

    /** An EventHandler to handle keys that get pressed. */
    private class KeyEventHandler implements EventHandler<KeyEvent> {
        int textCenterX;
        int textCenterY;
        double textHeight;
        double textWidth;

        /** The Text to display on the screen. */
        // We have the initilization here
        private Text displayText = new Text(0, 0, "");
        private Group keyroot;
        public Group getRoot() {
            return keyroot;
        }
        // This function is only called once at the first type 
        // Thus root.getChildren().add() only takes care of the first element
        // If we want to add subsequent Text to the nodes, we should do it in the handle method.
        public KeyEventHandler(final Group root, int windowWidth, int windowHeight) {
            // deal with opening case later
            keyroot = root;
            textCenterX = 5;
            textCenterY = 0;
            // Initialize the cursor here:
            cursor = new CursorDisplay();
        }

        // Save helper;
        public void save() {
            String name = "NewFile";
            // System.out.println("in the condition to save file");
            if (getParameters().getRaw().isEmpty()) {
                // System.out.println("NewFile");
                name = "NewFile.txt";
            } else {
                name = getParameters().getRaw().get(0);
                // System.out.println(name);
                File fold = new File(name);
                fold.delete();
            }
            File fnew = new File(name);
            try {
                FileWriter f2 = new FileWriter(fnew, true);
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    Text temp = (Text) listOfWords.get(i);
                    String tempS = temp.getText();
                    f2.write(tempS);
                }
                f2.close();
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }

        }
        // Size up helper
        public void fontSizeUp() {
            // System.out.println("enlarge");
            fontSize += 4;
            render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
            getRoot().getChildren().remove(cursor.cursorBoundingBox());
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
            getRoot().getChildren().add(cursor.cursorBoundingBox());
        }

        // Size down helper
        public void fontSizeDown() {
            // System.out.println("shrink");
            fontSize -= 4;
            render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
            getRoot().getChildren().remove(cursor.cursorBoundingBox());
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
            getRoot().getChildren().add(cursor.cursorBoundingBox());

        }
        // Type charater helper
        public void typeCharacter(String characterTyped) {
            currentPos += 1;
            // Object stackCursor = (Object) listOfWords.getCurrentNode();
            stackList = new LinkedListDeque<Object> ();
            for (int j = 0; j < listOfWords.size() - 1; j++) {
                stackList.addText((Text) listOfWords.get(j));
            }
            getRoot().getChildren().clear();
            // System.out.println("Typing character");
            Text input  = new Text(characterTyped);
            listOfWords.addText(input);

            render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
            // The rendering process change the X, Y, and we use it as an identifier
            actionSaver.putIn(stackList);

            for (int i = 0; i < listOfWords.size() - 1; i++) {
                getRoot().getChildren().add((Text) listOfWords.get(i));
            }
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());    
            getRoot().getChildren().add(cursor.cursorBoundingBox());               
            setScrollBar();
        }

        // Back space helper
        public void backSpace() {
            if (currentPos > 0) {
                stackList = new LinkedListDeque<Object> ();
                for (int j = 0; j < listOfWords.size() - 1; j++) {
                    stackList.addText((Text) listOfWords.get(j));
                }
                listOfWords.deleteText();
                actionSaver.putIn((Object) stackList);
                currentPos -= 1;
                render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
                getRoot().getChildren().clear();
                setScrollBar();
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    getRoot().getChildren().add((Text) listOfWords.get(i));
                }
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                getRoot().getChildren().add(cursor.cursorBoundingBox());                   
            }
        }
        // Undo an action
        public void undo() {
            // System.out.println("in the condition for undo");
            // Call back the proper position in the actionSaver;
            try {
                LinkedListDeque<Object> helper = (LinkedListDeque<Object>) actionSaver.popOut();
                // System.out.println("then we copy");
                LinkedListDeque<Object> temp = (LinkedListDeque<Object>) listOfWords;
                listOfWords = helper;
                getRoot().getChildren().clear();
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    getRoot().getChildren().add((Text) listOfWords.get(i));
                }
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());    
                getRoot().getChildren().add(cursor.cursorBoundingBox());               
                render(listOfWords, Editor.getWindowWidth(),Editor.getWindowHeight());
                // Replace the redo value 
                undoSaver.putIn(temp);

            } catch (ArrayIndexOutOfBoundsException outOfBounds) {
                return;
            } catch (ClassCastException classCastException) {
                return;
            }
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
            getRoot().getChildren().remove(scrollBar);
            setScrollBar();
        }
        // Redo
        public void redo() {
            // System.out.println("in the condition for redo");
            try {
                LinkedListDeque<Object> helper = (LinkedListDeque<Object>) undoSaver.popOut();
                // Redo should happen only if there is an undo
                listOfWords = helper;
                getRoot().getChildren().clear();
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    getRoot().getChildren().add((Text) listOfWords.get(i));
                }
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());    
                getRoot().getChildren().add(cursor.cursorBoundingBox());               
                render(listOfWords, Editor.getWindowWidth(),Editor.getWindowHeight());

            } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsExceptionUndo) {
                return;
            }
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
            getRoot().getChildren().remove(scrollBar);
            setScrollBar();
        }
        // cursor move up
        public void cursorMoveUp() {
            // System.out.println("Up");
            // Get the x and y coordinates of the cursor
            int currentX = listOfWords.getCursorX();
            int currentY = listOfWords.getCursorY();
            // System.out.println(hashFunctionUP(currentX, currentY));
            listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionUP(currentX, currentY));
            int count = 0;
            while (currentY != 0) {
                String temp = ((Text)listOfWords.get(count)).getText();
                if (temp.equals("\n") || temp.equals("\r") || temp.equals("\r\n")) {
                    break;
                } else if (Math.abs(listOfWords.getCursorX() - currentX) < 3) {
                    listOfWords.cursorMoveRight();
                    break;
                }
                listOfWords.cursorMoveRight();
                count += 1;
            }
            render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
            cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
            getRoot().getChildren().remove(scrollBar);
            setScrollBar();
        }

        public void cursorMoveDown() {
            // System.out.println("Down");
            try {
                // loop through the list with cursorMoveRight()
                int currentX = listOfWords.getCursorX();
                int currentY = listOfWords.getCursorY();
                // System.out.println(hashFunctionDown(currentX, currentY));
                listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionDown(currentX, currentY));
                int count = 0;
                if ((currentY / getFontHeight()) != getNumberOfRows() - 1) {
                    while (true) {
                        String temp = ((Text) listOfWords.get(count)).getText();
                        if (temp.equals("\n") || temp.equals("\r") || temp.equals("\r\n")) {
                            break;
                        } else if (Math.abs(listOfWords.getCursorX() - currentX) < 3) {
                            listOfWords.cursorMoveRight();
                            break;
                        }
                        listOfWords.cursorMoveRight();
                        count += 1;
                    }
                } else { // when already in the last line, move to the end
                    String temp = ((Text) listOfWords.get(count)).getText();
                    while (!(temp.equals("\n") || temp.equals("\r") || temp.equals("\r\n"))) {
                        listOfWords.cursorMoveRight();
                        count += 1;
                        temp = ((Text) listOfWords.get(count)).getText();
                    }
                }
                render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                getRoot().getChildren().remove(scrollBar);
                setScrollBar();
            } catch (NullPointerException nullPointerException) {
                return;
            }
        }

        @Override
        public void handle(KeyEvent keyEvent) {
            if (keyEvent.isShortcutDown()) {
                KeyCode code = keyEvent.getCode();
                // System.out.println("Into the isShortcutDown condition");
                if (code == KeyCode.PLUS || code == KeyCode.EQUALS) {
                    fontSizeUp();
                } else if (code == KeyCode.MINUS) {
                    if (fontSize > 4) {
                        fontSizeDown();
                    }
                    // displayText.setFont(Font.font(fontName, fontSize));
                } else if (code == KeyCode.S) {
                    // The short cut for save the file
                    save();
                } else if (code == KeyCode.Z) {
                    // The short cut for undo
                    undo();
                } else if (code == KeyCode.Y) {
                    // The short cut for repeat
                    redo();
                } else if (code == KeyCode.P) {
                    // The short cut for printing cursor position
                    // System.out.println("This is the cursor position");
                    String cursorPosition = listOfWords.getCursorPosition();
                    System.out.println(cursorPosition);
                } else if (code == KeyCode.C) {
                    // The short cut for copying
                    // System.out.println("Copying");
                    ClipboardContent newContent = new ClipboardContent();
                    // Set the string get from selection
                    newContent.putString("");
                    clipboard.setContent(newContent);
                } else if (code == KeyCode.V) {
                    // The short cut for pasting:
                    // System.out.println("pasting");
                    // Check whether the clipboard has content
                    try {
                        String paste = clipboard.getString();
                        if (paste == null) {
                            return;
                        } else {
                            for (int i = 0; i < paste.length(); i++) {
                                typeCharacter(String.valueOf(paste.charAt(i)));
                            }
                        }
                    } catch (NullPointerException nullPointerExceptionPaste) {
                        return;
                    }
                }
                keyEvent.consume();
            } else if (keyEvent.getEventType() == KeyEvent.KEY_TYPED) {
                // Use the KEY_TYPED event rather than KEY_PRESSED for letter keys, because with
                // the KEY_TYPED event, javafx handles the "Shift" key and associated
                // // capitalization.
                String characterTyped = keyEvent.getCharacter();
                if (characterTyped.length() > 0 && characterTyped.charAt(0) != 8) {
                    typeCharacter(characterTyped);
                    keyEvent.consume();
                }
            } else if (keyEvent.getEventType() == KeyEvent.KEY_PRESSED) {
                // Arrow keys should be processed using the KEY_PRESSED event, because KEY_PRESSED
                // events have a code that we can check (KEY_TYPED events don't have an associated
                // KeyCode).
                KeyCode code = keyEvent.getCode();
                Text input = new Text("");
                if (code == KeyCode.BACK_SPACE) {
                    backSpace();
                }
                if ((code == KeyCode.ENTER) || keyEvent.getCharacter().equals("\n") || keyEvent.getCharacter().equals("\r\n") || keyEvent.getCharacter().equals("\r")) {
                    // System.out.println("Enter Pressed");
                    listOfWords.addText(new Text("\n"));
                    render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
                    getRoot().getChildren().clear();
                    for (int i = 0; i < listOfWords.size(); i++) {
                        Text temp = (Text) listOfWords.get(i);
                        if (temp.getText().equals("\n") || temp.equals(null)) {
                            getRoot().getChildren().add(new Text("\n"));
                        } else {
                            getRoot().getChildren().add((Text) listOfWords.get(i));
                        }
                    }
                    cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                    getRoot().getChildren().add(cursor.cursorBoundingBox());
                    getRoot().getChildren().remove(scrollBar);
                    setScrollBar();
                }
                if (code == KeyCode.LEFT) {
                    // System.out.println("Left");
                    if (currentPos > 0) {
                        currentPos -= 1;
                        listOfWords.cursorMoveLeft();
                        cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());                   
                    }
                    getRoot().getChildren().remove(scrollBar);
                    setScrollBar();
                }
                if (code == KeyCode.RIGHT) {
                    // System.out.println("Right");
                    listOfWords.cursorMoveRight();
                    cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                    getRoot().getChildren().remove(scrollBar);
                    setScrollBar();
                }
                if (code == KeyCode.UP) {
                    cursorMoveUp();
                }
                if (code == KeyCode.DOWN) {
                    cursorMoveDown();
                }
                keyEvent.consume();
            }
        }
    }
    // The hash function that takes in a pair of x, y coordinates
    // And return a node of the linkedlist
    public static Object hashFunctionClick(int x, int y) {
        // Get the row number based on the 
        int rowIndex = y / getFontHeight();
        // System.out.println("rowIndex");
        // Find the first node of that row
        if (rowIndex < linePointer.length) {
            return linePointer[rowIndex];
        } else {
            return linePointer[0];
        }
    }
    // The hash function that helps move up
    public static Object hashFunctionUP(int x, int y) {
        int rowIndex = y / getFontHeight();
        if (rowIndex == 0) {
            return linePointer[rowIndex];
        } else {
            return linePointer[rowIndex - 1];
        }
    } 
    // The hash functin that helps move down
    public static Object hashFunctionDown(int x, int y) {
        int rowIndex = y / getFontHeight();
        if (rowIndex == getNumberOfRows() - 1) {
            return linePointer[rowIndex];
        } else {
            return linePointer[rowIndex + 2];
        }
    }
    // The event handler for mouse
    private class MouseClickEventHandler implements EventHandler<MouseEvent> {
        Text positionText;
        int lastPositionX;
        int lastPositionY;
        int selectionX;
        int selectionY;
        ArrayList<Rectangle> selectionBoxes;
        MouseClickEventHandler(Group root) {
            // this.root = root;
            positionText = new Text("");
            positionText.setTextOrigin(VPos.BOTTOM);
            root.getChildren().add(positionText);
            selectionBoxes = new ArrayList<Rectangle>();
        }
        // Each operation of the mouse would be handled by this method
        @Override
        public void handle(MouseEvent mouseEvent) {
            // Write the main handle solutions here
            int mousePressedX = (int) mouseEvent.getX();
            int mousePressedY = (int) mouseEvent.getY();
            EventType eventType = mouseEvent.getEventType();
            if (eventType == MouseEvent.MOUSE_CLICKED) {
                try {
                    root.getChildren().removeAll(selectionBoxes);
                    selectionBoxes.clear();
                } catch (NullPointerException nullPointerExceptionSelection) {
                    listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionClick(mousePressedX, mousePressedY));;
                }
                listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionClick(mousePressedX, mousePressedY));
                try {
                    while (Math.abs(listOfWords.getCursorX() - mousePressedX) > 3) {
                        listOfWords.cursorMoveRight();
                        cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                    }
                } catch (NullPointerException nullPointerException) {
                    return;
                }
                render(listOfWords, Editor.getWindowWidth(), Editor.getWindowHeight());
                setScrollBar();
            } else if (eventType == MouseEvent.MOUSE_PRESSED) {
                listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionClick(mousePressedX, mousePressedY));
                try {
                    while (Math.abs(listOfWords.getCursorX() - mousePressedX) > 3) {
                        listOfWords.cursorMoveRight();
                        cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                    }
                } catch (NullPointerException nullPointerException) {
                    return;
                }
                selectionX = (int) listOfWords.getCursorX();
                selectionY = (int) listOfWords.getCursorY();
            } else if (eventType == MouseEvent.MOUSE_DRAGGED) {
                // Draw a box for the path between the last mouse position
                // and the current position
                // Rectangle(double x, double y, double width, double height)
                // The X, Y coordinate here should come from the listOfWords that the mouse dragged through

                Rectangle selection = new Rectangle(selectionX, selectionY, listOfWords.getTextWidth(), getFontHeight());
                root.getChildren().add(selection);
                selection.setFill(Color.rgb(173, 216, 230, 0.3)); // Light Blue
                selectionBoxes.add(selection);
                // And then update
                // listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionClick(mousePressedX, mousePressedY));
                try {
                    while ((listOfWords.getCursorX() < mousePressedX) && (listOfWords.getCursorY() <= mousePressedY)) {
                        listOfWords.cursorMoveRight();
                        cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                    }
                } catch (NullPointerException nullPointerException) {
                    return;
                }
                selectionX = (int) listOfWords.getCursorX();
                selectionY = (int) listOfWords.getCursorY();
            } else if (eventType == MouseEvent.MOUSE_RELEASED) {
                // Remove the possibly useless stuff
                // root.getChildren().removeAll(selectionBoxes);
                listOfWords.setCursor((LinkedListDeque<Object>.Node) hashFunctionClick(mousePressedX, mousePressedY));
                // Run a for loop to make all the rectangles
                root.getChildren().removeAll(selectionBoxes);
                for (int i = 0; i < selectionBoxes.size(); i++) {
                    Rectangle temp = (Rectangle) selectionBoxes.get(i);
                    root.getChildren().add(temp);
                }
            }
        }
            
    }

    // This function is called once, when Editor.java is called and everything gets started
    // Including input the text, get a cursor, set up scroll bar
    // And stays alive ever since
    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        root = new Group();
        // The Scene represents the window: its height and width will be the height and width
        // of the window displayed.
        int windowWidth = WINDOW_WIDTH;
        int windowHeight = WINDOW_HEIGHT;
        Scene scene = new Scene(root, windowWidth, windowHeight, Color.WHITE);

        EventHandler<KeyEvent> keyEventHandler = new KeyEventHandler(root, windowWidth, windowHeight);
        EventHandler<MouseEvent> mouseEventHandler = new MouseClickEventHandler(root);
        scene.setOnKeyTyped(keyEventHandler);
        scene.setOnKeyPressed(keyEventHandler);
        scene.setOnMouseClicked(new MouseClickEventHandler(root));
        scene.setOnMousePressed(mouseEventHandler);
        scene.setOnMouseDragged(mouseEventHandler);
        scene.setOnMouseReleased(mouseEventHandler);

        setScrollBar();
        // Input the existing file.
        // Or create a new file.
        // To see whether there is file
        if (getParameters().getRaw().isEmpty() == false) {
            Print inputFileHelper = new Print();
            List<String> parameter = getParameters().getRaw();
            inputFileHelper.print(parameter);
            LinkedListDeque<Object> inputFile = inputFileHelper.wordsRead();
            // Take care of the wrapping here
            render((LinkedListDeque<Object>) inputFile, Editor.getWindowWidth(), Editor.getWindowHeight());

            // Put them in the root
            int inputWordCount = inputFile.size();
            for (int i = 0; i < inputWordCount - 1; i++) {
                // System.out.println("here is the importing process");
                Text temp = (Text) inputFile.get(i);
                temp.setTextOrigin(VPos.TOP);
                temp.setFont(Font.font(DEFAULT_FONT_NAME, DEFAULT_FONT_SIZE));
                listOfWords.addText(temp);
                root.getChildren().add(temp);
                currentPos += 1;
            }
            
        }
        // Initialize the cursor here:
        root.getChildren().add(cursor.cursorBoundingBox());
        cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
        cursor.makeCursorColorChange();

        // This is for the clipboard:
        // Get the system clipboard 
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String stringContents = clipboard.getString();

        // This is for the window resizing:
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                ObservableValue<? extends Number> observableValue,
                Number oldScreenWidth,
                Number newScreenWidth) {
                currentWindowWidth = newScreenWidth.intValue();
                // Re-render the list
                root.getChildren().clear();
                render(listOfWords, (int) newScreenWidth.intValue(), (int) Editor.getWindowHeight());
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    getRoot().getChildren().add((Text) listOfWords.get(i));
                }
                // Set the new Scroll Bar width
                setScrollBar();
                scrollBar.setLayoutX(newScreenWidth.intValue() - scrollBar.getLayoutBounds().getWidth());
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                root.getChildren().add(cursor.cursorBoundingBox());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(
                ObservableValue<? extends Number> observableValue,
                Number oldScreenHeight,
                Number newScreenHeight) {
                currentWindowHeight = newScreenHeight.intValue();
                // Re-render the list
                root.getChildren().clear();
                render(listOfWords, Editor.getWindowWidth(), (int) newScreenHeight.intValue());
                for (int i = 0; i < listOfWords.size() - 1; i++) {
                    getRoot().getChildren().add((Text) listOfWords.get(i));
                }
                // Also need to set the scroll bar
                setScrollBar();
                scrollBar.setPrefHeight(newScreenHeight.intValue());
                cursor.updateBoundingBox(listOfWords.getCursorX(), listOfWords.getCursorY(), listOfWords.getCursorHeight());
                root.getChildren().add(cursor.cursorBoundingBox());
            }
        });
        render(listOfWords, getWindowWidth(), getWindowHeight());
        // This is boilerplate, necessary to setup the window where things are displayed.
        primaryStage.setTitle("The Editor");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

