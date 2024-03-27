package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.utilitys.CustomersUtility;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.collections.ObservableList;
import com.filmverleih.filmverleih.entity.Movies;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.text.TextAlignment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;

import java.time.LocalDate;
import java.text.DecimalFormat;

/**
 * controller class for the cart frame of the application
 * where the shopping cart can be seen with a list of
 * the selected movies, providing a price calculation
 * and displaying the rental and return date.
 *
 * TODO connect Backend
 *
 * @author Hannes, Jannis, Marc
 */
public class CartController {

    private static final double FIXED_PRICE = 7.50;
    private List<Movies> fullMovieList = new ArrayList<>(); //List that must contain the movies in cart
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final String ERR_MOVIE_NULL = "Error: movie is null";

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private TextField txf_CartID;
    @FXML
    private TableView<Movies> tbv_CartItemsTable;
    @FXML
    private TableColumn<Movies, String> tbc_Movie;
    @FXML
    private TableColumn<Movies, String> tbc_Price;
    @FXML
    private HBox hbx_CartTotal;
    @FXML
    private Label lbl_CartTotalValue;
    @FXML
    private Label lbl_DateValue;
    @FXML
    private Label lbl_ReturnDateValue;
    @FXML
    private Button btn_OrderCart;
    @FXML
    private VBox vbx_CartMovieCardsVBox;
    @FXML
    private ScrollPane scp_Cart;
    @FXML
    private AnchorPane acp_CartBackground;
    @FXML
    private AnchorPane acp_newCustomerPopup;
    @FXML
    private StackPane stp_cartOuterStackPane;
    @FXML
    private Label lbl_errorDuplicateRentalMessage;
    @FXML
    private Label lbl_errorNoID;
    @FXML
    private Label lbl_errorEmptyCart;
    @FXML
    private Button btn_checkID;

    //PopUp FXML components
    @FXML
    private TextField txf_PopUpCustomerID;
    @FXML
    private TextField txf_PopUpCustomerSurName;
    @FXML
    private TextField txf_PopUpCustomerLastName;
    @FXML
    private TextField txf_PopUpCustomerStreet;
    @FXML
    private TextField txf_PopUpCustomerPostalCode;
    @FXML
    private TextField txf_PopUpCustomerCity;
    @FXML
    private TextField txf_PopUpCustomerPhone;
    @FXML
    private TextField txf_PopUpCustomerEMail;
    @FXML
    private Button btn_newCustomerPopupConfirm;
    @FXML
    private Button btn_newCustomerPopupCancel;
    @FXML
    private Label lbl_errorInvalidEmail;
    @FXML
    private Label lbl_errorInvalidPhone;

    //Customer information card
    @FXML
    private AnchorPane acp_customerInfoCard;
    @FXML
    private Label lbl_customerIDValue;
    @FXML
    private Label lbl_customerSurnameValue;
    @FXML
    private Label lbl_customerLastNameValue;
    @FXML
    private Label lbl_customerStreetValue;
    @FXML
    private Label lbl_customerCityValue;
    @FXML
    private Label lbl_customerPhoneValue;
    @FXML
    private Label lbl_customerEmailValue;


    /**
     *This method fills in the movie-cards to the movie list on the left
     */
    public void fillMovieList() throws IOException {
        for(Movies movie : fullMovieList){
            addMovieToMovieList(movie);
        }
    }

