package com.filmverleih.filmverleih;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    public static BorderPane borderPane;
    public static FXMLLoader fxmlLoader;

    @Override
    public void start(Stage stage) throws IOException {

        borderPane = new BorderPane();

        Parent fxmlContent = loadFxml("Navbar.fxml");
        borderPane.setTop(fxmlContent);

        loadCenter("Libary.fxml");
        loadRightSidebar("Filter.fxml");

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    public static Parent loadFxml(String fxml) throws IOException {
        fxmlLoader = new FXMLLoader(MainApplication.class.getResource(fxml));
        return fxmlLoader.load();
    }

    public static void loadCenter(String fxml) throws IOException {
        Parent fxmlContent = loadFxml(fxml);
        borderPane.setCenter(fxmlContent);
    }

    public static void loadRightSidebar(String fxml) throws IOException {
        if (fxml.equals("")) {
            borderPane.setRight(null);
        }
        Parent fxmlContent = loadFxml(fxml);
        borderPane.setRight(fxmlContent);
    }

    public static void main(String[] args) {
        launch();
    }
}