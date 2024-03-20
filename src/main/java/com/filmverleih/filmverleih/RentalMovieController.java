package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import static java.lang.String.valueOf;

/**
 * controller class for the rental cards providing an option to
 * remove a certain card from the rental view and displaying some
 * movie and rental information
 *
 * @author Luka, Hannes
 */
public class RentalMovieController {

    private RentalController rentalController;
    private Movies movie;

    @FXML
    private HBox hbx_rentalMovieCard;

    @FXML
    private ImageView imv_rentalMovieCover;
    @FXML
    private Label lbl_rentalMovieTitle;
    @FXML
    private Label lbl_rentalMovieLength;
    @FXML
    private Label lbl_rentalMovieYear;
    @FXML
    private Label lbl_rentalMovieCustomer;
    @FXML
    private Label lbl_rentalMovieCustomerNr;
    @FXML
    private Label lbl_rentalMovieDate;
    @FXML
    private Label lbl_rentalMovieReturnDate;
    @FXML
    private Label lbl_rentalMovieStatus;

    @FXML
    private Button btn_rentalMovieRemind;
    @FXML
    private Button btn_rentalMovieExtend;
    @FXML
    private Button btn_rentalMovieReturn;


    public void setRentalController(RentalController rentalController) {
        this.rentalController = rentalController;
    }



    /**
     * This method gets the movie information for the provided movie object and sets them to the according label
     */
    public void insertMovieInfo(Movies movie) {
        this.movie = movie;
        String imageUrl = movie.getCover();
        if(!imageUrl.isEmpty()) {

            imv_rentalMovieCover.setImage(new Image(movie.getCover()));
            imv_rentalMovieCover.setPreserveRatio(false);
            imv_rentalMovieCover.setFitWidth(200);
            imv_rentalMovieCover.setFitHeight(300);

        }
        lbl_rentalMovieTitle.setText(movie.getName());
        lbl_rentalMovieLength.setText(valueOf(movie.getLength()));
        lbl_rentalMovieYear.setText(valueOf(movie.getYear()));
    }

    /**
     * This method gets the customer information for the rental and sets them to the according label
     * /TODO actually get customer info from db as soon as db is updated
     */
    public void insertCustomerInfo() {
        lbl_rentalMovieCustomer.setText("max");
        lbl_rentalMovieDate.setText("heute");
        lbl_rentalMovieReturnDate.setText("morgen");
        lbl_rentalMovieStatus.setText("verliehen");
    }

    /**
     * This method removes the rental card from the rental view
     * TODO remove/return rented movie from db
     */
    public void removeFromRental() {
        System.out.println("console test: return button has been clicked");

        if (rentalController != null) {
            rentalController.removeFromRental(hbx_rentalMovieCard, movie);
        }
    }

    /**
     * This method extends the movie rental
     * TODO implement an extension of the return date in db
     */
    public void extendRental() {
        System.out.println("console test: extend button has been clicked");
    }

    /**
     * This method reminds the customer of his rental
     * TODO find a way to actually remind the customer
     */
    public void remindCustomerOfRental() {
        System.out.println("console test: remind button has been clicked");
    }


}
