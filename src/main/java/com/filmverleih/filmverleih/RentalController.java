package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Rentals;
import com.filmverleih.filmverleih.utilitys.*;

import com.filmverleih.filmverleih.pdfGentators.WarningPdfGenerator;
import com.filmverleih.filmverleih.utilitys.RentalsUtility;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/**
 * controller class for the rental view of the application
 * which provides an overview of all rented movies, displaying
 * the movie itself and customer / rental information
 * with options of reminding the customer, extending the rental
 * and returning the movie

 *
 * @author Hannes, Luka, Marc , Jonas
 */

public class RentalController {

    @FXML
    AnchorPane anp_rentalOuterAnchorPane;

    @FXML
    Pane pane;
    private NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,EditMovieController,Integer> connector;

    @FXML
    ScrollPane scp_rentalScrollPane;
    @FXML
    GridPane grp_rentalGrid;

    private double windowWidth;
    public Predicate<Rentals> predicate;
    public Comparator<Rentals> comparator;


    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */

    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,EditMovieController,Integer> connector) {
      
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

        scp_rentalScrollPane.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                double windowHeight = newValue.doubleValue();
                grp_rentalGrid.setMinHeight(windowHeight);
            }
        });

        this.comparator = Comparator.comparing(rental -> rental.getMovie().getName());

        //Load Rental View
        List<Rentals> allRentals = RentalsUtility.getAllRentedMovies();

        updateRental(allRentals);
        sortMovies();
    }

    /**
     * Updates the display of movies within the GridPane based on the provided list of movies.
     * Each movie is represented by a StackPane containing either an ImageView with the movie cover image
     * or a Label with the movie name if no cover image is available.
     *
     * @param movieList The list of Movies objects to be displayed or updated.
     */
    public void updateRental(List<Rentals> movieList) throws IOException {
        for (Rentals rentalMovie: movieList) {
            addRentedMovieToRental(rentalMovie);
        }
        adjustColumnCount();
    }

    /**
     * This method adds a Movie to the RentalView GridPane
     * @param rental the Rented Movie which has to be added
     * @throws IOException throws an Exception if the FXML couldn't be loaded.
     */
    private void addRentedMovieToRental(Rentals rental) throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RentalMovie.fxml"));
        HBox rentalCard = loader.load();
        RentalMovieController controller = loader.getController();
        controller.setRentalController(this);

        controller.insertMovieInfo(rental);
        controller.insertCustomerInfo();

        rentalCard.setUserData(rental);
        GridPane.setMargin(rentalCard,  new Insets(20, 0, 0, 20));
        grp_rentalGrid.add(rentalCard, 1, 1);
    }
    /**
     * Sorts the movie StackPane objects within the GridPane and rearranges them accordingly.
     * The sorting is based on a comparator associated with the Movies objects.
     */
    public void sortMovies() {
        List<HBox> hBoxes = new ArrayList<>(grp_rentalGrid.getChildren().stream()
                .filter(node -> node instanceof HBox)
                .map(node -> (HBox) node)
                .toList());

        hBoxes.sort((hBox1, hBox2) -> {
            Rentals rental1 = (Rentals) hBox1.getUserData();
            Rentals rental2 = (Rentals) hBox2.getUserData();

            if (rental1 != null && rental2 != null) {
                return this.comparator.compare(rental1, rental2);
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
                Rentals rental = (Rentals) hBox.getUserData();
                boolean isVisible = predicate.test(rental);
                hBox.setVisible(isVisible);
                hBox.setManaged(isVisible);
            }
        });
        adjustColumnCount();
    }

    /**
     * This method updates syncs the RentalView GridPane with the Rentals in the Database.
     *
     */
    public void syncRentalWithDb() throws IOException {
        List<Rentals> rentalsInDb = RentalsUtility.getAllRentedMovies();
        List<Rentals> rentalsToAdd = new ArrayList<>(); // Collect movies to add
        for (Rentals rentalInDb: rentalsInDb) {
            boolean found = false;
            for (Node node : grp_rentalGrid.getChildren()) {
                Rentals rentalInLibrary = (Rentals) node.getUserData();
                assert rentalInLibrary != null;
                if (rentalInDb.getMovieid() == rentalInLibrary.getMovieid()) {
                    if (!rentalInDb.equals(rentalInLibrary)) {
                        removeRentalFromRentalView(rentalInLibrary);
                    }
                    found = true;
                    break; // Found the movie in the library, no need to add
                }
            }
            if (!found) {
                rentalsToAdd.add(rentalInDb); // Collect movies to add
            }
        }

        for (Rentals rentalToAdd : rentalsToAdd) {
            addRentedMovieToRental(rentalToAdd); // Add collected movies to library
        }
        adjustColumnCount();
    }

    /**
     * This method updates a specific Rental in the RentalView GridPane.
     * It deletes the rentalToUpdate in the Pane and adds the changed.
     */
    public void updateRentalInRentalView(Rentals rentalToUpdate) throws IOException {
        removeRentalFromRentalView(rentalToUpdate);
        addRentedMovieToRental(rentalToUpdate);
        sortMovies();
        filterMovies();
    }

    /**
     * This Method removes a Rental from the RentalView
     * @param rentalToDelete is the Rental in the GridPane which should get deleted.
     */
    public void removeRentalFromRentalView(Rentals rentalToDelete) {
        Iterator<Node> iterator = grp_rentalGrid.getChildren().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Rentals rentalInLibrary = (Rentals) node.getUserData();
            if (rentalInLibrary.equals(rentalToDelete)) {
                iterator.remove(); // Use iterator to remove the node
                adjustColumnCount();
                return;
            }
        }
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
        sortMovies();
    }

    /**
     * This method returns a rented movie and removes it from the rental view.
     * @param hBox the rental movie card which will be removed
     * @param rental the rental that will be returned
     */
    public void removeFromRental(HBox hBox, Rentals rental) {
        if (RentalsUtility.deleteRentalFromDB(rental.getMovieid(), rental.getCustomerid())) {
            MoviesUtility.increaseMovieCountByID(rental.getMovieid());
            connector.getLibraryController().updateMovieInLibrary(MoviesUtility.getMovieById(rental.getMovieid()));

            grp_rentalGrid.getChildren().remove(hBox);
            adjustColumnCount();
        } else {
            LoggerUtility.logger.warn("could not remove movie from rental; movieID: " + rental.getMovie().getMovieid() + " customerID: " + rental.getCustomerid());
        }
    }

    /**
     * Method to extend a rental
     *  TODO Implement update Card Info with new End date
     * @param hBox
     * @param rental
     */
    public void extendRental(HBox hBox, Rentals rental) {
        LocalDate date = LocalDate.parse(rental.getEnddate());
        LocalDate newDate = date.plusWeeks(1);
        if (RentalsUtility.extendRentalinDB(rental.getMovieid(), rental.getCustomerid(), newDate.toString())) {
            rental.setEnddate(newDate.toString());
        } else {
            LoggerUtility.logger.warn("could extend rental; movieID: " + rental.getMovie().getMovieid() + " customerID: " + rental.getCustomerid());
        }
    }

    /**
     * Method to remind a customer
     * @param rental the rental to be reminded
     */
    public void remindCustomer(Rentals rental) {
        if (WarningPdfGenerator.generatePdf(rental.getMovie().getName(), rental.getStartdate(), rental.getEnddate())) {
            LoggerUtility.logger.info("customer reminded successfully...");
        } else {
            LoggerUtility.logger.info("customer could not be reminded...");
        }
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public AnchorPane getOuterPane() {
        return anp_rentalOuterAnchorPane;
    }
}