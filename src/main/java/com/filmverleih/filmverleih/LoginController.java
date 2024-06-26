package com.filmverleih.filmverleih;


import com.filmverleih.filmverleih.entity.Users;
import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import com.filmverleih.filmverleih.utilitys.UserUtility;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * This class represents the controller for the login view
 */
public class LoginController {

    @FXML
    private TextField txf_loginName;
    @FXML
    private TextField txf_loginPassword;
    private Users loggedUser;
    @FXML
    private Label lbl_loginWrongCredentials;
    @FXML
    private BorderPane bpn_login;
    @FXML
    private PasswordField pwf_loginPasswordField;
    @FXML
    private CheckBox cbx_showPassword;

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController, EditMovieController,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,EditMovieController,Integer> connector) {
        this.connector = connector;
    }

    /**
     * This login method first checks if username/password is right
     * if they are right then the user will be allowed to enter the program
     * if not then he will have to try again
     * @throws NoSuchAlgorithmException
     */
    @FXML
    public void login() throws NoSuchAlgorithmException {
        NavbarController navbarController = connector.getNavbarController();
        LibraryController libraryController  = connector.getLibraryController();


        if(checkUsers()) {
            MainApplication.borderPane.setTop(navbarController.getBorderPane());
            MainApplication.borderPane.setCenter(libraryController.getOuterPane());
            MainApplication.borderPane.setRight(connector.getFilterController().getOuterPane());
            lbl_loginWrongCredentials.setVisible(false);
            LoggerUtility.logger.info("Login successful...");
        }else{
            lbl_loginWrongCredentials.setText("Benutzername oder Passwort ist falsch");
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event -> lbl_loginWrongCredentials.setVisible(false));
            lbl_loginWrongCredentials.setVisible(true);
            pauseTransition.play();
            LoggerUtility.logger.info("Login failed...");
        }

        txf_loginName.setText("");
        txf_loginPassword.setText("");
        pwf_loginPasswordField.setText("");

    }

    /**
     * This method allows the user to show or hide the password he typed in
     * by using a checkbox
     */
    @FXML
    public void changeVisibility(){
        if(cbx_showPassword.isSelected()){
            txf_loginPassword.setText(pwf_loginPasswordField.getText());
            txf_loginPassword.setVisible(true);
            pwf_loginPasswordField.setVisible(false);
            return;
        }
        pwf_loginPasswordField.setText(txf_loginPassword.getText());
        pwf_loginPasswordField.setVisible(true);
        txf_loginPassword.setVisible(false);
    }


    /**
     *  check if a user with the name/password exists
     *  we have to compare both textfields(PasswordField and TextField)(Line 79)
     */
    public boolean checkUsers() throws NoSuchAlgorithmException {
        Encryptor encryptor = new Encryptor();
        String loginName = txf_loginName.getText();
        String loginPassword = pwf_loginPasswordField.getText();

        if (cbx_showPassword.isSelected()) {
            loginPassword = txf_loginPassword.getText();
        } else {
            txf_loginPassword.setText(pwf_loginPasswordField.getText());
        }
        List<Users> users = UserUtility.getFullUserList();
        for(Users user: users){
            if(user.getName().equals(loginName) && (user.getPassword().equals(encryptor.encryptPassword(loginPassword)))){
                loggedUser = user;
                return true;
            }
        }
        return false;
    }

    public Users getLoggedUser(){
        return loggedUser;
    }

    public void setLoggedUserToNull(){
        loggedUser = null;
    }

    public Label getLbl_loginWrongCredentials(){
        return lbl_loginWrongCredentials;
    }

    public BorderPane getPane(){
        return bpn_login;

    }

    public TextField getTxf_loginPassword(){
        return txf_loginPassword;
    }
}
