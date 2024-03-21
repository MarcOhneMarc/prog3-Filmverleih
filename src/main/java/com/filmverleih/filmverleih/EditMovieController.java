package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.Arrays;

import java.util.List;

import static java.lang.String.valueOf;

public class EditMovieController {
    private NWayControllerConnector<NavbarController,
                                    LibraryController,
                                    MovieController,
                                    RentalController,
                                    SettingsController,
                                    FilterController,
                                    CartController,
                                    EditMovieController,
                                    Integer,
                                    Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,
                                                    LibraryController,
                                                    MovieController,
                                                    RentalController,
                                                    SettingsController,
                                                    FilterController,
                                                    CartController,
                                                    EditMovieController,
                                                    Integer,
                                                    Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private AnchorPane acp_EditMovieBackground;
    @FXML
    private TextField txf_movieEditID;
    @FXML
    private TextField txf_movieEditName;
    @FXML
    private TextField txf_movieEditYear;
    @FXML
    private TextField txf_movieEditLength;
    @FXML
    private TextField txf_movieEditFSK;
    @FXML
    private TextField txf_movieEditRating;
    @FXML
    private TextField txf_movieEditGenre1;
    @FXML
    private TextField txf_movieEditGenre2;
    @FXML
    private TextField txf_movieEditGenre3;
    @FXML
    private TextField txf_movieEditDirector1;
    @FXML
    private TextField txf_movieEditDirector2;
    @FXML
    private TextField txf_movieEditDirector3;
    @FXML
    private TextField txf_movieEditCount;
    @FXML
    private TextField txf_movieEditStudio;
    @FXML
    private TextField txf_movieEditActors;
    @FXML
    private TextField txf_movieEditLinkToCover;
    @FXML
    private TextArea txa_movieEditComment;

    private Movies movie;

    private int currentMovieId;
    private String currentMovieName;
    private int currentMovieYear;
    private int currentMovieDuration;
    private int currentMovieFSK;
    private BigDecimal currentMovieRating;
    private String currentMovieGenres;
    private String currentMovieDirectors;
    private int currentMovieCount;
    private String currentMovieStudio;
    private String currentMovieActors;
    private String currentLinkToCover;
    private String currentMovieComment;
    private String genreArray[];
    private String directorsArray[];


    public void initialize(Movies movie) {
        this.movie = movie;
        movieDataGetter();
        insertMovieData();
        txfListenerInitializer();
    }

    private void movieDataGetter() {
        this.currentMovieId = movie.getMovieid();
        this.currentMovieName = movie.getName();
        this.currentMovieYear = movie.getYear();
        this.currentMovieDuration = movie.getLength();
        this.currentMovieFSK = movie.getFsk();
        this.currentMovieRating = movie.getRating();
        this.currentMovieGenres = movie.getGenre();
        this.currentMovieDirectors = movie.getDirectors();
        this.currentMovieCount = movie.getCount();
        this.currentMovieStudio = movie.getStudio();
        this.currentMovieActors = movie.getActors();
        this.currentLinkToCover = movie.getCover();
        this.currentMovieComment = movie.getComment();
    }

    private void insertMovieData() {
        txf_movieEditID.setText(valueOf(currentMovieId));
        txf_movieEditName.setText(currentMovieName);
        txf_movieEditYear.setText(valueOf(currentMovieYear));
        txf_movieEditLength.setText(valueOf(currentMovieDuration));
        txf_movieEditFSK.setText(valueOf(currentMovieFSK));
        txf_movieEditRating.setText(valueOf(currentMovieRating));

        if(currentMovieGenres != null) {
            this.genreArray = currentMovieGenres.split(", ");
            txf_movieEditGenre1.setText(genreArray[0]);
            if(genreArray.length > 1) {
                txf_movieEditGenre2.setText(genreArray[1]);
                if (genreArray.length > 2) {
                    txf_movieEditGenre3.setText(genreArray[2]);
                }
            }
        }

        if(currentMovieDirectors != null) {
            this.directorsArray = currentMovieDirectors.split(", ");
            txf_movieEditDirector1.setText(directorsArray[0]);
            if(directorsArray.length > 1) {
                txf_movieEditDirector2.setText(directorsArray[1]);
                if (directorsArray.length > 2) {
                    txf_movieEditDirector3.setText(directorsArray[2]);
                }
            }
        }

        txf_movieEditCount.setText(valueOf(currentMovieCount));
        txf_movieEditStudio.setText(currentMovieStudio);
        txf_movieEditActors.setText(currentMovieActors);
        txf_movieEditLinkToCover.setText(currentLinkToCover);
        txa_movieEditComment.setText(currentMovieComment);
        txa_movieEditComment.setWrapText(true);
    }


    private void txfListenerInitializer() {
        txf_movieEditName.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(currentMovieName)) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditYear.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(valueOf(currentMovieYear))) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditLength.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(valueOf(currentMovieDuration))) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditFSK.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(valueOf(currentMovieFSK))) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditRating.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(valueOf(currentMovieRating))) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditGenre1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[0])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditGenre2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[1])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditGenre3.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[2])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditDirector1.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[0])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditDirector2.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[1])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditDirector3.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(genreArray[2])) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditCount.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(valueOf(currentMovieCount))) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditStudio.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(currentMovieStudio)) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditActors.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(currentMovieActors)) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txf_movieEditLinkToCover.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(currentLinkToCover)) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });

        txa_movieEditComment.textProperty().addListener((observable, oldValue, newValue) -> {
            if(!newValue.equals(currentMovieComment)) {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
            else {
                txf_movieEditLinkToCover.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
            }
        });
        //addChangeListener(txf_movieEditYear);

    }

    /*public static void addChangeListener(TextField textField) {
        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

            }
        });
    }*/

    public AnchorPane getOuterPane() {
        return acp_EditMovieBackground;
    }
}
