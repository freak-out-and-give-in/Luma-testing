package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.BasePage;
import pom.CheckoutPage;
import support.Checkout;
import util.TestUtil;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CheckoutStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private CheckoutPage checkoutPage;
    private final BasePage basePage;

    public CheckoutStepDefinitions() {
        this.basePage = new BasePage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @DataTableType
    public Checkout defineCheckout(Map<String, String> entry) {
        return new Checkout(entry.get("email"), entry.get("firstName"), entry.get("lastName"), entry.get("company"),
                entry.get("streetAddress1"), entry.get("streetAddress2"), entry.get("streetAddress3"),
                entry.get("city"), entry.get("postalCode"), entry.get("country"), entry.get("phoneNumber"),
                entry.get("shippingMethod"));
    }

    @Given("A product has been added to my shopping cart")
    public void aProductHasBeenAddedToMyShoppingCart() {
        TestUtil.addProductToCart(driver, "https://magento.softwaretestingboard.com/hero-hoodie.html");
    }

    @And("I am on the checkout page")
    public void iAmOnTheCheckoutPage() {
        checkoutPage = basePage.proceedToCheckout(true);
    }

    @When("I enter my shipping details as")
    public void iEnterMyShippingDetailsAs(List<Checkout> checkout) {
        checkoutPage.fillOutShippingDetails(checkout.get(0));
    }

    @And("I review, confirm and place the order")
    public void iReviewConfirmAndPlaceTheOrder() {
        checkoutPage.reviewPaymentAndPlaceOrder();
    }

    @Then("the order is successful")
    public void theOrderIsSuccessful() {
        assertTrue(checkoutPage.wasTheOrderSuccessful());
    }
}
