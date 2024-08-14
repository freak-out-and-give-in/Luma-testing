package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.AdvancedSearchPage;
import pom.AdvancedSearchResultsPage;
import support.AdvancedSearch;
import util.TestUtil;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdvancedSearchStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final AdvancedSearchPage advancedSearchPage;
    private AdvancedSearchResultsPage advancedSearchResultsPage;
    private AdvancedSearch advancedSearch;
    public AdvancedSearchStepDefinitions() {
        this.advancedSearchPage = new AdvancedSearchPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @DataTableType
    public AdvancedSearch defineAdvancedSearch(Map<String, String> entry) {
        String productName = entry.getOrDefault("productName", "");
        String sku = entry.getOrDefault("sku", "");
        String description = entry.getOrDefault("description", "");
        String shortDescription = entry.getOrDefault("shortDescription", "");
        String lowerPrice = entry.getOrDefault("lowerPrice", "");
        String upperPrice = entry.getOrDefault("upperPrice", "");

        return new AdvancedSearch(productName, sku, description, shortDescription, lowerPrice, upperPrice);
    }
    
    @Given("I am on the advanced search page")
    public void iAmOnTheAdvancedSearchPage() {
        advancedSearchPage.get();
    }

    @When("I search for the product with")
    public void iSearchForTheProductWith(List<AdvancedSearch> advancedSearches) {
        advancedSearch = advancedSearches.get(0);
        advancedSearchResultsPage = advancedSearchPage.searchFor(advancedSearch);
    }

    @Then("I am redirected to the advanced search result's page")
    public void iAmRedirectedToTheAdvancedSearchResultsPage() {
        advancedSearchResultsPage.isLoaded();
    }

    @And("all the product's names contain the input")
    public void allTheProductsNamesContainTheInput() {
        assertTrue(advancedSearchResultsPage.getProductResultsPage().doAllProductsContainThisName(
                advancedSearch.getProductName()));
    }

    @And("the products contain the sku value")
    public void theProductsContainTheSkuValue() {
        assertTrue(advancedSearchResultsPage.getProductResultsPage().doAllProductsContainThisSku(
                advancedSearch.getSku()));
    }
}
