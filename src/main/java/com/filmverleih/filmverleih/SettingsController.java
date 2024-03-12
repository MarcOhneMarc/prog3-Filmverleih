package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
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

    @FXML
    private TabPane tbp_settingsTabView;
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

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
    TableView tbv_employeeTable;


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

    public TabPane getOuterPane()
    {
        return tbp_settingsTabView;
    }
}
