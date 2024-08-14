package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    private final WebDriver driver;

    @FindBy(css = ".action.showcart")
    private WebElement cartButton;

    @FindBy(id = "top-cart-btn-checkout")
    private WebElement proceedToCheckoutButton;

    @FindBy(className = "message-success")
    private WebElement successMessage;

    public BasePage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    // Only works if the cart is not empty
    public CheckoutPage proceedToCheckout(boolean justAddedAProduct) {
        driver.manage().window().maximize();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        if (justAddedAProduct) {
            // Wait until confirmation message that the product has been added to the cart
            wait.until(ExpectedConditions.visibilityOf(successMessage));
        } else {
            // Wait for cart to load the newly added product
            waitForCartToLoadNewlyAddedProduct();
        }

        cartButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(proceedToCheckoutButton));
        proceedToCheckoutButton.click();

        return new CheckoutPage(driver);
    }

    public int howManyProductsAreInMyCart(boolean justAddedAProduct) {
        if (justAddedAProduct) {
            waitForCartToLoadNewlyAddedProduct();
        }

        try {
            return Integer.parseInt(driver.findElement(By.className("counter-number")).getText());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private void waitForCartToLoadNewlyAddedProduct() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("loading-mask"))));
    }
}
