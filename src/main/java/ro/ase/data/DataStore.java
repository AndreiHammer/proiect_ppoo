package ro.ase.data;

import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;

import java.util.List;

public interface DataStore {
    List<Customer> getCustomers();

    List<Product> getProducts();

    List<Order> getOrders();

    Order createOrder(Order order);

    void writeOrders();
}
