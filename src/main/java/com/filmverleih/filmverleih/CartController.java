package com.filmverleih.filmverleih;

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
 * @author Hannes, Jannis
 */
public class CartController {

    private static final double FIXED_PRICE = 7.50;
    private List<Movies> fullMovieList = new ArrayList<>(); //List that must contain the movies in cart
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    private static final String ERR_MOVIE_NULL = "Error: movie is null";

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
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

    /**
     *This method fills in the movie-cards to the movie list on the left
     * TODO parameter List must be transferred to show the correct list of movies in cart
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
       Utility utility = new Utility();
       if(utility.checkCustomerDuplicate(Integer.parseInt(txf_CartID.getText()))) {
           for (Movies movies : fullMovieList) {
               utility.addRentalToDB(movies.getMovieid(), Integer.parseInt(txf_CartID.getText()), calculateCurrentDate().toString(), calculateReturnDate().toString());

               removeMovieFromCart(movies);
               vbx_CartMovieCardsVBox.getChildren().clear();
           }
       } else {
           acp_newCustomerPopup.setDisable(false);
           acp_newCustomerPopup.setVisible(true);
       };
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public AnchorPane getOuterPane() {
        setOrderInformationLabels();
        return acp_CartBackground;
    }
}
