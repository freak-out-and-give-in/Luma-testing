package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.WebDriver;
import support.Registration;
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegisterPageTest {

    private WebDriver driver;
    private RegisterPage registerPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        registerPage = new RegisterPage(driver).get();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/registration/registrationValid.csv", numLinesToSkip = 1)
    void registerAs_withValidRegistration(String firstName, String lastName, String email,
                                                              String password, String confirmPassword) {
        Registration registration = new Registration(firstName, lastName, email, password, confirmPassword);

        registerPage.registerAs(registration);

        assertTrue(registerPage.wasAccountCreated() || registerPage.doesUserAlreadyExist());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/registration/registrationInvalid.csv", numLinesToSkip = 1)
    void registerAs_withInvalidRegistration(String firstName, String lastName, String email,
                                                              String password, String confirmPassword) {
        Registration registration = new Registration(firstName, lastName, email, password, confirmPassword);

        registerPage.registerAs(registration);

        assertFalse(registerPage.wasAccountCreated() && registerPage.doesUserAlreadyExist());
    }
}