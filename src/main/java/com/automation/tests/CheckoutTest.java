package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.data.TestData;
import com.automation.pages.CheckoutPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// CartBaseTest already seeds a Backpack and lands on the cart page; this
// only needs to take the one extra step into checkout. TestNG runs
// superclass @BeforeMethod methods first, so seedCart() always completes
// before proceedToCheckout() here.
public class CheckoutTest extends CartBaseTest {

    @BeforeMethod
    public void proceedToCheckout() {
        cartPage.proceedToCheckout();
    }

    @Test(description = "Checkout form is displayed on step one")
    public void testCheckoutFormDisplayed() {
        CheckoutPage checkout = new CheckoutPage();
        Assert.assertTrue(checkout.isFormDisplayed(), "Checkout step-one form should be visible");
    }

    @Test(description = "Missing first name shows validation error")
    public void testMissingFirstNameError() {
        CheckoutPage checkout = new CheckoutPage();
        checkout.fillShippingInfo("", TestData.Shipping.LAST_NAME, TestData.Shipping.POSTAL_CODE);
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
