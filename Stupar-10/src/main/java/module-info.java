module hr.javafx.stupar10 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.javafx.stupar10 to javafx.fxml;
    exports hr.javafx.stupar10;
}