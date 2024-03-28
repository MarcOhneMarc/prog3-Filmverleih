package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Users;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import com.filmverleih.filmverleih.utilitys.UserUtility;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

/**
 * controller class for the settings frame of the application
 * Tab1: movies can be managed, added or deleted
 * Tab2: user can be managed, added or removed and seen in a Table
 *
 * @author Hannes
 */
public class SettingsController {
    //private NavbarController navbarController;


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

    private List<Users> fullUserList = UserUtility.getFullUserList();
    private ObservableList<Users> fullUserListObservable = FXCollections.observableArrayList();
    private static final String ERR_USER_NULL = "Error: user is null";

    //outer pane
    @FXML
    private TabPane tbp_settingsTabView;

    //components of the movie managing tab
    @FXML
    private Tab mitarbeiterTab;
    @FXML
    private AnchorPane acp_movieAddNodes;
    @FXML
    private TextField txf_movieID;
    @FXML
    private Label lbl_movieAddNameTitle;
    @FXML
    private TextField txf_movieAddName;
    @FXML
    private Label lbl_movieAddYearTitle;
    @FXML
    private TextField txf_movieAddYear;
    @FXML
    private Label lbl_movieAddLengthTitle;
    @FXML
    private TextField txf_movieAddLength;
    @FXML
    private Label lbl_movieAddFskTitle;
    @FXML
    private TextField txf_movieAddFSK;
    @FXML
    private Label lbl_movieAddRatingTitle;
    @FXML
    private TextField txf_movieAddRating;
    @FXML
    private Label lbl_movieAddGenre1Title;
    @FXML
    private TextField txf_movieAddGenre1;
    @FXML
    private Label lbl_movieAddGenre2Title;
    @FXML
    private TextField txf_movieAddGenre2;
    @FXML
    private Label lbl_movieAddGenre3Title;
    @FXML
    private TextField txf_movieAddGenre3;
    @FXML
    private Label lbl_movieAddDirector1Title;
    @FXML
    private TextField txf_movieAddDirector1;
    @FXML
    private Label lbl_movieAddDirector2Title;
    @FXML
    private TextField txf_movieAddDirector2;
    @FXML
    private Label lbl_movieAddDirector3Title;
    @FXML
    private TextField txf_movieAddDirector3;
    @FXML
    private Label lbl_movieAddCountTitle;
    @FXML
    private TextField txf_movieAddCount;
    @FXML
    private TextField txf_movieAddStudio;
    @FXML
    private Label lbl_movieAddActorsTitle;
    @FXML
    private TextField txf_movieAddActors;
    @FXML
    private Label lbl_movieAddLinkToCoverTitle;
    @FXML
    private TextField txf_movieAddLinkToCover;
    @FXML
    private TextArea txa_movieAddComment;
    @FXML
    private Label lbl_movieAddSaveFeedback;
    @FXML
    private CheckBox cbx_selDVD;
    @FXML
    private CheckBox cbx_selBlueRay;
    @FXML
    private TextField txf_deleteMovieId;
    @FXML
    private CheckBox cbx_movieAddSelDVD;
    @FXML
    private CheckBox cbx_movieAddSelBluRay;
    @FXML
    private TextField txf_deleteMovieId1;
    @FXML
    private Label lbl_movieAddDeleteIdFeedback;
    @FXML
    private ImageView igv_movieAddConstraintInfoButton;
    @FXML
    private AnchorPane acp_movieAddConstraintInfo;
    @FXML
    private AnchorPane acp_movieAddDeleteConfirmation;
    @FXML
    private Button btn_movieAddDeleteConfirm;
    @FXML
    private Label lbl_movieAddDeleteFeedback;

    private final String MOVIE_DELETE_SUCCESSFUL = "Der Film wurde erfolgreich entfernt.";
    private final String MOVIE_DELETE_FAILED = "Es ist etwas schiefgelaufen! Versuchen Sie es bitte erneut.";
    private final String MOVIE_SAVE_SUCCESSFUL = "Die Daten wurden erfolgreich gespeichert.";
    private final String MOVIE_SAVE_FAILED = "Manche Textfelder stimmen nicht mit den Anforderungen überein. " +
            "Die Titel dieser wurden rot markiert.";
    private final String MOVIE_SAVE_WENT_WRONG = "Etwas ist schiefgelaufen, bitte versuchen Sie es erneut.";


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



