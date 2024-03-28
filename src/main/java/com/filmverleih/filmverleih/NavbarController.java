package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * This class represents the controller for the navigation bar in the application.
 * It handles user interactions related to navigation between different views.
 */
public class NavbarController {
    //Instantiate Controller-Connector for Navbar-Library-Connection
    @FXML
    private BorderPane navbarPane;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController, Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,Integer,Integer> connector) {
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

    @FXML
    public void changeToSettings() throws IOException {
        SettingsController settingsController = connector.getSettingsController();
        MainApplication.borderPane.setCenter(settingsController.getOuterPane());
    }

    public void disableNavBar() {
        navbarPane.setDisable(true);
    }

    public void enableNavBar() {
        navbarPane.setDisable(false);
    }

    @FXML
    public void changeToCart() throws IOException {
        CartController cartController = connector.getCartController();
        MainApplication.borderPane.setCenter(cartController.getOuterPane());
    }

    public BorderPane getBorderPane(){
        return navbarPane;
    }
}