    /**
     * This method adds one movie to the card view list
     * The FXMLLoader lodes the needed fxml for the single cards
     * which are then added to the vbox on the left
     * @param movie
     * @throws IOException
     */
    private void addMovieToMovieList(Movies movie) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("CartMovie.fxml"));
        HBox movieCard = loader.load();
        CartMovieController controller = loader.getController();

        controller.setCartController(this);

        vbx_CartMovieCardsVBox.getChildren().add(movieCard);
        controller.insertMovieInfo(movie);

        vbx_CartMovieCardsVBox.setSpacing(20.0);
    }

    /**
     * This method removes a movie Card from the ScrollPane of the left SplitView side
     * @param movieCard the movie card which will be removed
     * @param movie the correspondent movie to the movie card which will be removed
     */
    public void removeMovieCard(HBox movieCard, Movies movie) {
        vbx_CartMovieCardsVBox.getChildren().remove(movieCard);
        removeMovieFromCart(movie);

        //TODO find a better place for the following call
        lbl_errorDuplicateRentalMessage.setVisible(false);
        updateCart();
    }


    /**
     * This method fills the TableView with movies from a List
     * which is copied into a ObservableList.
     * It uses the name of the movie and its price.
     */
    public void fillTableView() {
        ObservableList<Movies> fullMovieListObservable = FXCollections.observableArrayList();
        for (Movies movie : fullMovieList) {
            fullMovieListObservable.add(movie);
        }

        tbv_CartItemsTable.setItems(fullMovieListObservable);

        tbc_Movie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tbc_Price.setCellValueFactory(cellData -> new SimpleStringProperty(decimalFormat.format(FIXED_PRICE) + "€"));
    }

    /**
     * This method calculates the total price for the items that
     * are in the cart.
     * @return total price of the cart
     */
    private double calculateTotalPrice() {
        return fullMovieList.size() * FIXED_PRICE;
    }

    /**
     * This method calculates the current Date using LocalDate
     * from java.time.
     * @return local / current Date
     */
    private LocalDate calculateCurrentDate() {
        LocalDate localDate = LocalDate.now();
        return localDate;
    }

    /**
     * This method calculates the return Date using LocalDate
     * from java.time. and adding one week
     * @return local / current Date plus two weeks
     */
    private LocalDate calculateReturnDate() {
        LocalDate returnDate = LocalDate.now().plusWeeks(1);
        return returnDate;
    }


    /**
     * This method sets the text of the correspondent label to the different
     * order information:
     * -totalPrice
     * -currentDate
     * -returnDate
     */
    private void setOrderInformationLabels() {
        updateTotalPrice();
        lbl_DateValue.setText(calculateCurrentDate().toString());
        lbl_ReturnDateValue.setText(calculateReturnDate().toString());

        lbl_DateValue.setAlignment(Pos.CENTER_RIGHT);
        lbl_ReturnDateValue.setAlignment(Pos.CENTER_RIGHT);
    }

    /**
     * This method updates the label of the total price
     */
    private void updateTotalPrice() {
        lbl_CartTotalValue.setText(String.valueOf(calculateTotalPrice()) + "€");
        lbl_CartTotalValue.setAlignment(Pos.CENTER_RIGHT);
    }

    /**
     * This method adds a movie from the cart and initializes an update for
     * the TableView as well as updating the total price
     * @param movie the movie to add to the cart
     */
    public void addMovieToCart(Movies movie) throws IOException {
        if (movie == null) {
            throw new IllegalArgumentException(ERR_MOVIE_NULL);
        } else {
            fullMovieList.add(movie);
            tbv_CartItemsTable.getItems().clear();
            addMovieToMovieList(movie);
            fillTableView();
            updateTotalPrice();
        }
    }

    /**
     * This method removes a movie from the cart and initializes an update for
     * the TableView as well as updating the total price
     * @param movie the movie to remove from cart
     */
    public void removeMovieFromCart(Movies movie) {
        if (movie == null) {
            throw new IllegalArgumentException(ERR_MOVIE_NULL);
        } else {
            fullMovieList.remove(movie);
            tbv_CartItemsTable.getItems().clear();
            fillTableView();
            updateTotalPrice();
            updateCart();
        }
    }

    /**
     * This method is called when clicking the order button
     * and then saves the order from the cart in the rentals table
     * of the db
     *
     * If the provided customerID is already in the customer table
     * the order will be made
     * Else there will be a PopUp where a new customer can be saved to
     * the db with all needed attributes
     */
    @FXML
    public void orderCart() {

       if(Utility.checkCustomerDuplicate(Integer.parseInt(txf_CartID.getText()))) {
           for (int i = 0; i < fullMovieList.size(); i++) {
               boolean addSuccessful = Utility.addRentalToDB(
                       fullMovieList.get(i).getMovieid(),
                       Integer.parseInt(txf_CartID.getText()),
                       calculateCurrentDate().toString(),
                       calculateReturnDate().toString());
               if (!addSuccessful) {
                    setDuplicateRentalLabel(fullMovieList.get(i));
               } else {
                   vbx_CartMovieCardsVBox.getChildren().remove(i);
                   removeMovieFromCart(fullMovieList.get(i));
                   //TODO find a better place for the following call
                   acp_customerInfoCard.setVisible(false);
               }
           }
       } else {
           //enablePopUpDisableCart();
       };
        updateCart();
    }

    /**
     * This method sets a label if a certain movie could not be rented because
     * the movie is already rented by the customer (duplicate key value)
     * @param movie the movie that could not be rented
     */
    public void setDuplicateRentalLabel(Movies movie) {
        System.out.println("The movie " + movie.getName() + " has already been rented to costumer");
        lbl_errorDuplicateRentalMessage.setText(movie.getName() + " befindet sich bereits in Leihgabe an den Kunden!");
        lbl_errorDuplicateRentalMessage.setWrapText(true);
        lbl_errorDuplicateRentalMessage.setVisible(true);
    }


    /**
     * This method enables the new customer registration pop up
     * and disables the navbar and the "background" pane
     * in order to not allow the user to navigate away
     */
    private void enablePopUpDisableCart() {
        connector.getNavbarController().disableNavBar();
        acp_newCustomerPopup.setDisable(false);
        acp_newCustomerPopup.setVisible(true);
        acp_CartBackground.setDisable(true);
    }

    /**
     * This method confirms the registration of a new customer
     * which saves it in the customers table in the db, if the button
     * "confirm" is clicked.
     * Then it closes the PopUp and while enabling the "background"
     * again.
     */
    @FXML
    private void confirmNewCustomerRegistration() {
        if (validateUniquePhone() && validateUniqueEmail()) {
            registerNewCustomer();
            connector.getNavbarController().enableNavBar();
            acp_newCustomerPopup.setDisable(true);
            acp_newCustomerPopup.setVisible(false);
            acp_CartBackground.setDisable(false);
        }
    }

    /**
     * This method cancels the registration of a new customer
     * and closes the PopUp while enabling "background" again,
     * if the "cancel" button is clicked.
     */
    @FXML
    private void cancelNewCustomerRegistration() {
        connector.getNavbarController().enableNavBar();
        acp_newCustomerPopup.setDisable(true);
        acp_newCustomerPopup.setVisible(false);
        acp_CartBackground.setDisable(false);
    }

    /**
     * This method gets the input from the new customer registration
     * TextFields needed for creating a new customer in the db
     * TODO check if getLastAddedCustomerID is a valid way to get it
     */
    private void registerNewCustomer() {
        boolean addSuccessful = Utility.addCustomerToDB(
                txf_PopUpCustomerSurName.getText(),
                txf_PopUpCustomerLastName.getText(),
                txf_PopUpCustomerStreet.getText(),
                txf_PopUpCustomerPostalCode.getText(),
                txf_PopUpCustomerCity.getText(),
                txf_PopUpCustomerPhone.getText(),
                txf_PopUpCustomerEMail.getText()
        );

        if (addSuccessful) {
            txf_CartID.setText(String.valueOf(Utility.getLastAddedCustomerID()));

        }
    }

    /**
     * This method checks whether all TextFields are filled in order to
     * disable or enable the new customer registration confirm button
     */
    @FXML
    private void checkWhetherToDisableNewCustomerButton() {
        boolean anyEmpty = txf_PopUpCustomerSurName.getText().isEmpty() ||
                txf_PopUpCustomerLastName.getText().isEmpty() ||
                txf_PopUpCustomerStreet.getText().isEmpty() ||
                txf_PopUpCustomerPostalCode.getText().isEmpty() ||
                txf_PopUpCustomerCity.getText().isEmpty() ||
                txf_PopUpCustomerPhone.getText().isEmpty() ||
                txf_PopUpCustomerEMail.getText().isEmpty() ||
                !validatePhone() ||
                !validateEmail();


        btn_newCustomerPopupConfirm.setDisable(anyEmpty);
    }

    /**
     * This method is linked to the ID input TextField and reacts if
     * there is typing in this field.
     * It then checks whether the cart is empty and the id input filed
     * in order to disable or enable the order button and showing error
     * message labels.
     */
    @FXML
    public void checkIDEmpty() {
        if (txf_CartID.getText().isBlank()) {
            btn_checkID.setDisable(true);
            btn_OrderCart.setDisable(true);
            lbl_errorNoID.setVisible(true);
        } else {
            lbl_errorNoID.setVisible(false);
            btn_checkID.setDisable(false);
            if (!fullMovieList.isEmpty()) {
                btn_OrderCart.setDisable(false);
                lbl_errorEmptyCart.setVisible(false);
            } else {
                btn_OrderCart.setDisable(true);
                lbl_errorEmptyCart.setVisible(true);
            }
        }
    }

    /**
     * This method checks whether an email is valid or not
     * using the apache commons validator EmailValidator
     * @return true if valid, false if invalid
     */
    private boolean validateEmail() {
        boolean validEmail = EmailValidator.getInstance().isValid(txf_PopUpCustomerEMail.getText());
        if (!validEmail) {
            lbl_errorInvalidEmail.setVisible(true);
            return false;
        } else {
            lbl_errorInvalidEmail.setVisible(false);
            return true;
        }
    }

    /**
     * This method checks whether a phone number is valid or not
     * using a phone number regex
     * @return true if valid, false if invalid
     */
    private boolean validatePhone() {
        boolean validPhone = txf_PopUpCustomerPhone.getText().toString().matches("^\\+?[0-9 ]+$");
        if (!validPhone) {
            lbl_errorInvalidPhone.setVisible(true);
            return false;
        } else {
            lbl_errorInvalidPhone.setVisible(false);
            return true;
        }
    }

    /**
     * This method checks whether an email is already in db
     * and sets an error message label if it is true
     * @return true if email is unique, false if not
     */
    private boolean validateUniqueEmail() {
        if (CustomersUtility.checkDuplicateEmailInCustomer(txf_PopUpCustomerEMail.getText())) {
            lbl_errorInvalidEmail.setText("Diese Email ist bereits vergeben!");
            lbl_errorInvalidEmail.setVisible(true);
            return false;
        } else {
            lbl_errorInvalidEmail.setVisible(false);
            return true;
        }
    }

    /**
     * This method checks whether a phone number is already in db
     * and sets an error message label if it is true
     * @return true if phone number is unique, false if not
     */
    private boolean validateUniquePhone() {
        if (CustomersUtility.checkDuplicatePhoneInCustomer(txf_PopUpCustomerPhone.getText())) {
            lbl_errorInvalidPhone.setText("Diese Telefon-Nr ist bereits vergeben!");
            lbl_errorInvalidPhone.setVisible(true);
            return false;
        } else {
            lbl_errorInvalidPhone.setVisible(false);
            return true;
        }
    }

    @FXML
    private void checkID() {
        boolean bool = CustomersUtility.checkDuplicateCustomerID(Integer.parseInt(txf_CartID.getText()));
        if (!bool) {
            enablePopUpDisableCart();
        } else {
            setCustomerInfo(CustomersUtility.getCustomersByID(Integer.parseInt(txf_CartID.getText())));
            acp_customerInfoCard.setVisible(true);
        }
    }

    private void setCustomerInfoCard() {

    }

    private void setCustomerInfo(Customers customers) {
        lbl_customerIDValue.setText(String.valueOf(customers.getCustomerid()));
        lbl_customerSurnameValue.setText(customers.getFirstname());
        lbl_customerLastNameValue.setText(customers.getLastname());
        lbl_customerStreetValue.setText(customers.getStreet());
        lbl_customerCityValue.setText(customers.getCity());
        lbl_customerPhoneValue.setText(customers.getPhone());
        lbl_customerEmailValue.setText(customers.getEmail());
    }



    /**
     * This method updates the card by setting the order information labels
     * and checking whether the ID is empty
     */
    public void updateCart() {
        setOrderInformationLabels();
        checkIDEmpty();
    }

    /**
     * This method initializes the CartController focussing on setting
     * keyType events to the TextFields for phone and email
     */
    @FXML
    private void initialize() {
        txf_PopUpCustomerPhone.setOnKeyTyped(event -> {
            validatePhone();
            checkWhetherToDisableNewCustomerButton();
        });

        txf_PopUpCustomerEMail.setOnKeyTyped(event -> {
            validateEmail();
            checkWhetherToDisableNewCustomerButton();
        });
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public StackPane getOuterPane() {
        updateCart();
        return stp_cartOuterStackPane;
    }
}
