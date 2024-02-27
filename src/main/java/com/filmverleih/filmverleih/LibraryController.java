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

import java.io.IOException;
import java.util.List;

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
                                    Integer,
                                    Integer,
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
                            Integer,
                            Integer,
                            Integer,
                            Integer> connector) {
        this.connector = connector;
    }

    @FXML
    private ScrollPane scrollPane; // pane to scroll in the grid (Child = gridPane)

    @FXML
    private GridPane gridPane; // pane to show the movie covers (Child = ImageView) (Parent = scrollPane)

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

                label.setOnMouseClicked(event ->{
                    try {
                        goToMovie(AllMovies.get(finalI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }); //lambda
                gridPane.add(label,i % 4,i / 4);
                GridPane.setMargin(label, new Insets(20, 0, 0, 20)); // margin of the covers*/
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
                
                gridPane.add(imageView, i % 4, i / 4);
                GridPane.setMargin(imageView, new Insets(20, 0, 0, 20)); // margin of the covers
            }
        }
        // Initialize the count of columns
        adjustColumnCount(scrollPane.getWidth());
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
        movieController.setEditImage();
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