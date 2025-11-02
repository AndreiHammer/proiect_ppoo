package ro.ase.data;

import ro.ase.model.Cart;
import ro.ase.model.OrderItem;
import ro.ase.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CartReader extends Reader {
    private List<Product> products;

    public CartReader(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Cart> read(String path) {
        List<String> lines;

        try {
            lines = readFromFile(path);
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }

        Map<String, List<CartLineData>> groupedCarts = lines.stream()
                .map(this::parseLine)
                .collect(Collectors.groupingBy(data -> data.customerId));

        List<Cart> carts = new ArrayList<>();
        for (Map.Entry<String, List<CartLineData>> entry : groupedCarts.entrySet()) {
            List<CartLineData> cartLines = entry.getValue();
            if(cartLines.isEmpty()) continue;

            List<OrderItem> orderItems = cartLines.stream()
                    .map(this::createOrderItem)
                    .collect(Collectors.toList());

            Cart cart = new Cart();
            cart.setOrderItems(orderItems);

            double totalPrice = orderItems.stream()
                    .mapToDouble(item -> item.getPrice() * item.getPieces())
                    .sum();
            cart.setPrice(totalPrice);

            carts.add(cart);
        }

        return carts;
    }

    private CartLineData parseLine(String line) {
        String[] parts = splitLineByComma(line);
        CartLineData cartLineData = new CartLineData();
        cartLineData.customerId = parts[0];
        cartLineData.productName = parts[1];
        cartLineData.pieces = Integer.parseInt(parts[2]);
        cartLineData.itemPrice = Double.parseDouble(parts[3]);
        return cartLineData;
    }

    private OrderItem createOrderItem(CartLineData data) {
        return products.stream()
                .filter(p -> p.getName().equals(data.productName))
                .findFirst()
                .map(product -> new OrderItem(product, data.pieces, data.itemPrice))
                .orElseThrow(() -> new IllegalArgumentException("Product not found: " + data.productName));
    }

    private static class CartLineData {
        String customerId;
        String productName;
        int pieces;
        double itemPrice;
    }
}
