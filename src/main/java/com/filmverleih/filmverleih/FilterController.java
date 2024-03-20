package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class FilterController {

    @FXML
    VBox vbx_FilterBackground;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    public VBox getOuterPane(){
        return vbx_FilterBackground;
    }
}