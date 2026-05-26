package com.automation.listeners;

import com.automation.config.DriverFactory;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenshotListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        if (DriverFactory.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverFactory.getDriver())
                    .getScreenshotAs(OutputType.BYTES);

                String testName = result.getName();
                Path screenshotDir = Paths.get("target/screenshots");
                Files.createDirectories(screenshotDir);
                Files.write(screenshotDir.resolve(testName + "_FAILED.png"), screenshot);

                System.out.println("Screenshot saved: " + screenshotDir.resolve(testName + "_FAILED.png"));
            } catch (IOException e) {
                System.err.println("Failed to save screenshot: " + e.getMessage());
            }
        }
    }
}
