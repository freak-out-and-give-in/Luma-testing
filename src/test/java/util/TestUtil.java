package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import pom.*;
import support.Checkout;
import support.Registration;

import java.util.List;
import java.util.Random;

public class TestUtil {

    private static final String baseUrl = "https://magento.softwaretestingboard.com/";

    public static WebDriver getDriverFromBrowser(String browser) {
        return switch (browser) {
            case "chrome" -> new ChromeDriver();
            case "firefox" -> new FirefoxDriver();
            case "edge" -> new EdgeDriver();
            default -> throw new IllegalArgumentException("No browser has the name " + browser);
        };

    }

    // After using this method you need to .get() back to your original url, as this stays on the registration page
    public static void registerAs(WebDriver driver, String email, String password) {
        Registration registration = new Registration(makeRandomName(), makeRandomName(), email, password, password);

        RegisterPage registerPage = new RegisterPage(driver).get();
        registerPage.registerAs(registration);
    }

    private static String makeRandomName() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 12;

        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int) (random.nextFloat() * (rightLimit - leftLimit + 1));

            // First letter is capitalised
            if (i == 0) {
                buffer.append((char) (randomLimitedInt - 32));
            } else {
                buffer.append((char) randomLimitedInt);
            }
        }

        return buffer.toString();
    }

    private static String makeRandomEmail() {
        return makeRandomName() + "@domain.com";
    }

    private static String makeRandomPassword() {
        return makeRandomName() + "56%^D";
    }

    public static void addProductToCart(WebDriver driver, String url) {
        ProductPage productPage = new ProductPage(driver, url).get();

        productPage.addToCart();
    }

    public static void navigateToHomePage(WebDriver driver) {
        driver.navigate().to(baseUrl);
    }

    public static void fastRegisterAndLogin(WebDriver driver) {
        String email = makeRandomEmail();
        String password = makeRandomPassword();

        registerAs(driver, email, password);
    }

    public static void addProductsToComparisonList(WebDriver driver, int amountOfProducts, CategoryPage categoryPage,
                                                   List<String> namesOfProductsAddedToComparisonList) {
        for (int i = 1; i <= amountOfProducts; i++) {
            ProductPage productPage = categoryPage.getProductResultsPage().selectNthProduct(i);
            namesOfProductsAddedToComparisonList.add(productPage.getName());

            productPage.addToComparisonList();
            driver.navigate().back();
            driver.navigate().back();
        }
    }

    public static void addToCartAndOrderProduct(WebDriver driver, String url) {
        addProductToCart(driver, url);

        CheckoutPage checkoutPage = new BasePage(driver).proceedToCheckout(true);
        checkoutPage.fillOutShippingDetails(new Checkout(makeRandomEmail(), makeRandomName(), makeRandomName(),
                "", "street address 123", "", "", "Southampton",
                "SO15 3TG", "United Kingdom", "1234 56789", "Fixed"));
        checkoutPage.reviewPaymentAndPlaceOrder();
    }
}
