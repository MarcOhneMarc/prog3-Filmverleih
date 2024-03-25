package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

import java.io.IOException;
import java.util.List;

/**
 * controller class for the rental view of the application
 * which provides an overview of all rented movies, displaying
 * the movie itself and customer / rental information
 * with options of reminding the customer, extending the rental
 * and returning the movie
 *
 * TODO connect Backend
 *
 * @author Hannes, Luka
 */
public class RentalController {

    @FXML
    AnchorPane anp_rentalOuterAnchorPane;

    @FXML

    Pane pane;
    ScrollPane scp_rentalScrollPane;
    @FXML
    GridPane grp_rentalGrid;

    private double windowWidth = 1920;

    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector;

    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, EditMovieController,Integer,Integer> connector) {
        this.connector = connector;
    }

    /**
     * This method initializes the rental view
     * sets up the behavior of the scroll pane and adjusts the column count based on window size.
     * also, it initializes the view with test rentals for development purposes.
     * TODO get rented movies from db
     * @throws IOException
     */
    public void initialize() throws IOException {
        // Set behavior of scrollPane
        scp_rentalScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scp_rentalScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        // Listener for change of window size
        scp_rentalScrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                windowWidth = newValue.doubleValue();
                adjustColumnCount(newValue.doubleValue());
            }
        });

        //Load Rental View
        //TODO change to not get all movies but only those that are rented from db
        List<Movies> allMovies = Utility.getFullMovieList();
        for (int i = 0; i < allMovies.size(); i++) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("RentalMovie.fxml"));
            HBox rentalCard = loader.load();
            RentalMovieController controller = loader.getController();
            controller.setRentalController(this);

            controller.insertMovieInfo(allMovies.get(i));
            controller.insertCustomerInfo();

            GridPane.setMargin(rentalCard,  new Insets(20, 0, 0, 20));
            grp_rentalGrid.add(rentalCard, i, i / 4);
        }

        // Initialize the count of columns
        adjustColumnCount(scp_rentalScrollPane.getWidth());
    }


    /**
     * Adjusts the number of columns in the grid based on the width of the window.
     * It ensures that the movie covers are displayed appropriately depending on the window size.
     *
     * @param windowWidth the width of the window
     */
    private void adjustColumnCount(double windowWidth) {
        double cardWidth = 750 + 20; // Width of card plus margin
        int numColumns = Math.max(1, (int) (windowWidth / cardWidth)); // Count of columns from windowWidth

        int row = 0;
        int column = 0;
        for (Node child : grp_rentalGrid.getChildren()) {
            GridPane.setRowIndex(child, row);
            GridPane.setColumnIndex(child, column);
            column++;
            if (column == numColumns) {
                column = 0;
                row++;
            }
        }
    }

    /**
     * This method returns a rented movie and removes it from the rental view.
     * @param hBox the rental movie card which will be removed
     * @param movie the movie that will be returned
     */
    public void removeFromRental(HBox hBox, Movies movie) {
        grp_rentalGrid.getChildren().remove(hBox);
        adjustColumnCount(windowWidth);
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public AnchorPane getOuterPane() {
        return anp_rentalOuterAnchorPane;
    }
}
