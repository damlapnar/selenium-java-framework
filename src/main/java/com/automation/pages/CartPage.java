package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = ".cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "[data-test='continue-shopping']")
    private WebElement continueShoppingButton;

    public CartPage() { super(); }

    public int getItemCount() { return cartItems.size(); }

    public boolean containsItem(String itemName) {
        return driver.findElements(By.xpath(
            "//div[@class='cart_item'][.//div[text()='" + itemName + "']]")).size() > 0;
    }

    public void removeItem(String itemName) {
        String dataTest = "remove-" + itemName.toLowerCase()
            .replace(" ", "-")
            .replace("(", "")
            .replace(")", "");
        driver.findElement(By.cssSelector("[data-test='" + dataTest + "']")).click();
    }

    public void proceedToCheckout() { click(checkoutButton); }

    public void continueShopping() { click(continueShoppingButton); }

    public String getItemPrice(String itemName) {
        WebElement item = driver.findElement(By.xpath(
            "//div[@class='cart_item'][.//div[text()='" + itemName + "']]"));
        return item.findElement(By.cssSelector(".inventory_item_price")).getText();
    }
}
