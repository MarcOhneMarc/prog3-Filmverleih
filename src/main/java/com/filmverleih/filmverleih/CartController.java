package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

public class CartController {

    @FXML
    private BorderPane bdp_CartBorderPane;

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;


    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private TextField txf_CartName;
    @FXML
    private TextField txf_CartSurname;
    @FXML
    private TextField txf_CartID;
    @FXML
    private TableView tbv_CartItemsTable;
    @FXML
    private Label lbl_CartTotalValue;
    @FXML
    private Label lbl_DateValue;
    @FXML
    private Label lbl_ReturnDateValue;
    @FXML
    private Button btn_OrderCart;

    @FXML
    public void orderCart() {
        System.out.println("console test: order button has been clicked");
    }

    public BorderPane getOuterPane() {
        return bdp_CartBorderPane;
    }
}
