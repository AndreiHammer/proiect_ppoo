package ro.ase.model;

import java.util.Objects;

/**
 * Represents a product that can be purchased, including its type, description, and price.
 */
public class Product {
    private int id;
    private String name;
    private ProductType productType;
    private String description;
    private double price;

    public Product() {
    }

    public Product(int id, String name, ProductType productType, String description, double price) {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.description = description;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Product product = (Product) obj;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(productType, product.productType) && Objects.equals(description,
                product.description) && Objects.equals(price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, productType, description, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productType=" + productType.getType() +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }

    /**
     * Builder for {@link Product} to ease fluent construction.
     */
    public static class ProductBuilder {
        private int id;
        private String name;
        private ProductType productType;
        private String description;
        private double price;

        public ProductBuilder id(int id) {
            this.id = id;
            return this;
        }

        public ProductBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductBuilder productType(ProductType productType) {
            this.productType = productType;
            return this;
        }

        public ProductBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductBuilder price(double price) {
            this.price = price;
            return this;
        }

        public Product build() {
            return new Product(id, name, productType, description, price);
        }
    }
}
