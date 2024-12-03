package hr.java.restaurant.model;

import hr.java.restaurant.generics.Describable;

public class DelivererDescription implements Describable<Deliverer> {
    @Override
    public String describe(Deliverer deliverer) {
        return "\nDeliverer: " + deliverer.getFirstName() + " " + deliverer.getLastName() +
                "\nID: " + deliverer.getId() +
                "\nSalary: " + deliverer.getContract().getSalary() +
                "\nContract Start Date: " + deliverer.getContract().getStartTime() +
                "\nContract End Date: " + deliverer.getContract().getEndTime() +
                "\nContract Type: " + deliverer.getContract().getContractType();
    }
}
