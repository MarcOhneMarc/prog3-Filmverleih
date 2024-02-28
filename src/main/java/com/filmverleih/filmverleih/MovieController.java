package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.input.MouseEvent;
import java.math.BigDecimal;
import java.net.URISyntaxException;

public class MovieController {
    NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector;
    /**
     * sets NWayControllerConnector as active connector for this controller, called from MainApplication
     * @param connector the controller passed by MainApplication
     */
    public void setConnector(NWayControllerConnector<NavbarController,LibraryController,MovieController,RentalController,SettingsController,FilterController,Integer, Integer,Integer,Integer> connector) {
        this.connector = connector;
    }
    //<editor-fold desc = "Attribute-Armageddon">
    //Attributes
    @FXML
    private ImageView ivw_Cover;
    @FXML
    private SplitPane sp_Movie;
    @FXML
    private GridPane gp_Table;
    @FXML
    private AnchorPane ap_rightFrame;

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
    @FXML
    private Label lbl_comment;
    @FXML
    private ImageView iv_name;
    @FXML
    private ImageView iv_year;
    @FXML
    private ImageView iv_genre;
    @FXML
    private ImageView iv_length;
    @FXML
    private ImageView iv_rating;
    @FXML
    private ImageView iv_count;
    @FXML
    private ImageView iv_type;
    @FXML
    private ImageView iv_cover;
    @FXML
    private ImageView iv_directors;
    @FXML
    private ImageView iv_studio;
    @FXML
    private ImageView iv_actors;
    @FXML
    private ImageView iv_fsk;
    @FXML
    private ImageView iv_comment;
    private boolean isEditing = false;
    //</editor-fold>
    //<editor-fold desc = "PageCreation-Poolparty">
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
        lbl_comment.setText(comment);
        if (!cover.isBlank()||!cover.isEmpty()) ivw_Cover.setImage(new Image(cover));
        else {
            ivw_Cover.setImage(new Image(Utility.getAbsolutePath("testcover/beemovie.png")));
        }
    }
    /**
     * Makes sure, every Label filled by fillPage() gets an edit-icon next to it
     * and adds replace(e) logic to the icons
     */
    public void setEditImage(){
        Image edit;
        edit = new Image(Utility.getAbsolutePath("icons/edit.png"));
        iv_name.setImage(edit);
        iv_name.setOnMouseClicked(e -> replace(e));
        iv_year.setImage(edit);
        iv_year.setOnMouseClicked(e -> replace(e));
        iv_genre.setImage(edit);
        iv_genre.setOnMouseClicked(e -> replace(e));
        iv_length.setImage(edit);
        iv_length.setOnMouseClicked(e -> replace(e));
        iv_rating.setImage(edit);
        iv_rating.setOnMouseClicked(e -> replace(e));
        iv_count.setImage(edit);
        iv_count.setOnMouseClicked(e -> replace(e));
        iv_type.setImage(edit);
        iv_type.setOnMouseClicked(e -> replace(e));
        iv_cover.setImage(edit);
        iv_cover.setOnMouseClicked(e -> replace(e));
        iv_directors.setImage(edit);
        iv_directors.setOnMouseClicked(e -> replace(e));
        iv_studio.setImage(edit);
        iv_studio.setOnMouseClicked(e -> replace(e));
        iv_actors.setImage(edit);
        iv_actors.setOnMouseClicked(e -> replace(e));
        iv_fsk.setImage(edit);
        iv_fsk.setOnMouseClicked(e -> replace(e));
        iv_comment.setImage(edit);
        iv_comment.setOnMouseClicked(e -> editComment());

    }
    //</editor-fold>
    //<editor-fold desc = "Method-Mayhem">
    /**
     * replaces the edit button with an accapt and a cancel button, makes the corresponding label
     * a textfield to fill in whatever you want to change
     * @param event the Mouse Event that fired the method, needed to get the target
     *              NOTE: somehow, the UI sometimes does not register clickEvents, might be my slow-ass machine...
     */
    public void replace(MouseEvent event){
        if (isEditing) return;
        ImageView target = (ImageView)event.getTarget();
        String id = target.getId();
        String text = "";
        int col = 1;
        Label lbl = new Label();
        int row = switch (id) {
            case "iv_name" -> {
                text = lbl_name.getText();
                lbl = lbl_name;
                yield 1;
            }
            case "iv_year" -> {
                text = lbl_year.getText();
                lbl = lbl_year;
                yield 2;
            }
            case "iv_genre" -> {
                text = lbl_genre.getText();
                lbl = lbl_genre;
                yield 3;
            }
            case "iv_length" -> {
                text = lbl_length.getText();
                lbl = lbl_length;
                yield 4;
            }
            case "iv_rating" -> {
                text = lbl_rating.getText();
                lbl = lbl_rating;
                yield 5;
            }
            case "iv_count" -> {
                text = lbl_count.getText();
                lbl = lbl_count;
                yield 6;
            }
            case "iv_type" -> {
                text = lbl_type.getText();
                lbl = lbl_type;
                yield 7;
            }
            case "iv_cover" -> {
                text = lbl_cover.getText();
                lbl = lbl_cover;
                yield 8;
            }
            case "iv_directors" -> {
                text = lbl_cover.getText();
                lbl = lbl_directors;
                yield 9;
            }
            case "iv_studio" -> {
                text = lbl_studio.getText();
                lbl = lbl_studio;
                yield 10;
            }
            case "iv_actors" -> {
                text = lbl_actors.getText();
                lbl = lbl_actors;
                yield 11;
            }
            case "iv_fsk" -> {
                text = lbl_fsk.getText();
                lbl = lbl_fsk;
                yield 12;
            }
            default ->{
                yield 0;
            }
        };
        TextField edit = new TextField(text);
        gp_Table.getChildren().remove(lbl);
        gp_Table.add(edit,col,row);
        gp_Table.getChildren().remove(target);
        ImageView iv_yes = new ImageView(new Image(Utility.getAbsolutePath("icons/yes.png")));
        iv_yes.setFitHeight(20);
        iv_yes.setFitWidth(20);
        ImageView iv_no = new ImageView(new Image(Utility.getAbsolutePath("icons/no.png")));
        iv_no.setFitHeight(20);
        iv_no.setFitWidth(20);
        Label lblFinal = lbl;
        String textFinal = text;
        gp_Table.add(iv_yes,1,row);
        GridPane.setHalignment(iv_yes, HPos.RIGHT);
        gp_Table.getChildren().remove(target);
        gp_Table.add(iv_no,2,row);
        isEditing = true;
        iv_yes.setOnMouseClicked(e -> accept(lblFinal,col,row,iv_no,iv_yes,target,edit,edit.getText(),textFinal));
        iv_no.setOnMouseClicked(e->cancel(lblFinal,col,row,iv_no,iv_yes,target,edit));
    }

    /**
     * cancels a previous edit event
     * TODO: try to reduce the code a bit
     * @param lbl the original label, that was replaced
     * @param col the column the original label was in
     * @param row the row the label was in (always 1 in this case)
     * @param iv_no the cancel-icon
     * @param iv_yes the accept icon
     * @param iv_prev the previous edit-icon
     * @param edit the edit field that was created
     *             NOTE: while those fields are not necessarily needed, they make code more readable
     *             NOTE: somehow, the UI sometimes does not register clickEvents, might be my slow-ass machine...
     */
    public void cancel(Label lbl, int col, int row, ImageView iv_no, ImageView iv_yes, ImageView iv_prev, TextField edit){
        gp_Table.getChildren().remove(iv_yes);
        gp_Table.getChildren().remove(iv_no);
        gp_Table.getChildren().remove(edit);
        gp_Table.add(lbl, col,row);
        gp_Table.add(iv_prev,col+1,row);
        setEditImage();
        isEditing = false;
    }

    /**
     * accepts a change made in an edit event
     * TODO: check if text is the same, fire cancel then
     * @param lbl the original label, that was replaced
     * @param col the column the original label was in
     * @param row the row the label was in (always 1 in this case)
     * @param iv_no the cancel-icon
     * @param iv_yes the accept icon
     * @param iv_prev the previous edit-icon
     * @param edit the edit field that was created
     * @param newValue the new value taken from the TextField of the edit-event
     *                 NOTE: UpdateMovieInDB not yet modified/tested, handle with caution!
     */
    public void accept(Label lbl, int col, int row, ImageView iv_no, ImageView iv_yes, ImageView iv_prev, TextField edit, String newValue, String oldValue){
        if (!newValue.equals(oldValue)) {
            gp_Table.getChildren().remove(iv_yes);
            gp_Table.getChildren().remove(iv_no);
            gp_Table.getChildren().remove(edit);
            lbl.setText(newValue);
            gp_Table.add(lbl, col, row);
            gp_Table.add(iv_prev, col + 1, row);
            Utility.UpdateMovieInDB(Integer.parseInt(lbl_id.getText()), Utility.findColumnByRow(row), newValue);
            connector.getLibraryController().refresh();
            isEditing = false;
        }
        else cancel(lbl,col,row,iv_no,iv_yes,iv_prev,edit);
    }
    private void editComment(){
        if (isEditing)return;
        isEditing=true;
        String oldValue = lbl_comment.getText();
        ap_rightFrame.getChildren().remove(lbl_comment);
        TextArea edit = new TextArea(oldValue);
        edit.setMinSize(200,150);
        edit.setMaxSize(200,150);
        ap_rightFrame.getChildren().add(edit);
        AnchorPane.setBottomAnchor(edit, 0.0);
        AnchorPane.setLeftAnchor(edit,0.0);
        AnchorPane.setRightAnchor(edit, 0.0);
        ap_rightFrame.getChildren().remove(iv_comment);
        Label accept = new Label();
        ImageView iv_yes = new ImageView();
        iv_yes.setFitWidth(20);
        iv_yes.setFitHeight(20);
        iv_yes.setImage(new Image(Utility.getAbsolutePath("icons/yes.png")));
        accept.setGraphic(iv_yes);
        Label cancel = new Label();
        ImageView iv_no = new ImageView();
        iv_no.setFitHeight(20);
        iv_no.setFitWidth(20);
        iv_no.setImage(new Image(Utility.getAbsolutePath("icons/no.png")));
        cancel.setGraphic(iv_no);
        ap_rightFrame.getChildren().add(accept);
        ap_rightFrame.getChildren().add(cancel);
        AnchorPane.setRightAnchor(cancel,0.0);
        AnchorPane.setBottomAnchor(cancel,150.0);
        AnchorPane.setRightAnchor(accept,20.0);
        AnchorPane.setBottomAnchor(accept,150.0);
        cancel.setOnMouseClicked(e -> cancelComment(accept,cancel,edit));
        accept.setOnMouseClicked(e -> acceptComment(accept,cancel,edit,oldValue));
    }

    private void cancelComment(Label accept, Label cancel,TextArea edit){
        ap_rightFrame.getChildren().remove(accept);
        ap_rightFrame.getChildren().remove(cancel);
        ap_rightFrame.getChildren().remove(edit);
        ap_rightFrame.getChildren().add(lbl_comment);
        ap_rightFrame.getChildren().add(iv_comment);
        isEditing = false;
    }
    private void acceptComment(Label accept, Label cancel,TextArea edit, String oldValue){
        String newValue = edit.getText();
        if (newValue.equals(oldValue)) cancelComment(accept,cancel,edit);
        else {
            ap_rightFrame.getChildren().remove(accept);
            ap_rightFrame.getChildren().remove(cancel);
            ap_rightFrame.getChildren().remove(edit);
            ap_rightFrame.getChildren().add(lbl_comment);
            ap_rightFrame.getChildren().add(iv_comment);
            lbl_comment.setText(newValue);
            Utility.UpdateMovieInDB(Integer.parseInt(lbl_id.getText()),Utility.findColumnByRow(0),newValue);
            connector.getLibraryController().refresh();
            isEditing = false;
        }
    }
    //</editor-fold>
    //<editor-fold desc = "getter-setter-sweater">
    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public SplitPane getOuterPane()
    {
        return sp_Movie;
    }
    //</editor-fold>
    // credits for the edit-icon : <a href="https://www.flaticon.com/free-icons/register" title="register icons">Register icons created by Irfansusanto20 - Flaticon</a>
}