package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.data.TestData;
import com.automation.pages.InventoryPage;
import com.automation.pages.NavigationPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTest extends AuthenticatedBaseTest {

    private NavigationPage navigationPage;

    @BeforeMethod
    public void initPage() {
        navigationPage = new NavigationPage();
    }

    @Test(description = "Burger menu opens the sidebar")
    public void testOpenMenu() {
        navigationPage.openMenu();
        Assert.assertTrue(navigationPage.isMenuOpen(), "Sidebar should be open");
        Assert.assertTrue(navigationPage.isInventoryLinkDisplayed(), "Inventory link should be visible in the open menu");
    }

    @Test(description = "Logout returns to the login page")
    public void testLogout() {
        navigationPage.logout();
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertFalse(url.contains("inventory"), "Logout should leave the inventory page: " + url);
    }

    @Test(description = "Reset app state clears the cart badge")
    public void testResetAppStateClearsCart() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        Assert.assertTrue(inventoryPage.isCartBadgeVisible(), "Cart badge should show after adding an item");

        navigationPage.resetAppState();
        Assert.assertFalse(inventoryPage.isCartBadgeVisible(), "Reset App State should clear the cart badge");
    }
}
