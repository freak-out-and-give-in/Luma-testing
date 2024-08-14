package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.LoadableComponent;
import support.AdvancedSearch;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedSearchResultsPage extends LoadableComponent<AdvancedSearchResultsPage> {

    private final WebDriver driver;
    private final AdvancedSearch advancedSearch;
    private final ProductResultsPage productResultsPage;

    public AdvancedSearchResultsPage(WebDriver driver, AdvancedSearch advancedSearch) {
        this.driver = driver;
        this.advancedSearch = advancedSearch;
        this.productResultsPage = new ProductResultsPage(driver);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/catalogsearch/advanced/result/?" +
                "name=" + advancedSearch.getProductName() + "&" +
                "p=" + advancedSearch.getPage() + "&" +
                "sku=" + advancedSearch.getSku() + "&" +
                "description=" + advancedSearch.getDescription() + "&" +
                "short_description=" + advancedSearch.getShortDescription() + "&" +
                "price%5Bfrom%5D=" + advancedSearch.getLowerPrice() + "&" +
                "price%5Bto%5D=" + advancedSearch.getUpperPrice());
    }

    @Override
    public void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("advanced/result"), "Not on the advanced search results page: " + url);
    }

    public ProductResultsPage getProductResultsPage() {
        return productResultsPage;
    }
}
