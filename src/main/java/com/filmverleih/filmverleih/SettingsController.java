package com.filmverleih.filmverleih;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class SettingsController {

    @FXML
    private TextField txf_movieID;
    @FXML
    private TextField txf_movieName;
    @FXML
    private TextField txf_movieYear;
    @FXML
    private TextField txf_movieLength;
    @FXML
    private TextField txf_movieFSK;
    @FXML
    private TextField txf_movieRating;
    @FXML
    private TextField txf_movieGenre1;
    @FXML
    private TextField txf_movieGenre2;
    @FXML
    private TextField txf_movieGenre3;
    @FXML
    private TextField txf_movieDirector1;
    @FXML
    private TextField txf_movieDirector2;
    @FXML
    private TextField txf_movieDirector3;
    @FXML
    private TextField txf_movieCount;
    @FXML
    private TextField txf_movieUndefined1;
    @FXML
    private TextField txf_movieUndefined2;
    @FXML
    private TextField txf_movieUndefined3;
    @FXML
    private TextField txf_movieUndefined4;
    @FXML
    private TextField txf_movieUndefined5;
    @FXML
    private TextField txf_movieLinkToCover;
    @FXML
    private TextArea txa_movieComment;
    @FXML
    private CheckBox cbx_selDVD;
    @FXML
    private CheckBox cbx_selBlueRay;


    int movieID;
    String movieName;
    int movieYear;
    int movieLength;
    int movieFSK;
    double movieRating;
    String movieGenre1;
    String movieGenre2;
    String movieGenre3;
    String movieDirector1;
    String movieDirector2;
    String movieDirector3;
    int movieCount;
    String movieUndefined1;
    String movieUndefined2;
    String movieUndefined3;
    String movieUndefined4;
    String movieUndefined5;
    String movieLinkToCover;
    String movieComment;
    boolean dvd;
    boolean blueRay;



    @FXML
    public void addMovie(ActionEvent event) {
        System.out.println("console test: add movie button was clicked");
    }


    @FXML
    public void deleteMovie() {
        System.out.println("console test: delete movie button was clicked");
    }


}