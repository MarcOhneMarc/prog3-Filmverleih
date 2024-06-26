package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.String.valueOf;

/**
 * controller class for the edit movie view
 *
 * @author Jannis
 */
public class EditMovieController {

    private NWayControllerConnector<NavbarController,
                                    LibraryController,
                                    MovieController,
                                    RentalController,
                                    SettingsController,
                                    FilterController,
                                    CartController,
                                    LoginController,
                                    EditMovieController,
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
                                                    LoginController,
                                                    EditMovieController,
                                                    Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private StackPane stp_movieEditBackStackPane;
    @FXML
    private AnchorPane acp_EditMovieBackground;
    @FXML
    private GridPane grp_movieEditParamSelect;
    @FXML
    private TextField txf_movieEditID;
    @FXML
    private Label lbl_movieEditNameTitle;
    @FXML
    private TextField txf_movieEditName;
    @FXML
    private Label lbl_movieEditYearTitle;
    @FXML
    private TextField txf_movieEditYear;
    @FXML
    private Label lbl_movieEditLengthTitle;
    @FXML
    private TextField txf_movieEditLength;
    @FXML
    private Label lbl_movieEditFskTitle;
    @FXML
    private TextField txf_movieEditFSK;
    @FXML
    private Label lbl_movieEditRatingTitle;
    @FXML
    private TextField txf_movieEditRating;
    @FXML
    private Label lbl_movieEditGenre1Title;
    @FXML
    private TextField txf_movieEditGenre1;
    @FXML
    private Label lbl_movieEditGenre2Title;
    @FXML
    private TextField txf_movieEditGenre2;
    @FXML
    private Label lbl_movieEditGenre3Title;
    @FXML
    private TextField txf_movieEditGenre3;
    @FXML
    private Label lbl_movieEditDirector1Title;
    @FXML
    private TextField txf_movieEditDirector1;
    @FXML
    private Label lbl_movieEditDirector2Title;
    @FXML
    private TextField txf_movieEditDirector2;
    @FXML
    private Label lbl_movieEditDirector3Title;
    @FXML
    private TextField txf_movieEditDirector3;
    @FXML
    private Label lbl_movieEditCountTitle;
    @FXML
    private TextField txf_movieEditCount;
    @FXML
    private TextField txf_movieEditStudio;
    @FXML
    private Label lbl_movieEditActorsTitle;
    @FXML
    private TextField txf_movieEditActors;
    @FXML
    private Label lbl_movieEditLinkToCoverTitle;
    @FXML
    private TextField txf_movieEditLinkToCover;
    @FXML
    private TextArea txa_movieEditComment;
    @FXML
    private ImageView igv_movieEditNameUndo;
    @FXML
    private ImageView igv_movieEditYearUndo;
    @FXML
    private ImageView igv_movieEditLengthUndo;
    @FXML
    private ImageView igv_movieEditFskUndo;
    @FXML
    private ImageView igv_movieEditRatingUndo;
    @FXML
    private ImageView igv_movieEditGenre1Undo;
    @FXML
    private ImageView igv_movieEditGenre2Undo;
    @FXML
    private ImageView igv_movieEditGenre3Undo;
    @FXML
    private ImageView igv_movieEditDirector1Undo;
    @FXML
    private ImageView igv_movieEditDirector2Undo;
    @FXML
    private ImageView igv_movieEditDirector3Undo;
    @FXML
    private ImageView igv_movieEditCountUndo;
    @FXML
    private ImageView igv_movieEditStudioUndo;
    @FXML
    private ImageView igv_movieEditActorsUndo;
    @FXML
    private ImageView igv_movieEditLinkToCoverUndo;
    @FXML
    private ImageView igv_movieEditCommentUndo;
    @FXML
    private CheckBox cbx_movieEditSelDVD;
    @FXML
    private CheckBox cbx_movieEditSelBluRay;
    @FXML
    private Button btn_confirmMovieEdit;
    @FXML
    private Label lbl_movieEditSaveFeedback;
    @FXML
    private Button btn_cancelMovieEdit;
    @FXML
    private Button btn_deleteMovieEdit;
    @FXML
    private Button btn_movieEditDeleteConfirm;
    @FXML
    private Label lbl_movieEditDeleteFeedback;
    @FXML
    private ImageView igv_movieEditXButton;
    @FXML
    private ImageView igv_movieEditConstraintInfoButton;
    @FXML
    private AnchorPane acp_movieEditConstraintInfo;
    @FXML
    private AnchorPane acp_movieEditDeleteConfirmation;

