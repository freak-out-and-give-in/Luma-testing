package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BasePageTest {

    private WebDriver driver;
    private BasePage basePage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        basePage = new BasePage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void proceedToCheckout_justAddedAProductTrue() {
        TestUtil.addProductToCart(driver, "https://magento.softwaretestingboard.com/supernova-sport-pant.html");

        basePage.proceedToCheckout(true);

        assertTrue(driver.getCurrentUrl().endsWith("shipping"));
    }

    @Test
    void proceedToCheckout_justAddedAProductFalse() {
        TestUtil.addProductToCart(driver, "https://magento.softwaretestingboard.com/supernova-sport-pant.html");

        basePage.proceedToCheckout(false);

        assertTrue(driver.getCurrentUrl().endsWith("shipping"));
    }

    @Test
    void howManyItemsAreInMyCart_oneAdded() {
        TestUtil.addProductToCart(driver, "https://magento.softwaretestingboard.com/supernova-sport-pant.html");

        assertEquals(basePage.howManyProductsAreInMyCart(true), 1);
    }

    @Test
    void howManyItemsAreInMyCart_noneAdded() {
        TestUtil.navigateToHomePage(driver);

        assertEquals(basePage.howManyProductsAreInMyCart(false), 0);
    }
}