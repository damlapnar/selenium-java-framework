package com.automation.tests;

import com.automation.config.DriverFactory;
import com.automation.data.TestData;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeMethod
    public void initPage() {
        loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
    }

    @Test(description = "Valid login navigates to inventory page")
    public void testValidLogin() {
        loginPage.login(TestData.Users.STANDARD_USERNAME, TestData.Users.PASSWORD);
        Assert.assertTrue(
            DriverFactory.getDriver().getCurrentUrl().contains("inventory"),
            "Should navigate to inventory after login"
        );
    }

    @Test(description = "Invalid credentials shows error message")
    public void testInvalidLogin() {
        loginPage.login("invalid_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Error message should be visible");
        Assert.assertTrue(
            loginPage.getErrorMessage().contains("do not match"),
            "Error should mention credentials mismatch"
        );
    }

    @Test(description = "Locked out user shows appropriate error")
    public void testLockedOutUser() {
        loginPage.login(TestData.Users.LOCKED_OUT_USERNAME, TestData.Users.PASSWORD);
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"));
    }

    @Test(dataProvider = "invalidCredentials", description = "Multiple invalid credential combinations")
    public void testInvalidCredentialCombinations(String username, String password, String expectedError) {
        loginPage.login(username, password);
        Assert.assertTrue(
            loginPage.getErrorMessage().contains(expectedError),
            "Expected error: " + expectedError
        );
    }

    @DataProvider(name = "invalidCredentials")
    public Object[][] invalidCredentials() {
        return new Object[][] {
            {"", "secret_sauce", "Username is required"},
            {"standard_user", "", "Password is required"},
            {"wrong", "wrong", "do not match"},
        };
    }
}