    private Movies movie;
    private ArrayList<ArrayList<Object>> txfStringUndoList = new ArrayList<>();
    private final String MOVIE_DELETE_SUCCESSFUL = "Der Film wurde erfolgreich entfernt.";
    private final String MOVIE_DELETE_FAILED = "Es ist etwas schiefgelaufen! Versuchen Sie es bitte erneut.";
    private final String MOVIE_SAVE_SUCCESSFUL = "Die Daten wurden erfolgreich gespeichert.";
    private final String MOVIE_SAVE_FAILED = "Manche Textfelder stimmen nicht mit den Anforderungen überein. " +
            "Die Titel dieser wurden rot markiert.";
    private final String MOVIE_SAVE_WENT_WRONG = "Etwas ist schiefgelaufen, bitte versuchen Sie es erneut.";

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
    private String currentMovieType;
    private String[] genreArray = new String[10];
    private String[] directorsArray = new String[10];

    private String changedName;
    private int changedYear;
    private int changedLength;
    private int changedFsk;
    private BigDecimal changedRating;
    private String changedGenres;
    private String changedDirectors;
    private int changedCount;
    private String changedStudio;
    private String changedActors;
    private String changedLinkToCover;
    private String changedComment;
    private String changedType;


    /**
     * initializes the controller with the given movie
     * @param movie the movie
     */
    public void initialize(Movies movie) {
        this.movie = movie;
        txfStringUndoList.clear();
        movieDataGetter();
        splitGenreDirectorsToArray();
        resetStylingAndDisables();
        txfListFiller();
        txfListenerInitializer();
        txaListenerInitializer();
        insertMovieData();
        addOnlyNumbersConstraint();
        undoButtonAddEventHandler();
        checkBoxAddEventHandler();
        checkAdmin();
    }

    /**
     * Splits the current movie's genres and directors into arrays.
     * If the genres or directors are not null, they are split by ", " and stored in corresponding arrays.
     */
    private void splitGenreDirectorsToArray() {
        if(currentMovieGenres != null) {
            this.genreArray = currentMovieGenres.split(", ");
        }
        if(currentMovieDirectors != null) {
            this.directorsArray = currentMovieDirectors.split(", ");
        }
    }

    /**
     * Checks if the logged in user is an admin.
     * If the user is not an admin, the delete button is disabled.
     * If the user is an admin, the delete button is enabled.
     */
    private void checkAdmin(){
        if (!connector.getLoginController().getLoggedUser().getIsadmin()) {
            btn_deleteMovieEdit.setDisable(true);
            btn_deleteMovieEdit.setVisible(false);
        }
        else {
            btn_deleteMovieEdit.setVisible(true);
            btn_deleteMovieEdit.setDisable(false);
        }
    }

