package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.data.TestData;
import com.automation.pages.InventoryPage;
import com.automation.pages.ProductPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProductTest extends AuthenticatedBaseTest {

    private ProductPage productPage;

    @BeforeMethod
    public void openProduct() {
        InventoryPage inventoryPage = new InventoryPage();
        inventoryPage.openProduct(TestData.Products.BACKPACK);
        productPage = new ProductPage();
    }

    @Test(description = "Product detail page shows name, description, and price")
    public void testProductDetailsDisplayed() {
        Assert.assertTrue(productPage.isProductNameDisplayed(), "Product name should be visible");
        Assert.assertTrue(productPage.isProductDescriptionDisplayed(), "Product description should be visible");
        Assert.assertTrue(productPage.isProductPriceDisplayed(), "Product price should be visible");
    }

    @Test(description = "Adding to cart from the detail page swaps the button to Remove")
    public void testAddToCartFromDetailPage() {
        productPage.addToCart();
        Assert.assertTrue(productPage.isRemoveButtonDisplayed(), "Button should switch to Remove after adding to cart");
    }

    @Test(description = "Back button returns to the inventory page")
    public void testBackButtonReturnsToInventory() {
        productPage.goBack();
        String url = DriverFactory.getDriver().getCurrentUrl();
        Assert.assertTrue(url.contains("inventory.html"), "Should return to inventory: " + url);
    }
}
