module hr.javafx.stupar10v {
    requires javafx.controls;
    requires javafx.fxml;


    opens hr.javafx.stupar10v to javafx.fxml;
    exports hr.javafx.stupar10v;
}