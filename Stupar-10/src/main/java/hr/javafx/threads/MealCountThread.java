package hr.javafx.threads;

import hr.javafx.restaurant.model.Meal;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;

import java.math.BigDecimal;

public class MealCountThread implements Runnable{
    private ObservableList<Meal> mealList;
    private Label mealCountLabel;

    public MealCountThread(ObservableList<Meal> mealList, Label mealCountLabel) {
        this.mealList = mealList;
        this.mealCountLabel = mealCountLabel;
    }

    @Override
    public void run() {
        long count = mealList.stream()
                .filter(meal -> meal.getPrice().compareTo(new BigDecimal("9.99")) > 0)
                .count();

        Platform.runLater(() -> {
            mealCountLabel.setText("Number of Meals with Price > 10: " + count);
        });

    }
}
