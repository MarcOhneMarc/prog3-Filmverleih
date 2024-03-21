package com.filmverleih.filmverleih;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;

public class FilterController {

    AnchorPane anchorPane;
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
    }

    public AnchorPane getOuterPane()
    {
        return anchorPane;
    }
}
