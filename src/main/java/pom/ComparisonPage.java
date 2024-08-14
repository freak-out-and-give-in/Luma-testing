package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ComparisonPage extends LoadableComponent<ComparisonPage> {

    private final WebDriver driver;

    @FindBy(css = "td[data-th='Product']")
    private List<WebElement> products;

    public ComparisonPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/catalog/product_compare/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("product_compare/"), "Not on the comparison page: " + url);
    }

    public boolean areAnyOfTheseProductsHere(List<String> productNamesInput) {
        return new HashSet<>(products.stream()
                .map(product -> product.findElement(By.cssSelector(".product-item-name a")).getText())
                .toList())
                .containsAll(productNamesInput);
    }

    public void removeProduct(String productNameInput) {
        for (int i = 0; i < products.size(); i++) {
            WebElement product = products.get(i);
            String productName = product.findElement(By.cssSelector(".product-item-name a")).getText();

            if (productName.equals(productNameInput)) {
                List<WebElement> deletes = driver.findElements(By.className("delete"));
                deletes.get(i).click();

                new WebDriverWait(driver, Duration.ofSeconds(2))
                        .until(ExpectedConditions.elementToBeClickable(
                                driver.findElement(By.className("action-accept"))));

                WebElement confirmDeletionButton = driver.findElement(By.className("action-accept"));
                confirmDeletionButton.click();

                return;
            }
        }

        throw new NoSuchElementException("No product has the name " + productNameInput);
    }
}
