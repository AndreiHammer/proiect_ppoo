package ro.ase.controller;

import ro.ase.data.ProductDataStore;
import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Product;
import ro.ase.model.Order;
import ro.ase.service.AuthenticationException;
import ro.ase.service.StoreService;
import ro.ase.service.StoreServiceImpl;
import ro.ase.view.CLIView;
import ro.ase.view.View;

import java.util.List;

public class App {
    private final ProductDataStore dataStore;
    private final StoreService storeService;
    private final View view;
    private Customer currentCustomer;

    public App(String dataPath) {
        this.dataStore = new ProductDataStore(dataPath);
        this.storeService = new StoreServiceImpl(dataStore);
        this.view = new CLIView();
    }

    public void run() {
        dataStore.init();

        try {
            currentCustomer = authenticateCustomer();
            mainMenuLoop();
        } catch (AuthenticationException e) {
            view.printError("Authentication failed: " + e.getMessage());
        }
    }

    private Customer authenticateCustomer() throws AuthenticationException {
        Credentials credentials = view.readCredentials();
        Customer customer = storeService.authenticate(credentials);
        view.printWelcomeMessage(customer);
        return customer;
    }

    private void mainMenuLoop() {
        boolean running = true;
        
        while (running) {
            view.printMainMenu();
            int choice = view.readMenuChoice();
            
            switch (choice) {
                case 1:
                    handleViewAllProducts();
                    break;
                case 2:
                    handleSearchProducts();
                    break;
                case 3:
                    handleViewCart();
                    break;
                case 4:
                    handleAddToCart();
                    break;
                case 5:
                    handleRemoveFromCart();
                    break;
                case 6:
                    handleUpdateCartQuantity();
                    break;
                case 7:
                    handleClearCart();
                    break;
                case 8:
                    handleCheckout();
                    break;
                case 9:
                    handleViewOrderHistory();
                    break;
                case 0:
                    running = false;
                    view.printSuccess("Thank you for shopping with us! Goodbye!");
                    break;
                default:
                    view.printError("Invalid choice. Please try again.");
            }
        }
    }

    private void handleViewAllProducts() {
        List<Product> products = storeService.listAllProducts();
        view.printAllProducts(products);
    }

    private void handleSearchProducts() {
        String searchTerm = view.readSearchTerm();
        if (searchTerm.isEmpty()) {
            view.printError("Search term cannot be empty.");
            return;
        }
        
        List<Product> products = storeService.searchProducts(searchTerm);
        view.printMessage("\nSearch Results for: '" + searchTerm + "'");
        view.printProducts(products);
    }

    private void handleViewCart() {
        var cart = storeService.getCart(currentCustomer);
        view.printCart(cart);
    }

    private void handleAddToCart() {
        view.printMessage("\nFirst, let's see what products are available:");
        List<Product> products = storeService.listAllProducts();
        view.printProducts(products);
        
        int productId = view.readProductId();
        if (productId < 0) {
            view.printError("Invalid product ID.");
            return;
        }
        
        int quantity = view.readQuantity();
        if (quantity < 0) {
            view.printError("Invalid quantity.");
            return;
        }
        
        try {
            storeService.addToCart(currentCustomer, productId, quantity);
            view.printSuccess("Product added to cart successfully!");
        } catch (IllegalArgumentException e) {
            view.printError(e.getMessage());
        }
    }

    private void handleRemoveFromCart() {
        var cart = storeService.getCart(currentCustomer);
        
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            view.printError("Your cart is empty. Nothing to remove.");
            return;
        }
        
        view.printMessage("\nCurrent cart contents:");
        view.printCart(cart);
        
        int productId = view.readProductId();
        if (productId < 0) {
            view.printError("Invalid product ID.");
            return;
        }
        
        try {
            storeService.removeFromCart(currentCustomer, productId);
            view.printSuccess("Product removed from cart successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.printError(e.getMessage());
        }
    }

    private void handleUpdateCartQuantity() {
        var cart = storeService.getCart(currentCustomer);
        
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            view.printError("Your cart is empty. Nothing to update.");
            return;
        }
        
        view.printMessage("\nCurrent cart contents:");
        view.printCart(cart);
        
        int productId = view.readProductId();
        if (productId < 0) {
            view.printError("Invalid product ID.");
            return;
        }
        
        int quantity = view.readQuantity();
        if (quantity < 0) {
            view.printError("Invalid quantity.");
            return;
        }
        
        try {
            storeService.updateCartItem(currentCustomer, productId, quantity);
            view.printSuccess("Cart updated successfully!");
        } catch (IllegalArgumentException | IllegalStateException e) {
            view.printError(e.getMessage());
        }
    }

    private void handleClearCart() {
        try {
            storeService.clearCart(currentCustomer);
            view.printSuccess("Cart cleared successfully!");
        } catch (Exception e) {
            view.printError("Error clearing cart: " + e.getMessage());
        }
    }

    private void handleCheckout() {
        var cart = storeService.getCart(currentCustomer);
        
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            view.printError("Your cart is empty. Please add products before checking out.");
            return;
        }
        
        view.printCart(cart);
        view.printMessage("\nPress Enter to confirm checkout...");
        view.readMenuChoice();
        
        try {
            Order order = storeService.createOrder(currentCustomer);
            view.printSuccess("Order placed successfully! Order ID: " + order.getOrderId());
            view.printOrder(order);
        } catch (IllegalStateException e) {
            view.printError(e.getMessage());
        }
    }

    private void handleViewOrderHistory() {
        List<Order> orders = storeService.getOrderHistory(currentCustomer);
        view.printOrderHistory(orders);
    }
}
