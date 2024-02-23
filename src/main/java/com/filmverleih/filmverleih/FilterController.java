package com.filmverleih.filmverleih;

public class FilterController {
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }
}
