package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

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
                                    EditMovieController,
                                    Integer,
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
                            EditMovieController,
                            Integer,
                            Integer> connector) {
        this.connector = connector;
        this.cartController = connector.getCartController();
    }

    @FXML
    private ScrollPane scrollPane; // pane to scroll in the grid (Child = gridPane)

    @FXML
    private GridPane gridPane; // pane to show the movie covers (Child = ImageView) (Parent = scrollPane)

    private CartController cartController;

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
                adjustColumnCount(newValue.doubleValue());
            }
        });

        //Load Library View
        String imgUrl = "";
        List<Movies> AllMovies = Utility.getFullMovieList(); //get all Movies From DB
        for (int i = 0; i < AllMovies.size(); i++) {
            int finalI = i;
            imgUrl = AllMovies.get(i).getCover();

            StackPane stackPane = new StackPane();
            gridPane.add(stackPane, i, i / 4);

            StackPane stackPaneViewAddToCart = createAddToCartButton(AllMovies, stackPane, finalI);

            if (imgUrl.isEmpty() || imgUrl.isBlank()) //If Movie has no img-URL create a Label instead
            {

                Label label = new Label(AllMovies.get(i).getName());

                label.setWrapText(true); // Enable text wrapping
                label.setAlignment(Pos.CENTER); // Center align the text
                label.setMaxWidth(200); // Set maximum width for wrapping

                // Set the size of the StackPane
                label.setMinSize(200, 300); // Mindestgröße des Labels auf 200x300 setzen
                label.setMaxSize(200, 300); // Höchstgröße des Labels auf 200x300 setzen
                label.getStyleClass().add("movieLabelLibrary");

                label.setOnMouseEntered(event ->{stackPaneViewAddToCart.setOpacity(100);});
                label.setOnMouseExited(event ->{stackPaneViewAddToCart.setOpacity(0);});

                label.setOnMouseClicked(event ->{
                    try {
                        goToMovie(AllMovies.get(finalI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }); //lambda
                stackPane.getChildren().add(label);
                StackPane.setMargin(label, new Insets(20, 0, 0, 20)); // margin of the covers*/
            }
            else {//put the Cover in the library
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(false);
                imageView.setImage(new Image(imgUrl));

                imageView.setFitWidth(200);
                imageView.setFitHeight(300);

                imageView.setOnMouseClicked(event ->{
                    try {
                        goToMovie(AllMovies.get(finalI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }); //lambda

                imageView.setOnMouseEntered(event ->{stackPaneViewAddToCart.setOpacity(100);});
                imageView.setOnMouseExited(event ->{stackPaneViewAddToCart.setOpacity(0);});

                stackPane.getChildren().add(imageView);
                StackPane.setMargin(imageView, new Insets(20, 0, 0, 20)); // margin of the covers
            }
            stackPane.getChildren().add(stackPaneViewAddToCart);
        }
        // Initialize the count of columns
        adjustColumnCount(scrollPane.getWidth());
    }

    /**
     * This method creates a StackPane that acts as a button to add a movie to cart
     * the params are used to ensure the functionality of the button
     * @param AllMovies
     * @param stackPane
     * @param finalI
     * @return
     */
    public StackPane createAddToCartButton(List<Movies> AllMovies, StackPane stackPane, int finalI){
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
                cartController.addMovieToCart(AllMovies.get(finalI));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        //Button changes size on when clicked
        stackPaneViewAddToCart.setOnMousePressed(event -> {stackPaneViewAddToCart.setMaxSize(39, 39);});
        stackPaneViewAddToCart.setOnMouseReleased(event -> {stackPaneViewAddToCart.setMaxSize(40, 40);});

        Pane pan_paneCartIconBackground = new Pane();
        //pan_paneCartIconBackground.setPrefSize(35, 35);
        stackPaneViewAddToCart.setMaxSize(40, 40);
        stackPaneViewAddToCart.getChildren().addAll(pan_paneCartIconBackground, imageViewAddToCart);

        return stackPaneViewAddToCart;
    }

    /**
     * Adjusts the number of columns in the grid based on the width of the window.
     * It ensures that the movie covers are displayed appropriately depending on the window size.
     *
     * @param windowWidth the width of the window
     */
    private void adjustColumnCount(double windowWidth) {
        double imageWidth = 200 + 40; // Width of images plus margin
        int numColumns = Math.max(1, (int) (windowWidth / imageWidth)); // Count of columns from windowWidth

        int row = 0;
        int column = 0;
        for (Node child : gridPane.getChildren()) {
            GridPane.setRowIndex(child, row);
            GridPane.setColumnIndex(child, column);
            column++;
            if (column == numColumns) {
                column = 0;
                row++;
            }
        }
    }
    //uses connector to switch smoothly between controllers, without loosing track of runtime-instance
    public void goToMovie(Movies movie) throws IOException {
        MovieController movieController = connector.getMovieController();
        MainApplication.borderPane.setCenter(movieController.getOuterPane());
        movieController.fillPage(movie);

        //MainApplication.setCenter(outerPane) (idea for rework of setters in MainApp)
    }

    /**
     * @return returns main frame of the scene to the connector the method is called from
     */
    public ScrollPane getOuterPane()
    {
        return scrollPane;
    }
    
}