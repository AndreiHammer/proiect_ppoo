package ro.ase.model;

/**
 * Enumerates product categories supported by the store.
 */
public enum ProductType {
    DEVICE("device"),
    ACCESORY("accesory");

    private final String type;

    ProductType(String type) {
        this.type = type;
    }

    /**
     * @return the string representation of the type
     */
    public String getType() {
        return type;
    }

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
