package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
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
    private TextField txf_year;
    @FXML
    public TextField txf_genre;
    @FXML
    public TextField txf_minLength;
    @FXML
    public TextField txf_maxLength;
    @FXML
    public Label lbl_rating;
    @FXML
    public Slider sld_rating;
    @FXML
    public CheckBox cbx_ratingEmpty;
    @FXML
    public TextField txf_type;
    @FXML
    public TextField txf_comment;
    @FXML
    public TextField txf_director;
    @FXML
    public TextField txf_studio;
    @FXML
    public TextField txf_actor;
    @FXML
    public TextField txf_fsk;


    public String searchBar;

    private Predicate<Movies> predicate = movie -> true;

    @FXML
    public void initialize() {
        sld_rating.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            double roundedValue = Math.round(value * 10.0) / 10.0;
            lbl_rating.setText(String.valueOf(roundedValue));
        });
    }

    public void generateFilters() {
        String yearFilter = txf_year.getText();
        String genreFilter = txf_genre.getText();
        String minLengthFilter = txf_minLength.getText();
        String maxLengthFilter = txf_maxLength.getText();
        double ratingFilter = sld_rating.getValue();
        String typeFilter = txf_type.getText();
        String commentFilter = txf_comment.getText();
        String directorFilter = txf_director.getText();
        String studioFilter = txf_studio.getText();
        String actorFilter = txf_actor.getText();
        String fskFilter = txf_fsk.getText();

        predicate = movie -> true;

        if (!searchBar.isEmpty()) {
            predicate = predicate.and(movie -> movie.getName().toLowerCase().contains(searchBar.toLowerCase()));
        }
        if (!yearFilter.isEmpty()) {
            int intYear = Integer.parseInt(yearFilter);
            predicate = predicate.and(movie -> movie.getYear() == intYear);
        }
        if (!genreFilter.isEmpty()) {
            predicate = predicate.and(movie -> movie.getGenre().toLowerCase().contains(genreFilter.toLowerCase()));
        }
        if (!minLengthFilter.isEmpty() || !maxLengthFilter.isEmpty()) {
            predicate = predicate.and(movie -> {
                int length = movie.getLength();
                boolean minLengthCheck = minLengthFilter.isEmpty() || length >= Integer.parseInt(minLengthFilter);
                boolean maxLengthCheck = maxLengthFilter.isEmpty() || length <= Integer.parseInt(maxLengthFilter);
                return minLengthCheck && maxLengthCheck;
            });
        }
        if (cbx_ratingEmpty.isSelected()) {
            BigDecimal roundedValue = BigDecimal.valueOf(Math.round(ratingFilter * 10.0) / 10.0);
            predicate = predicate.and(movie -> movie.getRating().equals(ratingFilter));
        }p

        applyFilter();
    }

    public void applyFilter() {
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