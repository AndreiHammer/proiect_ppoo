package ro.ase.model;

import java.util.Objects;

public class OrderItem {
    private Product product;
    private int pieces;
    private double price;

    public OrderItem() {
    }

    public OrderItem(Product product, int pieces, double price) {
        this.product = product;
        this.pieces = pieces;
        this.price = price;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getPieces() {
        return pieces;
    }

    public void setPieces(int pieces) {
        this.pieces = pieces;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        OrderItem orderItem = (OrderItem) o;
        return pieces == orderItem.pieces && Objects.equals(product, orderItem.product) && Objects.equals(price, orderItem.price);
    }

    @Override public int hashCode() {
        return Objects.hash(product, pieces, price);
    }

    @Override public String toString() {
        return "OrderItem{" +
                "product=" + product +
                ", pieces=" + pieces +
                ", price=" + price +
                '}';
    }

    public static class Builder {
        private Product product;
        private int pieces;
        private double price;

        public Builder product(Product product) {
            this.product = product;
            return this;
        }

        public Builder pieces(int pieces) {
            this.pieces = pieces;
            return this;
        }

        public Builder price(double price) {
            this.price = price;
            return this;
        }

        public OrderItem build() {
            return new OrderItem(product, pieces, price);
        }
    }
}
