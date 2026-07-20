package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.pages.CartPage;
import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    private InventoryPage inventoryPage;
    private CartPage cartPage;

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage();
    }

    @Test(description = "Added item appears in cart")
    public void testItemAppearsInCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage = new CartPage();
        Assert.assertTrue(cartPage.containsItem("Sauce Labs Backpack"));
        Assert.assertEquals(cartPage.getItemCount(), 1);
    }

    @Test(description = "Multiple items all appear in cart")
    public void testMultipleItemsInCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");
        inventoryPage.goToCart();
        cartPage = new CartPage();
        Assert.assertEquals(cartPage.getItemCount(), 2);
        Assert.assertTrue(cartPage.containsItem("Sauce Labs Backpack"));
        Assert.assertTrue(cartPage.containsItem("Sauce Labs Bike Light"));
    }

    @Test(description = "Removing item decrements cart count")
    public void testRemoveItemFromCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");
        inventoryPage.goToCart();
        cartPage = new CartPage();
        cartPage.removeItem("Sauce Labs Backpack");
        Assert.assertEquals(cartPage.getItemCount(), 1);
        Assert.assertFalse(cartPage.containsItem("Sauce Labs Backpack"));
    }

    @Test(description = "Cart item shows its price")
    public void testCartItemShowsPrice() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage = new CartPage();
        String price = cartPage.getItemPrice("Sauce Labs Backpack");
        Assert.assertTrue(price.startsWith("$"), "Price should start with $: " + price);
    }

    @Test(description = "Continue shopping returns to inventory")
    public void testContinueShopping() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.goToCart();
        cartPage = new CartPage();
        cartPage.continueShopping();
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory"), "Should return to inventory, got: " + url);
    }
}
