package com.filmverleih.filmverleih;

import com.filmverleih.filmverleih.entity.Movies;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    //<editor-fold desc = "Attribute-Armageddon">
    //Attributes
    Movies movie;
    @FXML
    private AnchorPane ap_Movie;

    //Labels that need to be filled

    @FXML
    private Label lbl_name;
    @FXML
    private Label lbl_year;
    @FXML
    private HBox hbx_genre;
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


    private boolean isEditing = false;
    //</editor-fold>
    //<editor-fold desc = "PageCreation-Poolparty">
    /**
     * displays the Movie details in Center of application
     * @param movie the movie passed by the LibraryController via NWayControllerConnector
     */
    public void fillPage(Movies movie)
    {
        this.movie = movie;
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
        ImageView iv_cover = new ImageView();
        iv_cover.setFitHeight(450);
        iv_cover.setFitWidth(300);
        lbl_name.setText(name);
        lbl_year.setText(String.valueOf(year));
        fillHBox(hbx_genre,Utility.getLabelsFromString(genre));
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
        if (!cover.isBlank()||!cover.isEmpty()) {
            iv_cover.setImage(new Image(cover));
            lbl_cover.setGraphic(iv_cover);
        }
        else {
            iv_cover.setImage(new Image(Utility.getAbsolutePath("testcover/beemovie.png")));
            lbl_cover.setGraphic(iv_cover);
        }
    }
    /**
     * Makes sure, every Label filled by fillPage() gets an edit-icon next to it
     * and adds replace(e) logic to the icons
     */
    public void setEditable(){
        Image edit;
        edit = new Image(Utility.getAbsolutePath("icons/edit.png"));
        lbl_name.setOnMouseClicked(e -> replace(e, movie));;
        lbl_year.setOnMouseClicked(e -> replace(e,movie));
        lbl_length.setOnMouseClicked(e -> replace(e,movie));
        lbl_rating.setOnMouseClicked(e -> replace(e,movie));
        lbl_count.setOnMouseClicked(e -> replace(e,movie));
        lbl_type.setOnMouseClicked(e -> replace(e,movie));
        lbl_cover.setOnMouseClicked(e -> replace(e,movie));
        lbl_directors.setOnMouseClicked(e -> replace(e,movie));
        lbl_studio.setOnMouseClicked(e -> replace(e,movie));
        lbl_actors.setOnMouseClicked(e -> replace(e,movie));
        lbl_fsk.setOnMouseClicked(e -> replace(e,movie));
        lbl_comment.setOnMouseClicked(e -> replace(e,movie));
    }
    //</editor-fold>
    //<editor-fold desc = "Method-Mayhem">
    /**
     * replaces the edit button with an accapt and a cancel button, makes the corresponding label
     * a textfield to fill in whatever you want to change
     * @param event the Mouse Event that fired the method, needed to get the target
     *              NOTE: somehow, the UI sometimes does not register clickEvents, might be my slow-ass machine...
     */

    public void replace(MouseEvent event, Movies movie){
        if (isEditing) return;
        isEditing = true;
        Label source = (Label)event.getSource();
        String fxid = source.getId();
        String oldValue="";

        double[] sizes = Utility.getLabelSizes(source);
        AnchorPane anchorPane = switch (fxid){
            case "lbl_name" ->{
                oldValue = movie.getName();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_year" -> {
                oldValue = String.valueOf(movie.getYear());
                yield (AnchorPane)source.getParent();
            }
            case "lbl_fsk" ->{
                oldValue = String.valueOf(movie.getFsk());
                yield (AnchorPane)source.getParent();
            }
            case "lbl_length" -> {
                oldValue = String.valueOf(movie.getLength());
                yield (AnchorPane)source.getParent();
            }
            case "lbl_rating" -> {
                oldValue = String.valueOf(movie.getRating());
                yield (AnchorPane)source.getParent();
            }
            case "lbl_directors" -> {
                oldValue = movie.getDirectors();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_actors" -> {
                oldValue = movie.getActors();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_studio" -> {
                oldValue = movie.getStudio();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_cover" -> {
                oldValue = movie.getCover();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_count" -> {
                oldValue = String.valueOf(movie.getCount());
                yield (AnchorPane)source.getParent();
            }
            case "lbl_type" -> {
                oldValue = movie.getType();
                yield (AnchorPane)source.getParent();
            }
            case "lbl_comment" -> {
                oldValue = movie.getComment();
                yield (AnchorPane)source.getParent();
            }
            default -> {
                if (fxid.matches("lbl_genre\\d+")){
                    oldValue = movie.getGenre();
                    sizes[0] = 631;
                    sizes[1] = 37;
                    yield  (AnchorPane) hbx_genre.getParent();
                }
                else yield null;
            }
        };
        //anchorPane.getChildren().remove(source);
        anchorPane.getChildren().clear();
        TextField replacement = new TextField();
        Utility.setTextFieldSizes(replacement,sizes);
        replacement.setText(oldValue);
        anchorPane.getChildren().add(replacement);
        Label accept = new Label();
        ImageView iv_yes = new ImageView();
        iv_yes.setFitWidth(37);
        iv_yes.setFitHeight(37);
        iv_yes.setImage(new Image(Utility.getAbsolutePath("icons/yes.png")));
        accept.setGraphic(iv_yes);
        anchorPane.getChildren().add(accept);
        Utility.setRightAndTopAnchor(accept,42,0);
        Label cancel = new Label();
        ImageView iv_no = new ImageView();
        iv_no.setFitWidth(37);
        iv_no.setFitHeight(37);
        iv_no.setImage(new Image(Utility.getAbsolutePath("icons/no.png")));
        cancel.setGraphic(iv_no);
        String oldValueFinal = oldValue;
        cancel.setOnMouseClicked(e -> cancel(source,anchorPane));
        accept.setOnMouseClicked(e -> accept(source,anchorPane,replacement,oldValueFinal));
        anchorPane.getChildren().add(cancel);
        Utility.setRightAndTopAnchor(cancel,84,0);
    }

    public void cancel(Label source, AnchorPane parent){
        if (source.getId().matches("lbl_genre\\d+")){
            hbx_genre.getChildren().clear();
            parent.getChildren().clear();
            fillHBox(hbx_genre, Utility.getLabelsFromString(movie.getGenre()));
            parent.getChildren().add(hbx_genre);
        }
        else {
            parent.getChildren().clear();
            parent.getChildren().add(source);
            setEditable();
        }
        isEditing = false;
    }

    public void accept(Label source, AnchorPane parent, TextField replacement,String oldValue){
        String newValue = replacement.getText();
        if (newValue.equals(oldValue)) cancel(source,parent);
        else {
            if (source.getId().matches("lbl_genre\\d+")) {
                hbx_genre.getChildren().clear();
                parent.getChildren().clear();
                fillHBox(hbx_genre, Utility.getLabelsFromString(newValue));
                parent.getChildren().add(hbx_genre);

            }
            else{
                parent.getChildren().clear();
                source.setText(newValue);
                parent.getChildren().add(source);
            }
            Utility.UpdateMovieInDB(movie.getMovieid(),Utility.findColumnByFXID(source.getId()),newValue);
            connector.getLibraryController().refresh();
            isEditing = false;
        }
    }
    public void fillHBox(HBox hbox, String[] genres){
        hbox.getChildren().clear();
        Label temp;
        for (int i = 0; i < genres.length; i++){
            temp = new Label(genres[i]);
            temp.setId("lbl_genre"+i);
            temp.setOnMouseClicked(e -> replace(e,movie));
            hbox.getChildren().add(temp);
        }
    }

    //</editor-fold>
    //<editor-fold desc = "getter-setter-sweater">
    /**
     * @return passes the main frame if the scene to the Controller it is called from
     */
    public AnchorPane getOuterPane()
    {
        return ap_Movie;
    }
    //</editor-fold>
    // credits for the edit-icon : <a href="https://www.flaticon.com/free-icons/register" title="register icons">Register icons created by Irfansusanto20 - Flaticon</a>
}