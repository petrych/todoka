/**
 * This class is responsible for starting the application and setting up the view component.
 */

import org.json.simple.parser.ParseException;

import java.io.IOException;


public class Startup {
    public static void main(String[] args) throws IOException, ParseException {
        // Create an object of a specific view.
        ConsoleView console = new ConsoleView();

        // Call the main method for the specific view.
        console.mainLoop();
    }
}