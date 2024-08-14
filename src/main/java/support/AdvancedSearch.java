package support;

import util.SupportUtil;

public class AdvancedSearch {

    private String productName;
    private String sku;
    private String description;
    private String shortDescription;
    private String lowerPrice;
    private String upperPrice;
    private int page = 1;

    public AdvancedSearch(String productName, String sku, String description, String shortDescription,
                          String lowerPrice, String upperPrice) {
        this.productName = productName;
        this.sku = sku;
        this.description = description;
        this.shortDescription = shortDescription;
        this.lowerPrice = lowerPrice;
        this.upperPrice = upperPrice;

        SupportUtil.changeAdvancedSearchNullValuesToEmpty(this);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getLowerPrice() {
        return lowerPrice;
    }

    public void setLowerPrice(String lowerPrice) {
        this.lowerPrice = lowerPrice;
    }

    public String getUpperPrice() {
        return upperPrice;
    }

    public void setUpperPrice(String upperPrice) {
        this.upperPrice = upperPrice;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
