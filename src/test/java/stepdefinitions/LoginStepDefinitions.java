package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.LoginPage;
import support.User;
import util.TestUtil;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final LoginPage loginPage;

    public LoginStepDefinitions() {
        this.loginPage = new LoginPage(driver);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @DataTableType
    public User defineUser(Map<String, String> entry) {
        return new User(entry.get("email"), entry.get("password"));
    }

    @Given("I am on the login page")
    public void iAmOnTheLoginPage() {
        loginPage.get();
    }

    @Given("I already have an account")
    public void iAlreadyHaveAnAccount() {
        TestUtil.registerAs(driver, "darcyW@gmail.com", "g7vEm$gui5TaR");
    }

    @When("I click the login button")
    public void iClickTheLoginButton() {
        loginPage.clickLoginButton();
    }

    @When("I login with the parameters")
    public void iLoginWithTheParameters(List<User> user) {
        loginPage.loginAs(user.get(0));
    }

    @Then("I am not logged in")
    public void iAmNotLoggedIn() {
        assertFalse(loginPage.amILoggedIn());
    }

    @Then("I am logged in")
    public void iAmLoggedIn() {
        assertTrue(loginPage.amILoggedIn());
    }
}
