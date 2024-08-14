package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryPage extends LoadableComponent<CategoryPage> {

    private final WebDriver driver;
    private final String category;
    private final ProductResultsPage productResultsPage;

    public CategoryPage(WebDriver driver, String category) {
        this.driver = driver;
        this.category = category;
        this.productResultsPage = new ProductResultsPage(driver);

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/" + category);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains(".html"), "Not on a category page: " + url);
    }

    public ProductResultsPage getProductResultsPage() {
        return productResultsPage;
    }
}
