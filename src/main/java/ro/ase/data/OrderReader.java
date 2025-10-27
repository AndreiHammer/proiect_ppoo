package ro.ase.data;

import ro.ase.model.Order;
import ro.ase.model.OrderItem;
import ro.ase.model.Product;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OrderReader extends Reader {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private final List<Product> products;

    public OrderReader(List<Product> products) {
        this.products = products;
    }

    @Override
    public List<Order> read(String path) {
        List<String> lines;
        
        try {
            lines = readFromFile(path);
        } catch (IllegalArgumentException e) {
            // File is empty or doesn't exist, return empty list
            return new ArrayList<>();
        }
        
        // Group lines by orderId and customerId
        Map<String, List<OrderLineData>> groupedOrders = lines.stream()
                .map(this::parseLine)
                .collect(Collectors.groupingBy(data -> data.orderId + "_" + data.customerId));
        
        // Convert grouped data to Order objects
        List<Order> orders = new ArrayList<>();
        for (List<OrderLineData> orderLines : groupedOrders.values()) {
            if (orderLines.isEmpty()) continue;
            
            OrderLineData firstLine = orderLines.get(0);
            List<OrderItem> orderItems = orderLines.stream()
                    .map(this::createOrderItem)
                    .collect(Collectors.toList());
            
            Order order = new Order();
            order.setOrderId(Long.parseLong(firstLine.orderId));
            order.setCustomerId(Long.parseLong(firstLine.customerId));
            order.setOrderItems(orderItems);
            order.setPrice(firstLine.totalPrice);
            order.setTimestampCreated(firstLine.timestamp);
            
            orders.add(order);
        }
        
        return orders;
    }

    private OrderLineData parseLine(String line) {
        String[] parts = splitLineByComma(line);
        OrderLineData data = new OrderLineData();
        data.orderId = parts[0];
        data.customerId = parts[1];
        data.productName = parts[2];
        data.pieces = Integer.parseInt(parts[3]);
        data.itemPrice = Double.parseDouble(parts[4]);
        data.timestamp = LocalDateTime.parse(parts[5], DATE_TIME_FORMATTER);
        data.totalPrice = Double.parseDouble(parts[6]);
        return data;
    }

    private OrderItem createOrderItem(OrderLineData data) {
        Product product = products.stream()
                .filter(p -> p.getName().equals(data.productName))
                .findFirst()
                .orElse(null);
        
        if (product == null) {
            throw new IllegalArgumentException("Product not found: " + data.productName);
        }
        
        return new OrderItem(product, data.pieces, data.itemPrice);
    }

    private static class OrderLineData {
        String orderId;
        String customerId;
        String productName;
        int pieces;
        double itemPrice;
        LocalDateTime timestamp;
        double totalPrice;
    }
}

