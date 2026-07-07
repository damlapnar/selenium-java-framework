package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(css = ".inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(css = ".shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "select.product_sort_container")
    private WebElement sortDropdown;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartLink;

    public InventoryPage() { super(); }

    public int getProductCount() { return inventoryItems.size(); }

    public void addItemToCart(String itemName) {
        WebElement btn = driver.findElement(
            By.xpath("//div[@class='inventory_item'][.//div[text()='" + itemName + "']]//button"));
        click(btn);
    }

    public void sortBy(String option) {
        new Select(sortDropdown).selectByValue(option);
    }

    public String getCartBadgeCount() { return getText(cartBadge); }

    public boolean isCartBadgeVisible() { return isDisplayed(cartBadge); }

    public void goToCart() { click(cartLink); }

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
