package ro.ase.data;

import ro.ase.model.Product;
import ro.ase.model.ProductType;

import java.util.ArrayList;
import java.util.List;

public class ProductReader extends Reader {

    @Override
    public List<Product> read(String path) {
        List<String> lines = readFromFile(path);
        List<Product> products = new ArrayList<Product>();
        for (String line : lines) {
            products.add(buildProduct(line));
        }
        return products;
    }

    private Product buildProduct(String line) {
        String[] parts = splitLineByComma(line);
        return new Product.ProductBuilder()
                .id(Integer.parseInt(parts[0]))
                .name(parts[1])
                .productType(ProductType.fromString(parts[2]))
                .description(parts[3])
                .price(Double.parseDouble(parts[4]))
                .build();
    }
}
