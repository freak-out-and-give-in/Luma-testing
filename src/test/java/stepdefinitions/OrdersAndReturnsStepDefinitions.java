package stepdefinitions;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.OrdersAndReturnsPage;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrdersAndReturnsStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final OrdersAndReturnsPage ordersAndReturnsPage;

    public OrdersAndReturnsStepDefinitions() {
        ordersAndReturnsPage = new OrdersAndReturnsPage(driver);
    }

    @And("I place an order")
    public void iPlaceAnOrder() {
        TestUtil.fastRegisterAndLogin(driver);
        TestUtil.addToCartAndOrderProduct(driver,
                "https://magento.softwaretestingboard.com/erika-running-short.html");
    }

    @And("I am on the orders and returns page")
    public void iAmOnTheOrdersAndReturnsPage() {
        ordersAndReturnsPage.get();
    }

    @When("I view my order")
    public void iViewMyOrder() {
        ordersAndReturnsPage.viewOrder();
    }

    @Then("My order is correct")
    public void myOrderIsCorrect() {
        assertTrue(ordersAndReturnsPage.isMyOrderCorrect("Erika Running Short"));
    }
}
