module hr.javafx.stupar7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;


    exports hr.javafx.main;
    opens hr.javafx.main to javafx.fxml;
    exports hr.javafx.controller;
    opens hr.javafx.controller to javafx.fxml;
}