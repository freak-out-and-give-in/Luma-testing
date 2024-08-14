package support;

public class Product {

    private final String name;
    private final String url;
    private final double price;

    public Product(String name, String url, double price) {
        this.name = name;
        this.url = url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public double getPrice() {
        return price;
    }
}
