package ro.ase.data;

import ro.ase.model.Cart;
import ro.ase.model.Customer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Reads {@link Customer} entities from a CSV file.
 */
public class CustomerReader extends Reader<Customer> {

    @Override
    public List<Customer> read(String path) {
        List<String> lines = readFromFile(path);
        return lines.stream()
                .map(this::buildCustomer)
                .collect(Collectors.toList());
    }

    /** Parses a line into a {@link Customer}. */
    private Customer buildCustomer(String line) {
        String[] parts = splitLineByComma(line);
        long customerId = Long.parseLong(parts[2]);
        return new Customer.Builder()
                .userName(parts[0])
                .password(parts[1])
                .id(customerId)
                .name(parts[3])
                .cart(Cart.getEmptyCart())
                .build();
    }
}
