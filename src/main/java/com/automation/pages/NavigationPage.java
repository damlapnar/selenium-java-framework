package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NavigationPage extends BasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement burgerMenuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeMenuButton;

    @FindBy(className = "bm-menu-wrap")
    private WebElement menu;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement inventorySidebarLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutSidebarLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetSidebarLink;

    // Native click() on these react-burger-menu controls doesn't reliably
    // register in headless Chrome (same class of issue every other page
    // object in this project already works around) — jsClick() plus an
    // explicit wait for the resulting state is what actually works.
    public void openMenu() {
        jsClick(burgerMenuButton);
        wait.until(ExpectedConditions.visibilityOf(inventorySidebarLink));
    }

    public void closeMenu() {
        jsClick(closeMenuButton);
    }

    public void logout() {
        openMenu();
        jsClick(logoutSidebarLink);
        wait.until(d -> !d.getCurrentUrl().contains("inventory"));
    }

    public void resetAppState() {
        openMenu();
        jsClick(resetSidebarLink);
    }

    // .bm-menu-wrap stays technically "displayed" (per Selenium's
    // display/visibility/opacity checks) even while closed — react-burger-menu
    // hides it off-canvas with a CSS transform, which Selenium's isDisplayed()
    // doesn't account for. A link that only renders once the menu is open is
    // the reliable signal of open/closed state.
    public boolean isMenuOpen() {
        return isDisplayed(inventorySidebarLink);
    }

    public boolean isInventoryLinkDisplayed() {
        return isDisplayed(inventorySidebarLink);
    }
}
