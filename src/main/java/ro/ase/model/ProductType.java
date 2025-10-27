package ro.ase.model;

public enum ProductType {
    DEVICE("device"),
    ACCESORY("accesory");

    private final String type;

    ProductType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

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
