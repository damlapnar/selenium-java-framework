package com.automation.listeners;

import com.automation.config.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotListener implements ITestListener {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        if (DriverFactory.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

                String testName = result.getName();
                Path screenshotDir = Paths.get("target/screenshots");
                Files.createDirectories(screenshotDir);
                Path saved = screenshotDir.resolve(testName + "_FAILED.png");
                Files.write(saved, screenshot);

                log.info("Screenshot saved: {}", saved);
            } catch (IOException e) {
                log.error("Failed to save screenshot", e);
            } catch (WebDriverException e) {
                // The session/window can already be gone by the time a failure is
                // reported (crashed browser, closed window). getScreenshotAs()
                // throws this unchecked, and letting it escape here kills the
                // whole forked surefire JVM instead of just this one test.
                log.error("Could not capture screenshot, browser session unavailable", e);
            }
        }
    }
}
