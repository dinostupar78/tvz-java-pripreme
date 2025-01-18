module hr.javafx.stupar9 {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.javafx.stupar9 to javafx.fxml;
    exports hr.javafx.stupar9;
}