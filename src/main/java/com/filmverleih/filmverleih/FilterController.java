package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterController {

    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
        this.libraryController = connector.getLibraryController();
    }

    private LibraryController libraryController;

    @FXML
    VBox vbx_FilterBackground;

    @FXML
    private TextField testFilter;

    public void generateFilters() {
        String filterText = testFilter.getText(); // Assuming testFilter is some UI element for input

        Predicate<Movies> predicate = movie -> {
            if (filterText == null || filterText.isEmpty()) {
                return true; // If filter text is empty, show all movies
            }

            return movie.getName().toLowerCase().contains(filterText.toLowerCase());
        };

        // Apply filter and update library view
        applyFilter(predicate);
    }

    public void applyFilter(Predicate<Movies> predicate) {

        if (libraryController == null) {
            // Handle the scenario where libraryController is null
            System.err.println("Library controller is null. Cannot apply filter.");
            return;
        }
        List<Movies> currentMovieList = libraryController.getAllMovies();

        if (currentMovieList.isEmpty()) {
            return;
        }

        List<Movies> updatedMoviesList = currentMovieList.stream()
                .filter(predicate)
                .collect(Collectors.toList());

        libraryController.updateMovies(updatedMoviesList); // Update the library view with filtered movies
    }

    public VBox getOuterPane(){
        return vbx_FilterBackground;
    }
}