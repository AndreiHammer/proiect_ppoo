package ro.ase.data;

import ro.ase.model.Cart;
import ro.ase.model.Customer;
import ro.ase.model.OrderItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Writes carts to a CSV file.
 */
public class CartWriter {

    /**
     * Writes all non-empty carts of the given customers to the output file.
     *
     * @param customers  customers containing carts
     * @param outputFile target CSV path
     */
    public void writeCarts(List<Customer> customers, String outputFile) {
        File file = new File(outputFile);
        try {
            file.createNewFile();
            FileWriter fw = new FileWriter(file);
            String csvContent = customers.stream()
                    .filter(customer -> customer.getCart() != null
                            && customer.getCart().getOrderItems() != null
                            && !customer.getCart().getOrderItems().isEmpty())
                    .map(customer -> addCartItems(customer.getCart(), customer.getId()))
                    .filter(content -> !content.isEmpty())
                    .collect(Collectors.joining("\n"));

            fw.write(csvContent);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** Serializes all items of a cart for a single customer. */
    private String addCartItems(Cart cart, long customerId) {
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            return "";
        }

        return cart.getOrderItems().stream()
                .map(item -> addCartItem(item, customerId))
                .collect(Collectors.joining("\n"));
    }

    /** Serializes a single cart item. */
    private String addCartItem(OrderItem orderItem, long customerId) {
        return String.join(
                ",",
                Long.toString(customerId),
                orderItem.getProduct().getName(),
                Integer.toString(orderItem.getPieces()),
                String.valueOf(orderItem.getPrice())
        );
    }
}
