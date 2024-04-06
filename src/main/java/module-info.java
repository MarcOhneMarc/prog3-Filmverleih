module com.filmverleih.filmverleih {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.hibernate.orm.core;
    requires java.sql;
    requires java.naming;
    requires jakarta.persistence;
    requires org.apache.pdfbox;
    requires java.desktop;
    requires org.apache.commons.validator;
    requires org.slf4j;
    requires org.apache.logging.log4j;


    opens com.filmverleih.filmverleih to javafx.fxml;
    exports com.filmverleih.filmverleih;
    exports com.filmverleih.filmverleih.entity;
    opens com.filmverleih.filmverleih.entity to javafx.fxml, org.hibernate.orm.core;
}