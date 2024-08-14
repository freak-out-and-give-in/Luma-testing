package pom;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import util.TestUtil;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ComparisonPageTest {

    private WebDriver driver;
    private ComparisonPage comparisonPage;

    @BeforeEach
    void setUp() {
        driver = TestUtil.getDriverFromBrowser(System.getenv("browser"));
        comparisonPage = new ComparisonPage(driver);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void areAnyOfTheseProductsHere_1(String path, String firstProductName) {
        List<String> listOfProductNames = new ArrayList<>(List.of(firstProductName));
        CategoryPage categoryPage = new CategoryPage(driver, path).get();

        TestUtil.addProductsToComparisonList(driver, 1, categoryPage, listOfProductNames);
        comparisonPage.get();

        assertTrue(comparisonPage.areAnyOfTheseProductsHere(listOfProductNames));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void areAnyOfTheseProductsHere_4(String path, String firstProductName, String secondProductName,
                                     String thirdProductName, String fourthProductName) {
        List<String> listOfProductNames = new ArrayList<>(List.of(firstProductName, secondProductName,
                thirdProductName, fourthProductName));
        CategoryPage categoryPage = new CategoryPage(driver, path).get();

        TestUtil.addProductsToComparisonList(driver, 4, categoryPage, listOfProductNames);
        comparisonPage.get();

        assertTrue(comparisonPage.areAnyOfTheseProductsHere(listOfProductNames));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void removeProduct_1(String path, String firstProductName, String secondProductName) {
        List<String> listOfProductNames = new ArrayList<>(List.of(firstProductName, secondProductName));
        CategoryPage categoryPage = new CategoryPage(driver, path).get();

        TestUtil.addProductsToComparisonList(driver, 2, categoryPage, listOfProductNames);
        comparisonPage.get();
        comparisonPage.removeProduct(secondProductName);

        assertTrue(comparisonPage.areAnyOfTheseProductsHere(List.of(firstProductName)));
        assertFalse(comparisonPage.areAnyOfTheseProductsHere(List.of(secondProductName)));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void removeProduct_All(String path, String firstProductName, String secondProductName, String thirdProductName,
                         String fourthProductName) {
        List<String> listOfProductNames = new ArrayList<>(List.of(firstProductName, secondProductName,
                thirdProductName, fourthProductName));
        CategoryPage categoryPage = new CategoryPage(driver, path).get();

        TestUtil.addProductsToComparisonList(driver, 4, categoryPage, listOfProductNames);
        comparisonPage.get();
        comparisonPage.removeProduct(secondProductName);
        comparisonPage.removeProduct(fourthProductName);
        comparisonPage.removeProduct(firstProductName);
        comparisonPage.removeProduct(thirdProductName);

        assertFalse(comparisonPage.areAnyOfTheseProductsHere(listOfProductNames));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/category/category.csv", numLinesToSkip = 1)
    void removeProduct_nonExistent(String path, String firstProductName, String secondProductName,
                                   String thirdProductName) {
        List<String> listOfProductNames = new ArrayList<>(List.of(firstProductName, secondProductName));
        CategoryPage categoryPage = new CategoryPage(driver, path).get();

        TestUtil.addProductsToComparisonList(driver, 1, categoryPage, listOfProductNames);
        comparisonPage.get();

        assertThrows(NoSuchElementException.class, () -> comparisonPage.removeProduct(thirdProductName));
    }
}