package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import support.Registration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegisterPage extends LoadableComponent<RegisterPage> {

    private final WebDriver driver;

    @FindBy(className = "submit")
    private WebElement createAccountButton;

    @FindBy(id = "firstname")
    private WebElement firstName;

    @FindBy(id = "lastname")
    private WebElement lastName;

    @FindBy(id = "email_address")
    private WebElement email;

    @FindBy(id = "password")
    private WebElement password;

    @FindBy(id = "password-confirmation")
    private WebElement confirmPassword;

    public RegisterPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/create/");
    }

    @Override
    protected void isLoaded() throws Error {
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.endsWith("create/"), "Not on the register page: " + currentUrl);
    }

    public void registerAs(Registration registration) {
        firstName.sendKeys(registration.getFirstName());
        lastName.sendKeys(registration.getLastName());
        email.sendKeys(registration.getEmail());
        password.sendKeys(registration.getPassword());
        confirmPassword.sendKeys(registration.getConfirmPassword());

        createAccountButton.click();
    }

    public void clickCreateAccountButton() {
        createAccountButton.click();
    }

    public boolean wasAccountCreated() {
        String currentUrl = driver.getCurrentUrl();
        return !currentUrl.endsWith("create/");
    }

    public boolean doesUserAlreadyExist() {
        return driver.findElement(By.className("messages")) != null;
    }
}
