package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import support.SearchTerm;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchTermsPage extends LoadableComponent<SearchTermsPage> {

    private final WebDriver driver;

    public SearchTermsPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://magento.softwaretestingboard.com/search/term/popular/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("popular/"), "Not on the search terms page: " + url);
    }

    public SearchResultsPage clickThisSearchTerm(SearchTerm searchTerm) {
        findThisSearchTerm(searchTerm).click();

        return new SearchResultsPage(driver, searchTerm.getSearchTerm());
    }

    private WebElement findThisSearchTerm(SearchTerm searchTerm) {
        List<WebElement> searchTerms = driver.findElements(By.className("item"));

        return searchTerms.stream()
                .filter(term -> term.getText().equals(searchTerm.getSearchTerm()))
                .findFirst().get();
    }

    public boolean hasMySearchBeenSuccessful() {
        return !driver.getCurrentUrl().endsWith("popular/");
    }
}
