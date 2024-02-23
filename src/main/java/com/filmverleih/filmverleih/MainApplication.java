package com.filmverleih.filmverleih;

import jakarta.persistence.criteria.CriteriaBuilder;
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
    private Parent navbarRoot;
    private NavbarController navbarController;
    private Parent libraryRoot;
    private LibraryController libraryController;
    private Parent movieRoot;
    private MovieController movieController;
    private Parent rentalRoot;
    private RentalController rentalController;
    private Parent settingsRoot;
    private SettingsController settingsController;
    private Parent filterRoot;
    private FilterController filterController;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }
    public static BorderPane borderPane; // the main frame of the application

    private void loadRootsAndControllers() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader = Utility.loadFXML("Navbar.fxml");
            navbarRoot = loader.load();
            navbarController = loader.getController();
            loader  = Utility.loadFXML("Library.fxml");
            libraryRoot = loader.load();
            libraryController = loader.getController();
            loader = Utility.loadFXML("Movie.fxml");
            movieRoot = loader.load();
            movieController = loader.getController();
            loader = Utility.loadFXML("Rental.fxml");
            rentalRoot = loader.load();
            rentalController = loader.getController();
            loader = Utility.loadFXML("settings-view.fxml");
            settingsRoot = loader.load();
            settingsController = loader.getController();
            loader = Utility.loadFXML("Filter.fxml");
            filterRoot = loader.load();
            filterController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void connectControllers(){
        connector =
                new NWayControllerConnector<>
                        ( navbarController,
                          libraryController,
                          movieController,
                          rentalController,
                          settingsController,
                          filterController);
        navbarController.setConnector(connector);
        libraryController.setConnector(connector);
        movieController.setConnector(connector);
        rentalController.setConnector(connector);
        settingsController.setConnector(connector);
        filterController.setConnector(connector);
    }
    public MainApplication() throws IOException {
    }
    //fire-up all the scene related stuff

    /**

     * This method initializes and starts the main application frame.
     * It loads the navigation bar and the library initially, and sets up the main frame of the application.
     *
     * @param stage the primary stage for the application, onto which the application scene can be set
     * @throws IOException if an error occurs during loading of FXML files or setting up the scene
     */
    @Override
    public void start(Stage stage) throws IOException {
        //fire-up scenes
        loadRootsAndControllers();
        connectControllers();
        borderPane = new BorderPane(); // the main frame of the application
        Scene scene = new Scene(borderPane); // creates a new scene with the borderpane
        borderPane.setTop(navbarRoot);
        borderPane.setCenter(libraryRoot);
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
    //TODO:must be reworked, see LibraryController
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
    //TODO:must be reworked, see LibraryController
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
    //TODO:must be reworked, see LibraryController
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