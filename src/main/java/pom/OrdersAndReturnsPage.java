package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdersAndReturnsPage extends LoadableComponent<OrdersAndReturnsPage> {

    private final WebDriver driver;

    @FindBy(className = "view")
    private WebElement viewOrderButton;

    @FindBy(className = "product-item-name")
    private WebElement productName;

    public OrdersAndReturnsPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/sales/order/history/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("order/"), "Not on the orders and returns page: " + url);
    }

    public void viewOrder() {
        driver.navigate().refresh();
        viewOrderButton.click();
    }

    public boolean isMyOrderCorrect(String expectedName) {
        return expectedName.equals(productName.getAttribute("textContent").trim());
    }
}
