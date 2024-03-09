package com.filmverleih.filmverleih;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.filmverleih.filmverleih.entity.Movies;
import javafx.scene.layout.HBox;

import static java.lang.String.valueOf;

public class CartMovieController {

    @FXML
    private HBox hbx_CartMovie;
    @FXML
    private Label lbl_CartMovieTitle;
    @FXML
    private HBox hbx_CartMovieRatingDuration;
    @FXML
    private Label lbl_CartMovieRating;
    @FXML
    private Label lbl_CartMovieDuration;
    @FXML
    private Label lbl_CartMovieYear;
    @FXML
    private Button btn_CartDeleteMovie;


    /**
     * This method gets the movie information for the provided movie object and sets them to the according label
     */
    public void insertMovieInfo(Movies movie) {
        lbl_CartMovieTitle.setText(movie.getName());
        lbl_CartMovieRating.setText(valueOf(movie.getRating() + "/10 ☆"));
        lbl_CartMovieDuration.setText(valueOf(movie.getLength()));
        lbl_CartMovieYear.setText(valueOf(movie.getYear()));
    }

    /**
     * This method deletes the movie from cart
     */
    public void deleteFromCart() {
        System.out.println("console test: delete button has been clicked");
    }
}
