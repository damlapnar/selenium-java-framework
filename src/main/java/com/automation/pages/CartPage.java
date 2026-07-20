package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(css = "[data-test='checkout']")
    private WebElement checkoutButton;

    @FindBy(css = "[data-test='continue-shopping']")
    private WebElement continueShoppingButton;

    public int getItemCount() {
        return cartItems.size();
    }

    public boolean isItemInCart(String itemName) {
        return driver.findElements(By.xpath(
            "//div[@class='cart_item'][.//div[@class='inventory_item_name' and text()='"
                + itemName + "']]"
        )).size() > 0;
    }

    public void removeItem(String itemName) {
        WebElement button = driver.findElement(By.xpath(
            "//div[@class='cart_item'][.//div[@class='inventory_item_name' and text()='"
                + itemName + "']]//button"
        ));
        click(button);
    }

    public void proceedToCheckout() {
        click(checkoutButton);
    }

    public void continueShopping() {
        click(continueShoppingButton);
    }
}
