package ro.ase.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a shopping cart associated with a customer, containing
 * a list of {@link OrderItem} entries and the aggregated total price.
 */
public class Cart {
    private Long customerId;
    private List<OrderItem> orderItems;
    private double price;

    public Cart() {
        this.orderItems = new ArrayList<>();
    }

    public Cart(Long customerId, List<OrderItem> orderItems, double price) {
        this.customerId = customerId;
        this.orderItems = orderItems;
        this.price = price;
    }

    public static Cart getEmptyCart() {
        return new Cart(0L, new ArrayList<>(), 0);
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cart cart = (Cart) o;
        return Objects.equals(customerId, cart.customerId) && Objects.equals(orderItems, cart.orderItems) && Objects.equals(price, cart.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, orderItems, price);
    }

    @Override
    public String toString() {
        return "Cart{" +
                "customerId=" + customerId +
                ", orderItems=" + orderItems +
                ", price=" + price +
                '}';
    }
}

