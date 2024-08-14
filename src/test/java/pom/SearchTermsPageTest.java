package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import support.SearchTerm;
import util.TestUtil;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SearchTermsPageTest {

    private WebDriver driver;
    private SearchTermsPage searchTermsPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        searchTermsPage = new SearchTermsPage(driver).get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/searchterms/searchTermsValid.csv", numLinesToSkip = 1)
    void clickThisSearchTerm_withValidData(String searchTerm) {
        searchTermsPage.clickThisSearchTerm(new SearchTerm(searchTerm));

        assertTrue(searchTermsPage.hasMySearchBeenSuccessful());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/searchterms/searchTermsInvalid.csv", numLinesToSkip = 1)
    void clickThisSearchTerm_withInvalidData(String searchTerm) {
        assertThrows(NoSuchElementException.class, () ->
                searchTermsPage.clickThisSearchTerm(new SearchTerm(searchTerm)));
    }
}