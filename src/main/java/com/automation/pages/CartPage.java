package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "[data-test='continue-shopping']")
    private WebElement continueShoppingButton;

    public CartPage() { super(); }

    public int getItemCount() {
        return driver.findElements(By.cssSelector(".cart_item")).size();
    }

    public boolean containsItem(String itemName) {
        return driver.findElements(By.cssSelector(".inventory_item_name"))
                .stream().anyMatch(el -> itemName.equals(el.getText()));
    }

    public void removeItem(String itemName) {
        String key = "remove-" + itemName.toLowerCase()
                .replace(" ", "-")
                .replace("(", "").replace(")", "")
                .replace(",", "").replace(".", "");
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("[data-test='" + key + "']")));
        jsClick(btn);
        // Wait for item count to decrease
        int sizeBefore = getItemCount() + 1;
        wait.until(d -> driver.findElements(By.cssSelector(".cart_item")).size() < sizeBefore);
    }

    public void proceedToCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton));
        jsClick(checkoutButton);
        wait.until(ExpectedConditions.urlContains("checkout-step-one"));
    }

    public void continueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(continueShoppingButton));
        jsClick(continueShoppingButton);
        wait.until(ExpectedConditions.urlContains("inventory"));
    }

    public String getItemPrice(String itemName) {
        for (WebElement item : driver.findElements(By.cssSelector(".cart_item"))) {
            List<WebElement> names = item.findElements(By.cssSelector(".inventory_item_name"));
            if (!names.isEmpty() && itemName.equals(names.get(0).getText())) {
                return item.findElement(By.cssSelector(".inventory_item_price")).getText();
            }
        }
        throw new RuntimeException("Item not found in cart: " + itemName);
    }

    private void jsClick(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
