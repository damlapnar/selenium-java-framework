package com.automation.tests;

import com.automation.data.TestData;
import com.automation.pages.InventoryPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends AuthenticatedBaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void initPage() {
        inventoryPage = new InventoryPage();
    }

    @Test(description = "Inventory page displays all six products")
    public void testDisplaysAllProducts() {
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }

    @Test(description = "Adding an item updates the cart badge")
    public void testAddItemToCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1");
    }

    @Test(description = "Adding multiple items updates the cart badge count")
    public void testAddMultipleItemsToCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.addItemToCart(TestData.Products.BIKE_LIGHT);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "2");
    }

    @Test(description = "Removing an item from the inventory page clears the cart badge")
    public void testRemoveItemFromCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        Assert.assertEquals(inventoryPage.getCartBadgeText(), "1");
        inventoryPage.removeItemFromCart(TestData.Products.BACKPACK);
        Assert.assertFalse(inventoryPage.isCartBadgeDisplayed(), "Cart badge should disappear at zero items");
    }
}
