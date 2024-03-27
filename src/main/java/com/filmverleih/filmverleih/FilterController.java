package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Controller class for managing filters in the application.
 */
public class FilterController {

    AnchorPane anchorPane;
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */

    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
        this.libraryController = connector.getLibraryController();
        this.navbarController = connector.getNavbarController();
        this.rentalController = connector.getRentalController();
    }

    private LibraryController libraryController;
    private NavbarController navbarController;
    private RentalController rentalController;

    @FXML
    private ComboBox<String> cbx_sort;
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
    public ComboBox<String> cbx_type;
    @FXML
    public TextField txf_comment;
    @FXML
    public TextField txf_director;
    @FXML
    public TextField txf_studio;
    @FXML
    public TextField txf_actor;
    @FXML
    public ComboBox<String> cbx_fsk;

    public String searchBar;

    private boolean isLibrary;
    private Map<String, String> libraryFilterConfig;
    private boolean isRental;
    private Map<String, String> rentalFilterConfig;

    /**
     * Initializes the FilterController.
     */
    @FXML
    private void initialize() {
        cbx_fsk.setValue("keine Auswahl");
        cbx_type.setValue("keine Auswahl");
        libraryFilterConfig = getConfigMap();
        changeToLibrary();


        sld_rating.valueProperty().addListener((observable, oldValue, newValue) -> {
            double value = newValue.doubleValue();
            double roundedValue = Math.round(value * 10.0) / 10.0;
            lbl_rating.setText(String.valueOf(roundedValue));
        });

        cbx_sort.setOnAction(event -> {
            String selectedOption = cbx_sort.getSelectionModel().getSelectedItem();
            if (selectedOption != null) {
                sortListBy(selectedOption);
            }
        });
    }

    /**
     * Generates a comparator to sort with in the library or rental Page
     */
    private void sortListBy(String selectedOption) {
        Comparator<Movies> comparator = null;

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
            if (isLibrary) {
                libraryController.comparator = comparator;
                libraryController.sortMovies();
            } else if (isRental) {
                rentalController.comparator = comparator;
                rentalController.sortMovies();
            } else {
                return;
            }
        }
    }

    /**
     * Generates a predicate to filter with in the library or rental Page
     */
    public void generateFilters() {
        String yearFilter = txf_year.getText();
        String genreFilter = txf_genre.getText();
        String minLengthFilter = txf_minLength.getText();
        String maxLengthFilter = txf_maxLength.getText();
        double ratingFilter = sld_rating.getValue();
        String typeFilter;
        if (cbx_type.getValue().equals("Blu-Ray")) {
            typeFilter = "BR";
        } else {
            typeFilter = cbx_type.getValue();
        }
        String commentFilter = txf_comment.getText();
        String directorFilter = txf_director.getText();
        String studioFilter = txf_studio.getText();
        String actorFilter = txf_actor.getText();
        String fskFilter = cbx_fsk.getValue();

        Predicate<Movies> predicate = movie -> true;

        if (navbarController.searchbar != null && !navbarController.searchbar.getText().isEmpty()) {
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
            Double roundedValue = Double.valueOf(Math.round(ratingFilter * 10.0) / 10.0);
            predicate = predicate.and(movie ->
                    Double.valueOf(movie.getRating().toString()) > roundedValue);
        }

        if (!typeFilter.isEmpty()) {
            if (!typeFilter.equals("keine Auswahl")) {
                if (typeFilter.equals("Blu-Ray")) {
                    predicate = predicate.and(movie -> movie.getType().contains("BR"));
                }
                else {
                    predicate = predicate.and(movie -> movie.getType().toLowerCase().contains(typeFilter.toLowerCase()));
                }
            }
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
            System.out.println(studioFilter);
            predicate = predicate.and(movie ->
                    movie.getStudio().toLowerCase().contains(studioFilter.toLowerCase()));
        }
        if (!actorFilter.isEmpty()) {
            System.out.println(actorFilter);
            predicate = predicate.and(movie ->
                    movie.getActors().toLowerCase().contains(actorFilter.toLowerCase()));
        }
        if (!fskFilter.isEmpty()) {
            if (!fskFilter.equals("keine Auswahl")) {
                int fsk = Integer.parseInt(fskFilter);
                predicate = predicate.and(movie ->
                        movie.getFsk() == fsk);
            }
        }

        if (!(predicate == null)) {
            if (isLibrary) {
                libraryController.predicate = predicate;
                libraryController.filterMovies();
            } else if (isRental) {
                rentalController.predicate = predicate;
                rentalController.filterMovies();
            } else {
                return;
            }
        }
    }

    /**
     * Undos and Clears all Filter-Fields
     */
    public void resetFilters() {
        this.navbarController.searchbar.setText("");
        txf_year.setText("");
        txf_genre.setText("");
        txf_minLength.setText("");
        txf_maxLength.setText("");
        sld_rating.setValue(0);
        cbx_type.setValue("keine Auswahl");
        txf_comment.setText("");
        txf_director.setText("");
        txf_studio.setText("");
        txf_actor.setText("");
        cbx_fsk.setValue("keine Auswahl");

        if (isLibrary) {
            libraryController.predicate = movie -> true;
            libraryController.filterMovies();
        } else if (isRental) {
            rentalController.predicate = movie -> true;
            rentalController.filterMovies();
        } else {
            return;
        }
    }

    public Map<String, String> getConfigMap() {
        Map<String, String> config = new HashMap<>();
        config.put("year", txf_year.getText());
        config.put("genre", txf_genre.getText());
        config.put("minLength", txf_minLength.getText());
        config.put("maxLength", txf_maxLength.getText());
        config.put("rating", String.valueOf(sld_rating.getValue()));
        config.put("type", cbx_type.getValue());
        config.put("comment", txf_comment.getText());
        config.put("director", txf_director.getText());
        config.put("studio", txf_studio.getText());
        config.put("actor", txf_actor.getText());
        config.put("fsk", cbx_fsk.getValue());
        return config;
    }

    public void applyFilterConfig(Map<String, String> filterConfig) {
        txf_year.setText(filterConfig.get("year"));
        txf_genre.setText(filterConfig.get("genre"));
        txf_minLength.setText(filterConfig.get("minLength"));
        txf_maxLength.setText(filterConfig.get("maxLength"));
        sld_rating.setValue(Double.parseDouble(filterConfig.get("rating")));
        cbx_type.setValue(filterConfig.get("type"));
        txf_comment.setText(filterConfig.get("comment"));
        txf_director.setText(filterConfig.get("director"));
        txf_studio.setText(filterConfig.get("studio"));
        txf_actor.setText(filterConfig.get("actor"));
        cbx_fsk.setValue(filterConfig.get("fsk"));
    }

    public void changeToRental() {
        if (isRental) {
            return;
        }
        libraryFilterConfig = getConfigMap();
        applyFilterConfig(rentalFilterConfig);
        cbx_sort.getItems().clear();
        cbx_sort.getItems().addAll(
                "Name aufsteigend",
                "Name absteigend",
                "Jahr aufsteigend",
                "Jahr absteigend",
                "Bewertung aufsteigend",
                "Bewertung absteigend",
                "Länge aufsteigend",
                "Länge absteigend",
                "FSK aufsteigend",
                "FSK absteigend",
                "RENTAL"
        );

        isRental = true;
        isLibrary = false;
    }

    public void changeToLibrary() {
        if (isLibrary) {
            return;
        }
        rentalFilterConfig = getConfigMap();
        applyFilterConfig(libraryFilterConfig);
        cbx_sort.getItems().clear();
        cbx_sort.getItems().addAll(
                "Name aufsteigend",
                "Name absteigend",
                "Jahr aufsteigend",
                "Jahr absteigend",
                "Bewertung aufsteigend",
                "Bewertung absteigend",
                "Länge aufsteigend",
                "Länge absteigend",
                "FSK aufsteigend",
                "FSK absteigend"
        );

        isRental = false;
        isLibrary = true;
    }

    /**
     * Retrieves the outer pane of the filter interface.
     * @return the outer pane (VBox)
     */
    public VBox getOuterPane(){
        return vbx_FilterBackground;
    }
}