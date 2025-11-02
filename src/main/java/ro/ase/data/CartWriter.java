package ro.ase.data;

import ro.ase.model.Cart;
import ro.ase.model.Customer;
import ro.ase.model.OrderItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class CartWriter {

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

    private String addCartItems(Cart cart, long customerId) {
        if (cart == null || cart.getOrderItems() == null || cart.getOrderItems().isEmpty()) {
            return "";
        }

        return cart.getOrderItems().stream()
                .map(item -> addCartItem(item, customerId))
                .collect(Collectors.joining("\n"));
    }

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
