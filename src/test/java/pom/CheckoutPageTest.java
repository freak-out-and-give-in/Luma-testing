package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import support.Checkout;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutPageTest {

    private WebDriver driver;
    private CheckoutPage checkoutPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        BasePage basePage = new BasePage(driver);

        TestUtil.addProductToCart(driver, "https://magento.softwaretestingboard.com/montana-wind-jacket.html");
        checkoutPage = basePage.proceedToCheckout(true);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/checkout/checkoutValid.csv", numLinesToSkip = 1)
    void fillOutShippingDetails_withValidData(String email, String firstName, String lastName, String company, String streetAddress1,
                                              String streetAddress2, String streetAddress3, String city, String postalCode,
                                              String country, String phoneNumber, String shippingMethod) {
        Checkout checkout = new Checkout(email, firstName, lastName, company, streetAddress1, streetAddress2,
                streetAddress3, city, postalCode, country, phoneNumber, shippingMethod);

        checkoutPage.fillOutShippingDetails(checkout);

        assertTrue(checkoutPage.amIOnTheReviewAndPlaceOrderSection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/checkout/checkoutInvalid.csv", numLinesToSkip = 1)
    void fillOutShippingDetails_withInvalidData(String email, String firstName, String lastName, String company, String streetAddress1,
                                                String streetAddress2, String streetAddress3, String city, String postalCode,
                                                String country, String phoneNumber, String shippingMethod) {
        Checkout checkout = new Checkout(email, firstName, lastName, company, streetAddress1, streetAddress2,
                streetAddress3, city, postalCode, country, phoneNumber, shippingMethod);

        checkoutPage.fillOutShippingDetails(checkout);

        assertFalse(checkoutPage.amIOnTheReviewAndPlaceOrderSection());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/checkout/checkoutValid.csv", numLinesToSkip = 1)
    void reviewPaymentAndPlaceOrder_withValidData(String email, String firstName, String lastName, String company, String streetAddress1,
                                                  String streetAddress2, String streetAddress3, String city, String postalCode,
                                                  String country, String phoneNumber, String shippingMethod) {
        Checkout checkout = new Checkout(email, firstName, lastName, company, streetAddress1, streetAddress2,
                streetAddress3, city, postalCode, country, phoneNumber, shippingMethod);
        checkoutPage.fillOutShippingDetails(checkout);

        checkoutPage.reviewPaymentAndPlaceOrder();

        assertTrue(checkoutPage.wasTheOrderSuccessful());
    }

    @Test
    void wasTheOrderSuccessful() {
        assertFalse(checkoutPage.wasTheOrderSuccessful());
    }
}