package hr.java.restaurant.model;

import hr.java.restaurant.generics.Describable;

public class DelivererDescription implements Describable<Deliverer> {
    @Override
    public String describe(Deliverer deliverer) {
        return "Deliverer: " + deliverer.getFirstName() + " " + deliverer.getLastName();
    }
}
