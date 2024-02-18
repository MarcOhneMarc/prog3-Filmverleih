package com.filmverleih.filmverleih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**

 * The MainApplication class creates the main frame where the navbar and the library are initially loaded in.
 * It also provides methods to change the center and left sidebar content.
 */
public class MainApplication extends Application {

    public static BorderPane borderPane; // the main frame of the application

    /**

     * This method initializes and starts the main application frame.
     * It loads the navigation bar and the library initially, and sets up the main frame of the application.
     *
     * @param stage the primary stage for the application, onto which the application scene can be set
     * @throws IOException if an error occurs during loading of FXML files or setting up the scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        borderPane = new BorderPane(); // the main frame of the application

        // loads the navigation bar FXML file as Parent
        Parent fxmlContent = loadFxml("Navbar.fxml");
        borderPane.setTop(fxmlContent); // sets the navigation to the top of the borderpane

        loadCenter("Library.fxml"); // sets the library to the center of the borderpane
        loadRightSidebar("Filter.fxml");  // sets the filter to the right sidebar of the borderpane

        Scene scene = new Scene(borderPane); // creates a new scene with the borderpane

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**

     *  * Loads an FXML file and returns the root node of the FXML file.
     *  *
     *  * @param fxml the path to the FXML file to be loaded as a String
     *  * @return the root node of the loaded scene graph as a Parent
     *  * @throws IOException if an error occurs during loading of the FXML file
    */
    public static Parent loadFxml(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }

    /**

     * Loads an FXML file and sets its content as the center of the main application frame.
     *
     * @param fxml the path to the FXML file to be loaded
     * @throws IOException if an error occurs during loading of the FXML file
     */
    public static void loadCenter(String fxml) throws IOException {
        Parent fxmlContent = loadFxml(fxml); // loads the specified FXML file as Parent
        borderPane.setCenter(fxmlContent); // sets the loaded content as the center of the main application frame
    }


    /**
     * Loads an FXML file and sets its content as the right sidebar of the main application frame.
     * If the provided FXML path is empty, it removes the right sidebar.
     *
     * @param fxml the path to the FXML file to be loaded, or an empty string to remove the right sidebar
     * @throws IOException if an error occurs during loading of the FXML file
     */
    public static void loadRightSidebar(String fxml) throws IOException {
        if (fxml.equals("")) {
            // If the provided FXML path is empty, removes the right sidebar
            borderPane.setRight(null);
        } else {
            // Otherwise, loads the specified FXML file as Parent
            Parent fxmlContent = loadFxml(fxml);
            // Sets the loaded content as the right sidebar of the main application frame
            borderPane.setRight(fxmlContent);
        }
    }

    /**
     * The main entry point of the application.
     * It launches the JavaFX application by calling the launch method provided by Application class.
     * This method is the starting point for JavaFX applications.
     *
     * @param args command-line arguments passed to the application (not used)
     */
    public static void main(String[] args) {
        launch();
    }
}