package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Users;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * controller class for the settings frame of the application
 * Tab1: movies can be managed, added or deleted
 * Tab2: user can be managed, added or removed and seen in a Table
 *
 * @author Hannes
 */
public class SettingsController {

    private List<Users> fullUserList = Utility.getFullUserList();
    private ObservableList<Users> fullUserListObservable = FXCollections.observableArrayList();
    private static final String ERR_USER_NULL = "Error: user is null";


    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    //outer pane
    @FXML
    private TabPane tbp_settingsTabView;

    //components of the movie managing tab
    @FXML
    private TextField txf_movieID;
    @FXML
    private TextField txf_movieName;
    @FXML
    private TextField txf_movieYear;
    @FXML
    private TextField txf_movieLength;
    @FXML
    private TextField txf_movieFSK;
    @FXML
    private TextField txf_movieRating;
    @FXML
    private TextField txf_movieGenre1;
    @FXML
    private TextField txf_movieGenre2;
    @FXML
    private TextField txf_movieGenre3;
    @FXML
    private TextField txf_movieDirector1;
    @FXML
    private TextField txf_movieDirector2;
    @FXML
    private TextField txf_movieDirector3;
    @FXML
    private TextField txf_movieCount;
    @FXML
    private TextField txf_movieStudio;
    @FXML
    private TextField txf_movieActors;
    @FXML
    private TextField txf_movieUndefined3;
    @FXML
    private TextField txf_movieUndefined4;
    @FXML
    private TextField txf_movieUndefined5;
    @FXML
    private TextField txf_movieLinkToCover;
    @FXML
    private TextArea txa_movieComment;
    @FXML
    private CheckBox cbx_selDVD;
    @FXML
    private CheckBox cbx_selBlueRay;
    @FXML
    private TextField txf_deleteMovieId;


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




    /**
     * test method to link the add button to the controller
     * which prints a small verification message in the console
     * that the add button has been clicked
     */
    @FXML
    public void addMovie() {
        String movieType = "DVD";
        Utility utility = new Utility();
        System.out.println("console test: add movie button was clicked");
        if (cbx_selBlueRay.isSelected()) {
             movieType = "BlueRay";
        };
        utility.newMovieInDB(txf_movieName.getText(),
                Integer.parseInt(txf_movieYear.getText()),
                txf_movieGenre1.getText() + ", "
                        + txf_movieGenre2.getText() + ", "
                        + txf_movieGenre3.getText(),
                Integer.parseInt(txf_movieLength.getText()),
                BigDecimal.valueOf(Double.parseDouble(txf_movieRating.getText())),
                Integer.parseInt(txf_movieCount.getText()),
                txf_movieCount.getText(),
                txf_movieLinkToCover.getText(),
                txa_movieComment.getText(),
                txf_movieDirector1.getText() + ", "
                        + txf_movieDirector2.getText() + ", "
                        + txf_movieDirector3.getText(),
                txf_movieStudio.getText(),
                txf_movieActors.getText(),
                Integer.parseInt(txf_movieFSK.getText()), movieType);

    }

    /**
     * test method to link the delete button to the controller
     * which prints a small verification message in the console
     * that the delete button has been clicked
     */
    @FXML
    public void deleteMovie() {
        System.out.println("console test: delete movie button was clicked");
        Utility utility = new Utility();
        utility.DeleteMovieInDB(Integer.parseInt(txf_deleteMovieId.getText()));
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
        return tbp_settingsTabView;
    }
}
