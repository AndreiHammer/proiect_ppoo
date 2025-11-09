package ro.ase.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Enumerates product categories supported by the store.
 */
@AllArgsConstructor
@Getter
public enum ProductType {
    DEVICE("device"),
    ACCESORY("accesory");

    private final String type;

    /**
     * Parses a string into a {@link ProductType} value (case-insensitive).
     *
     * @param type the input string
     * @return matching {@link ProductType}
     * @throws IllegalArgumentException if the type is null or unknown
     */
    public static ProductType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Product type cannot be null");
        }

        for (ProductType productType : ProductType.values()) {
            if (productType.type.equalsIgnoreCase(type)) {
                return productType;
            }
        }

        throw new IllegalArgumentException("Unknown product type: " + type);
    }
}
