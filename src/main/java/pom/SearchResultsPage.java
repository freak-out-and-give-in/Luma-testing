package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchResultsPage extends LoadableComponent<SearchResultsPage> {

    private final WebDriver driver;

    private final String url;

    public SearchResultsPage(WebDriver driver, String searchTerm) {
        this.driver = driver;
        url = "https://magento.softwaretestingboard.com/catalogsearch/result/?q=" + searchTerm;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get(url);
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertEquals(url, getUrl(), "Not on the search results page: " + url);
    }

    public String getUrl() {
        return url;
    }
}
