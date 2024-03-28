package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.utilitys.MoviesUtility;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This class represents the controller for the library view in the application.
 * It manages the behavior and appearance of the library interface, including the display of movie covers.
 */
public class LibraryController {

    private NWayControllerConnector<NavbarController,
                                    LibraryController,
                                    MovieController,
                                    RentalController,
                                    SettingsController,
                                    FilterController,
                                    CartController,
                                    LoginController,
                                    EditMovieController,                           
                                    Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,
                            LibraryController,
                            MovieController,
                            RentalController,
                            SettingsController,
                            FilterController,
                            CartController,
                            LoginController,
                            EditMovieController,             
                            Integer> connector) {
        this.connector = connector;
        this.cartController = connector.getCartController();
    }

    @FXML
    private ScrollPane scrollPane; // pane to scroll in the grid (Child = gridPane)

    @FXML
    public AnchorPane outerAnchorPane;

    @FXML
    private GridPane gridPane; // pane to show the movie covers (Child = ImageView) (Parent = scrollPane)

    private CartController cartController;
    private double windowWidth;
    public Predicate<Movies> predicate;
    public Comparator<Movies> comparator;

    /**
     * Initializes the library view.
     * Sets up the behavior of the scroll pane and adjusts the column count based on window size.
     * Also, it initializes the view with test movie covers for development purposes.
     */
    public void initialize() {
        // Set behavior of scrollPane
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

        // Listener for change of window size
        scrollPane.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                windowWidth = newValue.doubleValue();
                gridPane.setMaxWidth(windowWidth);
                gridPane.setPrefWidth(windowWidth);
                gridPane.setMinWidth(windowWidth);
                adjustColumnCount();
            }
        });

        List<Movies> allMovies = MoviesUtility.getFullMovieList(); //get all Movies From DB
        this.comparator = Comparator.comparing(Movies::getName);
        updateMovies(allMovies);
    }

    /**
     * Sorts the movie StackPane objects within the GridPane and rearranges them accordingly.
     * The sorting is based on a comparator associated with the Movies objects.
     */
    public void sortMovies() {
        List<StackPane> stackPanes = new ArrayList<>();

        // Fügen Sie alle StackPanes dem gridPane hinzu
        for (Node node : gridPane.getChildren()) {
            if (node instanceof StackPane) {
                stackPanes.add((StackPane) node);
            }
        }

        stackPanes.sort((stackPane1, stackPane2) -> {
            Movies movie1 = getMovieFromStackPane(stackPane1);
            Movies movie2 = getMovieFromStackPane(stackPane2);

            if (movie1 != null && movie2 != null) {
                return this.comparator.compare(movie1, movie2);
            }
            return 0; // Wenn movie1 oder movie2 null ist, gibt es keinen Unterschied in der Reihenfolge
        });

        int numColumns = calculateNumColumns();
        int index = 0;

        /* Print all Movies Sorted
        for (StackPane stackPane : stackPanes) {
            for (Node child : stackPane.getChildren()) {
                if (child instanceof ImageView || child instanceof Label) {
                    if (child.getUserData() instanceof Movies) {
                        Movies movie = (Movies) child.getUserData();
                        System.out.println(movie.getName()); // Assuming Movies class has appropriate toString() method
                        break;
                    }
                }
            }
        }*/

        for (StackPane stackPane : stackPanes) {
            if (stackPane.isVisible() && stackPane.isManaged()) {
                int row = index / numColumns;
                int column = index % numColumns;
                GridPane.setRowIndex(stackPane, row);
                GridPane.setColumnIndex(stackPane, column);
                index++;
            }
        }
    }

    /**
     * Retrieves the Movies object associated with the provided StackPane.
     *
     * @param stackPane The StackPane whose associated Movies object is to be retrieved.
     * @return The Movies object associated with the StackPane, or null if no such object is found.
     */
    private Movies getMovieFromStackPane(StackPane stackPane) {
        for (Node child : stackPane.getChildren()) {
            if (child instanceof ImageView || child instanceof Label) {
                if (child.getUserData() instanceof Movies) {
                    return (Movies) child.getUserData();
                }
            }
        }
        return null;
    }

    /**
     * Filters the movie StackPane objects within the GridPane based on the provided predicate.
     * Sets the visibility and manageability of each StackPane accordingly.
     * Adjusts the column count after filtering.
     */
    public void filterMovies() {
        gridPane.getChildren().forEach(node -> {
            if (node instanceof StackPane stackPane) {
                boolean isVisible = stackPane.getChildren().stream()
                        .filter(child -> child instanceof ImageView || child instanceof Label)
                        .map(child -> (Node) child)
                        .map(Node::getUserData)
                        .anyMatch(data -> data instanceof Movies && predicate.test((Movies) data));
                stackPane.setVisible(isVisible);
                stackPane.setManaged(isVisible);
            }
        });
        adjustColumnCount();
    }

    /**
     * Updates the display of movies within the GridPane based on the provided list of movies.
     * Each movie is represented by a StackPane containing either an ImageView with the movie cover image
     * or a Label with the movie name if no cover image is available.
     *
     * @param movieList The list of Movies objects to be displayed or updated.
     */
    public void updateMovies(List<Movies> movieList) {
        for (Movies movie: movieList) {
            String imgUrl = movie.getCover();

            StackPane stackPane = new StackPane();
            gridPane.add(stackPane, 1, 1);

            StackPane stackPaneViewAddToCart = createAddToCartButton(movieList, stackPane, movie);

            Node node = null;

            if (imgUrl.isEmpty() || imgUrl.isBlank()) //If Movie has no img-URL create a Label instead
            {
                Label label = new Label(movie.getName());
                label.setWrapText(true); // Enable text wrapping
                label.setAlignment(Pos.CENTER); // Center align the text

                // Set the size of the StackPane
                label.setMinSize(200, 300); // Mindestgröße des Labels auf 200x300 setzen
                label.setMaxSize(200, 300); // Höchstgröße des Labels auf 200x300 setzen
                label.getStyleClass().add("movieLabelLibrary");

                node = label;
            }
            else {//put the Cover in the library
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(false);
                imageView.setImage(new Image(imgUrl));
                imageView.setFitWidth(200);
                imageView.setFitHeight(300);

                node = imageView;
            }

            node.setUserData(movie);

            node.setOnMouseEntered(event -> stackPaneViewAddToCart.setOpacity(100));
            node.setOnMouseExited(event -> stackPaneViewAddToCart.setOpacity(0));
            node.setOnMouseClicked(event -> {
                try {
                    goToMovie(movie);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });

            stackPane.getChildren().add(node);
            StackPane.setMargin(node, new Insets(20, 0, 0, 20));
            stackPane.getChildren().add(stackPaneViewAddToCart);
        }
        adjustColumnCount();
    }

    /**
     * This method updates all Movies in case of changes
     */
    public void updateMovieList() {
        gridPane.getChildren().clear();
        List<Movies> allMovies = MoviesUtility.getFullMovieList();
        updateMovies(allMovies);
    }

    /**
     * This method creates a StackPane that acts as a button to add a movie to cart
     * the params are used to ensure the functionality of the button
     * @param AllMovies
     * @param stackPane
     * @param movie
     * @return
     */
    public StackPane createAddToCartButton(List<Movies> AllMovies, StackPane stackPane, Movies movie){
        StackPane stackPaneViewAddToCart = new StackPane();
        ImageView imageViewAddToCart = new ImageView();
        stackPaneViewAddToCart.getStyleClass().add("btn_class_libraryAddMovieToCartButton");
        imageViewAddToCart.setImage(new Image(getClass().getResourceAsStream("shoppingcart.png")));
        StackPane.setMargin(stackPaneViewAddToCart, new Insets(0, 10, 10, 0));
        stackPane.setAlignment(Pos.BOTTOM_RIGHT);
        stackPaneViewAddToCart.setAlignment(Pos.CENTER);
        imageViewAddToCart.setPickOnBounds(true);
        imageViewAddToCart.setFitWidth(30);
        imageViewAddToCart.setFitHeight(30);
        stackPaneViewAddToCart.setOpacity(0);
        stackPaneViewAddToCart.setOnMouseEntered(event ->{//Button turns green when hovered over
            stackPaneViewAddToCart.setOpacity(100);
            stackPaneViewAddToCart.setStyle("-fx-background-color: #518E21;");
        });
        stackPaneViewAddToCart.setOnMouseExited(event ->{//Button turns gray when not hovering over anymore
            stackPaneViewAddToCart.setOpacity(0);
            stackPaneViewAddToCart.setStyle("-fx-background-color: #5C5C5C;");
        });

        stackPaneViewAddToCart.setOnMouseClicked(event ->{//addMovieToCart method is being executed on click
            try {
                cartController.addMovieToCart(movie);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Button changes size on when clicked
        stackPaneViewAddToCart.setOnMousePressed(event -> {stackPaneViewAddToCart.setMaxSize(39, 39);});
        stackPaneViewAddToCart.setOnMouseReleased(event -> {stackPaneViewAddToCart.setMaxSize(40, 40);});

        Pane pan_paneCartIconBackground = new Pane();
        stackPaneViewAddToCart.setMaxSize(40, 40);
        stackPaneViewAddToCart.getChildren().addAll(pan_paneCartIconBackground, imageViewAddToCart);

        return stackPaneViewAddToCart;
    }

    /**
     * Calculates the number of columns that can fit within the GridPane based on its width
     * and the width of the images to be displayed.
     *
     * @return The number of columns that can fit within the GridPane.
     */
    private int calculateNumColumns() {
        double gridWidth = gridPane.getWidth();
        double imageWidth = 200+20;
        return Math.max(1, (int) (windowWidth / imageWidth));
    }

    /**
     * Adjusts the number of columns in the grid based on the width of the window.
     * It ensures that the movie covers are displayed appropriately depending on the window size.
     *
     */
    private void adjustColumnCount() {
        int numColumns = calculateNumColumns();

        gridPane.getColumnConstraints().clear();
        for (int i = 0; i < numColumns; i++) {
            ColumnConstraints columnConstraints = new ColumnConstraints();
            columnConstraints.setPercentWidth(100.0 / numColumns);
            gridPane.getColumnConstraints().add(columnConstraints);
        }

        int childIndex = 0;
        for (Node child : gridPane.getChildren()) {
            if (child.isVisible() && child.isManaged()) {
                int row = childIndex / numColumns;
                int column = childIndex % numColumns;
                GridPane.setRowIndex(child, row);
                GridPane.setColumnIndex(child, column);
                childIndex++;
            }
        }
        sortMovies();
    }

    /**
     * Navigates to the details page of the specified movie.
     *
     * @param movie The movie to navigate to.
     * @throws IOException If an I/O error occurs while navigating.
     */
    public void goToMovie(Movies movie) throws IOException {
        MovieController movieController = connector.getMovieController();
        MainApplication.borderPane.setCenter(movieController.getOuterPane());
        MainApplication.borderPane.setRight(null);
        movieController.fillPage(movie);

        //MainApplication.setCenter(outerPane) (idea for rework of setters in MainApp)
    }

    /**
     * @return returns main frame of the scene to the connector the method is called from
     */
    public AnchorPane getOuterPane()
    {
        return outerAnchorPane;
    }
    
}