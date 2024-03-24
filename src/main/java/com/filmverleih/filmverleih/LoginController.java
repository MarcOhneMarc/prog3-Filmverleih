package com.filmverleih.filmverleih;


import com.filmverleih.filmverleih.entity.Users;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;


import java.util.List;

public class LoginController {

    @FXML
    private TextField txf_loginName;
    @FXML
    private TextField txf_loginPassword;
    private Users loggedUser;
    @FXML
    private Label lbl_loginWrongCredentials;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private PasswordField pwf_loginPasswordField;
    @FXML
    private CheckBox cbx_showPassword;

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController, Integer,Integer> connector;

    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,Integer,Integer> connector) {
        this.connector = connector;
    }

    @FXML
    public void login(){
        NavbarController navbarController = connector.getNavbarController();
        LibraryController libraryController  = connector.getLibraryController();

        if(checkUsers()) {
            MainApplication.borderPane.setTop(navbarController.getBorderPane());
            MainApplication.borderPane.setCenter(libraryController.getOuterPane());
            lbl_loginWrongCredentials.setVisible(false);
        }else{
            lbl_loginWrongCredentials.setText("Benutzername oder Passwort ist falsch");
            PauseTransition pauseTransition = new PauseTransition(Duration.seconds(3));
            pauseTransition.setOnFinished(event -> lbl_loginWrongCredentials.setVisible(false));
            lbl_loginWrongCredentials.setVisible(true);
            pauseTransition.play();
        }

        txf_loginName.setText("");
        txf_loginPassword.setText("");
        pwf_loginPasswordField.setText("");

    }

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
    public boolean checkUsers(){
        String loginName = txf_loginName.getText();
        String loginPassword = pwf_loginPasswordField.getText();

        List<Users> users = Utility.getFullUserList();
        for(Users user: users){
            if(user.getName().equals(loginName) && (user.getPassword().equals(loginPassword) || user.getPassword().equals(txf_loginPassword.getText()))){
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

    public AnchorPane getPane(){
        return loginPane;
    }

    public TextField getTxf_loginPassword(){
        return txf_loginPassword;
    }
}
