package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = "select.product_sort_container")
    private WebElement sortDropdown;

    public InventoryPage() { super(); }

    public int getProductCount() { return inventoryItems.size(); }

    public void addItemToCart(String itemName) {
        int prevCount = currentCartCount();
        String key = "add-to-cart-" + itemName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "").replace(")", "")
                .replace(",", "").replace(".", "");
        driver.findElement(By.cssSelector("[data-test='" + key + "']")).click();
        // Wait for badge to reflect the new item before proceeding
        int expected = prevCount + 1;
        wait.until(d -> currentCartCount() == expected);
    }

    private int currentCartCount() {
        List<WebElement> badges = driver.findElements(By.cssSelector(".shopping_cart_badge"));
        return badges.isEmpty() ? 0 : Integer.parseInt(badges.get(0).getText());
    }

    public void sortBy(String option) {
        new Select(sortDropdown).selectByValue(option);
    }

    public String getCartBadgeCount() {
        return driver.findElement(By.cssSelector(".shopping_cart_badge")).getText();
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(By.cssSelector(".shopping_cart_badge")).isEmpty();
    }

    public void goToCart() {
        // Direct navigation is more reliable than clicking the cart icon in headless mode
        String origin = driver.getCurrentUrl().replaceAll("(https?://[^/]+).*", "$1");
        driver.navigate().to(origin + "/cart.html");
    }

    public String getFirstItemName() {
        return driver.findElement(By.cssSelector(".inventory_item_name")).getText();
    }

    public String getFirstItemPrice() {
        return driver.findElement(By.cssSelector(".inventory_item_price")).getText();
    }

    public List<String> getAllItemNames() {
        return driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream().map(WebElement::getText).toList();
    }

    public List<Double> getAllItemPrices() {
        return driver.findElements(By.cssSelector(".inventory_item_price"))
                .stream()
                .map(el -> Double.parseDouble(el.getText().replace("$", "")))
                .toList();
    }
}
