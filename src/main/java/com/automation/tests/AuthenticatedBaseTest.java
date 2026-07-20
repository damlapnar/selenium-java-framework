package com.automation.tests;

import com.automation.data.TestData;
import com.automation.pages.LoginPage;
import org.testng.annotations.BeforeMethod;

// Logs in as standard_user before every test method. TestNG runs superclass
// @BeforeMethod methods before subclass ones, so BaseTest.setUp() (driver
// init) always completes before this login() — each test gets a fresh
// WebDriver and a real UI login, not a shared/reused session.
public class AuthenticatedBaseTest extends BaseTest {

    @BeforeMethod
    public void login() {
        LoginPage loginPage = new LoginPage();
        loginPage.navigate(BASE_URL);
        loginPage.login(TestData.Users.STANDARD_USERNAME, TestData.Users.PASSWORD);
    }
}
