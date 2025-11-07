package ro.ase.view;

import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Product;
import ro.ase.model.Order;
import ro.ase.model.Cart;

import java.util.List;

/**
 * Abstraction for user interaction in the application (input/output).
 */
public interface View {
    /** Reads login credentials from the user. */
    Credentials readCredentials();

    /** Prints a welcome message after successful authentication. */
    void printWelcomeMessage(Customer customer);

    /** Prints all product header and list. */
    void printAllProducts(List<Product> products);

    /** Prints a list of products. */
    void printProducts(List<Product> products);

    /** Prints the main menu. */
    void printMainMenu();

    /** Reads a numeric menu choice. */
    int readMenuChoice();

    /** Prints a cart summary. */
    void printCart(Cart cart);

    /** Reads a product id. */
    int readProductId();

    /** Reads a quantity. */
    int readQuantity();

    /** Prints a single order details. */
    void printOrder(Order order);

    /** Prints an order history list. */
    void printOrderHistory(List<Order> orders);

    /** Prints a generic message. */
    void printMessage(String message);

    /** Prints an error message. */
    void printError(String error);

    /** Prints a success message. */
    void printSuccess(String message);

    /** Reads a search term from the user. */
    String readSearchTerm();
}
