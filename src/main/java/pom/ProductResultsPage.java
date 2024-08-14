package pom;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import support.Product;
import support.SortBy;

import java.util.ArrayList;
import java.util.List;

public class ProductResultsPage {

    private final WebDriver driver;

    private List<Product> products = new ArrayList<>();

    @FindBy(id = "sorter")
    private WebElement sortOptions;

    public ProductResultsPage(WebDriver driver) {
        this.driver = driver;
        this.products = getAllProductsOnEveryPage();

        PageFactory.initElements(driver, this);
    }

    public void initialiseProducts() {
        products = getAllProductsOnEveryPage();
    }

    private List<Product> findWebElementsAndConvertToProducts() {
        return driver.findElements(By.className("product-item-info")).stream()
                .map(webElement -> new Product(
                        webElement.findElement(By.className("product-item-link")).getText(),
                        webElement.findElement(By.className("product-item-link")).getAttribute("href"),
                        Double.parseDouble(webElement.findElement(By.className("price"))
                                .getText().substring(1))))
                .toList();
    }

    private List<Product> getAllProductsOnEveryPage() {
        // Gets the first, current page of products
        List<Product> productWebElements = new ArrayList<>(findWebElementsAndConvertToProducts());
        WebElement nextPageButton;
        try {
            nextPageButton = driver.findElement(By.className("next"));
        } catch (Exception e) {
            return productWebElements;
        }

        // While there is a next page of products
        while (nextPageButton != null) {
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", nextPageButton);

            // Adds all products on the page to the list
            productWebElements.addAll(findWebElementsAndConvertToProducts());

            // Finds the button to access the next page of products
            try {
                nextPageButton = driver.findElement(By.className("next"));
            } catch (Exception e) {
                break;
            }
        }

        return productWebElements;
    }

    public boolean doAllProductsContainThisName(String word) {
        return products.stream()
                .map(Product::getName)
                .map(String::toLowerCase)
                .allMatch(productName -> productName.contains(word));
    }

    public boolean doAllProductsContainThisSku(String sku) {
        List<String> listOfProductHref = products.stream()
                .map(Product::getUrl)
                .toList();

        for (String productHref : listOfProductHref) {
            ProductPage productPage = new ProductPage(driver, productHref);
            productPage.load();
            String productSku = productPage.getSku();

            if (!productSku.contains(sku)) {
                return false;
            }

            // load()
        }

        return true;
    }

    public boolean areThisAmountOfProductsFound(int amountOfProducts) {
        int amountOfProductsFound;
        try {
            amountOfProductsFound = Integer.parseInt(driver.findElement(By.cssSelector(".toolbar-number"))
                    .getText());
        } catch (NumberFormatException e) {
            throw new RuntimeException("The likely error is that no products were found");
        }

        return amountOfProducts == amountOfProductsFound;
    }

    public boolean wereAnyProductsFound() {
        try {
            driver.findElement(By.cssSelector(".toolbar-number"));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void sortBy(SortBy sortBy) {
        WebElement sortByOption = calculateSortByOption(sortBy);

        sortOptions.click();
        sortByOption.click();
    }

    private WebElement calculateSortByOption(SortBy sortBy) {
        String sortByName = sortBy.getValue().toLowerCase().trim();
        String sortByValue = "";
        switch (sortByName) {
            case "position" -> sortByValue = "position";
            case "product name" -> sortByValue = "name";
            case "price" -> sortByValue = "price";
        }

        return sortOptions.findElement(By.cssSelector("option[value='" + sortByValue + "']"));
    }

    public boolean areTheProductsSortedBy(String sortByValue) {
        products = getAllProductsOnEveryPage();
        sortByValue = sortByValue.toLowerCase();

        return switch (sortByValue) {
            case "price" -> checkIfSortedByPrice();
            case "product name" -> checkIfSortedByProductName();
            default -> throw new IllegalStateException("No sort by option with the value: " + sortByValue);
        };
    }

    private boolean checkIfSortedByPrice() {
        double previousValue = products.get(0).getPrice();

        for (Product product : products) {
            double currentProductsValue = product.getPrice();

            if (currentProductsValue < previousValue) {
                return false;
            }

            previousValue = currentProductsValue;
        }

        return true;
    }

    private boolean checkIfSortedByProductName() {
        String previousName = products.get(0).getName();

        for (Product product : products) {
            String currentName = product.getName();

            if (previousName.charAt(0) > currentName.charAt(0)) {
                return false;
            }

            previousName = currentName;
        }

        return true;
    }

    public ProductPage selectNthProduct(int n) {
        List<WebElement> products = driver.findElements(By.className("product-item"));
        WebElement product = products.get(n - 1);
        String productUrl = product.getAttribute("href");

        product.click();

        return new ProductPage(driver, productUrl);
    }
}
