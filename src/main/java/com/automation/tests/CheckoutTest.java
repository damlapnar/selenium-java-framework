package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.pages.CartPage;
import com.automation.pages.CheckoutPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage();
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        new CartPage().proceedToCheckout();
    }

    @Test(description = "Checkout form is displayed on step one")
    public void testCheckoutFormDisplayed() {
        CheckoutPage checkout = new CheckoutPage();
        Assert.assertTrue(checkout.isFormDisplayed(), "Checkout step-one form should be visible");
    }

    @Test(description = "Missing first name shows validation error")
    public void testMissingFirstNameError() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("", "Pinar", "10001");
        checkout.clickContinue();
        Assert.assertTrue(checkout.isErrorDisplayed(), "Error should appear for missing first name");
        Assert.assertTrue(checkout.getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "Cancel on step one returns to cart")
    public void testCancelReturnsToCart() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.clickCancel();
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("cart"), "Cancel should return to cart: " + url);
    }
}
