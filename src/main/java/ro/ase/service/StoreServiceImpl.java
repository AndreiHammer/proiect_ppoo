package ro.ase.service;

import ro.ase.data.DataStore;
import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;
import ro.ase.model.Cart;
import ro.ase.model.OrderItem;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Default implementation of {@link StoreService} backed by a {@link ro.ase.data.DataStore}.
 */
public class StoreServiceImpl implements StoreService {
    private final DataStore dataStore;

    public StoreServiceImpl(DataStore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public Customer authenticate(Credentials credentials) throws AuthenticationException {
        if (credentials == null || credentials.getUserName() == null || credentials.getPassword() == null) {
            throw new AuthenticationException("Invalid credentials: credentials cannot be null");
        }

        return dataStore.getCustomers().stream()
                .filter(customer -> credentials.getUserName().equals(customer.getUserName())
                        && credentials.getPassword().equals(customer.getPassword()))
                .findFirst()
                .orElseThrow(() -> new AuthenticationException(
                        "Authentication failed for user: " + credentials.getUserName()));
    }

    @Override
    public List<Product> listAllProducts() {
        return dataStore.getProducts();
    }

    @Override
    public Product getProductById(int productId) {
        return dataStore.getProducts().stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
    }

    @Override
    public void addToCart(Customer customer, int productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Product product = getProductById(productId);
        Cart cart = customer.getCart();
        
        if (cart == null) {
            cart = Cart.getEmptyCart();
            customer.setCart(cart);
        }

        OrderItem existingItem = cart.getOrderItems().stream()
                .filter(item -> item.getProduct().getId() == productId)
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setPieces(existingItem.getPieces() + quantity);
        } else {
            OrderItem newItem = new OrderItem(product, quantity, product.getPrice());
            cart.getOrderItems().add(newItem);
        }

        updateCartTotal(cart);
        dataStore.writeCarts();
    }

    @Override
    public void removeFromCart(Customer customer, int productId) {
        Cart cart = customer.getCart();
        
        if (cart == null || cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        boolean removed = cart.getOrderItems().removeIf(item -> item.getProduct().getId() == productId);
        
        if (!removed) {
            throw new IllegalArgumentException("Product with id " + productId + " not found in cart");
        }

        updateCartTotal(cart);
        dataStore.writeCarts();
    }

    @Override
    public void updateCartItem(Customer customer, int productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        Cart cart = customer.getCart();
        
        if (cart == null || cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Cart is empty");
        }

        OrderItem item = cart.getOrderItems().stream()
                .filter(i -> i.getProduct().getId() == productId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found in cart"));

        item.setPieces(quantity);
        updateCartTotal(cart);
        dataStore.writeCarts();
    }

    @Override
    public Cart getCart(Customer customer) {
        if (customer.getCart() == null) {
            customer.setCart(Cart.getEmptyCart());
        }
        return customer.getCart();
    }

    @Override
    public void clearCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart != null) {
            cart.getOrderItems().clear();
            cart.setPrice(0.0);
            dataStore.writeCarts();
        }
    }

    private void updateCartTotal(Cart cart) {
        double total = cart.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getPieces())
                .sum();
        cart.setPrice(total);
    }

    @Override
    public Order createOrder(Customer customer) throws IllegalStateException {
        Cart cart = customer.getCart();
        
        if (cart == null || cart.getOrderItems().isEmpty()) {
            throw new IllegalStateException("Cannot create order with empty cart");
        }

        Order order = new Order(customer);
        order.setOrderItems(new ArrayList<>(cart.getOrderItems()));
        order = dataStore.createOrder(order);

        clearCart(customer);
        
        return order;
    }

    @Override
    public List<Order> getOrderHistory(Customer customer) {
        return customer.getOrders();
    }

    @Override
    public List<Product> searchProducts(String searchTerm) {

        String lowerSearchTerm = searchTerm.toLowerCase().trim();
        
        return dataStore.getProducts().stream()
                .filter(product -> 
                    product.getName().toLowerCase().contains(lowerSearchTerm) ||
                    product.getDescription().toLowerCase().contains(lowerSearchTerm) ||
                    product.getProductType().getType().toLowerCase().contains(lowerSearchTerm)
                )
                .collect(Collectors.toList());
    }
}
