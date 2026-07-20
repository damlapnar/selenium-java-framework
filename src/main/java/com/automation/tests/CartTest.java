package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.data.TestData;
import com.automation.pages.CartPage;
import com.automation.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

// Extends AuthenticatedBaseTest rather than CartBaseTest: most tests here
// need two different items in the cart (or none yet), not the one
// pre-seeded Backpack CartBaseTest provides, so each test seeds its own.
public class CartTest extends AuthenticatedBaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPage() {
        inventoryPage = new InventoryPage();
    }

    @Test(description = "Added item appears in cart")
    public void testItemAppearsInCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage();
        Assert.assertTrue(cartPage.containsItem(TestData.Products.BACKPACK));
        Assert.assertEquals(cartPage.getItemCount(), 1);
    }

    @Test(description = "Multiple items all appear in cart")
    public void testMultipleItemsInCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.addItemToCart(TestData.Products.BIKE_LIGHT);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage();
        Assert.assertEquals(cartPage.getItemCount(), 2);
        Assert.assertTrue(cartPage.containsItem(TestData.Products.BACKPACK));
        Assert.assertTrue(cartPage.containsItem(TestData.Products.BIKE_LIGHT));
    }

    @Test(description = "Removing item decrements cart count")
    public void testRemoveItemFromCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.addItemToCart(TestData.Products.BIKE_LIGHT);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage();
        cartPage.removeItem(TestData.Products.BACKPACK);
        Assert.assertEquals(cartPage.getItemCount(), 1);
        Assert.assertFalse(cartPage.containsItem(TestData.Products.BACKPACK));
    }

    @Test(description = "Cart item shows its price")
    public void testCartItemShowsPrice() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage();
        String price = cartPage.getItemPrice(TestData.Products.BACKPACK);
        Assert.assertTrue(price.startsWith("$"), "Price should start with $: " + price);
    }

    @Test(description = "Continue shopping returns to inventory")
    public void testContinueShopping() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.goToCart();
        CartPage cartPage = new CartPage();
        cartPage.continueShopping();
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory"), "Should return to inventory, got: " + url);
    }
}
