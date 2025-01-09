module hr.javafx.stupar8 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.javafx.stupar8 to javafx.fxml;
    exports hr.javafx.stupar8;
}