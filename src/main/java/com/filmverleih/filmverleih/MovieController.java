package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.List;

import static java.lang.String.valueOf;

public class MovieController {
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController, LoginController,EditMovieController,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,CartController,LoginController,EditMovieController,Integer> connector) {
      
        this.connector = connector;
    }

    //Attributes
    @FXML
    private BorderPane bpn_borderPaneMovieScene;
    @FXML
    private HBox hbx_movieCoverInfos;
    @FXML
    private ImageView igv_coverMovieScene;
    @FXML
    private Label lbl_nameMovieScene;
    @FXML
    private Label lbl_yearMovieScene;
    @FXML
    private Label lbl_fskMovieScene;
    @FXML
    private Label lbl_durationMovieScene;
    @FXML
    private Label lbl_directorsMovieScene;
    @FXML
    private Label lbl_actorsMovieScene;
    @FXML
    private Label lbl_ratingMovieScene;
    @FXML
    private HBox hbx_genreMovieScene;
    @FXML
    private TextArea txa_commentMovieScene;
    @FXML
    private StackPane stp_addToCartInfoAndButton;
    @FXML
    private Label lbl_idValueMovieScene;
    @FXML
    private Label lbl_countValueMovieScene;
    @FXML
    private Label lbl_feedbackMessageMovieScene;
    @FXML
    private Button btn_addToCartMovieScene;
    @FXML
    private ImageView igv_editMovieMovieScene;

    private Movies movie;

    /**
     * displays the Movie details in Center of application
     * @param movie the movie passed by the LibraryController via NWayControllerConnector
     */
    public void fillPage(Movies movie)
    {
        this.movie = movie;
        String linkToCover = movie.getCover();
        clearFeedbackMessage();
        enableEditAndCartButtons();

        if (MovieEntryValidator.linkToCoverIsValid(linkToCover) && !linkToCover.isEmpty()) {
            igv_coverMovieScene.setImage(new Image(linkToCover));
        } else {
            igv_coverMovieScene.setImage(new Image(getClass().getResourceAsStream("icons/DefaultCover.png")));
        }

        lbl_nameMovieScene.setText(movie.getName());
        lbl_nameMovieScene.setStyle("-fx-font-size: " + (100 - movie.getName().length()));
        if (movie.getName().length() > 40) {
            int breakIndex = movie.getName().lastIndexOf(" ", 40);
            if (breakIndex != -1) {
                lbl_nameMovieScene.setText(movie.getName().substring(0, breakIndex) + "\n" + movie.getName().substring(breakIndex + 1));
            } else {
                lbl_nameMovieScene.setText(movie.getName().substring(0, 40) + "\n" + movie.getName().substring(40));
            }
        }
        lbl_yearMovieScene.setText(valueOf(movie.getYear()));
        lbl_fskMovieScene.setText("FSK " + valueOf(movie.getFsk()));
        lbl_durationMovieScene.setText(valueOf(movie.getLength()) + " min");
        lbl_directorsMovieScene.setText(movie.getDirectors());
        lbl_actorsMovieScene.setText(movie.getActors());

        hbx_genreMovieScene.getChildren().clear();
        lbl_ratingMovieScene.setText("☆" + movie.getRating() + "/10");

        List<String> genreList = List.of(movie.getGenre().split(", "));

        for(String genre : genreList) {
            Label genreLabel = new Label();
            genreLabel.setText(genre);
            genreLabel.getStyleClass().addAll("lbl_class_genreMovieScene", "lbl_class_movieSceneLabels");
            genreLabel.setPadding(new Insets(0,2,0,2));

            hbx_genreMovieScene.getChildren().add(genreLabel);
        }

        txa_commentMovieScene.setText(movie.getComment());
        txa_commentMovieScene.setWrapText(true);
        lbl_idValueMovieScene.setText(valueOf(movie.getMovieid()));
        lbl_countValueMovieScene.setText(valueOf(movie.getCount()));

        //if (!cover.isBlank()||!cover.isEmpty()) ivw_Cover.setImage(new Image(cover));
        //else ivw_Cover.setImage(new Image("file:com/filmverleih/filmverleih/icons/profil.png"));
    }

    /**
     * This method sets feedback message with specified style.
     *
     * @param message The message to be displayed
     * @param style The style to be applied to the message
     */
    public void feedbackMessage(String message, String style) {
        lbl_feedbackMessageMovieScene.setVisible(true);
        lbl_feedbackMessageMovieScene.setText(message);
        lbl_feedbackMessageMovieScene.setStyle(style);
    }

    /**
     * This method clears the feedback message.
     */
    public void clearFeedbackMessage(){
        lbl_feedbackMessageMovieScene.setVisible(false);
    }

    /**
     * This method disables edit and cart buttons.
     */
    public void disableEditAndCartButtons() {
        btn_addToCartMovieScene.setDisable(true);
        igv_editMovieMovieScene.setDisable(true);
    }

    /**
     * This method enables edit and cart buttons.
     */
    public void enableEditAndCartButtons() {
        btn_addToCartMovieScene.setDisable(false);
        igv_editMovieMovieScene.setDisable(false);
    }

    /**
     * This method sets the center of the main application border pane to the edit movie view,
     * initializes the edit movie controller with the given movie, and displays the edit interface.
     */
    public void changeToEdit() {
        bpn_borderPaneMovieScene.setDisable(false);
        EditMovieController editMovieController = connector.getEditMovieController();
        MainApplication.borderPane.setCenter(editMovieController.getOuterPane());
        editMovieController.initialize(movie);
    }

    /**
     * This method transfers the movie to the CartController where it is added to cart
     */
    public void transferMovieToCart() throws IOException {
        connector.getCartController().addMovieToCart(movie);
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public BorderPane getOuterPane()
    {
        return bpn_borderPaneMovieScene;
    }
}