    /**
     * Retrieves movie data from the current movie object and assigns it to corresponding variables.
     * This method populates various attributes with data such as movie ID, name, year, duration, FSK rating,
     * rating, genres, directors, count, studio, actors, cover link, comment, and type.
     */
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
        this.currentMovieType = movie.getType();
    }

    /**
     * Inserts movie data into corresponding UI fields for editing.
     * This method sets the text of various text fields and checkboxes with the current movie data.
     * It also handles cases where there might be multiple genres or directors.
     */
    private void insertMovieData() {
        txf_movieEditID.setText(valueOf(currentMovieId));
        txf_movieEditName.setText(currentMovieName);
        txf_movieEditYear.setText(valueOf(currentMovieYear));
        txf_movieEditLength.setText(valueOf(currentMovieDuration));
        txf_movieEditFSK.setText(valueOf(currentMovieFSK));
        txf_movieEditRating.setText(valueOf(currentMovieRating));

        if(currentMovieGenres != null) {
            txf_movieEditGenre1.setText(genreArray[0]);
        }
        if(genreArray.length > 1) {
            txf_movieEditGenre2.setText(genreArray[1]);
        }
        if (genreArray.length > 2) {
            StringBuilder restGenres = new StringBuilder(genreArray[2]);
            for (int i = 3; genreArray.length > i; i++) {
                restGenres.append(", ").append(genreArray[i]);
            }
            txf_movieEditGenre3.setText(String.valueOf(restGenres));
        }

        if(currentMovieDirectors != null) {
            txf_movieEditDirector1.setText(directorsArray[0]);
        }
        if(directorsArray.length > 1) {
            txf_movieEditDirector2.setText(directorsArray[1]);
        }
        if (directorsArray.length > 2) {
            StringBuilder restDirectors = new StringBuilder(directorsArray[2]);
            for (int i = 3; directorsArray.length > i; i++) {
                restDirectors.append(", ").append(directorsArray[i]);
            }
            txf_movieEditDirector3.setText(String.valueOf(restDirectors));
        }

        txf_movieEditCount.setText(valueOf(currentMovieCount));
        txf_movieEditStudio.setText(currentMovieStudio);
        txf_movieEditActors.setText(currentMovieActors);
        txf_movieEditLinkToCover.setText(currentLinkToCover);
        txa_movieEditComment.setText(currentMovieComment);
        txa_movieEditComment.setWrapText(true);
        if(currentMovieType.equals("DVD")) {cbx_movieEditSelDVD.setSelected(true); cbx_movieEditSelBluRay.setSelected(false);}
        if(currentMovieType.equals("BR")) {cbx_movieEditSelBluRay.setSelected(true); cbx_movieEditSelDVD.setSelected(false);}
    }

    /**
     * Adds constraints to ensure that only numbers are entered in certain text fields.
     * This method applies specific constraints to the UI text fields for editing movie details,
     * allowing only numeric input for fields such as year, length, FSK rating, rating, and count.
     */
    private void addOnlyNumbersConstraint() {
        TextFieldFunctions.addYearChecker(txf_movieEditYear);
        TextFieldFunctions.addDurationChecker(txf_movieEditLength);
        TextFieldFunctions.addFskChecker(txf_movieEditFSK);
        TextFieldFunctions.addRatingChecker(txf_movieEditRating);
        TextFieldFunctions.addNoCommaChecker(txf_movieEditGenre1);
        TextFieldFunctions.addNoCommaChecker(txf_movieEditGenre2);
        TextFieldFunctions.addNoCommaChecker(txf_movieEditDirector1);
        TextFieldFunctions.addNoCommaChecker(txf_movieEditDirector2);
        TextFieldFunctions.addOnlyNumberChecker(txf_movieEditCount);
    }

    /**
     * Fills text fields with current movie data and adds corresponding undo functionality.
     * This method populates text fields with the current movie's data and sets up undo functionality
     * for each text field, allowing users to revert changes.
     */
    private void txfListFiller() {
        addToTxfStringList(txf_movieEditName, currentMovieName, igv_movieEditNameUndo);
        addToTxfStringList(txf_movieEditYear, valueOf(currentMovieYear), igv_movieEditYearUndo);
        addToTxfStringList(txf_movieEditLength, valueOf(currentMovieDuration), igv_movieEditLengthUndo);
        addToTxfStringList(txf_movieEditFSK, valueOf(currentMovieFSK), igv_movieEditFskUndo);
        addToTxfStringList(txf_movieEditRating, valueOf(currentMovieRating), igv_movieEditRatingUndo);
        if(genreArray != null) {
            addToTxfStringList(txf_movieEditGenre1, genreArray[0], igv_movieEditGenre1Undo);
        } else {
            addToTxfStringList(txf_movieEditGenre1, null, igv_movieEditGenre1Undo);
        }
        if(genreArray.length > 1) {
            addToTxfStringList(txf_movieEditGenre2, genreArray[1], igv_movieEditGenre2Undo);
        } else {
            addToTxfStringList(txf_movieEditGenre2, null, igv_movieEditGenre2Undo);
        }
        if(genreArray.length > 2) {
            StringBuilder restGenres = new StringBuilder(genreArray[2]);
            for (int i = 3; genreArray.length > i; i++) {
                restGenres.append(", ").append(genreArray[i]);
            }
            addToTxfStringList(txf_movieEditGenre3, restGenres.toString(), igv_movieEditGenre3Undo);
        } else {
            addToTxfStringList(txf_movieEditGenre3, null, igv_movieEditGenre3Undo);
        }

        if(directorsArray != null) {
            addToTxfStringList(txf_movieEditDirector1, directorsArray[0], igv_movieEditDirector1Undo);
        } else {
            addToTxfStringList(txf_movieEditDirector1, null, igv_movieEditDirector1Undo);
        }
        if(directorsArray.length > 1) {
            addToTxfStringList(txf_movieEditDirector2, directorsArray[1], igv_movieEditDirector2Undo);
        } else {
            addToTxfStringList(txf_movieEditDirector2, null, igv_movieEditDirector2Undo);
        }
        if(directorsArray.length > 2) {
            StringBuilder restDirectors = new StringBuilder(directorsArray[2]);
            for (int i = 3; directorsArray.length > i; i++) {
                restDirectors.append(", ").append(directorsArray[i]);
            }
            addToTxfStringList(txf_movieEditDirector3, restDirectors.toString(), igv_movieEditDirector3Undo);
        } else {
            addToTxfStringList(txf_movieEditDirector3, null, igv_movieEditDirector3Undo);
        }
        addToTxfStringList(txf_movieEditCount, valueOf(currentMovieCount), igv_movieEditCountUndo);
        addToTxfStringList(txf_movieEditStudio, currentMovieStudio, igv_movieEditStudioUndo);
        addToTxfStringList(txf_movieEditActors, currentMovieActors, igv_movieEditActorsUndo);
        addToTxfStringList(txf_movieEditLinkToCover, currentLinkToCover, igv_movieEditLinkToCoverUndo);
    }

    /**
     * Adds a text field, its associated data, and its undo button to the list for undo functionality.
     * This method creates a temporary list containing the provided text field, data, and undo button,
     * and then adds this list to the list used for managing undo functionality.
     *
     * @param textField The text field to be added
     * @param data The unedited movie data
     * @param undoButton The undo button associated with the text field
     */
    private void addToTxfStringList(TextField textField, String data, ImageView undoButton) {
        ArrayList<Object> tempList = new ArrayList<>();
        tempList.add(textField);
        tempList.add(data);
        tempList.add(undoButton);
        txfStringUndoList.add(tempList);
    }

    /**
     * Initializes listeners for text fields in the undo list to manage changes.
     * This method sets up listeners for each text field in the undo list. When the text in a text field changes,
     * the listener compares the new value with the original data associated with the text field. If there's a change,
     * the text field's style is updated to indicate the modification.
     */
    private void txfListenerInitializer() {
        for(ArrayList<Object> tempRow : txfStringUndoList) {
            TextField tempTextField = (TextField) tempRow.getFirst();
                tempTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                    String tempCurrentMovieData = (String) tempRow.get(1);
                    if(newValue == null && tempCurrentMovieData == null) {
                        tempTextField.setStyle("-fx-text-fill: #FFF");
                    }
                    if(newValue != null) {
                        if (newValue.equals(tempCurrentMovieData)) {
                            tempTextField.setStyle("-fx-text-fill: #FFF");
                        } else {
                            tempTextField.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
                        }
                    }
                });
        }
    }

    /**
     * Initializes a listener for the comment text area to manage changes.
     * This method sets up a listener for the comment text area. When the text in the text area changes,
     * the listener compares the new value with the original comment associated with the movie.
     * If there's a change, the text area's style is updated to indicate the modification.
     */
    private void txaListenerInitializer() {
        txa_movieEditComment.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.equals(currentMovieComment)) {
                txa_movieEditComment.setStyle("-fx-text-fill: #FFF");
            } else {
                txa_movieEditComment.setStyle("-fx-text-fill: #FF4040; -fx-prompt-text-fill: #FF4040");
            }
        });
    }

    /**
     * Adds event handlers to undo buttons for reverting changes.
     * This method sets up event handlers for each undo button associated with text fields in the undo list.
     * When an undo button is clicked, it restores the original data associated with the respective text field.
     * Additionally, an event handler is added for the comment undo button to revert changes made to the comment text area.
     */
    private void undoButtonAddEventHandler() {
        for(ArrayList<Object> tempRow : txfStringUndoList) {
            TextField tempTextField = (TextField) tempRow.getFirst();
            String tempCurrentMovieData = (String) tempRow.get(1);
            ImageView tempUndoIcon = (ImageView) tempRow.get(2);
            tempUndoIcon.setOnMouseClicked(event -> tempTextField.setText(tempCurrentMovieData));
        }
        igv_movieEditCommentUndo.setOnMouseClicked(event -> txa_movieEditComment.setText(currentMovieComment));
    }

    /**
     * Adds event handlers to checkbox for managing selection changes.
     * This method sets up event handlers for the DVD and Blu-ray checkboxes in the edit movie view.
     * When a checkbox is clicked, it ensures that only one checkbox can be selected at a time and
     * triggers a method to check if checkbox selections have changed.
     */
    private void checkBoxAddEventHandler() {
        cbx_movieEditSelDVD.setOnAction(event -> {
            cbx_movieEditSelBluRay.setSelected(!cbx_movieEditSelDVD.isSelected());
            checkIfCheckBoxesChanged();
        });
        cbx_movieEditSelBluRay.setOnAction(event -> {
            cbx_movieEditSelDVD.setSelected(!cbx_movieEditSelBluRay.isSelected());
            checkIfCheckBoxesChanged();
        });
    }

    /**
     * Checks if checkbox selections have changed and updates styles accordingly.
     * This method checks if the selection of DVD or Blu-ray checkboxes has changed compared to the current movie type.
     * Depending on the changes, it adjusts the text color of the checkboxes to indicate the modification.
     */
    private void checkIfCheckBoxesChanged() {
        if(currentMovieType.equals("DVD") && !cbx_movieEditSelDVD.isSelected()) {
            cbx_movieEditSelDVD.setStyle("-fx-text-fill: #FF4040");
            cbx_movieEditSelBluRay.setStyle("-fx-text-fill: #FF4040");
        } else if(currentMovieType.equals("BR") && !cbx_movieEditSelBluRay.isSelected()) {
            cbx_movieEditSelBluRay.setStyle("-fx-fill: #FF4040");
            cbx_movieEditSelDVD.setStyle("-fx-text-fill: #FF4040");
        } else {
            cbx_movieEditSelBluRay.setStyle("-fx-fill: #FFF");
            cbx_movieEditSelDVD.setStyle("-fx-text-fill: #FFF");
        }
    }

    /**
     * Confirms the editing of the movie with updated information.
     * This method validates the entries, updates the movie information in the database if entries are valid,
     * and provides feedback on the success or failure of the operation.
     */
    public void confirmMovieEdit() {
        if(validEntryChecker()) {
            boolean dbUpdateSuccessful = MoviesUtility.UpdateMovieInDB(currentMovieId,
                    changedName,
                    changedYear,
                    changedLength,
                    changedFsk,
                    changedRating,
                    changedGenres,
                    changedDirectors,
                    changedCount,
                    changedStudio,
                    changedActors,
                    changedLinkToCover,
                    changedComment,
                    changedType);
            if (dbUpdateSuccessful) {
                lbl_movieEditSaveFeedback.setText(MOVIE_SAVE_SUCCESSFUL);
                lbl_movieEditSaveFeedback.setStyle("-fx-text-fill: #518E21");
                lbl_movieEditSaveFeedback.setVisible(true);
                this.movie = MoviesUtility.getMovieById(currentMovieId);
                connector.getLibraryController().updateMovieInLibrary(this.movie);
                LoggerUtility.logger.info("Movie has been edited and saved successfully...");
            } else {
                lbl_movieEditSaveFeedback.setText(MOVIE_SAVE_WENT_WRONG);
                lbl_movieEditSaveFeedback.setStyle("-fx-text-fill: #FF4040");
                lbl_movieEditSaveFeedback.setVisible(true);
                LoggerUtility.logger.info("Saving movie changes went wrong...");
            }
        } else {
            lbl_movieEditSaveFeedback.setText(MOVIE_SAVE_FAILED);
            lbl_movieEditSaveFeedback.setStyle("-fx-text-fill: #FF4040");
            lbl_movieEditSaveFeedback.setVisible(true);
            LoggerUtility.logger.info("Saving movie changes went wrong; wrong inputs...");
        }
    }

    /**
     * Checks if the entered data is valid for updating the movie.
     * This method validates the entered data for various fields including name, year, FSK rating, rating, genres,
     * directors, actors, and link to cover. It updates the styles of corresponding labels to indicate invalid entries.
     *
     * @return true if all entries are valid, false otherwise
     */
    private boolean validEntryChecker() {
        boolean entriesAreValid = true;

        //NameValidator
        if (MovieEntryValidator.notEmptyIsValid(txf_movieEditName.getText())) {
            lbl_movieEditNameTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditNameTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //YearValidator
        try {
            if (MovieEntryValidator.yearIsValid(Integer.parseInt(txf_movieEditYear.getText()))) {
                lbl_movieEditYearTitle.setStyle("-fx-text-fill: #949494");
            } else {
                lbl_movieEditYearTitle.setStyle("-fx-text-fill: #FF4040");
                entriesAreValid = false;
            }
        } catch (NumberFormatException e) {
            lbl_movieEditYearTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //LengthValidator
        if (MovieEntryValidator.notEmptyIsValid(txf_movieEditLength.getText())) {
            lbl_movieEditLengthTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditLengthTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //FSKValidator
        try {
            if (MovieEntryValidator.fskIsValid(Integer.parseInt(txf_movieEditFSK.getText()))) {
                lbl_movieEditFskTitle.setStyle("-fx-text-fill: #949494");
            } else {
                lbl_movieEditFskTitle.setStyle("-fx-text-fill: #FF4040");
                entriesAreValid = false;
            }
        } catch (NumberFormatException e) {
            lbl_movieEditFskTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //RatingValidator
        if (MovieEntryValidator.ratingIsValid(txf_movieEditRating.getText())) {
            lbl_movieEditRatingTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditRatingTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre1Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieEditGenre1.getText(), txf_movieEditGenre2.getText(), txf_movieEditGenre3.getText())) {
            lbl_movieEditGenre1Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditGenre1Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre2Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieEditGenre2.getText(), txf_movieEditGenre3.getText())) {
            lbl_movieEditGenre2Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditGenre2Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre3AlsListValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieEditGenre3.getText())) {
            lbl_movieEditGenre3Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditGenre3Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director1Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieEditDirector1.getText(), txf_movieEditDirector2.getText(), txf_movieEditDirector3.getText())) {
            lbl_movieEditDirector1Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditDirector1Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director2Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieEditDirector2.getText(), txf_movieEditDirector3.getText())) {
            lbl_movieEditDirector2Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditDirector2Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director3AlsListValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieEditGenre3.getText())) {
            lbl_movieEditDirector3Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditDirector3Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //CountValidator
        if (MovieEntryValidator.notEmptyIsValid(txf_movieEditCount.getText())) {
            lbl_movieEditCountTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditCountTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //ActorValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieEditActors.getText())) {
            lbl_movieEditActorsTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditActorsTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //LinkToCoverValidator
        if (MovieEntryValidator.linkToCoverIsValid(txf_movieEditLinkToCover.getText())) {
            lbl_movieEditLinkToCoverTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieEditLinkToCoverTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        if (entriesAreValid) {
            entriesAreValid = saveInfosAsNeededDataTypes();
        }

        return entriesAreValid;
    }

    /**
     * Converts and saves information from text fields to the appropriate data types.
     * This method retrieves data from text fields and converts them to the required data types.
     * It updates corresponding fields to hold the converted data for further processing.
     */
    private boolean saveInfosAsNeededDataTypes() {
        boolean saveInfoWorked = true;
        try {
            this.changedName = txf_movieEditName.getText();
            this.changedYear = Integer.parseInt(txf_movieEditYear.getText());
            this.changedLength = Integer.parseInt(txf_movieEditLength.getText());
            this.changedFsk = Integer.parseInt(txf_movieEditFSK.getText());
            this.changedRating = BigDecimal.valueOf(Double.parseDouble(txf_movieEditRating.getText()));
            this.changedGenres = txf_movieEditGenre1.getText();
            if (!txf_movieEditGenre1.getText().isEmpty() && !txf_movieEditGenre2.getText().isEmpty()) {
                this.changedGenres = this.changedGenres + ", " + txf_movieEditGenre2.getText();
                if (!txf_movieEditGenre3.getText().isEmpty()) {
                    this.changedGenres = this.changedGenres + ", " + txf_movieEditGenre3.getText();
                }
            }
            this.changedDirectors = txf_movieEditDirector1.getText();
            if (!txf_movieEditDirector1.getText().isEmpty() && !txf_movieEditDirector2.getText().isEmpty()) {
                this.changedDirectors = this.changedDirectors + ", " + txf_movieEditDirector2.getText();
                if (!txf_movieEditDirector3.getText().isEmpty()) {
                    this.changedDirectors = this.changedDirectors + ", " + txf_movieEditDirector3.getText();
                }
            }
            this.changedCount = Integer.parseInt(txf_movieEditCount.getText());
            this.changedStudio = txf_movieEditStudio.getText();
            this.changedActors = txf_movieEditActors.getText();
            this.changedLinkToCover = txf_movieEditLinkToCover.getText();
            this.changedComment = txa_movieEditComment.getText();
            if (cbx_movieEditSelDVD.isSelected()) {
                this.changedType = "DVD";
            } else {
                this.changedType = "BR";
            }
        } catch (NumberFormatException e) {
            LoggerUtility.logger.warn("NumberFormatException; saveInfosAsNeededDataTypes:\n" + e.getMessage());
            saveInfoWorked = false;
        }
        return saveInfoWorked;
    }

    /**
     * Cancels the movie editing operation and navigates back to the movie details page.
     * This method retrieves the movie controller, sets the center of the main application border pane to the movie details page,
     * and fills the page with the details of the current movie.
     */
    public void cancelMovieEdit() {
        MovieController movieController = connector.getMovieController();
        MainApplication.borderPane.setCenter(movieController.getOuterPane());
        movieController.fillPage(this.movie);
    }

    /**
     * Deletes the current movie from the database.
     * This method attempts to delete the current movie from the database.
     * If the deletion is successful, it provides feedback, disables edit and cart buttons,
     * resets styling and disables for the movie details, and navigates back to the movie details page.
     * If the deletion fails, it displays an error message and disables the delete confirmation button.
     */
    public void deleteMovie() {
        Boolean movieIsDeleted = MoviesUtility.DeleteMovieInDB(movie.getMovieid());
        if (movieIsDeleted) {
            MovieController movieController = connector.getMovieController();
            movieController.feedbackMessage(MOVIE_DELETE_SUCCESSFUL, "-fx-text-fill: #FF4040");
            movieController.disableEditAndCartButtons();
            enableEditNodes();
            MainApplication.borderPane.setCenter(movieController.getOuterPane());
            connector.getLibraryController().removeMovieFromLibrary(currentMovieId);
            LoggerUtility.logger.info("movie has been deleted successfully...");
        } else {
            lbl_movieEditDeleteFeedback.setText(MOVIE_DELETE_FAILED);
            lbl_movieEditDeleteFeedback.setVisible(true);
            btn_movieEditDeleteConfirm.setDisable(true);
            LoggerUtility.logger.info("delete movie failed...");
        }
    }

    /**
     * Resets styling and disables for movie details after editing.
     * This method resets the text field styles to default, sets label styles to default,
     * hides the delete feedback label, enables the delete confirmation button,
     * enables edit nodes, and disables delete confirmation.
     */
    private void resetStylingAndDisables() {
        for(ArrayList<Object> tempRow: txfStringUndoList) {
            TextField tempTxf = (TextField) tempRow.getFirst();
            tempTxf.setStyle("-fx-text-fill: #FFF");
        }
        lbl_movieEditNameTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditYearTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditLengthTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditFskTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditRatingTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditGenre1Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditGenre2Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditGenre3Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditDirector1Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditDirector2Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditDirector3Title.setStyle("-fx-text-fill: #949494");
        lbl_movieEditCountTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditActorsTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieEditDeleteFeedback.setVisible(false);
        lbl_movieEditSaveFeedback.setVisible(false);
        btn_movieEditDeleteConfirm.setDisable(false);
        enableEditNodes();
        disableDeleteConfirmation();
        disableMovieConstraintInfo();
    }

    /**
     * Toggles the visibility of the constraint info.
     * This method toggles the visibility of the constraint info.
     */
    public void constraintInfoButtonClick() {
        if(acp_movieEditConstraintInfo.isVisible()) {disableMovieConstraintInfo();}
        else {enableMovieConstraintInfo();}
    }

    /**
     * Toggles the visibility of the constraint info.
     * This method toggles the visibility of the constraint info.
     */
    private void enableMovieConstraintInfo() {
        acp_movieEditConstraintInfo.setVisible(true);
    }

    /**
     * Toggles the visibility of the constraint info.
     * This method toggles the visibility of the constraint info.
     */
    private void disableMovieConstraintInfo() {
        acp_movieEditConstraintInfo.setVisible(false);
    }

    /**
     * Opens the delete confirmation dialog.
     * This method disables the nodes related to editing and enables the delete confirmation.
     */
    public void openDeleteConfirmation() {
        disableEditNodes();
        enableDeleteConfirmation();
    }

    /**
     * Cancels the movie deletion operation.
     * This method enables the nodes related to editing and disables the delete confirmation.
     */
    public void cancelMovieDeletion() {
        enableEditNodes();
        disableDeleteConfirmation();
    }

    /**
     * Enables the nodes for editing.
     * This method sets the opacity of undo buttons to 100, enables the background of the edit movie pane,
     * and enables the navigation bar.
     */
    private void enableEditNodes() {
        for(ArrayList<Object> tempRow : txfStringUndoList) {
            ImageView tempUndoButton = (ImageView) tempRow.get(2);
            tempUndoButton.setOpacity(1);
        }
        igv_movieEditCommentUndo.setOpacity(1);
        igv_movieEditConstraintInfoButton.setOpacity(1);
        igv_movieEditXButton.setDisable(false);
        igv_movieEditXButton.setOpacity(1);
        acp_EditMovieBackground.setDisable(false);
        connector.getNavbarController().enableNavBar();
    }

    /**
     * Disables the nodes for editing.
     * This method sets the opacity of undo buttons to 0.3, disables the background of the edit movie pane,
     * and disables the navigation bar.
     */
    private void disableEditNodes() {
        for(ArrayList<Object> tempRow : txfStringUndoList) {
            ImageView tempUndoButton = (ImageView) tempRow.get(2);
            tempUndoButton.setOpacity(0.3);
        }
        igv_movieEditCommentUndo.setOpacity(0.3);
        igv_movieEditConstraintInfoButton.setOpacity(0.3);
        igv_movieEditXButton.setDisable(true);
        igv_movieEditXButton.setOpacity(0.3);
        acp_EditMovieBackground.setDisable(true);
        connector.getNavbarController().disableNavBar();
    }

    /**
     * Enables the delete confirmation.
     * This method enables the delete confirmation pane and makes it visible.
     */
    private void enableDeleteConfirmation() {
        acp_movieEditDeleteConfirmation.setDisable(false);
        acp_movieEditDeleteConfirmation.setVisible(true);
    }

    /**
     * Disables the delete confirmation.
     * This method disables the delete confirmation pane and makes it invisible.
     */
    private void disableDeleteConfirmation() {
        acp_movieEditDeleteConfirmation.setDisable(true);
        acp_movieEditDeleteConfirmation.setVisible(false);
    }
    
    public StackPane getOuterPane() {
        return stp_movieEditBackStackPane;
    }
}
