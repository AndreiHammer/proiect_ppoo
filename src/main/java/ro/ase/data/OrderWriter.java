package ro.ase.data;

import ro.ase.model.Order;
import ro.ase.model.OrderItem;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class OrderWriter {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public void writeOrders(List<Order> orders, String outputFile) {
        File file = new File(outputFile);
        try {
            file.createNewFile();
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(addOrders(orders));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String addOrders(List<Order> orders) {
        return orders
                .stream()
                .map(order -> addOrderItems(
                        order.getOrderItems(),
                        order.getPrice(),
                        order.getTimestampCreated(),
                        order.getOrderId(),
                        order.getCustomerId()
                ))
                .collect(Collectors.joining("\n"));
    }

    private String addOrderItems(List<OrderItem> orderItems, double totalPrice, LocalDateTime timestampCreated, Long orderId, long customerId) {
        return orderItems
                .stream()
                .map(orderItem -> addOrderItem(
                        orderItem,
                        totalPrice,
                        timestampCreated,
                        orderId,
                        customerId
                ))
                .collect(Collectors.joining("\n"));
    }

    private String addOrderItem(OrderItem orderItem, double totalPrice, LocalDateTime timestampCreated, Long orderId, long customerId) {
        return String.join(
                ",",
                Long.toString(orderId),
                Long.toString(customerId),
                orderItem.getProduct().getName(),
                Integer.toString(orderItem.getPieces()),
                String.valueOf(orderItem.getPrice()),
                timestampCreated.format(DATE_TIME_FORMATTER),
                String.valueOf(totalPrice));
    }
}
