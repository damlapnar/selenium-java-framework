package com.automation.pages;

import com.automation.config.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        super(DriverFactory.getDriver());
        PageFactory.initElements(DriverFactory.getDriver(), this);
    }

    public void navigate(String url) {
        driver.get(url);
    }

    public void login(String username, String password) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
        passwordInput.clear();
        passwordInput.sendKeys(password);
        loginButton.click();
    }

    public boolean isErrorDisplayed() {
        try {
            return errorMessageElement.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public String getErrorMessage() {
        return errorMessageElement.getText();
    }
}
