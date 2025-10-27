package ro.ase.view;

import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Product;
import ro.ase.model.Order;
import ro.ase.model.Cart;

import java.util.List;

public interface View {
    Credentials readCredentials();

    void printWelcomeMessage(Customer customer);

    void printAllProducts(List<Product> products);

    void printProducts(List<Product> products);

    void printMainMenu();

    int readMenuChoice();

    void printCart(Cart cart);

    int readProductId();

    int readQuantity();

    void printOrder(Order order);

    void printOrderHistory(List<Order> orders);

    void printMessage(String message);

    void printError(String error);

    void printSuccess(String message);

    String readSearchTerm();
}