    //components of the employee managing tab
    @FXML
    TextField txf_userIdAdd;
    @FXML
    TextField txf_userFirstName;
    @FXML
    TextField txf_userSurname;
    @FXML
    TextField txf_userIdDelete;
    @FXML
    Label lbl_addUser;
    @FXML
    Button btn_addUser;
    @FXML
    Button btn_deleteUser;
    @FXML
    CheckBox cbx_selAdminUser;
    @FXML
    TableView<Users> tbv_userTable;
    @FXML
    TableColumn<Users, Integer> tbc_userID;
    @FXML
    TableColumn<Users, String> tbc_userName;
    @FXML
    TableColumn<Users, Boolean> tbc_userIsAdmin;




    public void initialize() {
        resetStylingAndDisables();
        addOnlyNumbersConstraint();
        checkBoxAddEventHandler();
    }

    /**
     * Adds constraints to ensure that only numbers are entered in certain text fields.
     * This method applies specific constraints to the UI text fields for adding movie details,
     * allowing only numeric input for fields such as year, length, FSK rating, rating, count and MovieId.
     */
    private void addOnlyNumbersConstraint() {
        TextFieldFunctions.addYearChecker(txf_movieAddYear);
        TextFieldFunctions.addDurationChecker(txf_movieAddLength);
        TextFieldFunctions.addFskChecker(txf_movieAddFSK);
        TextFieldFunctions.addRatingChecker(txf_movieAddRating);
        TextFieldFunctions.addNoCommaChecker(txf_movieAddGenre1);
        TextFieldFunctions.addNoCommaChecker(txf_movieAddGenre2);
        TextFieldFunctions.addNoCommaChecker(txf_movieAddDirector1);
        TextFieldFunctions.addNoCommaChecker(txf_movieAddDirector2);
        TextFieldFunctions.addOnlyNumberChecker(txf_movieAddCount);
        TextFieldFunctions.addOnlyNumberChecker(txf_deleteMovieId1);
    }

    /**
     * Adds event handlers to checkbox for managing selection changes.
     * This method sets up event handlers for the DVD and Blu-ray checkboxes in the add movie view.
     * When a checkbox is clicked, it ensures that only one checkbox can be selected at a time and
     * triggers a method to check if checkbox selections have changed.
     */
    private void checkBoxAddEventHandler() {
        cbx_movieAddSelDVD.setOnAction(event -> {
            cbx_movieAddSelBluRay.setSelected(!cbx_movieAddSelDVD.isSelected());
        });
        cbx_movieAddSelBluRay.setOnAction(event -> {
            cbx_movieAddSelDVD.setSelected(!cbx_movieAddSelBluRay.isSelected());
        });
    }

