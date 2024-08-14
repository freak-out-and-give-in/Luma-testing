package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import support.User;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LoginPageTest {

    private WebDriver driver;
    private LoginPage loginPage;

    @BeforeEach
    public void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        loginPage = new LoginPage(driver).get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/login/loginValid.csv", numLinesToSkip = 1)
    void loginAs_withValidData(String email, String password) {
        TestUtil.registerAs(driver, email, password);
        loginPage.get();

        User user = new User(email, password);
        loginPage.loginAs(user);

        assertTrue(loginPage.amILoggedIn());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/login/loginInvalid.csv", numLinesToSkip = 1)
    void loginAs_withInvalidData(String email, String password) {
        User user = new User(email, password);
        loginPage.loginAs(user);

        assertFalse(loginPage.amILoggedIn());
    }

    @Test
    void amILoggedIn_withNoInput() {
        assertFalse(loginPage.amILoggedIn());
    }
}