package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

class SearchResultsPageTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        SearchResultsPage searchResultsPage = new SearchResultsPage(driver, "").get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }
}