package ro.ase.data;

import ro.ase.model.Cart;
import ro.ase.model.OrderItem;
import ro.ase.model.Product;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Reads {@link ro.ase.model.Cart} entities from a CSV file.
 */
public class CartReader extends Reader<Cart> {
    private final List<Product> products;

    public CartReader(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Cart> read(String path) {
        List<String> lines;
        try {
            lines = readFromFile(path);
        } catch (IllegalArgumentException e) {
            return List.of();
        }

        Map<String, List<CartLineData>> groupedCarts = lines.stream()
                .map(this::parseLine)
                .collect(Collectors.groupingBy(data -> data.customerId));

        return groupedCarts.entrySet().stream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> {
                    List<OrderItem> orderItems = entry.getValue().stream()
                            .map(this::createOrderItem)
                            .collect(Collectors.toList());

                    Cart cart = new Cart();
                    cart.setCustomerId(Long.parseLong(entry.getKey()));
                    cart.setOrderItems(orderItems);

                    double totalPrice = orderItems.stream()
                            .mapToDouble(item -> item.getPrice() * item.getPieces())
                            .sum();
                    cart.setPrice(totalPrice);

                    return cart;
                })
                .collect(Collectors.toList());
    }

    /** Parses a single CSV line describing a cart item row. */
    private CartLineData parseLine(String line) {
        String[] parts = splitLineByComma(line);
        CartLineData cartLineData = new CartLineData();
        cartLineData.customerId = parts[0];
        cartLineData.productName = parts[1];
        cartLineData.pieces = Integer.parseInt(parts[2]);
        cartLineData.itemPrice = Double.parseDouble(parts[3]);
        return cartLineData;
    }

    /** Creates an {@link OrderItem} resolved from the product catalog. */
    private OrderItem createOrderItem(CartLineData data) {
        return products.stream()
                .filter(p -> p.getName().equals(data.productName))
                .findFirst()
                .map(product -> new OrderItem(product, data.pieces, data.itemPrice))
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + data.productName));
    }

    /** Internal structure holding parsed cart line data. */
    private static class CartLineData {
        String customerId;
        String productName;
        int pieces;
        double itemPrice;
    }
}
