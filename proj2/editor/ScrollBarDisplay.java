// Package
package editor;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.ScrollBar;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


// The scroll bars that we will implemente
public class ScrollBarDisplay extends Application {
    private final int WINDOW_WIDTH = 500;
    private final int WINDOW_HEIGHT = 500;
    @Override
    public void start(Stage primaryStage) {
        // Create a Node that will be the parent of all things displayed on the screen.
        Group root = new Group();
        // The scene represents the window: its height and width will be the height and width 
        // of the window displayed
        Scene scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT, Color.WHITE);
        // Make a vertical scroll bar on the right side of the screen
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        // Set the height of the scroll bar so that it fills the whole screen
        scrollBar.setPrefHeight(WINDOW_HEIGHT);
        // Set the range of the scroll bar
        // The minimum should be the starting height of the first line
        // scrollBar.setMin();
        // The maximun should reach whatever line the text have
        // scrollBar.setMax();
        // Add the scroll bar to the scene graph, so that it appears on the screen
        root.getChildren().add(scrollBar);
        double usableScreenWidth = WINDOW_WIDTH - scrollBar.getLayoutBounds().getWidth();
        scrollBar.setLayoutX(usableScreenWidth);
        // Setup the window where things are displayed
        primaryStage.setTitle("Scroll Bar Display");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
