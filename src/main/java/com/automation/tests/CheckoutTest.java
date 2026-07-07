package com.automation.tests;

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
    public void setUp() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage();
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        new CartPage().proceedToCheckout();
    }

    @Test(description = "Valid checkout completes with thank you message")
    public void testSuccessfulCheckout() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("Damla", "Pinar", "10001");
        checkout.clickContinue();
        checkout.clickFinish();
        Assert.assertEquals(checkout.getOrderCompleteText(), "Thank you for your order!");
    }

    @Test(description = "Missing first name shows validation error")
    public void testMissingFirstNameError() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("", "Pinar", "10001");
        checkout.clickContinue();
        Assert.assertTrue(checkout.isErrorDisplayed());
        Assert.assertTrue(checkout.getErrorMessage().contains("First Name is required"));
    }

    @Test(description = "Missing last name shows validation error")
    public void testMissingLastNameError() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("Damla", "", "10001");
        checkout.clickContinue();
        Assert.assertTrue(checkout.isErrorDisplayed());
        Assert.assertTrue(checkout.getErrorMessage().contains("Last Name is required"));
    }

    @Test(description = "Step two shows order summary with total")
    public void testOrderSummaryOnStepTwo() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("Damla", "Pinar", "10001");
        checkout.clickContinue();
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("checkout-step-two"), "Should be on step two: " + url);
        Assert.assertFalse(checkout.getTotalLabel().isEmpty(), "Total should be visible");
    }

    @Test(description = "Cancel returns to cart page")
    public void testCancelReturnsToCart() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.clickCancel();
        String url = driver.getCurrentUrl();
        Assert.assertTrue(url.contains("cart"), "Should return to cart: " + url);
    }
}
