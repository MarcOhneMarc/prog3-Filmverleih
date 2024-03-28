package com.filmverleih.filmverleih;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The MainApplication class creates the main frame where the navbar and the library are initially loaded in.
 * It also provides methods to change the center and left sidebar content.
 */
public class MainApplication extends Application {
    //Attributes
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
    private Parent cartRoot;
    private CartController cartController;
    private LoginController loginController;
    private Parent loginRoot;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController,LoginController,EditMovieController,Integer> connector;
    private Parent editMovieRoot;
    private EditMovieController editMovieController;
    public static BorderPane borderPane; // the main frame of the application
    /**
     * Loads the fxml and pairs it with its respective controller
     *
     */
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

            loader = Utility.loadFXML("Settings.fxml");
            settingsRoot = loader.load();
            settingsController = loader.getController();

            loader = Utility.loadFXML("Filter.fxml");
            filterRoot = loader.load();
            filterController = loader.getController();

            loader = Utility.loadFXML("Cart.fxml");
            cartRoot = loader.load();
            cartController = loader.getController();
          
            loader = Utility.loadFXML("Login.fxml");
            loginRoot= loader.load();
            loginController = loader.getController();

            loader = Utility.loadFXML("EditMovie.fxml");
            editMovieRoot = loader.load();
            editMovieController = loader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * connects existing controllers to the NWayController
     */
    private void connectControllers(){
        connector =
                new NWayControllerConnector<>
                        ( navbarController,
                          libraryController,
                          movieController,
                          rentalController,
                          settingsController,
                          filterController,
                          cartController,
                          loginController);
                          editMovieController);
        navbarController.setConnector(connector);
        libraryController.setConnector(connector);
        movieController.setConnector(connector);
        rentalController.setConnector(connector);
        settingsController.setConnector(connector);
        filterController.setConnector(connector);
        cartController.setConnector(connector);
        loginController.setConnector(connector);
        editMovieController.setConnector(connector);
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
        loginController.getLbl_loginWrongCredentials().setVisible(false);
        loginController.getTxf_loginPassword().setVisible(false);
        borderPane.setCenter(loginRoot);

        String css = this.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        stage.setTitle("Quantum-Vortex");

        Image icon = new Image(getClass().getResourceAsStream("logo.png"));
        stage.getIcons().add(icon);

        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
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