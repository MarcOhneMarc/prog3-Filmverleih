package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class RentalController {
    @FXML
    Pane pane;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
    }

    public Pane getOuterPane(){
        return pane;
    }
}
