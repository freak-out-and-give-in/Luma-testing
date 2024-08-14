package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductPageTest {

    private WebDriver driver;
    private ProductPage productPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    private String makeFullUrl(String productNameUrlPath) {
        return "https://magento.softwaretestingboard.com/" + productNameUrlPath;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productValid.csv", numLinesToSkip = 1)
    void getSku_withValidData(String productNameUrlPath, String name, String sku) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        assertEquals(sku, productPage.getSku());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productInvalid.csv", numLinesToSkip = 1)
    void getSku_withInvalidData(String productNameUrlPath, String name, String sku) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        assertNotEquals(sku, productPage.getSku());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productValid.csv", numLinesToSkip = 1)
    void getName_withValidData(String productNameUrlPath, String name) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        assertEquals(name, productPage.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productValid.csv", numLinesToSkip = 1)
    void addToCart_positive(String productNameUrlPath) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        productPage.addToCart();

        assertTrue(productPage.isTheCartBeingAddedTo());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productValid.csv", numLinesToSkip = 1)
    void addToCart_negative(String productNameUrlPath) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        assertFalse(productPage.isTheCartBeingAddedTo());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/review/review.csv", numLinesToSkip = 1)
    void addReview_positive(int rating, String nickname, String summary, String description) {
        productPage = new ProductPage(driver, makeFullUrl("gwen-drawstring-bike-short.html")).get();

        productPage.addReview(rating, nickname, summary, description);

        assertTrue(productPage.wasTheReviewSuccessfullyAdded());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/product/productValid.csv", numLinesToSkip = 1)
    void addReview_negative(String productNameUrlPath) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        assertFalse(productPage.wasTheReviewSuccessfullyAdded());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/compare/compare.csv", numLinesToSkip = 1)
    void addToComparisonList_1(String productNameUrlPath) {
        productPage = new ProductPage(driver, makeFullUrl(productNameUrlPath)).get();

        productPage.addToComparisonList();
        String productName = productPage.getName();
        ComparisonPage comparisonPage = new ComparisonPage(driver).get();

        assertTrue(comparisonPage.areAnyOfTheseProductsHere(List.of(productName)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/compare/compare.csv", numLinesToSkip = 1)
    void addToComparisonList_3(String productNameUrlPath1, String productNameUrlPath2, String productNameUrlPath3) {
        List<String> productNameUrls = List.of(productNameUrlPath1, productNameUrlPath2, productNameUrlPath3);

        List<String> productNames = new ArrayList<>();
        for (String productNameUrl : productNameUrls) {
            productPage = new ProductPage(driver, makeFullUrl(productNameUrl)).get();

            productPage.addToComparisonList();
            productNames.add(productPage.getName());
        }

        ComparisonPage comparisonPage = new ComparisonPage(driver).get();

        assertTrue(comparisonPage.areAnyOfTheseProductsHere(productNames));
    }
}