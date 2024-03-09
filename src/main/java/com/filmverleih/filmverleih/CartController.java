package com.filmverleih.filmverleih;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.collections.ObservableList;
import com.filmverleih.filmverleih.entity.Movies;
import javafx.scene.layout.HBox;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import java.time.LocalDate;

/**
 * controller class for the cart frame of the application
 * where the shopping cart can be seen with a list of
 * the selected movies, providing a price calculation
 * and displaying the rental and return date.
 *
 * TODO Price calculation
 * TODO Date calculation
 * TODO connect Backend
 *
 * TODO if movie is removed from left scrollPane, also remove from TableView and make new price calculation (update button?)
 *
 * TODO !!! determine if price, current / return date calculation is for Backend or here !!!
 *
 * @author Hannes, Jannis
 */
public class CartController {

    private static final double FIXED_PRICE = 7.50;

    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private BorderPane bdp_CartBorderPane;
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
    private TableColumn<Movies, Double> tbc_Price;
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

    /**
     *This method fills in the movie-cards to the movie list on the left
     * TODO parameter List must be transferred to show the correct list of movies in cart
     */
    public void fillMovieList() throws IOException {
        List<Movies> fullMovieList = Utility.getFullMovieList();
        for(Movies movie : fullMovieList){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CartMovie.fxml"));
            HBox movieCard = loader.load();
            CartMovieController controller = loader.getController();

            controller.setCartController(this);

            vbx_CartMovieCardsVBox.getChildren().add(movieCard);
            controller.insertMovieInfo(movie);
        }
    }

    public void removeMovieCard(HBox movieCard) {
        vbx_CartMovieCardsVBox.getChildren().remove(movieCard);
    }


    /**
     * This method fills the TableView with movies from a List
     * which is copied into a ObservableList.
     * It uses the name of the movie and its price.
     * TODO determine price for movies (all the same?)
     * TODO Delete or update if Backend is connected (use list as method argument)
     */
    public void fillTableView() {

        List<Movies> fullMovieList = Utility.getFullMovieList();
        ObservableList<Movies> fullMovieListObservable = FXCollections.observableArrayList();
        for (Movies movie : fullMovieList) {
            fullMovieListObservable.add(movie);
        }


        tbv_CartItemsTable.setItems(fullMovieListObservable);

        tbc_Movie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        tbc_Price.setCellValueFactory(cellData -> new SimpleDoubleProperty(FIXED_PRICE).asObject());
    }

    /**
     * This method calculates the total price for the items that
     * are in the cart.
     * TODO change from fullMovieList to an argument List (list of movies in the cart)
     * @return total price of the cart
     */
    private double calculateTotalPrice() {
        List<Movies> fullMovieList = Utility.getFullMovieList();
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
     * from java.time. and adding two weeks
     * @return local / current Date plus two weeks
     *TODO determine how long movies are rented (here for example two weeks)
     */
    private LocalDate calculateReturnDate() {
        LocalDate returnDate = LocalDate.now().plusWeeks(2);
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
        lbl_CartTotalValue.setText(String.valueOf(calculateTotalPrice()) + "€");
        lbl_DateValue.setText(calculateCurrentDate().toString());
        lbl_ReturnDateValue.setText(calculateReturnDate().toString());
    }



    /**
     * test method to link the order button to the controller
     * which prints a small verification message in the console
     * that the order button has been clicked
     *
     * Also added a call for the fillTableView() Method in order
     * to test the TableView, after pressing the button once.
     * TODO Delete or update if Backend is connected
     */
    @FXML
    public void orderCart() throws IOException {
        fillTableView();
        setOrderInformationLabels();
        fillMovieList();
        System.out.println("console test: order button has been clicked");
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public BorderPane getOuterPane() {
        return bdp_CartBorderPane;
    }
}
