package hr.java.restaurant.generics;

import hr.java.restaurant.model.Deliverer;

import java.util.Stack;

public class DelivererStack {
    private Stack<Deliverer> stack;

    public DelivererStack() {
        this.stack = new Stack<>();
    }

    public void add(Deliverer deliverer) {
        stack.push(deliverer);
    }

    public Deliverer pop() {
        return stack.isEmpty() ? null : stack.pop();
    }

    public Deliverer top() {
        return stack.isEmpty() ? null : stack.peek();
    }

    public void printStack() {
        for (int i = 0; i < stack.size(); i++) {
            System.out.println(stack.get(i));
        }
    }
}
