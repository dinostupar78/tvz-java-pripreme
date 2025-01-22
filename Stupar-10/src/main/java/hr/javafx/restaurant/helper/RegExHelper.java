package hr.javafx.restaurant.helper;

public class RegExHelper {
    public static void main(String[] args) {
        if("-10.0".matches("^[0-9]{1,12}(?:\\.[0-9]{1,4})?$")) {
            System.out.println("TRUE");
        }
        else {
            System.out.println("FALSE");
        }
    }
}
