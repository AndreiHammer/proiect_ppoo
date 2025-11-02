package ro.ase.view;

import ro.ase.model.Credentials;
import ro.ase.model.Customer;
import ro.ase.model.Product;
import ro.ase.model.Order;
import ro.ase.model.Cart;
import ro.ase.model.OrderItem;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class CLIView implements View {
    private final Scanner scanner;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public CLIView() {
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Credentials readCredentials() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        Credentials credentials = new Credentials();
        credentials.setUserName(username);
        credentials.setPassword(password);

        return credentials;
    }

    @Override
    public void printWelcomeMessage(Customer customer) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Welcome to Online Store, " + customer.getName() + "!");
        System.out.println("=".repeat(50) + "\n");
    }

    @Override
    public void printMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. View All Products");
        System.out.println("2. Search Products");
        System.out.println("3. View Cart");
        System.out.println("4. Add Product to Cart");
        System.out.println("5. Remove Product from Cart");
        System.out.println("6. Update Product Quantity in Cart");
        System.out.println("7. Clear Cart");
        System.out.println("8. Checkout (Place Order)");
        System.out.println("9. View Order History");
        System.out.println("0. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice: ");
    }

    @Override
    public int readMenuChoice() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void printAllProducts(List<Product> products) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ALL PRODUCTS");
        System.out.println("=".repeat(50));
        printProducts(products);
    }

    @Override
    public void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("No products found.");
            return;
        }

        System.out.printf("%-5s %-25s %-15s %-30s %-10s%n",
                "ID", "Name", "Type", "Description", "Price");
        System.out.println("-".repeat(90));

        for (Product product : products) {
            String desc = product.getDescription();
            if (desc.length() > 28) {
                desc = desc.substring(0, 25) + "...";
            }
            System.out.printf("%-5d %-25s %-15s %-30s $%.2f%n",
                    product.getId(),
                    product.getName(),
                    product.getProductType().getType(),
                    desc,
                    product.getPrice());
        }
        System.out.println("=".repeat(90));
    }

    @Override
    public void printCart(Cart cart) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("SHOPPING CART");
        System.out.println("=".repeat(50));

        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-12s %-12s%n",
                "ID", "Product", "Quantity", "Unit Price", "Total");
        System.out.println("-".repeat(70));

        for (OrderItem item : cart.getOrderItems()) {
            double itemTotal = item.getPrice() * item.getPieces();
            System.out.printf("%-5d %-20s %-10d $%-11.2f $%.2f%n",
                    item.getProduct().getId(),
                    item.getProduct().getName(),
                    item.getPieces(),
                    item.getPrice(),
                    itemTotal);
        }

        System.out.println("-".repeat(70));
        System.out.printf("%-50s $%.2f%n", "TOTAL:", cart.getPrice());
        System.out.println("=".repeat(50));
    }

    @Override
    public int readProductId() {
        System.out.print("Enter product ID: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public int readQuantity() {
        System.out.print("Enter quantity: ");
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    @Override
    public void printOrder(Order order) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ORDER DETAILS");
        System.out.println("=".repeat(50));
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer ID: " + order.getCustomerId());
        System.out.println("Date: " + order.getTimestampCreated().format(DATE_TIME_FORMATTER));
        System.out.println("-".repeat(50));

        System.out.printf("%-25s %-10s %-15s%n",
                "Product", "Quantity", "Price");
        System.out.println("-".repeat(50));

        for (OrderItem item : order.getOrderItems()) {
            System.out.printf("%-25s %-10d $%.2f%n",
                    item.getProduct().getName(),
                    item.getPieces(),
                    item.getPrice() * item.getPieces());
        }

        System.out.println("-".repeat(50));
        System.out.printf("%-35s $%.2f%n", "TOTAL:", order.getPrice());
        System.out.println("=".repeat(50));
    }

    @Override
    public void printOrderHistory(List<Order> orders) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("ORDER HISTORY");
        System.out.println("=".repeat(50));

        if (orders == null || orders.isEmpty()) {
            System.out.println("You have no orders yet.");
            return;
        }

        for (int i = 0; i < orders.size(); i++) {
            System.out.println("\n--- Order #" + (i + 1) + " ---");
            printOrder(orders.get(i));
        }
    }

    @Override
    public void printMessage(String message) {
        System.out.println(message);
    }

    @Override
    public void printError(String error) {
        System.out.println("\n[ERROR] " + error + "\n");
    }

    @Override
    public void printSuccess(String message) {
        System.out.println("\n[SUCCESS] " + message + "\n");
    }

    @Override
    public String readSearchTerm() {
        System.out.print("Enter search term: ");
        return scanner.nextLine().trim();
    }
}
