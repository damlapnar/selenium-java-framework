package com.automation.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessageElement;

    public LoginPage() {
        super();
    }

    public void navigate(String url) {
        driver.get(url);
    }

    public void login(String username, String password) {
        type(usernameInput, username);
        type(passwordInput, password);
        click(loginButton);
    }

    public boolean isErrorDisplayed() {
        return isDisplayed(errorMessageElement);
    }

    public String getErrorMessage() {
        return getText(errorMessageElement);
    }
}
