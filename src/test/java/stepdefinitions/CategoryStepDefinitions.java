package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.CategoryPage;
import support.SortBy;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CategoryStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final CategoryPage categoryPage;

    public CategoryStepDefinitions() {
        this.categoryPage = new CategoryPage(driver, "men/bottoms-men/shorts-men.html");
    }

    @After
    void tearDown() {
        driver.quit();
    }

    @DataTableType
    public SortBy defineSortBy(String value) {
        return SortBy.valueOf(value.toUpperCase());
    }

    @Given("I am on a category page")
    public void iAmOnACategoryPage() {
        categoryPage.get();
    }

    @When("^I sort by \"([^\"]*)\"$")
    public void iClickSortBy(SortBy sortBy) {
        categoryPage.getProductResultsPage().sortBy(sortBy);
    }

    @Then("^The products are sorted by \"([^\"]*)\"$")
    public void theProductsAreSortedBy(SortBy sortBy) {
        assertTrue(categoryPage.getProductResultsPage().areTheProductsSortedBy(sortBy.getValue()));
    }
}
