module org.example.datasensefx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires jbcrypt;
    requires java.sql;
    requires com.zaxxer.hikari;

    opens org.example.datasensefx to javafx.fxml;
    opens org.example.datasensefx.controllers to javafx.fxml;
    exports org.example.datasensefx;
    opens org.example.datasensefx.utils to javafx.fxml;
}