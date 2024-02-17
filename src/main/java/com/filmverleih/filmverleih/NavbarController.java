package com.filmverleih.filmverleih;

import javafx.fxml.FXML;

import java.io.IOException;

public class NavbarController {

    @FXML
    public void changeToBibliothek() throws IOException {
        MainApplication.loadCenter("Library.fxml");
        MainApplication.loadRightSidebar("Filter.fxml");
    }

    @FXML
    public void changeToVerleih() throws IOException {
        MainApplication.loadCenter("Rental.fxml");
        MainApplication.loadRightSidebar("Filter.fxml");
    }

    /*
    @FXML
    public void changeToSettings() throws IOException {
        MainApplication.loadCenter("Settings.fxml");
        MainApplication.loadRightSidebar("");
    }*/
}