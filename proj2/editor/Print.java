package editor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
import java.util.LinkedList;
import java.util.List;
public class Print {
    // This method checks which command line argument is set
    // And either print or do nothing accordingly
    private static LinkedListDeque<Object> wordsRead;
    private static int wordCount;
    public Print() {
        wordsRead = new LinkedListDeque<Object> ();
        wordCount = 0;
    }
    public int wordCount() {
        return wordCount;
    }
    public LinkedListDeque<Object> wordsRead() {
        return wordsRead;
    }
    private static String inputFilename;
    private static String optional;
    public static void print(List<String> parameter) {
        if (parameter.isEmpty()) {
            return;
        }
        if (parameter.get(0).isEmpty()) {
            return;
        }
        if (parameter.get(0) != null) {
            inputFilename = parameter.get(0);
            if (parameter.toString().contains(",")) {
            	optional = parameter.get(1);
            } else {
            	optional = "";
            }
        } else {
            return;
        }
        if (inputFilename.length()== 0 ) {
            // No filename is provided, open a new file
            // System.out.println("No filename provided");
            // System.exit(1);
        } else {
            if (optional.equals("debug")) {
                // The second optional command line argument:
                // If optional == blank, no output.
                // If optional == "debug", print any output facilitate debugging:
                System.out.println("Ok lets debug. Something is wrong.");
                return;
            } else if (optional.equals("")) {
                // Check to make sure that the inputfile exists:
                try {
                    File inputFile = new File(inputFilename);
                    // Check to make sure that the input file exists
                    if (!inputFile.exists()) {
                        System.out.println("Unable to open the file with name " + inputFilename
                            + " does not exist.");
                        System.exit(0);
                    }
                    FileReader reader = new FileReader(inputFile);
                    // Use buffer as a helper
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    int intRead = -1;
                    while ((intRead = bufferedReader.read()) != -1) {
                        char charRead = (char) intRead;
                        String temp = String.valueOf(charRead);
                        Text input = new Text(temp);
                        wordsRead.addText(input);
                        wordCount += 1;
                    }
                    // Render.render(wordsRead);
                    System.out.println("Successfully read " +inputFilename + " .");
                    System.out.println("In total there are " + wordCount +" characters.");
                    bufferedReader.close();
                } catch (FileNotFoundException fileNotFoundException) {
                    System.out.println("File not found! Exception was: " + fileNotFoundException);
                } catch(IOException ioException) {
                    System.out.println("Oh well, Exception was: " + ioException);
                }
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("This is main");
    }
}
