package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class RentalController {
    @FXML
    Pane pane;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    public Pane getOuterPane(){
        return pane;
    }
}
