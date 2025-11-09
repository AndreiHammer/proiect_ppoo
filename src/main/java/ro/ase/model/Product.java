package ro.ase.model;

import lombok.Builder;
import lombok.Data;

/**
 * Represents a product that can be purchased, including its type, description, and price.
 */
@Data
@Builder
public class Product {
    private int id;
    private String name;
    private ProductType productType;
    private String description;
    private double price;
}
