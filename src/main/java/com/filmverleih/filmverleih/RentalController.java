package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

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
    @FXML
    ScrollPane scp_rentalScrollPane;
    @FXML
    GridPane grp_rentalGrid;

    private double windowWidth;
    public Predicate<Movies> predicate;
    public Comparator<Movies> comparator;


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
                grp_rentalGrid.setMaxWidth(windowWidth);
                grp_rentalGrid.setPrefWidth(windowWidth);
                grp_rentalGrid.setMinWidth(windowWidth);
                adjustColumnCount();
            }
        });

        this.comparator = Comparator.comparing(Movies::getName);

        //Load Rental View
        //TODO change to not get all movies but only those that are rented from db
        List<Movies> allMovies = MoviesUtility.getFullMovieList();
        updateRental(allMovies);
    }

    /**
     * Updates the display of movies within the GridPane based on the provided list of movies.
     * Each movie is represented by a StackPane containing either an ImageView with the movie cover image
     * or a Label with the movie name if no cover image is available.
     *
     * @param movieList The list of Movies objects to be displayed or updated.
     */
    public void updateRental(List<Movies> movieList) throws IOException {
        for (Movies movie: movieList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("RentalMovie.fxml"));
            HBox rentalCard = loader.load();
            RentalMovieController controller = loader.getController();
            controller.setRentalController(this);

            controller.insertMovieInfo(movie);
            controller.insertCustomerInfo();

            rentalCard.setUserData(movie);
            GridPane.setMargin(rentalCard,  new Insets(20, 0, 0, 20));
            grp_rentalGrid.add(rentalCard, 1, 1);
        }
        adjustColumnCount();
    }

    /**
     * Sorts the movie StackPane objects within the GridPane and rearranges them accordingly.
     * The sorting is based on a comparator associated with the Movies objects.
     */
    public void sortMovies() {
        List<HBox> hBoxes = grp_rentalGrid.getChildren().stream()
                .filter(node -> node instanceof HBox)
                .map(node -> (HBox) node)
                .toList();

        hBoxes.sort((hBox1, hBox2) -> {
            Movies movie1 = (Movies) hBox1.getUserData();
            Movies movie2 = (Movies) hBox2.getUserData();

            if (movie1 != null && movie2 != null) {
                return this.comparator.compare(movie1, movie2);
            }
            return 0; // Wenn movie1 oder movie2 null ist, gibt es keinen Unterschied in der Reihenfolge
        });

        int numColumns = calculateNumColumns();
        int index = 0;

        for (HBox hBox : hBoxes) {
            if (hBox.isVisible() && hBox.isManaged()) {
                int row = index / numColumns;
                int column = index % numColumns;
                grp_rentalGrid.setRowIndex(hBox, row);
                grp_rentalGrid.setColumnIndex(hBox, column);
                index++;
            }
        }
    }

    /**
     * Filters the movie StackPane objects within the GridPane based on the provided predicate.
     * Sets the visibility and manageability of each StackPane accordingly.
     * Adjusts the column count after filtering.
     */
    public void filterMovies() {
        grp_rentalGrid.getChildren().forEach(node -> {
            if (node instanceof HBox hBox) {
                boolean isVisible = hBox.getChildren().stream()
                        .map(Node::getUserData)
                        .anyMatch(data -> data instanceof Movies && predicate.test((Movies) data));
                hBox.setVisible(isVisible);
                hBox.setManaged(isVisible);
            }
        });
        adjustColumnCount();
    }



    /**
     * Calculates the number of columns that can fit within the GridPane based on its width
     * and the width of the images to be displayed.
     *
     * @return The number of columns that can fit within the GridPane.
     */
    private int calculateNumColumns() {
        double gridWidth = grp_rentalGrid.getWidth();
        double imageWidth = 750+20;
        return Math.max(1, (int) (windowWidth / imageWidth));
    }

    /**
     * Adjusts the number of columns in the grid based on the width of the window.
     * It ensures that the movie covers are displayed appropriately depending on the window size.
     *
     */
    private void adjustColumnCount() {
        int numColumns = calculateNumColumns();

        grp_rentalGrid.getColumnConstraints().clear();
        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / numColumns);
            grp_rentalGrid.getColumnConstraints().add(columnConstraints);
        }

        int childIndex = 0;
        for (Node child : grp_rentalGrid.getChildren()) {
            if (child.isVisible() && child.isManaged()) {
                int row = childIndex / numColumns;
                int column = childIndex % numColumns;
                grp_rentalGrid.setRowIndex(child, row);
                grp_rentalGrid.setColumnIndex(child, column);
                childIndex++;
            }
        }
        //sortMovies();
    }

    /**
     * This method returns a rented movie and removes it from the rental view.
     * @param hBox the rental movie card which will be removed
     * @param movie the movie that will be returned
     */
    public void removeFromRental(HBox hBox, Movies movie) {
        grp_rentalGrid.getChildren().remove(hBox);
        adjustColumnCount();
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public AnchorPane getOuterPane() {
        return anp_rentalOuterAnchorPane;
    }
}
