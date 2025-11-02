package ro.ase.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Cart {
    private List<OrderItem> orderItems;
    private double price;

    public Cart() {
        this.orderItems = new ArrayList<>();
    }

    public Cart(List<OrderItem> orderItems, double price) {
        this.orderItems = orderItems;
        this.price = price;
    }

    public static Cart getEmptyCart() {
        return new Cart(new ArrayList<>(), 0);
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(orderItems, cart.orderItems) && Objects.equals(price, cart.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderItems, price);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "orderItems=" + orderItems +
                ", price=" + price +
                '}';
    }
}

