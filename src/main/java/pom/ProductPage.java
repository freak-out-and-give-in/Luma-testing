package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProductPage extends LoadableComponent<ProductPage> {

    private final WebDriver driver;

    private final String url;

    @FindBy(className = "value")
    private WebElement sku;

    @FindBy(className = "base")
    private WebElement name;

    @FindBy(css = ".swatch-option.text")
    private WebElement firstSizeOption;

    @FindBy(css = ".swatch-option.color")
    private WebElement firstColorOption;

    @FindBy(id = "product-addtocart-button")
    private WebElement addToCartButton;

    @FindBy(className = "towishlist")
    private WebElement wishListButton;

    @FindBy(className = "tocompare")
    private WebElement comparisonButton;

    @FindBy(id = "nickname_field")
    private WebElement reviewNicknameInput;

    @FindBy(id = "summary_field")
    private WebElement summaryInput;

    @FindBy(id = "review_field")
    private WebElement reviewDescriptionInput;

    @FindBy(css = "#review-form button")
    private WebElement submitReviewButton;

    public ProductPage(WebDriver driver, String url) {
        this.driver = driver;
        this.url = url;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get(url);
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();
        assertEquals(currentUrl, url, "Not on the product page of " + url + ": " + currentUrl);
    }

    public String getSku() {
        return sku.getText();
    }

    public String getName() {
        return name.getText();
    }

    public void addToCart() {
        // I think this sometimes fails because the color option hasn't loaded, so we've added a wait here
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        wait.until(ExpectedConditions.elementToBeClickable(firstColorOption));

        firstSizeOption.click();
        firstColorOption.click();
        addToCartButton.click();
    }

    public boolean isTheCartBeingAddedTo() {
        String originalCartAmount = driver.findElement(By.className("counter-number")).getText();

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        try {
            wait.until(ExpectedConditions.textToBePresentInElement(
                    driver.findElement(By.className("counter-number")), (originalCartAmount + 1)));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public WishListPage addToWishlist() {
        wishListButton.click();

        return new WishListPage(driver);
    }

    public void addReview(int rating, String nickname, String summary, String review) {
        clickReviewsTab();

        selectRating(rating);
        reviewNicknameInput.sendKeys(nickname);
        summaryInput.sendKeys(summary);
        reviewDescriptionInput.sendKeys(review);

        submitReviewButton.click();
    }

    private void clickReviewsTab() {
        driver.findElement(By.id("tab-label-reviews-title")).click();
    }

    private void selectRating(int rating) {
        driver.findElement(By.className("rating-" + rating)).click();
    }

    public boolean wasTheReviewSuccessfullyAdded() {
        try {
            driver.findElement(By.className("message-success"));
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public void addToComparisonList() {
        comparisonButton.click();
    }
}
