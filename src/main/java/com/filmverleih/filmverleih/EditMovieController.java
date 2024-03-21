package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    ArrayList<ArrayList<Object>> txfStringList = new ArrayList<>();

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
    private String[] genreArray;
    private String[] directorsArray;


    public void initialize(Movies movie) {
        this.movie = movie;
        movieDataGetter();
        splitGenreDirectorsToArray();
        insertMovieData();
        txfListFiller();
        txfListenerInitializer();
    }

    private void splitGenreDirectorsToArray() {
        if(currentMovieGenres != null) {
            this.genreArray = currentMovieGenres.split(", ");
        }
        if(currentMovieDirectors != null) {
            this.directorsArray = currentMovieDirectors.split(", ");
        }
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
            txf_movieEditGenre1.setText(genreArray[0]);
            if(genreArray.length > 1) {
                txf_movieEditGenre2.setText(genreArray[1]);
                if (genreArray.length > 2) {
                    txf_movieEditGenre3.setText(genreArray[2]);
                }
            }
        }

        if(currentMovieDirectors != null) {
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

    private void txfListFiller() {
        addToTxfStringList(txf_movieEditName, currentMovieName);
        addToTxfStringList(txf_movieEditYear, valueOf(currentMovieYear));
        addToTxfStringList(txf_movieEditLength, valueOf(currentMovieDuration));
        addToTxfStringList(txf_movieEditFSK, valueOf(currentMovieFSK));
        addToTxfStringList(txf_movieEditRating, valueOf(currentMovieRating));
        if(genreArray != null) {
            addToTxfStringList(txf_movieEditGenre1, genreArray[0]);
            if(genreArray.length > 1) {
                addToTxfStringList(txf_movieEditGenre2, genreArray[1]);
                    if(genreArray.length > 2) {
                    addToTxfStringList(txf_movieEditGenre3, genreArray[2]);
                }
            }
        }
        if(directorsArray != null) {
            addToTxfStringList(txf_movieEditDirector1, directorsArray[0]);
            if(directorsArray.length > 1) {
                addToTxfStringList(txf_movieEditDirector2, directorsArray[1]);
                if(directorsArray.length > 2) {
                    addToTxfStringList(txf_movieEditDirector3, directorsArray[2]);
                }
            }
        }
        addToTxfStringList(txf_movieEditCount, valueOf(currentMovieCount));
        addToTxfStringList(txf_movieEditStudio, currentMovieStudio);
        addToTxfStringList(txf_movieEditActors, currentMovieActors);
        addToTxfStringList(txf_movieEditLinkToCover, currentLinkToCover);
    }

    private void addToTxfStringList(TextField textField, String data) {
        txfStringList.clear();
        ArrayList<Object> tempList = new ArrayList<>();
        tempList.add(textField);
        tempList.add(data);
        txfStringList.add(tempList);
    };

    private void txfListenerInitializer() {
        for(ArrayList<Object> tempRow : txfStringList) {
            TextField tempTextField = (TextField) tempRow.getFirst();
                tempTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    currentMovieName = (String) tempRow.get(1);
                    if (!newValue.equals(currentMovieName)) {
                        tempTextField.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
                    } else {
                        tempTextField.setStyle("-fx-text-fill: #FFF; -fx-prompt-text-fill: #FFF");
                    }
                });
        }
    }

    public AnchorPane getOuterPane() {
        return acp_EditMovieBackground;
    }
}
