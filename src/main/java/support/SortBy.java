package support;

public enum SortBy {
    PRICE("Price"),
    PRODUCT_NAME("Product name");

    private final String value;

    SortBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
