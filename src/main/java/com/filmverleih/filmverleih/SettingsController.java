package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Users;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.math.BigDecimal;

/**
 * controller class for the settings frame of the application
 * where movies can be added by typing in all
 * necessary details or deleted by giving the movieID
 *
 * @author Hannes
 */
public class SettingsController {

    private ObservableList<Users> employeeList = FXCollections.observableArrayList();
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
    TextField txf_employeeIdAdd;
    @FXML
    TextField txf_employeeFirstName;
    @FXML
    TextField txf_employeeSurname;
    @FXML
    TextField txf_employeeIdDelete;
    @FXML
    Label lbl_addEmployee;
    @FXML
    Button btn_addEmployee;
    @FXML
    Button btn_deleteEmployee;
    @FXML
    CheckBox cbx_selAdminEmployee;
    @FXML
    TableView<Users> tbv_employeeTable;
    @FXML
    TableColumn<Users, Integer> tbc_employeeID;
    @FXML
    TableColumn<Users, String> tbc_employeeName;
    @FXML
    TableColumn<Users, Boolean> tbc_employeeIsAdmin;



    int movieID;
    String movieName;
    int movieYear;
    int movieLength;
    int movieFSK;
    BigDecimal movieRating;
    String movieGenre1;
    String movieGenre2;
    String movieGenre3;
    String movieDirector1;
    String movieDirector2;
    String movieDirector3;
    int movieCount;
    String movieStudio;
    String movieActors;
    String movieUndefined3;
    String movieUndefined4;
    String movieUndefined5;
    String movieLinkToCover;
    String movieComment;
    boolean dvd;
    boolean blueRay;


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
        utility.newMovieInDB(txf_movieName.getText(), Integer.parseInt(txf_movieYear.getText()), txf_movieGenre1.getText() + ", " + txf_movieGenre2.getText() + ", " + txf_movieGenre3.getText(), Integer.parseInt(txf_movieLength.getText()), BigDecimal.valueOf(Double.parseDouble(txf_movieRating.getText())), Integer.parseInt(txf_movieCount.getText()), txf_movieCount.getText(), txf_movieLinkToCover.getText(), txa_movieComment.getText(), txf_movieDirector1.getText() + ", " + txf_movieDirector2.getText() + ", " + txf_movieDirector3.getText(), txf_movieStudio.getText(), txf_movieActors.getText(), Integer.parseInt(txf_movieFSK.getText()), movieType);

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
     * This method fills the TableView with users / employees from
     * the observableList employeeList
     * It uses the id, name and isAdmin from Users.
     */
    public void fillTableView() {
        tbv_employeeTable.setItems(employeeList);

        tbc_employeeID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getUserid()).asObject());
        tbc_employeeName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tbc_employeeIsAdmin.setCellValueFactory((cellData -> new SimpleBooleanProperty(cellData.getValue().getIsadmin())));
    }

    /**
     * This method adds an employee / user to the employee management TableView
     * @param user the user that will be added
     */
    public void addEmployeeToTableView(Users user) {
        if (user == null) {
            throw new IllegalArgumentException(ERR_USER_NULL);
        } else {
            employeeList.add(user);
            tbv_employeeTable.refresh();
        }
    }

    /**
     * This method removes an employee / user to the employee management TableView
     * @param user the user that will be removed
     */
    public void removeEmployeeToTableView(Users user) {
        if (user == null) {
            throw new IllegalArgumentException(ERR_USER_NULL);
        } else {
            employeeList.remove(user);
            tbv_employeeTable.refresh();
        }
    }

    /**
     * This method adds an employee / user and is linked to the add button of the
     * employee management tab
     * TODO actually add user to db
     */
    @FXML
    public void addEmployee() {
        System.out.println("console test: add employee button has been clicked");
    }

    /**
     * This method removes an employee / user and is linked to the delete button of the
     * employee management tab
     * TODO actually delete user to db
     */
    @FXML
    public void deleteEmployee() {
        System.out.println("console test: delete employee button has been clicked");
    }

    public TabPane getOuterPane()
    {
        return tbp_settingsTabView;
    }
}
