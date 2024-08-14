package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.DataTableType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import pom.RegisterPage;
import support.Registration;
import util.TestUtil;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterStepDefinitions {

    private final WebDriver driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
    private final RegisterPage registerPage;

    public RegisterStepDefinitions() {
        this.registerPage = new RegisterPage(driver);
    }

    @After
    public void quit() {
        driver.quit();
    }

    @DataTableType
    public Registration defineRegistration(Map<String, String> entry) {
        return new Registration(entry.get("firstName"), entry.get("lastName"), entry.get("email"),
                entry.get("password"), entry.get("confirmPassword"));
    }

    @Given("I am on the create account page")
    public void iAmOnTheCreateAccountPage() {
        registerPage.get();
    }

    @When("I click the create account button")
    public void iClickTheCreateAccountButton() {
        registerPage.clickCreateAccountButton();
    }

    @When("I register with the parameters")
    public void iRegisterWithTheParameters(List<Registration> registrations) {
        registerPage.registerAs(registrations.get(0));
    }

    @Then("no account is created")
    public void noAccountIsCreated() {
        assertFalse(registerPage.wasAccountCreated() && registerPage.doesUserAlreadyExist());
    }

    @Then("my account is created")
    public void myAccountIsCreated() {
        assertTrue(registerPage.wasAccountCreated() || registerPage.doesUserAlreadyExist());
    }
}