    /**
     * Confirms the addition of a new movie to the database after validating the entry.
     * Updates the user interface accordingly based on the success of the database update.
     */
    public void confirmMovieAdd() {
        if(validEntryChecker()) {
            boolean dbUpdateSuccessful = MoviesUtility.newMovieInDB(
                    changedName,
                    changedYear,
                    changedGenres,
                    changedLength,
                    changedRating,
                    changedCount,
                    changedType,
                    changedLinkToCover,
                    changedComment,
                    changedDirectors,
                    changedStudio,
                    changedActors,
                    changedFsk
                    );
            if (dbUpdateSuccessful) {
                lbl_movieAddSaveFeedback.setText(MOVIE_SAVE_SUCCESSFUL);
                lbl_movieAddSaveFeedback.setStyle("-fx-text-fill: #518E21");
                lbl_movieAddSaveFeedback.setVisible(true);
                connector.getLibraryController().updateMovieList();
            } else {
                lbl_movieAddSaveFeedback.setText(MOVIE_SAVE_WENT_WRONG);
                lbl_movieAddSaveFeedback.setStyle("-fx-text-fill: #FF4040");
                lbl_movieAddSaveFeedback.setVisible(true);
            }
        } else {
            lbl_movieAddSaveFeedback.setText(MOVIE_SAVE_FAILED);
            lbl_movieAddSaveFeedback.setStyle("-fx-text-fill: #FF4040");
            lbl_movieAddSaveFeedback.setVisible(true);
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
        if (MovieEntryValidator.notEmptyIsValid(txf_movieAddName.getText())) {
            lbl_movieAddNameTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddNameTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //YearValidator
        try {
            if (MovieEntryValidator.yearIsValid(Integer.parseInt(txf_movieAddYear.getText()))) {
                lbl_movieAddYearTitle.setStyle("-fx-text-fill: #949494");
            } else {
                lbl_movieAddYearTitle.setStyle("-fx-text-fill: #FF4040");
                entriesAreValid = false;
            }
        } catch (NumberFormatException e) {
            lbl_movieAddYearTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //LengthValidator
        if (MovieEntryValidator.notEmptyIsValid(txf_movieAddLength.getText())) {
            lbl_movieAddLengthTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddLengthTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //FSKValidator
        try {
            if (MovieEntryValidator.fskIsValid(Integer.parseInt(txf_movieAddFSK.getText()))) {
                lbl_movieAddFskTitle.setStyle("-fx-text-fill: #949494");
            } else {
                lbl_movieAddFskTitle.setStyle("-fx-text-fill: #FF4040");
                entriesAreValid = false;
            }
        } catch (NumberFormatException e) {
            lbl_movieAddFskTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //RatingValidator
        if (MovieEntryValidator.ratingIsValid(txf_movieAddRating.getText())) {
            lbl_movieAddRatingTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddRatingTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre1Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieAddGenre1.getText(), txf_movieAddGenre2.getText(), txf_movieAddGenre3.getText())) {
            lbl_movieAddGenre1Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddGenre1Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre2Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieAddGenre2.getText(), txf_movieAddGenre3.getText())) {
            lbl_movieAddGenre2Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddGenre2Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Genre3AlsListValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieAddGenre3.getText())) {
            lbl_movieAddGenre3Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddGenre3Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director1Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieAddDirector1.getText(), txf_movieAddDirector2.getText(), txf_movieAddDirector3.getText())) {
            lbl_movieAddDirector1Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddDirector1Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director2Validator
        if (MovieEntryValidator.splitTextFieldIsValid(txf_movieAddDirector2.getText(), txf_movieAddDirector3.getText())) {
            lbl_movieAddDirector2Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddDirector2Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //Director3AlsListValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieAddGenre3.getText())) {
            lbl_movieAddDirector3Title.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddDirector3Title.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //CountValidator
        if (MovieEntryValidator.notEmptyIsValid(txf_movieAddCount.getText())) {
            lbl_movieAddCountTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddCountTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //ActorValidator
        if (MovieEntryValidator.listWithCommaIsValid(txf_movieAddActors.getText())) {
            lbl_movieAddActorsTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddActorsTitle.setStyle("-fx-text-fill: #FF4040");
            entriesAreValid = false;
        }

        //LinkToCoverValidator
        if (MovieEntryValidator.linkToCoverIsValid(txf_movieAddLinkToCover.getText())) {
            lbl_movieAddLinkToCoverTitle.setStyle("-fx-text-fill: #949494");
        } else {
            lbl_movieAddLinkToCoverTitle.setStyle("-fx-text-fill: #FF4040");
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
            this.changedName = txf_movieAddName.getText();
            this.changedYear = Integer.parseInt(txf_movieAddYear.getText());
            this.changedLength = Integer.parseInt(txf_movieAddLength.getText());
            this.changedFsk = Integer.parseInt(txf_movieAddFSK.getText());
            this.changedRating = BigDecimal.valueOf(Double.parseDouble(txf_movieAddRating.getText()));
            this.changedGenres = txf_movieAddGenre1.getText();
            if (!txf_movieAddGenre1.getText().isEmpty() && !txf_movieAddGenre2.getText().isEmpty()) {
                this.changedGenres = this.changedGenres + ", " + txf_movieAddGenre2.getText();
                if (!txf_movieAddGenre3.getText().isEmpty()) {
                    this.changedGenres = this.changedGenres + ", " + txf_movieAddGenre3.getText();
                }
            }
            this.changedDirectors = txf_movieAddDirector1.getText();
            if (!txf_movieAddDirector1.getText().isEmpty() && !txf_movieAddDirector2.getText().isEmpty()) {
                this.changedDirectors = this.changedDirectors + ", " + txf_movieAddDirector2.getText();
                if (!txf_movieAddDirector3.getText().isEmpty()) {
                    this.changedDirectors = this.changedDirectors + ", " + txf_movieAddDirector3.getText();
                }
            }
            this.changedCount = Integer.parseInt(txf_movieAddCount.getText());
            this.changedStudio = txf_movieAddStudio.getText();
            this.changedActors = txf_movieAddActors.getText();
            this.changedLinkToCover = txf_movieAddLinkToCover.getText();
            this.changedComment = txa_movieAddComment.getText();
            if (cbx_movieAddSelDVD.isSelected()) {
                this.changedType = "DVD";
            } else {
                this.changedType = "BR";
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            saveInfoWorked = false;
        }
        return saveInfoWorked;
    }

    /**
     * Deletes the current movie from the database.
     * This method attempts to delete the current movie from the database.
     * If the deletion is successful, it provides feedback, disables add and cart buttons,
     * resets styling and disables for the movie details, and navigates back to the movie details page.
     * If the deletion fails, it displays an error message and disables the delete confirmation button.
     */
    public void deleteMovie() {
        boolean movieIsDeleted;
        try {
            movieIsDeleted = MoviesUtility.DeleteMovieInDB(Integer.parseInt(txf_deleteMovieId1.getText()));
        } catch (NumberFormatException e) {
            movieIsDeleted = false;
        }
        if (movieIsDeleted) {
            lbl_movieAddDeleteFeedback.setText(MOVIE_DELETE_SUCCESSFUL);
            lbl_movieAddDeleteFeedback.setStyle("-fx-text-fill: #FF4040");
        } else {
            lbl_movieAddDeleteFeedback.setText(MOVIE_DELETE_FAILED);
            lbl_movieAddDeleteFeedback.setVisible(true);
            btn_movieAddDeleteConfirm.setDisable(true);
        }
    }

    /**
     * Resets styling and disables for movie details after adding.
     * This method resets the text field styles to default, sets label styles to default,
     * hides the delete feedback label, enables the delete confirmation button,
     * enables add nodes, and disables delete confirmation.
     */
    private void resetStylingAndDisables() {
        lbl_movieAddNameTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddYearTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddLengthTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddFskTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddRatingTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddGenre1Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddGenre2Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddGenre3Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddDirector1Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddDirector2Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddDirector3Title.setStyle("-fx-text-fill: #949494");
        lbl_movieAddCountTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddActorsTitle.setStyle("-fx-text-fill: #949494");
        lbl_movieAddDeleteFeedback.setVisible(false);
        lbl_movieAddSaveFeedback.setVisible(false);
        btn_movieAddDeleteConfirm.setDisable(false);
        enableAddNodes();
        disableDeleteConfirmation();
        disableMovieConstraintInfo();
    }

    /**
     * Toggles the visibility of the movie constraint information panel.
     * If the panel is currently visible, it is disabled; otherwise, it is enabled.
     */
    public void constraintInfoButtonClick() {
        if(acp_movieAddConstraintInfo.isVisible()) {disableMovieConstraintInfo();}
        else {enableMovieConstraintInfo();}
    }

    /**
     * Sets AnchorPane with constraint infos visible
     */
    private void enableMovieConstraintInfo() {
        acp_movieAddConstraintInfo.setVisible(true);
    }

    /**
     * Sets AnchorPane with constraint infos invisible
     */
    private void disableMovieConstraintInfo() {
        acp_movieAddConstraintInfo.setVisible(false);
    }

    /**
     * Opens the delete confirmation dialog.
     * This method disables the nodes related to adding and enables the delete confirmation.
     */
    public void openDeleteConfirmation() {
        if (txf_deleteMovieId1 != null) {
            if (!txf_deleteMovieId1.getText().isEmpty()) {
                lbl_movieAddDeleteIdFeedback.setVisible(false);
                disableAddNodes();
                enableDeleteConfirmation();
            } else {
                lbl_movieAddDeleteIdFeedback.setVisible(true);
            }
        } else {
            lbl_movieAddDeleteIdFeedback.setVisible(true);
        }
    }

    /**
     * Cancels the movie deletion operation.
     * This method enables the nodes related to adding and disables the delete confirmation.
     */
    public void cancelMovieDeletion() {
        enableAddNodes();
        disableDeleteConfirmation();
    }

    /**
     * Enables the nodes for adding.
     * This method sets the opacity of ? button to 100, enables the background of the add movie pane,
     * and enables the navigation bar.
     */
    private void enableAddNodes() {
        igv_movieAddConstraintInfoButton.setOpacity(1);
        acp_movieAddNodes.setDisable(false);
        mitarbeiterTab.setDisable(false);
        //connector.getNavbarController().enableNavBar(); TODO
    }

    /**
     * Disables the nodes for adding.
     * This method sets the opacity of undo buttons to 0.3, disables the background of the add movie pane,
     * and disables the navigation bar.
     */
    private void disableAddNodes() {
        igv_movieAddConstraintInfoButton.setOpacity(0.3);
        acp_movieAddNodes.setDisable(true);
        mitarbeiterTab.setDisable(true);
        //connector.getNavbarController().disableNavBar(); TODO
    }

    /**
     * Enables the delete confirmation.
     * This method enables the delete confirmation pane and makes it visible.
     */
    private void enableDeleteConfirmation() {
        acp_movieAddDeleteConfirmation.setDisable(false);
        acp_movieAddDeleteConfirmation.setVisible(true);
    }

    /**
     * Disables the delete confirmation.
     * This method disables the delete confirmation pane and makes it invisible.
     */
    private void disableDeleteConfirmation() {
        acp_movieAddDeleteConfirmation.setDisable(true);
        acp_movieAddDeleteConfirmation.setVisible(false);
    }

    /**
     * This method fills the TableView with users from
     * the observableList fullUserList
     * It uses the id, name and isAdmin from Users.
     */
    public void fillTableView() {
        fullUserListObservable.clear();

        for (Users user : fullUserList) {
            fullUserListObservable.add(user);
        }

        tbv_userTable.setItems(fullUserListObservable);

        tbc_userID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserid()).asObject());
        tbc_userName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tbc_userIsAdmin.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue().getIsadmin())));
    }

