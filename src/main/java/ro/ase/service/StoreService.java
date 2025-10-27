package ro.ase.service;

import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;
import ro.ase.model.Cart;

import java.util.List;

public interface StoreService {
    Customer authenticate(Credentials credentials) throws AuthenticationException;

    List<Product> listAllProducts();

    Product getProductById(int productId);

    void addToCart(Customer customer, int productId, int quantity);

    void removeFromCart(Customer customer, int productId);

    void updateCartItem(Customer customer, int productId, int quantity);

    Cart getCart(Customer customer);

    void clearCart(Customer customer);

    Order createOrder(Customer customer) throws IllegalStateException;

    List<Order> getOrderHistory(Customer customer);

    List<Product> searchProducts(String searchTerm);
}
