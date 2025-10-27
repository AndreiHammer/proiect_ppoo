package ro.ase.data;

import ro.ase.model.Customer;
import ro.ase.model.Order;
import ro.ase.model.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductDataStore implements DataStore {
    private List<Customer> customers;
    private List<Product> products;
    private List<Order> orders;
    private final CustomerReader customerReader = new CustomerReader();
    private final ProductReader foodReader = new ProductReader();
    private final OrderWriter orderWriter = new OrderWriter();
    private final String inputFolderPath;
    private OrderReader orderReader;

    public ProductDataStore(String inputFolderPath) {
        this.inputFolderPath = inputFolderPath;
        this.orders = new ArrayList<>();
    }

    public void init() {
        this.customers = getCustomers();
        this.products = getProducts();
        this.orderReader = new OrderReader(this.products);
        this.orders = loadOrders();
        associateOrdersWithCustomers();
    }

    private void associateOrdersWithCustomers() {
        // Clear existing orders from customers to avoid duplicates
        for (Customer customer : customers) {
            if (customer.getOrders() != null) {
                customer.getOrders().clear();
            }
        }
        
        // Associate loaded orders with customers
        for (Order order : orders) {
            customers.stream()
                    .filter(customer -> customer.getId() == order.getCustomerId())
                    .forEach(customer -> customer.getOrders().add(order));
        }
    }

    @Override
    public List<Customer> getCustomers() {
        if (customers == null) {
            customers = customerReader.read(this.inputFolderPath + "/customers.csv");
        }
        return customers;
    }

    @Override
    public List<Product> getProducts() {
        if (products == null) {
            products = foodReader.read(this.inputFolderPath + "/products.csv");
        }
        return products;
    }

    @Override
    public List<Order> getOrders() {
        return orders;
    }

    private List<Order> loadOrders() {
        String ordersFilePath = inputFolderPath + "/orders.csv";
        File ordersFile = new File(ordersFilePath);
        
        if (!ordersFile.exists()) {
            // File doesn't exist yet, return empty list
            return new ArrayList<>();
        }
        
        try {
            if (orderReader == null) {
                return new ArrayList<>();
            }
            return orderReader.read(ordersFilePath);
        } catch (IllegalArgumentException e) {
            // File is empty, return empty list
            return new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error loading orders: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public Order createOrder(Order order) {
        long nextOrderId = 0;
        if(!orders.isEmpty()) {
            nextOrderId = orders.stream()
                    .mapToLong(o -> o.getOrderId() != null ? o.getOrderId() : 0)
                    .max()
                    .orElse(0) + 1;
        }

        order.setOrderId(nextOrderId);
        orders.add(order);

        // Associate order with customer
        customers.stream()
                .filter(customer -> customer.getId() == order.getCustomerId())
                .forEach(customer -> customer.getOrders().add(order));

        writeOrders();
        return order;
    }

    @Override
    public void writeOrders() {
        orderWriter.writeOrders(orders, inputFolderPath + "/orders.csv");
    }
}
