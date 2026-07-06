package com.automation.tests;

import com.automation.pages.InventoryPage;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class InventoryTest extends BaseTest {

    private InventoryPage inventoryPage;

    @BeforeMethod
    public void navigateAfterLogin() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage = new InventoryPage();
    }

    @Test(description = "Inventory page shows 6 products")
    public void testProductCount() {
        Assert.assertEquals(inventoryPage.getProductCount(), 6);
    }

    @Test(description = "Adding item updates cart badge")
    public void testAddToCart() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "1");
    }

    @Test(description = "Adding multiple items updates cart count")
    public void testAddMultipleItems() {
        inventoryPage.addItemToCart("Sauce Labs Backpack");
        inventoryPage.addItemToCart("Sauce Labs Bike Light");
        Assert.assertEquals(inventoryPage.getCartBadgeCount(), "2");
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
