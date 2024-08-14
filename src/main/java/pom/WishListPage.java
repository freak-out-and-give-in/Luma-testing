package pom;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WishListPage extends LoadableComponent<WishListPage> {

    private final WebDriver driver;

    @FindBy(className = "toolbar-number")
    private WebElement amountOfProducts;

    public WishListPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/wishlist/index/index/wishlist_id");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.contains("wishlist_id"), "Not on the wish list page: " + url);
    }

    public int howManyProductsAreInMyWishList() {
        try {
            return Integer.parseInt(amountOfProducts.getText().split(" ")[0]);
        } catch (NoSuchElementException e) {
            return 0;
        }
    }
}
