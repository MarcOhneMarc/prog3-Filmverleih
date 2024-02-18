package com.filmverleih.filmverleih;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

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

        // Test view of movie covers for development only
        String testImage = "C:\\Users\\Jonas\\Desktop\\shit\\src\\main\\resources\\com\\example\\shit\\testcover\\beemovie.png";
        for (int i = 0; i < 200; i++) {
            ImageView imageView = new ImageView();
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image(testImage));
            gridPane.add(imageView, i % 4, i / 4);
            GridPane.setMargin(imageView, new Insets(20, 0, 0, 20)); // margin of the covers
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
}