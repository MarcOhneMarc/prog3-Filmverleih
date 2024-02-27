package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
            try{
                ivw_Cover.setImage(new Image(Utility.getAbsolutePath("testcover/beemovie.png")));
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
    }
    /**
     * Makes sure, every Label filled by fillPage() gets an edit-icon next to it
     * and adds replace(e) logic to the icons
     */
    public void setEditImage(){
        Image edit;
        try {
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
            /* must be handled seperatly due to not being in the GridPane
            iv_comment.setOnMouseClicked(e -> replace(e));
            */
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
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
        ImageView iv_yes = new ImageView();
        ImageView iv_no = new ImageView();
        String id = target.getId();
        String text = "";
        Integer row = 0;
        Integer col = 1;
        Label lbl = new Label();
        switch(id){
            case "iv_name":
                text = lbl_name.getText();
                row = 1;
                lbl = lbl_name;
                break;
            case "iv_year":
                text = lbl_year.getText();
                row = 2;
                lbl = lbl_year;
                break;
            case "iv_genre":
                text = lbl_genre.getText();
                row = 3;
                lbl = lbl_genre;
                break;
            case "iv_length":
                text = lbl_length.getText();
                row = 4;
                lbl = lbl_length;
                break;
            case "iv_rating":
                text = lbl_rating.getText();
                row = 5;
                lbl = lbl_rating;
                break;
            case "iv_count":
                text = lbl_count.getText();
                row = 6;
                lbl = lbl_count;
                break;
            case "iv_type":
                text = lbl_type.getText();
                row = 7;
                lbl = lbl_type;
                break;
            case "iv_cover":
                text = lbl_cover.getText();
                row = 8;
                lbl = lbl_cover;
                break;
            case "iv_directors":
                text = lbl_cover.getText();
                row = 9;
                lbl = lbl_directors;
                break;
            case "iv_studio":
                text = lbl_studio.getText();
                row = 10;
                lbl = lbl_studio;
                break;
            case "iv_actors":
                text = lbl_actors.getText();
                row = 11;
                lbl = lbl_actors;
                break;
            case "iv_fsk":
                text = lbl_fsk.getText();
                row = 12;
                lbl = lbl_fsk;
                break;
            case "iv_comment":
                text = lbl_comment.getText();
                row = 0;
                lbl = lbl_comment;
                break;
        }
        TextField edit = new TextField(text);
        gp_Table.getChildren().remove(lbl);
        gp_Table.add(edit,col,row);
        gp_Table.getChildren().remove(target);
        try {
            iv_yes = new ImageView(new Image(Utility.getAbsolutePath("icons/yes.png")));
            iv_yes.setFitHeight(20);
            iv_yes.setFitWidth(20);
            iv_no = new ImageView(new Image(Utility.getAbsolutePath("icons/no.png")));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        iv_no.setFitHeight(20);
        iv_no.setFitWidth(20);
        ImageView iv_noFinal = iv_no;
        ImageView iv_yesFinal = iv_yes;
        int finalRow = row;
        Label lblFinal = lbl;
        ImageView targetFinal = target;
        String textFinal = text;
        gp_Table.add(iv_yesFinal,1,row);
        GridPane.setHalignment(iv_yesFinal, HPos.RIGHT);
        gp_Table.getChildren().remove(target);
        gp_Table.add(iv_noFinal,2,row);
        isEditing = true;
        iv_yesFinal.setOnMouseClicked(e -> accept(lblFinal,col,finalRow,iv_noFinal,iv_yesFinal,target,edit,edit.getText(),textFinal));
        iv_noFinal.setOnMouseClicked(e->cancel(lblFinal,col,finalRow,iv_noFinal,iv_yesFinal,target,edit));
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
    public void accept(Label lbl, int col, int row, ImageView iv_no, ImageView iv_yes, ImageView iv_prev, TextField edit, String newValue, String oldValue){System.out.println(newValue+oldValue);
        if (!newValue.equals(oldValue)) {
            gp_Table.getChildren().remove(iv_yes);
            gp_Table.getChildren().remove(iv_no);
            gp_Table.getChildren().remove(edit);
            lbl.setText(newValue);
            gp_Table.add(lbl, col, row);
            gp_Table.add(iv_prev, col + 1, row);
            Utility.UpdateMovieInDB(Integer.parseInt(lbl_id.getText()), Utility.findColumnByRow(row), newValue);
            isEditing = false;
        }
        else cancel(lbl,col,row,iv_no,iv_yes,iv_prev,edit);
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