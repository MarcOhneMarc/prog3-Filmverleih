package com.filmverleih.filmverleih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * SettingsApplication Class to start the program
 * Extends the JavaFX Application class
 * @author Hannes
 */
public class SettingsApplication extends Application {

    /**
     * start method which is called by the launch method out of the main method
     * creating and setting a scene and stage title
     * linking the application icon, css stylesheet, fxml scene
     * @param stage primary application stage
     */
    @Override
    public void start(Stage stage) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("settings.fxml"));
            Scene scene = new Scene(root);

            Image icon = new Image(getClass().getResourceAsStream("logo.png"));
            stage.getIcons().add(icon);

            String css = this.getClass().getResource("stylesheet.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Quantum-Vortex");
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * main method of the application which launches the JavaFX application
     * using the launch() method
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch();
    }
}