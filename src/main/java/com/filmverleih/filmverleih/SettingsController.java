package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Users;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import com.filmverleih.filmverleih.utilitys.UserUtility;
import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import com.filmverleih.filmverleih.utilitys.UserUtility;
import jakarta.persistence.NoResultException;
import javafx.animation.PauseTransition;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.util.ArrayList;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
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
    private Users userToDelete;


    NWayControllerConnector<NavbarController, LibraryController, MovieController, RentalController, SettingsController, FilterController, CartController, LoginController, EditMovieController, Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     *
     * @param connector the controller passed by MainApplication
     */

    public void setConnector(NWayControllerConnector<NavbarController, LibraryController, MovieController, RentalController, SettingsController, FilterController, CartController, LoginController, EditMovieController, Integer> connector) {

        this.connector = connector;
    }

    private List<Users> fullUserList = UserUtility.getFullUserList();
    private ObservableList<Users> fullUserListObservable = FXCollections.observableArrayList();
    private static final String ERR_USER_NULL = "Error: user is null";

    //outer pane
    @FXML
    private TabPane tbp_settingsTabView;
    @FXML
    private Tab tbs_settingsTab;
    @FXML
    private Tab tbs_mitarbeiterTab;
    @FXML
    private Tab tbs_profileTab;


    //components of the movie managing tab
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
    //@FXML
    //private TextField txf_deleteMovieId;
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
    private final String MOVIE_DOES_NOT_EXIST = "Der angegebene Film existiert nicht.";
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

    @FXML
    private Label lbl_deleteMovie;
    @FXML
    private Button btn_deleteMovie;
    @FXML
    private AnchorPane anp_employeeBackground;
    @FXML
    private AnchorPane anp_employeePopUp;
    @FXML
    private Label lbl_deleteEmployee;
    @FXML
    private Button btn_deleteEmployee;
    @FXML
    private Button btn_cancelDeletionEmployee;


    //components of the employee managing tab
    @FXML
    TextField txf_userIdAdd;
    @FXML
    TextField txf_userFirstName;
    @FXML
    TextField txf_userPassword;
    @FXML
    TextField txf_userIdDelete;
    @FXML
    Label lbl_addUser;
    @FXML
    Button btn_addUser;
    @FXML
    Button btn_deleteUser;
    @FXML
    Label lbl_loggedUserName;
    @FXML
    Label lbl_loggedUserId;
    @FXML
    Label lbl_loggedUserIsAdmin;
    @FXML
    Label lbl_passwordsDontMatch;
    @FXML
    Label lbl_passwordChanged;
    @FXML
    TextField txf_oldPassword;
    @FXML
    TextField txf_newPassword;
    @FXML
    TextField txf_newPasswordRepeat;
    @FXML
    Button btn_confirmChangePassword;

    @FXML
    CheckBox cbx_selAdminUser;
    @FXML
    Label lbl_idNotExisting;
    @FXML
    TableView<Users> tbv_userTable;
    @FXML
    TableColumn<Users, Integer> tbc_userID;
    @FXML
    TableColumn<Users, String> tbc_userName;
    @FXML
    TableColumn<Users, Boolean> tbc_userIsAdmin;



    public void initialize() {
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
                LoggerUtility.logger.info("Movie has been saved successfully...");
            } else {
                lbl_movieAddSaveFeedback.setText(MOVIE_SAVE_WENT_WRONG);
                lbl_movieAddSaveFeedback.setStyle("-fx-text-fill: #FF4040");
                lbl_movieAddSaveFeedback.setVisible(true);
                LoggerUtility.logger.info("Saving movie went wrong...");
            }
        } else {
            lbl_movieAddSaveFeedback.setText(MOVIE_SAVE_FAILED);
            lbl_movieAddSaveFeedback.setStyle("-fx-text-fill: #FF4040");
            lbl_movieAddSaveFeedback.setVisible(true);
            LoggerUtility.logger.info("Saving movie went wrong; wrong inputs...");
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
            LoggerUtility.logger.warn("saving info as needed data type went wrong; NumberFormatException: " + e.getMessage());
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
        //Utility utility = new Utility();
        //utility.DeleteMovieInDB(Integer.parseInt(txf_deleteMovieId.getText()));
        boolean movieIsDeleted;
        try {
            movieIsDeleted = MoviesUtility.DeleteMovieInDB(Integer.parseInt(txf_deleteMovieId1.getText()));
            LoggerUtility.logger.info("delete movie button was clicked");
        } catch (NumberFormatException e) {
            movieIsDeleted = false;
        }
        if (movieIsDeleted) {
            connector.getLibraryController().removeMovieFromLibrary(Integer.parseInt(txf_deleteMovieId1.getText()));
            lbl_movieAddDeleteFeedback.setText(MOVIE_DELETE_SUCCESSFUL);
            lbl_movieAddDeleteFeedback.setStyle("-fx-text-fill: #FF4040");
            lbl_movieAddDeleteFeedback.setVisible(true);
            btn_movieAddDeleteConfirm.setDisable(true);
            LoggerUtility.logger.info("movie has been deleted successfully...");
        } else {
            lbl_movieAddDeleteFeedback.setText(MOVIE_DELETE_FAILED);
            lbl_movieAddDeleteFeedback.setVisible(true);
            btn_movieAddDeleteConfirm.setDisable(true);
            LoggerUtility.logger.info("delete movie failed...");
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
        btn_movieAddDeleteConfirm.setDisable(false);
        lbl_movieAddSaveFeedback.setVisible(false);
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
        if (txf_deleteMovieId1.getText() != null) {
            if (!txf_deleteMovieId1.getText().isEmpty() && MoviesUtility.getMovieById(Integer.parseInt(txf_deleteMovieId1.getText())) != null) {
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
        tbs_mitarbeiterTab.setDisable(false);
        connector.getNavbarController().enableNavBar(); //TODO
    }

    /**
     * Disables the nodes for adding.
     * This method sets the opacity of undo buttons to 0.3, disables the background of the add movie pane,
     * and disables the navigation bar.
     */
    private void disableAddNodes() {
        igv_movieAddConstraintInfoButton.setOpacity(0.3);
        acp_movieAddNodes.setDisable(true);
        tbs_mitarbeiterTab.setDisable(true);
        connector.getNavbarController().disableNavBar(); //TODO
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
     * This method changes the view for each user depending on his rights
     * Admins have access to Employee Tab
     */
    public void viewForAdmins() {

        Users loggedUser = connector.getLoginController().getLoggedUser();
        lbl_idNotExisting.setVisible(false);
        if (loggedUser.getIsadmin()) {
            fillTableView();
            if (!tbp_settingsTabView.getTabs().contains(tbs_mitarbeiterTab)) {
                tbp_settingsTabView.getTabs().add(tbs_mitarbeiterTab);
            }
            txf_deleteMovieId1.setVisible(true);
            lbl_deleteMovie.setVisible(true);
            btn_deleteMovie.setVisible(true);
        } else {
            tbp_settingsTabView.getTabs().remove(tbs_mitarbeiterTab);
            txf_deleteMovieId1.setVisible(false);
            lbl_deleteMovie.setVisible(false);
            btn_deleteMovie.setVisible(false);
        }
    }

    /**
     * This method adds a user to the user management TableView
     * and to the db
     * @throws IllegalArgumentException
     */
    @FXML
    public void addUserToTableView() throws NoSuchAlgorithmException {
        Encryptor encryptor = new Encryptor();
        String name = txf_userFirstName.getText();
        String password = txf_userPassword.getText();
        boolean isAdmin = cbx_selAdminUser.isSelected();
        String hashedPassword = encryptor.encryptPassword(password);

        Users user = new Users();
        user.setName(name);
        user.setIsadmin(isAdmin);
        user.setPassword(hashedPassword);

        if (user == null) {
            throw new IllegalArgumentException(ERR_USER_NULL);
        } else {
            Utility utility = new Utility();
            utility.addUserToDB(user);
            fullUserListObservable.add(user);
            tbv_userTable.refresh();
        }
    }

    /**
     * This method removes a user from the user management TableView
     * and from the db
     */

    public void deleteUser() {
        if (userToDelete != null) {
            disableDeleteUserPopUp();
            Utility utility = new Utility();
            utility.deleteUserInDB(userToDelete);
            fullUserListObservable.remove(userToDelete);
            tbv_userTable.refresh();
        }
    }


    /**
     * This method checks for a user with the same id as the id in the TextField
     * @return true if the user exists and false if the user doesn't exist
     */
    public boolean checkUserExists() {
        int id = Integer.parseInt(txf_userIdDelete.getText());
        for (Users user : Utility.getFullUserList()) {
            if (user == null) {
                throw new IllegalArgumentException(ERR_USER_NULL);
            }
            if (user.getUserid() == id) {
                userToDelete = user;
                return true;
            }
        }
        return false;
    }

    /**
     * This method prints a text for the Employee to see that the
     * user he is trying to delete doesn't exist
     * The text will be shown for 3 seconds
     * @param id
     */
    public void userNotExisting(int id) {
        lbl_idNotExisting.setText("Mitarbeiter mit der ID " + id + " existiert nicht");
        PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
        pauseTransition.setOnFinished(event -> lbl_idNotExisting.setVisible(false));
        lbl_idNotExisting.setVisible(true);
        pauseTransition.play();
    }

    /**
     * If the user exists:
     * - a pop-up message will be shown on the screen so the Employee can choose
     * - if he really wants to remove the user
     * If the user doesn't exist:
     * - a message will be printed saying that the user he is trying to delete
     * - doesn't exist
     */
    public void enableDeleteUserPopUp() {
        if (checkUserExists()) {
            anp_employeeBackground.setDisable(true);
            anp_employeePopUp.setVisible(true);
            anp_employeePopUp.setDisable(false);
            connector.getNavbarController().disableNavBar();
            tbs_settingsTab.setDisable(true);
            tbs_profileTab.setDisable(true);
            lbl_deleteEmployee.setText("Möchten Sie den Mitarbeiter mit der ID " + userToDelete.getUserid() + " löschen?");
        } else {
            userNotExisting(Integer.parseInt(txf_userIdDelete.getText()));
        }
        emptyTextField();
    }

    /**
     * This method changes from the pop-up view back to the employee view
     */
    public void disableDeleteUserPopUp() {
        anp_employeeBackground.setDisable(false);
        anp_employeePopUp.setVisible(false);
        anp_employeePopUp.setDisable(true);
        connector.getNavbarController().enableNavBar();
        tbs_settingsTab.setDisable(false);
        tbs_profileTab.setDisable(false);
        emptyTextField();
    }

    /**
     * This method checks if the old password is right and then it checks
     * if both the new password fields are equal.
     */
    public void checkPasswordMatch() {
        if (!txf_oldPassword.getText().isBlank()) {
            if (txf_newPassword.getText().isBlank()) {
                btn_confirmChangePassword.setDisable(true);
            } else {
                if (txf_newPassword.getText().equals(txf_newPasswordRepeat.getText())) {
                    btn_confirmChangePassword.setDisable(false);
                    lbl_passwordsDontMatch.setVisible(false);
                } else {
                    lbl_passwordsDontMatch.setText("Passwörter stimmen nicht überein");
                    lbl_passwordsDontMatch.setVisible(true);
                    btn_confirmChangePassword.setDisable(true);
                }
            }
        } else {
            lbl_passwordsDontMatch.setText("Altes Passwort eingeben");
            lbl_passwordsDontMatch.setVisible(true);
            btn_confirmChangePassword.setDisable(true);
        }
    }

    /**
     * This method allows the user to change his password by clicking a button
     * the user can only change his password if the old password is right.
     * @throws NoSuchAlgorithmException
     */
    public void changePasswordButton() throws NoSuchAlgorithmException {
        Users loggedUser = connector.getLoginController().getLoggedUser();
        Encryptor encryptor = new Encryptor();


        if (encryptor.encryptPassword(txf_oldPassword.getText()).equals(loggedUser.getPassword())) {
            String hashedPassword = encryptor.encryptPassword(txf_newPassword.getText());
            if(hashedPassword.equals(loggedUser.getPassword())){
                lbl_passwordsDontMatch.setText("Das neue Passwort darf nicht das alte Passwort sein");
                lbl_passwordsDontMatch.setVisible(true);
            }else {
                loggedUser.setPassword(hashedPassword);
                lbl_passwordChanged.setVisible(true);
                Utility utility = new Utility();
                utility.UpdateUserPasswordInDB(hashedPassword, loggedUser.getUserid());
            }
        } else {
            lbl_passwordsDontMatch.setText("Falsches Passwort eingegeben");
            lbl_passwordsDontMatch.setVisible(true);
        }
    }

    /**
     * This method refreshes the users list.
     * It is used when logging out so we make sure that
     * the list is always updated.
     */
    public void refreshFullUserList() {
        fullUserList = Utility.getFullUserList();
    }

    /**
     * This method empties the text fields.
     */
    public void emptyTextField() {
        txf_userFirstName.setText("");
        txf_userPassword.setText("");
        txf_userIdDelete.setText("");
        txf_newPassword.setText("");
        txf_oldPassword.setText("");
        txf_newPasswordRepeat.setText("");
        lbl_passwordsDontMatch.setVisible(false);
        lbl_passwordChanged.setVisible(false);
    }

    /**
     * This method fills the user data in the profile tab.
     * ID and Name of the user.
     * user is Admin or not.
     */
    public void fillUserData() {
        LoginController loginController = connector.getLoginController();
        Users loggedUser = loginController.getLoggedUser();
        lbl_loggedUserName.setText(loggedUser.getName());
        lbl_loggedUserId.setText(String.valueOf(loggedUser.getUserid()));
        if (loggedUser.getIsadmin()) {
            lbl_loggedUserIsAdmin.setText("Der User ist ein Admin");
        } else {
            lbl_loggedUserIsAdmin.setText("Der User ist kein Admin");
        }
    }

    /**
     * This method gives us the view of the Settings tab
     */
    public TabPane getOuterPane() {
        fillUserData();
        emptyTextField();
        refreshFullUserList();
        viewForAdmins();
        fillTableView();
        initialize();
        resetStylingAndDisables();
        return tbp_settingsTabView;
    }
}
