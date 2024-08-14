package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.Checkout;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutPage extends LoadableComponent<CheckoutPage> {

    private final WebDriver driver;

    @FindBy(id = "customer-email")
    private WebElement email;

    @FindBy(name = "firstname")
    private WebElement firstName;

    @FindBy(name = "lastname")
    private WebElement lastName;

    @FindBy(name = "company")
    private WebElement company;

    @FindBy(name = "street[0]")
    private WebElement streetAddress1;

    @FindBy(name = "street[1]")
    private WebElement streetAddress2;

    @FindBy(name = "street[2]")
    private WebElement streetAddress3;

    @FindBy(name = "city")
    private WebElement city;

    @FindBy(name = "postcode")
    private WebElement postalCode;

    @FindBy(name = "country_id")
    private WebElement countryId;

    @FindBy(name = "telephone")
    private WebElement phoneNumber;

    @FindBy(id = "checkout-shipping-method-load")
    private WebElement shippingMethods;

    @FindBy(css = "#shipping-method-buttons-container button")
    private WebElement nextButton;

    @FindBy(css = "input[type='checkbox']")
    private WebElement billingShippingConfirmationButton;

    @FindBy(className = "checkout")
    private WebElement placeOrderButton;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    protected void load() {
        driver.get("https://magento.softwaretestingboard.com/checkout/#shipping");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("shipping"), "Not on the shipping page: " + url);
    }

    public void fillOutShippingDetails(Checkout checkout) {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(phoneNumber));

        try {
            email.sendKeys(checkout.getEmail());
        } catch (Exception ignored) { }
        firstName.sendKeys(checkout.getFirstName());
        lastName.sendKeys(checkout.getLastName());
        company.sendKeys(checkout.getCompany());
        streetAddress1.sendKeys(checkout.getStreetAddress1());
        streetAddress2.sendKeys(checkout.getStreetAddress2());
        streetAddress3.sendKeys(checkout.getStreetAddress3());
        city.sendKeys(checkout.getCity());
        postalCode.sendKeys(checkout.getPostalCode());
        chooseCountryOption(checkout.getCountry());
        phoneNumber.sendKeys(checkout.getPhoneNumber());
        waitForShippingMethodOption();

        wait.until(ExpectedConditions.elementToBeClickable(nextButton));
        nextButton.click();
    }

    private void chooseCountryOption(String country) {
        for (WebElement element : countryId.findElements(By.tagName("option"))) {
            String tempCountry = element.getText().trim();

            if (country.equals(tempCountry)) {
                element.click();
                return;
            }
        }

        throw new NoSuchElementException("No country has the name " + country);
    }

    private void waitForShippingMethodOption() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(4));

        // Waits for Javascript to load shipping options, and it gives us certain options based on the shipping
        // details we put in.
        // Then it waits for it to load and be visible again, and auto-checks it if there's only one option - I think.
        try {
            wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.className("radio"))));
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.className("radio"))));
        } catch (Exception ignored) {
        }
    }

    public void reviewPaymentAndPlaceOrder() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(4));
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("img[alt='Loading...']"))));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton));

        placeOrderButton.click();
    }

    public boolean wasTheOrderSuccessful() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(6));

        try {
            wait.until(ExpectedConditions.urlContains("success/"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean amIOnTheReviewAndPlaceOrderSection() {
        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(3));

        try {
            wait.until(ExpectedConditions.urlContains("payment"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
