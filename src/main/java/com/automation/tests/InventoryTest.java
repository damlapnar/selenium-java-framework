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

    @Test(description = "Inventory page shows 6 products")
    public void testProductCount() {
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }

    @Test(description = "Adding item updates cart badge")
    public void testAddToCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");
    }

    @Test(description = "Adding multiple items updates cart count")
    public void testAddMultipleItems() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.addItemToCart(TestData.Products.BIKE_LIGHT);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "2");
    }

    @Test(description = "Removing an item from the inventory page clears the cart badge")
    public void testRemoveItemFromCart() {
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");
        inventoryPage.removeItemFromCart(TestData.Products.BACKPACK);
        Assert.assertFalse(inventoryPage.isCartBadgeVisible(), "Cart badge should disappear at zero items");
    }

    @Test(description = "Sort A-Z places Backpack first")
    public void testSortAZ() {
        inventoryPage.sortBy("az");
        Assert.assertEquals(inventoryPage.getFirstItemName(), "Sauce Labs Backpack");
    }

    @Test(description = "Sort low-to-high places cheapest item first")
    public void testSortByPriceLowHigh() {
        inventoryPage.sortBy("lohi");
        Assert.assertEquals(inventoryPage.getFirstItemPrice(), "$7.99");
    }
}
