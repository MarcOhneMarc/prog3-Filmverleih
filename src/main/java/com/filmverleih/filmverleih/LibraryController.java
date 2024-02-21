package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

import java.util.List;

/**
 * This class represents the controller for the library view in the application.
 * It manages the behavior and appearance of the library interface, including the display of movie covers.
 */
public class LibraryController {

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
            imgUrl = AllMovies.get(i).getCover();
            if (imgUrl.isEmpty() || imgUrl.isBlank()) //If Movie has no img-URL create a Label instead
            {

                Label lbl = new Label();
                lbl.setAlignment(Pos.CENTER);
                lbl.setMinHeight(300);
                lbl.setMaxHeight(300);
                lbl.setMinWidth(200);
                lbl.setMaxWidth(200);
                lbl.setText(AllMovies.get(i).getName());
                gridPane.add(lbl,i%4,i/4);
            }
            else {//put the Cover in the library
                Button imageButton = new Button();
                ImageView imageView = new ImageView();
                imageView.setPreserveRatio(true);
                imageView.setImage(new Image(imgUrl));
                imageButton.setGraphic(imageView);
                imageButton.setMinHeight(300);
                imageButton.setMaxHeight(300);
                imageButton.setMinWidth(200);
                imageButton.setMaxWidth(200);
                imageButton.setOnAction(printMovie(AllMovies.get(i)));
                imageView.setFitWidth(200);
                imageView.setFitHeight(300);
                //imageButton.setOnAction();
                //imageView.setFitWidth(200);     //Make sure height and with
                //imageView.setFitHeight(300);    //stay in range for big covers
                gridPane.add(imageButton, i % 4, i / 4);
                GridPane.setMargin(imageButton, new Insets(20, 0, 0, 20)); // margin of the covers
            }
            // TODO: Create onMouseClick-Listener in this loop to make sure it persists, when library is done
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
    private EventHandler<ActionEvent> printMovie(Movies movie)
    {
        System.out.println(movie.toSting());
        return null;
    }
}