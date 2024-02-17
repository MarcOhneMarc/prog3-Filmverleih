package com.filmverleih.filmverleih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {

        try {

            Parent root = FXMLLoader.load(getClass().getResource("settings-view.fxml"));
            Scene scene = new Scene(root);

            Image icon = new Image(getClass().getResourceAsStream("logo.png"));
            stage.getIcons().add(icon);

            String css = this.getClass().getResource("application.css").toExternalForm();
            scene.getStylesheets().add(css);

            stage.setTitle("Quantum-Vortex");
            stage.setScene(scene);
            stage.setMaximized(true);
            //stage.setResizable(false);
            stage.show();




        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args) {
        launch();
    }
}