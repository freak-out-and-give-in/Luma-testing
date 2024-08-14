package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.BasePage;
import pom.CategoryPage;
import pom.ProductPage;
import pom.WishListPage;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final CategoryPage categoryPage;
    private ProductPage productPage;
    private WishListPage wishListPage;
    private final BasePage basePage;

    public ProductStepDefinitions() {
        categoryPage = new CategoryPage(driver, "men/tops-men/jackets-men.html");
        basePage = new BasePage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Given("I log in")
    public void iLogIn() {
        TestUtil.fastRegisterAndLogin(driver);
    }

    @Given("I am on the category page")
    public void iAmOnTheCategoryPage() {
        categoryPage.get();
    }

    @When("I select a product")
    public void iSelectAProduct() {
        productPage = categoryPage.getProductResultsPage().selectNthProduct(1);
    }

    @And("add the product to my cart")
    public void iAddAProductToMyCart() {
        productPage.addToCart();
    }

    @Then("The product is added to my cart")
    public void theProductIsAddedToMyCart() {
        assertEquals(1, basePage.howManyProductsAreInMyCart(true));
    }

    @And("add the product to my wish list")
    public void addTheProductToMyWishList() {
        wishListPage = productPage.addToWishlist();
    }

    @Then("The product is added to my wish list")
    public void theProductIsAddedToMyWishList() {
        assertEquals(wishListPage.howManyProductsAreInMyWishList(), 1);
    }

    @And("add a review to the product")
    public void addAReviewToTheProduct() {
        productPage.addReview(2, "nickname", "summary", "review");
    }

    @Then("The review has been posted")
    public void theReviewHasBeenPosted() {
        assertTrue(productPage.wasTheReviewSuccessfullyAdded());
    }
}
