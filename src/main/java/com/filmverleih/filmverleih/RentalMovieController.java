package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Customers;
import com.filmverleih.filmverleih.entity.Movies;
import com.filmverleih.filmverleih.entity.Rentals;
import com.filmverleih.filmverleih.utilitys.CustomersUtility;
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
    private Rentals rentals;
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
    public void insertMovieInfo(Rentals rentals) {
        this.movie = rentals.getMovie();
        this.rentals = rentals;
        String imageUrl = rentals.getMovie().getCover();
        if(!imageUrl.isEmpty()) {

            imv_rentalMovieCover.setImage(new Image(rentals.getMovie().getCover()));
            imv_rentalMovieCover.setPreserveRatio(false);
            imv_rentalMovieCover.setFitWidth(200);
            imv_rentalMovieCover.setFitHeight(300);

        }
        lbl_rentalMovieTitle.setText(rentals.getMovie().getName());
        lbl_rentalMovieLength.setText(valueOf(rentals.getMovie().getLength()) + " min");
        lbl_rentalMovieYear.setText(valueOf(rentals.getMovie().getYear()));
    }



    /**
     * This method gets the customer information for the rental and sets them to the according label
     * /TODO actually get customer info from db as soon as db is updated
     */
    public void insertCustomerInfo() {
        Customers customer = CustomersUtility.getCustomerById(rentals.getCustomerid());
        lbl_rentalMovieCustomer.setText(customer.getFirstname() + " " + customer.getLastname());
        lbl_rentalMovieDate.setText(rentals.getStartdate());
        lbl_rentalMovieReturnDate.setText(rentals.getEnddate());
        lbl_rentalMovieStatus.setText("verliehen");
    }

    /**
     * This method removes the rental card from the rental view
     * TODO remove/return rented movie from db
     */
    public void removeFromRental() {
        System.out.println("console test: return button has been clicked");

        if (rentalController != null) {
            rentalController.removeFromRental(hbx_rentalMovieCard, rentals);
        }
    }

    /**
     * This method extends the movie rental
     * TODO implement an extension of the return date in db
     */
    public void extendRental() {
        System.out.println("console test: extend button has been clicked");
        if (rentalController != null) {
            rentalController.extendRental(hbx_rentalMovieCard, rentals);
        }
    }

    /**
     * This method reminds the customer of his rental
     * TODO find a way to actually remind the customer
     */
    public void remindCustomerOfRental() {
        System.out.println("console test: remind button has been clicked");
    }


}