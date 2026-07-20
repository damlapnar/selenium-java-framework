package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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

    public void openMenu() {
        click(burgerMenuButton);
        waitForVisible(menu);
    }

    public void closeMenu() {
        click(closeMenuButton);
    }

    public void logout() {
        openMenu();
        click(logoutSidebarLink);
    }

    public void resetAppState() {
        openMenu();
        click(resetSidebarLink);
    }

    public boolean isMenuOpen() {
        return isDisplayed(menu);
    }

    public boolean isInventoryLinkDisplayed() {
        return isDisplayed(inventorySidebarLink);
    }
}
