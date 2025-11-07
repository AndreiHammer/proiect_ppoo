package ro.ase.service;

import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;
import ro.ase.model.Cart;

import java.util.List;

/**
 * Core business service for store operations such as authentication,
 * product browsing, cart management and order handling.
 */
public interface StoreService {
    /**
     * Authenticates a user by credentials.
     *
     * @param credentials username/password pair
     * @return the authenticated customer
     * @throws AuthenticationException if credentials are invalid or do not match
     */
    Customer authenticate(Credentials credentials) throws AuthenticationException;

    /**
     * Returns all available products.
     */
    List<Product> listAllProducts();

    /**
     * Finds a product by its id.
     *
     * @param productId product identifier
     * @return matching product
     * @throws IllegalArgumentException if not found
     */
    Product getProductById(int productId);

    /**
     * Adds a product to the customer's cart.
     */
    void addToCart(Customer customer, int productId, int quantity);

    /**
     * Removes a product from the customer's cart.
     */
    void removeFromCart(Customer customer, int productId);

    /**
     * Updates quantity of a product in the customer's cart.
     */
    void updateCartItem(Customer customer, int productId, int quantity);

    /**
     * Returns the customer's current cart (creates one if missing).
     */
    Cart getCart(Customer customer);

    /**
     * Clears all items from the customer's cart.
     */
    void clearCart(Customer customer);

    /**
     * Creates an order from the customer's cart.
     *
     * @throws IllegalStateException if the cart is empty
     */
    Order createOrder(Customer customer) throws IllegalStateException;

    /**
     * Returns the order history of the customer.
     */
    List<Order> getOrderHistory(Customer customer);

    /**
     * Performs a case-insensitive search across product name, description and type.
     */
    List<Product> searchProducts(String searchTerm);
}
