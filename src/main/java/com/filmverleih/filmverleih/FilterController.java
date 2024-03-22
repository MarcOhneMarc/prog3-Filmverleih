package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FilterController {

    private NWayControllerConnector<
            NavbarController,
            LibraryController,
            MovieController,
            RentalController,
            SettingsController,
            FilterController,
            CartController,
            Integer,
            Integer,
            Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(
            NWayControllerConnector<NavbarController,
                    LibraryController,
                    MovieController,
                    RentalController,
                    SettingsController,
                    FilterController,
                    CartController,
                    Integer,
                    Integer,
                    Integer> connector) {
        this.connector = connector;
        this.libraryController = connector.getLibraryController();
    }

    private LibraryController libraryController;

    @FXML
    private ComboBox<String> sortComboBox;
    @FXML
    private VBox vbx_FilterBackground;
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
    private Predicate<Movies> predicate;
    private Comparator<Movies> comparator;

    @FXML
    public void initialize() {
        sld_rating.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            double roundedValue = Math.round(value * 10.0) / 10.0;
            lbl_rating.setText(String.valueOf(roundedValue));
        });

        sortComboBox.setOnAction(event -> {
            String selectedOption = sortComboBox.getSelectionModel().getSelectedItem();
            if (selectedOption != null) {
                sortListBy(selectedOption);
            }
        });
    }

    private void sortListBy(String selectedOption) {
        comparator = null;

        switch (selectedOption) {
            case "Name aufsteigend":
                comparator = Comparator.comparing(Movies::getName);
                break;
            case "Name absteigend":
                comparator = Comparator.comparing(Movies::getName).reversed();
                break;
            case "Jahr aufsteigend":
                comparator = Comparator.comparingInt(Movies::getYear);
                break;
            case "Jahr absteigend":
                comparator = Comparator.comparingInt(Movies::getYear).reversed();
                break;
            case "Bewertung aufsteigend":
                comparator = Comparator.comparing(Movies::getRating);
                break;
            case "Bewertung absteigend":
                comparator = Comparator.comparing(Movies::getRating).reversed();
                break;
            case "Länge aufsteigend":
                comparator = Comparator.comparingInt(Movies::getLength);
                break;
            case "Länge absteigend":
                comparator = Comparator.comparingInt(Movies::getLength).reversed();
                break;
            case "FSK aufsteigend":
                comparator = Comparator.comparingInt(Movies::getFsk);
                break;
            case "FSK absteigend":
                comparator = Comparator.comparingInt(Movies::getFsk).reversed();
                break;
        }
        if (comparator != null) {
            libraryController.sortMovies(comparator);
        }
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
            predicate = predicate.and(movie ->
                    movie.getName().toLowerCase().contains(searchBar.toLowerCase()));
        }
        if (!yearFilter.isEmpty()) {
            int intYear = Integer.parseInt(yearFilter);
            predicate = predicate.and(movie ->
                    movie.getYear() == intYear);
        }
        if (!genreFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getGenre().toLowerCase().contains(genreFilter.toLowerCase()));
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
            predicate = predicate.and(movie ->
                    movie.getRating().equals(ratingFilter));
        }
        if (!typeFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getType().toLowerCase().contains(typeFilter.toLowerCase()));
        }
        if (!commentFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getComment().toLowerCase().contains(commentFilter.toLowerCase()));
        }
        if (!directorFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getDirectors().toLowerCase().contains(directorFilter.toLowerCase()));
        }
        if (!studioFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getStudio().toLowerCase().contains(studioFilter.toLowerCase()));
        }
        if (!actorFilter.isEmpty()) {
            predicate = predicate.and(movie ->
                    movie.getActors().toLowerCase().contains(actorFilter.toLowerCase()));
        }
        if (!fskFilter.isEmpty()) {
            int fsk = Integer.parseInt(fskFilter);
            predicate = predicate.and(movie ->
                    movie.getFsk() == fsk);
        }

        libraryController.filterMovies(predicate);
    }

    public void resetFilters() {
        txf_year.setText("");
        txf_genre.setText("");
        txf_minLength.setText("");
        txf_maxLength.setText("");
        sld_rating.setValue(0);
        txf_type.setText("");
        txf_comment.setText("");
        txf_director.setText("");
        txf_studio.setText("");
        txf_actor.setText("");
        txf_fsk.setText("");
        predicate = movie -> true;
        libraryController.filterMovies(predicate);
    }

    public VBox getOuterPane(){
        return vbx_FilterBackground;
    }
}