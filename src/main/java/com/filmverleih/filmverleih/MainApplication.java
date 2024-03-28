package com.filmverleih.filmverleih;

import jakarta.persistence.criteria.CriteriaBuilder;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

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
    private Parent editMovieRoot;
    private EditMovieController editMovieController;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;
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
                          editMovieController);
        navbarController.setConnector(connector);
        libraryController.setConnector(connector);
        movieController.setConnector(connector);
        rentalController.setConnector(connector);
        settingsController.setConnector(connector);
        filterController.setConnector(connector);
        cartController.setConnector(connector);
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
        // Set up the border pane and scene
        borderPane = new BorderPane();
        Scene scene = new Scene(borderPane);
        String css = this.getClass().getResource("stylesheet.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Set stage properties
        stage.setTitle("Quantum-Vortex");
        Image icon = new Image(Objects.requireNonNull(getClass().getResourceAsStream("logo.png")));
        stage.getIcons().add(icon);
        stage.setScene(scene);
        //stage.setMaximized(true);

        // Create a loading indicator
        VBox loadingScreenVBox = new VBox();
        ProgressBar loadingIndicator = new ProgressBar();
        loadingIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        loadingIndicator.setId("progress_Bar");
        loadingIndicator.setPrefWidth(400);
        loadingIndicator.setPrefHeight(12);
        loadingScreenVBox.setPrefSize(1920, 1080);
        Image logoImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("icons/quantumVortexLogo.png")));
        ImageView logo = new ImageView(logoImage);
        logo.setPreserveRatio(true);
        logo.setFitWidth(400);
        loadingScreenVBox.setSpacing(70);
        loadingScreenVBox.getChildren().add(logo);
        loadingScreenVBox.getChildren().add(loadingIndicator);
        loadingScreenVBox.setAlignment(Pos.CENTER);
        loadingScreenVBox.setPadding(new Insets(-150, 0, 0, 0)); // Hier wird die Padding-Eigenschaft geändert

        loadingScreenVBox.setStyle("-fx-background-color: #191919");
        borderPane.setCenter(loadingScreenVBox);

        // Show the stage
        stage.show();

        // Load resources and initialize scene in a background thread
        Task<Void> initializationTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Load roots and controllers
                loadRootsAndControllers();
                connectControllers();

                // Update UI in JavaFX Application Thread
                Platform.runLater(() -> {
                    // Once everything is loaded, replace the loading indicator with the actual content
                    borderPane.setCenter(null); // Remove the loading indicator
                    borderPane.setTop(navbarRoot);
                    borderPane.setRight(filterRoot);
                    borderPane.setCenter(libraryRoot);
                });
                return null;
            }
        };

        // Start the initialization task
        Thread thread = new Thread(initializationTask);
        thread.setDaemon(true);
        thread.start();
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