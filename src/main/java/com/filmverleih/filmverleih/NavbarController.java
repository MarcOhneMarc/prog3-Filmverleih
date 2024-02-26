package com.filmverleih.filmverleih;

import javafx.fxml.FXML;

import java.io.IOException;

/**
 * This class represents the controller for the navigation bar in the application.
 * It handles user interactions related to navigation between different views.
 */
public class NavbarController {
    //Instantiate Controller-Connector for Navbar-Library-Connection
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }
    /**
     * Handles the user's request to switch to the library view.
     * It loads the Library view into the center of the application frame and the Filter view into the right sidebar.
     *
     * @throws IOException if an error occurs during loading of FXML files
     */
    @FXML
    public void changeToLibrary() throws IOException {
        LibraryController libraryController = connector.getLibraryController();
        FilterController filterController  = connector.getFilterController();
        MainApplication.borderPane.setCenter(libraryController.getOuterPane());
        MainApplication.borderPane.setRight(filterController.getOuterPane());
    }

    /**
     * Handles the user's request to switch to the rental view.
     * It loads the Rental view into the center of the application frame and the Filter view into the right sidebar.
     *
     * @throws IOException if an error occurs during loading of FXML files
     */
    @FXML
    public void changeToRental() throws IOException {
        RentalController rentalController = connector.getRentalController();
        MainApplication.borderPane.setCenter(rentalController.getOuterPane());
    }
}