    /**
     * This method adds a user to the user management TableView
     * @param user the user that will be added
     */
    public void addUserToTableView(Users user) {
        if (user == null) {
            throw new IllegalArgumentException(ERR_USER_NULL);
        } else {
            fullUserListObservable.add(user);
            tbv_userTable.refresh();
        }
    }

    /**
     * This method removes a user to the user management TableView
     * @param user the user that will be removed
     */
    public void removeUserFromTableView(Users user) {
        if (user == null) {
            throw new IllegalArgumentException(ERR_USER_NULL);
        } else {
            fullUserListObservable.remove(user);
            tbv_userTable.refresh();
        }
    }

    /**
     * This method adds an user and is linked to the add button of the
     * user management tab
     * TODO actually add user to db
     * TODO add user to List and refresh TableView
     */
    @FXML
    public void addUser() {
        System.out.println("console test: add user button has been clicked");
    }

    /**
     * This method removes an user and is linked to the delete button of the
     * user management tab
     * TODO actually delete user to db
     * TODO remove user from List and refresh TableView
     */
    @FXML
    public void deleteUser() {
        System.out.println("console test: delete user button has been clicked");
    }

    /**
     * TODO remove fillTableView() from here and find a better suiting place for its calling
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public TabPane getOuterPane()
    {
        fillTableView();
        initialize();
        return tbp_settingsTabView;
    }
}
