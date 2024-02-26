package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.math.BigDecimal;

public class MovieController {
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }

    //Attributes
    @FXML
    private ImageView ivw_Cover;
    @FXML
    private TextArea ta_comment;
    @FXML
    private SplitPane sp_Movie;

    //Labels that need to be filled
    @FXML
    private Label lbl_id;
    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_year;
    @FXML
    private Label lbl_genre;
    @FXML
    private Label lbl_length;
    @FXML
    private Label lbl_rating;
    @FXML
    private Label lbl_count;
    @FXML
    private Label lbl_type;
    @FXML
    private Label lbl_cover;
    @FXML
    private Label lbl_directors;
    @FXML
    private Label lbl_studio;
    @FXML
    private Label lbl_actors;
    @FXML
    private Label lbl_fsk;

    /**
     * displays the Movie details in Center of application
     * @param movie the movie passed by the LibraryController via NWayControllerConnector
     */
    public void fillPage(Movies movie)
    {
        int id = movie.getMovieid();
        String name = movie.getName();
        int year = movie.getYear();
        String genre = movie.getGenre();
        int length = movie.getLength();
        BigDecimal rating = movie.getRating();
        int count = movie.getCount();
        String type = movie.getType();
        String cover = movie.getCover();
        String directors = movie.getDirectors();
        String studio = movie.getStudio();
        String actors = movie.getActors();
        String comment = movie.getComment();
        int fsk = movie.getFsk();
        lbl_id.setText(String.valueOf(id));
        lbl_name.setText(name);
        lbl_year.setText(String.valueOf(year));
        lbl_genre.setText(genre);
        lbl_length.setText(String.valueOf(length));
        lbl_rating.setText(String.valueOf(rating));
        lbl_count.setText(String.valueOf(count));
        lbl_type.setText(type);
        lbl_cover.setText(cover);
        lbl_directors.setText(directors);
        lbl_studio.setText(studio);
        lbl_actors.setText(actors);
        lbl_fsk.setText(String.valueOf(fsk));
        ta_comment.setText(comment);
        if (!cover.isBlank()||!cover.isEmpty()) ivw_Cover.setImage(new Image(cover));
        else ivw_Cover.setImage(new Image("file:com/filmverleih/filmverleih/icons/profil.png"));
    }

    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public SplitPane getOuterPane()
    {
        return sp_Movie;
    }
}