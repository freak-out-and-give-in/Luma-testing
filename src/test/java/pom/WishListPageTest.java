package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class WishListPageTest {

    private WebDriver driver;
    private WishListPage wishListPage;
    private CategoryPage categoryPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        categoryPage = new CategoryPage(driver, "men/bottoms-men/shorts-men.html");
        TestUtil.fastRegisterAndLogin(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void howManyProductsAreInMyWishList_one() {
        categoryPage.get();
        ProductPage productPage = categoryPage.getProductResultsPage().selectNthProduct(1);
        wishListPage = productPage.addToWishlist();

        assertEquals(wishListPage.howManyProductsAreInMyWishList(), 1);
    }

    @Test
    void howManyProductsAreInMyWishList_zero() {
        wishListPage = new WishListPage(driver).get();

        assertEquals(wishListPage.howManyProductsAreInMyWishList(), 0);
    }
}