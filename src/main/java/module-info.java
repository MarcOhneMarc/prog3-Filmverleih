module com.filmverleih.filmverleih {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.filmverleih.filmverleih to javafx.fxml;
    exports com.filmverleih.filmverleih;
}