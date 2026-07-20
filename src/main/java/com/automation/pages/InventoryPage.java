package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "select.product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    // @FindBy can't parameterize on an item name at compile time, so item-scoped
    // lookups (add/remove/open by product name) go through a fresh By.xpath()
    // built from the current inventory_item_name text instead of a fixed field.
    private WebElement itemContainer(String itemName) {
        return driver.findElement(By.xpath(
            "//div[@class='inventory_item'][.//div[@class='inventory_item_name' and text()='"
                + itemName + "']]"
        ));
    }

    private WebElement itemButton(String itemName) {
        return itemContainer(itemName).findElement(By.tagName("button"));
    }

    public int getProductCount() {
        return productItems.size();
    }

    public void addItemToCart(String itemName) {
        click(itemButton(itemName));
    }

    // Sauce Labs toggles the same button between Add/Remove for a given item,
    // so this is the identical click — sharing itemButton() keeps that locator
    // logic in one place instead of duplicating it.
    public void removeItemFromCart(String itemName) {
        click(itemButton(itemName));
    }

    public void openProduct(String itemName) {
        WebElement nameLink = itemContainer(itemName).findElement(By.className("inventory_item_name"));
        click(nameLink);
    }

    public void sortBy(String value) {
        waitForVisible(sortDropdown);
        new Select(sortDropdown).selectByValue(value);
    }

    public List<String> getProductNames() {
        return productNames.stream().map(WebElement::getText).collect(Collectors.toList());
    }

    public List<Double> getProductPrices() {
        return productPrices.stream()
            .map(el -> Double.parseDouble(el.getText().replaceAll("[^0-9.]", "")))
            .collect(Collectors.toList());
    }

    public String getCartBadgeText() {
        return getText(cartBadge);
    }

    public boolean isCartBadgeDisplayed() {
        return isDisplayed(cartBadge);
    }

    public void goToCart() {
        click(cartLink);
    }
}
