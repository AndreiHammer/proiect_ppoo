package ro.ase.data;

import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;

import java.util.List;

/**
 * Abstraction for accessing and persisting store data (customers, products, orders, carts).
 */
public interface DataStore {
    /** Returns all customers. */
    List<Customer> getCustomers();

    /** Returns all products. */
    List<Product> getProducts();

    /** Persists and returns the created order with generated id. */
    Order createOrder(Order order);

    /** Persists all orders to storage. */
    void writeOrders();

    /** Persists all carts to storage. */
    void writeCarts();
}
