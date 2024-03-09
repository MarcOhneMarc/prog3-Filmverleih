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
 * @author Hannes, Jannis
 */
public class CartController {

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
    private TableView<FilmForTest> tbv_CartItemsTable;
    @FXML
    private TableColumn<FilmForTest, String> tbc_Movie;
    @FXML
    private TableColumn<FilmForTest, Double> tbc_Price;
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
            vbx_CartMovieCardsVBox.getChildren().add(movieCard);
            controller.insertMovieInfo(movie);
        }
    }

    /**
     *This inner Class is ONLY for Testing the Table view
     * and creates simple Movie Objects with the Fields
     * needed for the TableView Columns
     * TODO Delete or update if Backend is connected
     */
    public static class FilmForTest {
        private String title;
        private double price;
        public FilmForTest(String title, double price) {
            this.title = title;
            this.price = price;
        }

        public String getTitle() {
            return title;
        }

        public double getPrice() {
            return price;
        }
    }

    /**
     * This method creates a testList with FilmForTest Objects
     * to fill the TableView
     * TODO Delete of update if Backend is connected
     */
    public void fillTableView() {
        ObservableList<FilmForTest> testList = FXCollections.observableArrayList();
        for (int i = 0; i < 15; i++) {
            testList.add(new FilmForTest("test" +i, 7.50));
        }

        tbv_CartItemsTable.setItems(testList);

        tbc_Movie.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        tbc_Price.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
    }



    /**
     * test method to link the order button to the controller
     * which prints a small verification message in the console
     * that the order button has been clicked
     *
     * Also added a call for the fillTableView() Method in order
     * to test the TableView, after pressing the button once.
     * TODO Delete of update if Backend is connected
     */
    @FXML
    public void orderCart() throws IOException {
        fillTableView();
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
