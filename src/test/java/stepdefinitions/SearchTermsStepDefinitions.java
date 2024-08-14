package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.SearchResultsPage;
import pom.SearchTermsPage;
import support.SearchTerm;
import util.TestUtil;

import java.util.List;
import java.util.Map;

public class SearchTermsStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final SearchTermsPage searchTermsPage;

    public SearchTermsStepDefinitions() {
        this.searchTermsPage = new SearchTermsPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @DataTableType
    public SearchTerm defineSearchTerm(Map<String, String> entry) {
        return new SearchTerm(entry.get("searchTerm"));
    }

    @Given("I am on the search terms page")
    public void iAmOnTheSearchTermsPage() {
        searchTermsPage.get();
    }

    @When("I click the search term")
    public void iClickTheSearchTerm(List<SearchTerm> searchTerm) {
        SearchResultsPage searchResultsPage = searchTermsPage.clickThisSearchTerm(searchTerm.get(0));
    }

    @Then("I am redirected to it's search result page")
    public void iAmRedirectedToItsSearchResultPage() {
        searchTermsPage.hasMySearchBeenSuccessful();
    }
}
