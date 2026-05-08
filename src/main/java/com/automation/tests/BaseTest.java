package com.automation.tests;

import com.automation.config.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    protected static final String BASE_URL =
        System.getProperty("baseUrl", "https://www.saucedemo.com");

    @BeforeMethod
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser) {
        DriverFactory.initDriver(browser);
    }

    @AfterMethod
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
