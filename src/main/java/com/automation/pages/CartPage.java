package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "[data-test='continue-shopping']")
    private WebElement continueShoppingButton;

    public CartPage() { super(); }

    public int getItemCount() {
        // Re-query to get fresh count after any DOM updates
        return driver.findElements(By.cssSelector(".cart_item")).size();
    }

    public boolean containsItem(String itemName) {
        // Find any .inventory_item_name whose text equals the item name
        return driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream()
                .anyMatch(el -> itemName.equals(el.getText()));
    }

    public void removeItem(String itemName) {
        String key = "remove-" + itemName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "").replace(")", "")
                .replace(",", "").replace(".", "");
        click(driver.findElement(By.cssSelector("[data-test='" + key + "']")));
    }

    public void proceedToCheckout() {
        click(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    public void continueShopping() {
        click(continueShoppingButton);
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    public String getItemPrice(String itemName) {
        // Find the cart_item that contains the named product, then get its price
        for (WebElement item : driver.findElements(By.cssSelector(".cart_item"))) {
            List<WebElement> names = item.findElements(By.cssSelector(".inventory_item_name"));
            if (!names.isEmpty() && itemName.equals(names.get(0).getText())) {
                return item.findElement(By.cssSelector(".inventory_item_price")).getText();
            }
        }
        throw new RuntimeException("Item not found in cart: " + itemName);
    }
}
