package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import support.User;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginPage extends LoadableComponent<LoginPage> {

    private final WebDriver driver;

    @FindBy(id = "email")
    private WebElement email;

    @FindBy(id = "pass")
    private WebElement password;

    @FindBy(id = "send2")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        PageFactory.initElements(driver, this);
    }

    @Override
    public void load() {
        driver.get("https://magento.softwaretestingboard.com/customer/account/login/");
    }

    @Override
    protected void isLoaded() throws Error {
        String url = driver.getCurrentUrl();
        assertTrue(url.endsWith("login/"), "Not on the login page: " + url);
    }

    public void loginAs(User user) {
        this.email.sendKeys(user.getEmail());
        this.password.sendKeys(user.getPassword());

        loginButton.click();
    }

    public void clickLoginButton() {
        loginButton.click();
    }

    public boolean amILoggedIn() {
        return !driver.getCurrentUrl().endsWith("login/");
    }
}
