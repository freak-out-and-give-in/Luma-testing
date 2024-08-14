package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import support.AdvancedSearch;
import support.SortBy;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.*;

class ProductResultsPageTest {

    private WebDriver driver;
    private AdvancedSearchResultsPage advancedSearchResultsPage;
    private CategoryPage categoryPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearchValid.csv", numLinesToSkip = 1)
    void doAllProductsContainThisName_withValidData(String productName, String sku, String description,
                                                    String shortDescription, String lowerPrice, String upperPrice) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        advancedSearchResultsPage = new AdvancedSearchResultsPage(driver, advancedSearch).get();

        assertTrue(advancedSearchResultsPage.getProductResultsPage().doAllProductsContainThisName(productName));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearchValid.csv", numLinesToSkip = 1)
    void doAllProductsContainThisSku_withValidData(String productName, String sku, String description,
                                                   String shortDescription, String lowerPrice, String upperPrice) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        advancedSearchResultsPage = new AdvancedSearchResultsPage(driver, advancedSearch).get();

        assertTrue(advancedSearchResultsPage.getProductResultsPage().doAllProductsContainThisSku(sku));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearchValid.csv", numLinesToSkip = 1)
    void areThisAmountOfProductsFound_withValidData(String productName, String sku, String description,
                                                    String shortDescription, String lowerPrice, String upperPrice,
                                                    int amountOfProducts) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        advancedSearchResultsPage = new AdvancedSearchResultsPage(driver, advancedSearch).get();

        assertTrue(advancedSearchResultsPage.getProductResultsPage().areThisAmountOfProductsFound(amountOfProducts));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearch_existentProducts.csv", numLinesToSkip = 1)
    void wereAnyProductsFound_withExistentData(String productName, String sku, String description,
                                               String shortDescription, String lowerPrice, String upperPrice) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        advancedSearchResultsPage = new AdvancedSearchResultsPage(driver, advancedSearch).get();

        assertTrue(advancedSearchResultsPage.getProductResultsPage().wereAnyProductsFound());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearch_nonExistentProducts.csv", numLinesToSkip = 1)
    void wereAnyProductsFound_withNonExistentData(String productName, String sku, String description,
                                                  String shortDescription, String lowerPrice, String upperPrice) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        advancedSearchResultsPage = new AdvancedSearchResultsPage(driver, advancedSearch).get();

        assertFalse(advancedSearchResultsPage.getProductResultsPage().wereAnyProductsFound());
    }

    @ParameterizedTest
    @EnumSource(SortBy.class)
    void sortBy_valid(SortBy sortBy) {
        categoryPage = new CategoryPage(driver, "women/tops-women/jackets-women.html").get();

        categoryPage.getProductResultsPage().sortBy(sortBy);

        assertTrue(categoryPage.getProductResultsPage().areTheProductsSortedBy(sortBy.getValue()));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void selectNthProduct_1(String path, String firstProductName) {
        categoryPage = new CategoryPage(driver, path).get();
        ProductPage productPage = categoryPage.getProductResultsPage().selectNthProduct(1);

        assertEquals(firstProductName, productPage.getName());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void selectNthProduct_4(String path, String firstProductName, String secondProductName, String thirdProductName,
                            String fourthProductName) {
        categoryPage = new CategoryPage(driver, path).get();
        ProductPage productPage = categoryPage.getProductResultsPage().selectNthProduct(4);

        assertEquals(fourthProductName, productPage.getName());
    }
}