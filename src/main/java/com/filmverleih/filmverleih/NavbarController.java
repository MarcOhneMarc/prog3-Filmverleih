package com.filmverleih.filmverleih;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * This class represents the controller for the navigation bar in the application.
 * It handles user interactions related to navigation between different views.
 */
public class NavbarController {

    /**
     * Handles the user's request to switch to the library view.
     * It loads the Library view into the center of the application frame and the Filter view into the right sidebar.
     *
     * @throws IOException if an error occurs during loading of FXML files
     */
    @FXML
    public void changeToLibrary() throws IOException {
        MainApplication.loadCenter("Library.fxml");
        MainApplication.loadRightSidebar("Filter.fxml");
    }

    /**
     * Handles the user's request to switch to the rental view.
     * It loads the Rental view into the center of the application frame and the Filter view into the right sidebar.
     *
     * @throws IOException if an error occurs during loading of FXML files
     */
    @FXML
    public void changeToRental() throws IOException {
        MainApplication.loadCenter("Rental.fxml");
        MainApplication.loadRightSidebar("Filter.fxml");
    }
}