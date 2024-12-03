package hr.java.restaurant.generics;

import java.util.List;

public class ListPrinter {
    public static <T> void printList(List<T> items) {
        for (T item : items) {
            System.out.println(item);
        }
    }
}
