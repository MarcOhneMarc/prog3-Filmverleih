package com.filmverleih.filmverleih;

import javafx.fxml.FXML;

import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

/**
 * This class represents the controller for the navigation bar in the application.
 * It handles user interactions related to navigation between different views.
 */
public class NavbarController {
    @FXML
    public TextField searchbar;

    private FilterController filterController;

    //Instantiate Controller-Connector for Navbar-Library-Connection
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
        this.filterController = connector.getFilterController();
    }

    @FXML
    private BorderPane bpn_navbarOuterBorderPane;

  
      /**
     * Initializes the controller after its root element has been completely processed.
     * Adds a listener to the search bar text field and updates the filter controller accordingly.
     */
    @FXML
    public void initialize() {
        // Add listener to text field and combo box for filtering
        searchbar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterController.searchBar = newValue;
            filterController.generateFilters();
        });
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
        showSearchbar();
        filterController.isLibrary = true;
        filterController.isRental = false;
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
        FilterController filterController  = connector.getFilterController();
        MainApplication.borderPane.setRight(filterController.getOuterPane());
        showSearchbar();
        filterController.isRental = true;
        filterController.isLibrary = false;

        //MainApplication.borderPane.setRight(null);
    }

    /**
     * Switches the main view of the application to the settings view.
     *
     * @throws IOException If an I/O error occurs while switching the view.
     */
    @FXML
    public void changeToSettings() throws IOException {
        SettingsController settingsController = connector.getSettingsController();
        MainApplication.borderPane.setCenter(settingsController.getOuterPane());

        MainApplication.borderPane.setRight(null);
        hideSearchbar();
    }

    /**
     * Switches the main view of the application to the cart view.
     *
     * @throws IOException If an I/O error occurs while switching the view.
     */
    @FXML
    public void changeToCart() throws IOException {
        CartController cartController = connector.getCartController();
        MainApplication.borderPane.setCenter(cartController.getOuterPane());

        MainApplication.borderPane.setRight(null);
        hideSearchbar();
    }

    private void showSearchbar() {
        searchbar.setVisible(true);
    }

    private void hideSearchbar() {
        searchbar.setVisible(false);
    }

    /**
     * Disables the navigation bar by setting its outer border pane to be disabled.
     * This prevents user interaction with the navigation bar components.
     */
    public void disableNavBar() {
        bpn_navbarOuterBorderPane.setDisable(true);
    }

    /**
     * Enables the navigation bar by setting its outer border pane to be enabled.
     * This allows user interaction with the navigation bar components.
     */
    public void enableNavBar() {
        bpn_navbarOuterBorderPane.setDisable(false);
    }

    public BorderPane getOuterPane() {
        return bpn_navbarOuterBorderPane;
    }
}