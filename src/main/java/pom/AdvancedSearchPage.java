package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import support.AdvancedSearch;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedSearchPage extends LoadableComponent<AdvancedSearchPage> {

    private final WebDriver driver;

    @FindBy(id = "name")
    private WebElement productName;

    @FindBy(id = "sku")
    private WebElement sku;

    @FindBy(id = "description")
    private WebElement description;

    @FindBy(id = "short_description")
    private WebElement shortDescription;

    @FindBy(id = "price")
    private WebElement lowerPrice;

    @FindBy(id = "price_to")
    private WebElement upperPrice;

    @FindBy(className = "primary")
    private WebElement searchButton;

    public AdvancedSearchPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://magento.softwaretestingboard.com/catalogsearch/advanced/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("advanced/"), "Not on the advanced search page: " + url);
    }

    public AdvancedSearchResultsPage searchFor(AdvancedSearch advancedSearch) {
        productName.sendKeys(advancedSearch.getProductName());
        sku.sendKeys(advancedSearch.getSku());
        description.sendKeys(advancedSearch.getDescription());
        shortDescription.sendKeys(advancedSearch.getShortDescription());
        lowerPrice.sendKeys(advancedSearch.getLowerPrice());
        upperPrice.sendKeys(advancedSearch.getUpperPrice());
        searchButton.click();

        return new AdvancedSearchResultsPage(driver, advancedSearch);
    }
}
