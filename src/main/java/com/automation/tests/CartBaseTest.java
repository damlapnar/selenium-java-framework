package com.automation.tests;

import com.automation.data.TestData;
import com.automation.pages.CartPage;
import com.automation.pages.InventoryPage;
import org.testng.annotations.BeforeMethod;

// Adds a Sauce Labs Backpack and navigates to the cart page — the common
// starting point for cart/checkout test classes, mirroring cartTest in the
// project's Playwright sibling framework.
public class CartBaseTest extends AuthenticatedBaseTest {

    protected CartPage cartPage;

    @BeforeMethod
    public void seedCart() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.addItemToCart(TestData.Products.BACKPACK);
        inventoryPage.goToCart();
        cartPage = new CartPage();
    }
}
