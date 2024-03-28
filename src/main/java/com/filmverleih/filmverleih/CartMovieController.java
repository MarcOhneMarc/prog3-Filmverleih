package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.utilitys.LoggerUtility;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import com.filmverleih.filmverleih.entity.Movies;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static java.lang.String.valueOf;

/**
 * controller class for the movie cards providing an option to
 * remove a certain movie from the cart and displaying some
 * movie information
 *
 * @author Jannis, Hannes
 */
public class CartMovieController {

    private CartController cartController;
    private Movies movie;

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
    @FXML
    private ImageView igv_CartMovieImage;
    @FXML
    private Label lbl_CartMovieCardFsk;


    public void setCartController(CartController cartController) {
        this.cartController = cartController;
    }


    /**
     * This method gets the movie information for the provided movie object and sets them to the according label
     */
    public void insertMovieInfo(Movies movie) {
        this.movie = movie;
        String imageUrl = movie.getCover();
        if(!imageUrl.isEmpty()) {
            igv_CartMovieImage.setImage(new Image(movie.getCover()));
        }
        lbl_CartMovieTitle.setText(movie.getName());
        lbl_CartMovieRating.setText(movie.getRating() + "/10 ☆");
        lbl_CartMovieDuration.setText(movie.getLength() + " min");
        lbl_CartMovieYear.setText(valueOf(movie.getYear()));
        lbl_CartMovieCardFsk.setText("FSK " + movie.getFsk());
    }

    /**
     * This method removes the movie from cart
     */
    public void removeFromCart() {
        LoggerUtility.logger.info("movie has been removed from cart: 016");

        if (cartController != null) {
            cartController.removeMovieCard(hbx_CartMovie, movie);
        }
    }
}
