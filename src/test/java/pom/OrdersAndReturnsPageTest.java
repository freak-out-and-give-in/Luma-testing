package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class OrdersAndReturnsPageTest {

    private WebDriver driver;
    private OrdersAndReturnsPage ordersAndReturnsPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        TestUtil.fastRegisterAndLogin(driver);
        TestUtil.addToCartAndOrderProduct(driver,
                "https://magento.softwaretestingboard.com/erika-running-short.html");

        ordersAndReturnsPage = new OrdersAndReturnsPage(driver).get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void viewOrder() {
        ordersAndReturnsPage.viewOrder();

        assertTrue(driver.getCurrentUrl().contains("order_id"));
    }

    @Test
    void isMyOrderCorrect_true() {
        ordersAndReturnsPage.viewOrder();

        assertTrue(ordersAndReturnsPage.isMyOrderCorrect("Erika Running Short"));
    }

    @Test
    void isMyOrderCorrect_false() {
        ordersAndReturnsPage.viewOrder();

        assertFalse(ordersAndReturnsPage.isMyOrderCorrect("Erika Running Sh"));
    }
}