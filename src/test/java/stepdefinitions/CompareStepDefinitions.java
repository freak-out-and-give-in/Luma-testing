package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.CategoryPage;
import pom.ComparisonPage;
import util.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CompareStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final CategoryPage categoryPage;
    private final ComparisonPage comparisonPage;
    private final List<String> namesOfProductsAddedToComparisonList;

    public CompareStepDefinitions() {
        categoryPage = new CategoryPage(driver, "women/bottoms-women/pants-women.html").get();
        comparisonPage = new ComparisonPage(driver);
        namesOfProductsAddedToComparisonList = new ArrayList<>();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("I add {int} products to my comparison list")
    public void iAddProductsToMyComparisonList(int amountOfProducts) {
        TestUtil.addProductsToComparisonList(driver, amountOfProducts, categoryPage,
                namesOfProductsAddedToComparisonList);
    }

    @When("I go to my comparison page")
    public void iGoToMyComparisonPage() {
        comparisonPage.get();
    }

    @Then("both the products are there")
    public void bothTheProductsAreThere() {
        comparisonPage.areAnyOfTheseProductsHere(namesOfProductsAddedToComparisonList);
    }

    @And("remove a product from my comparison list")
    public void removeAProductFromMyComparisonList() {
        String removedProductName = namesOfProductsAddedToComparisonList.get(0);
        namesOfProductsAddedToComparisonList.remove(0);

        comparisonPage.removeProduct(removedProductName);
    }

    @Then("the product is removed from my comparison list")
    public void theProductIsRemovedFromMyComparisonList() {
        assertTrue(comparisonPage.areAnyOfTheseProductsHere(namesOfProductsAddedToComparisonList));
    }
}
