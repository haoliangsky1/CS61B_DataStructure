// Package
package editor;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class CursorDisplay {
    // This is the class the implements the cursor of the text editor
    // I will follow the example of SingleLetterDisplay, make a Box for the cursor
    // and make it blink at the position next to the text.

    // The major job of this class is to draw a cursor at the given position,
    // cursorX and cursorY

    // Create a box to bound the cursor
    private static Rectangle cursorBoundingBox;
    // We first try to display a cursor at the center of a box:
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT = 500;
    // The blink period: black for 0.5s, and white for 0.5s and alternate.
    private static final double BLINK = 0.5;
    // Verticle line with width 1 pixel and heigh equal to the height of each letter
    private static final int CURSOR_WIDTH = 1;
    private static int CURSOR_HEIGHT = 12; // the height of each letter;
    // The position of the cursor, as a pair of {X, Y} coordinate, top left.
    // private static int[] position = new int[2];
    // Create a rectangle next to the text
    public CursorDisplay() {
        // System.out.println("Construct a cursor");
        cursorBoundingBox = new Rectangle(0, 0);

    }
    public static Rectangle cursorBoundingBox() {
        return cursorBoundingBox;
    }
    public void updateBoundingBox(int x, int y, int height) {
        // We want the cursor to be stay at the "cursor" node
        // System.out.println("updateBoundingBox");
        double cursorWidth = CURSOR_WIDTH;
        cursorBoundingBox.setWidth(cursorWidth);
        cursorBoundingBox.setHeight(height);
        cursorBoundingBox.setX(x);
        cursorBoundingBox.setY(y);
    }
    // An EventHandler to handle changing the color of the rectangle:
    private class RectangleBlinkEventHandler implements EventHandler<ActionEvent>{
        private  int currentColorIndex = 0;
        private Color[] boxColors = {Color.WHITE, Color.BLACK};
        RectangleBlinkEventHandler() {
            // Set the color to be the first color in the list
            changeColor();
            // System.out.println("The blink handler");
        }
        private void changeColor() {
            cursorBoundingBox.setFill(boxColors[currentColorIndex]);
            currentColorIndex = (currentColorIndex + 1) % boxColors.length;
        }
        public void handle(ActionEvent event) {
            // System.out.println("Change color handler");
            changeColor();
        }
    }
    // Make the cursor bounding box change color periodically
    public void makeCursorColorChange() {
        // System.out.println("Make color change");
        // Create a Timeline that will call the handle() function
        final Timeline timeline = new Timeline();
        // The box should continue blinking forever
        timeline.setCycleCount(Timeline.INDEFINITE);
        RectangleBlinkEventHandler cursorChange = new RectangleBlinkEventHandler();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(BLINK), cursorChange);
        timeline.getKeyFrames().addAll(keyFrame);
        timeline.play();
        // System.out.println("timeline");
    }

}



