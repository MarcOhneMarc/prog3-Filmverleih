package com.filmverleih.filmverleih;


import com.filmverleih.filmverleih.entity.Users;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class LoginController {

    @FXML
    private TextField txf_loginName;
    @FXML
    private TextField txf_loginPassword;

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
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Benutzername oder Passwort falsch");
            if(alert.showAndWait().get() == ButtonType.OK){
                MainApplication.launch();
            }
        }
    }

    public boolean checkUsers(){
        String loginName = txf_loginName.getText();
        String loginPassword = txf_loginPassword.getText();

        List<Users> users = Utility.getFullUserList();
        for(Users user: users){
            if(user.getName().equals(loginName) && user.getPassword().equals(loginPassword)){
                return true;
            }
        }
        return false;
    }

}
