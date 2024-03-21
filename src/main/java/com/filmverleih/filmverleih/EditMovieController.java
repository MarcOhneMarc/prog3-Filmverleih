package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
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
    private TextArea txa_movieEditComment;

    private Movies movie;

    public void initialize(Movies movie) {
        this.movie = movie;
        insertMovieData();
    }

    private void insertMovieData() {
        txf_movieEditID.setText(valueOf(movie.getMovieid()));
        txf_movieEditName.setText(movie.getName());
        txf_movieEditYear.setText(valueOf(movie.getYear()));
        txf_movieEditLength.setText(valueOf(movie.getLength()));
        txf_movieEditFSK.setText(valueOf(movie.getFsk()));
        txf_movieEditRating.setText(valueOf(movie.getRating()));

        if(!movie.getGenre().isEmpty()){
        String[] genreArray = movie.getGenre().split(", ");
        txf_movieEditGenre1.setText(genreArray[0]);
        if(genreArray.length > 1) {
            txf_movieEditGenre2.setText(genreArray[1]);
            if (genreArray.length > 2) {
                txf_movieEditGenre3.setText(genreArray[2]);
            }
        }}

        if(!movie.getDirectors().isEmpty()) {
        String[] directorsArray = movie.getDirectors().split(", ");
        txf_movieEditDirector1.setText(directorsArray[0]);
        if(directorsArray.length > 1) {
            txf_movieEditDirector2.setText(directorsArray[1]);
            if (directorsArray.length > 2) {
                txf_movieEditDirector3.setText(directorsArray[2]);
            }
        }}

        txf_movieEditCount.setText(valueOf(movie.getCount()));
        txf_movieEditStudio.setText(movie.getStudio());
        txf_movieEditActors.setText(movie.getActors());
        txa_movieEditComment.setText(movie.getComment());
        txa_movieEditComment.setWrapText(true);
    }

    public AnchorPane getOuterPane() {
        return acp_EditMovieBackground;
    }
}
