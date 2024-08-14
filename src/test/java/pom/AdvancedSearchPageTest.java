package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import support.AdvancedSearch;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AdvancedSearchPageTest {

    private WebDriver driver;
    private AdvancedSearchPage advancedSearchPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        advancedSearchPage = new AdvancedSearchPage(driver).get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearchValid.csv", numLinesToSkip = 1)
    void searchFor_withValidData(String productName, String sku, String description, String shortDescription,
                                 String lowerPrice, String upperPrice, String amountOfProducts) {
        int amountOfProductsInteger = convertAmountOfProductsToInteger(amountOfProducts);
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        AdvancedSearchResultsPage advancedSearchResultsPage = advancedSearchPage.searchFor(advancedSearch);

        assertTrue(advancedSearchResultsPage.getProductResultsPage().areThisAmountOfProductsFound(
                amountOfProductsInteger));
    }

    private int convertAmountOfProductsToInteger(String amountOfProducts) {
        if (amountOfProducts == null) {
            return 0;
        } else {
            return Integer.parseInt(amountOfProducts);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/advancedsearch/advancedSearchInvalid.csv", numLinesToSkip = 1)
    void searchFor_withInvalidData(String productName, String sku, String description, String shortDescription,
                                 String lowerPrice, String upperPrice) {
        AdvancedSearch advancedSearch = new AdvancedSearch(productName, sku, description, shortDescription,
                lowerPrice, upperPrice);

        AdvancedSearchResultsPage advancedSearchResultsPage = advancedSearchPage.searchFor(advancedSearch);

        assertFalse(advancedSearchResultsPage.getProductResultsPage().wereAnyProductsFound());
    }
}