package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ProductPage extends BasePage {

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(css = "[data-test^='add-to-cart']")
    private WebElement addToCartButton;

    @FindBy(css = "[data-test^='remove']")
    private WebElement removeFromCartButton;

    @FindBy(css = "[data-test='back-to-products']")
    private WebElement backButton;

    // Same headless-click reliability issue as every other page object here —
    // native click() doesn't consistently register, jsClick() does.
    public void addToCart() {
        jsClick(addToCartButton);
        waitForVisible(removeFromCartButton);
    }

    public void removeFromCart() {
        jsClick(removeFromCartButton);
    }

    public void goBack() {
        jsClick(backButton);
        wait.until(ExpectedConditions.urlContains("inventory.html"));
    }

    public boolean isProductNameDisplayed() {
        return isDisplayed(productName);
    }

    public boolean isProductDescriptionDisplayed() {
        return isDisplayed(productDescription);
    }

    public boolean isProductPriceDisplayed() {
        return isDisplayed(productPrice);
    }

    public boolean isRemoveButtonDisplayed() {
        return isDisplayed(removeFromCartButton);
    }
}
