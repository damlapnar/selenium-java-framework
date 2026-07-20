package com.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger log = LoggerFactory.getLogger(DriverFactory.class);
    private static final ThreadLocal<WebDriver> driverThread = new ThreadLocal<>();

    public static WebDriver getDriver() {
        return driverThread.get();
    }

    public static void initDriver(String browser) {
        boolean headless = Boolean.parseBoolean(System.getProperty("headless", "false"));
        // docker-compose.yml stands up a Selenium Grid (hub + chrome node) and
        // sets this for the "tests" container, but nothing here ever read it —
        // the container would try to launch a local ChromeDriver against a
        // browser that was never installed in its own image instead of using
        // the Grid it depends_on. RemoteWebDriver against the hub is what
        // actually makes that setup work.
        String remoteUrl = System.getenv("SELENIUM_REMOTE_URL");
        MutableCapabilities options = buildOptions(browser, headless);

        log.info("Starting {} session (headless={}, remote={})", browser, headless, remoteUrl != null);
        WebDriver driver = remoteUrl != null
            ? new RemoteWebDriver(toUrl(remoteUrl), options)
            : createLocalDriver(browser, options);

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driverThread.set(driver);
        log.info("{} session started", browser);
    }

    private static MutableCapabilities buildOptions(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (headless) options.addArguments("--headless");
                yield options;
            }
            case "safari" -> new MutableCapabilities();
            default -> {
                ChromeOptions options = new ChromeOptions();
                if (headless) options.addArguments("--headless=new");
                options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
                yield options;
            }
        };
    }

    private static WebDriver createLocalDriver(String browser, MutableCapabilities options) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                yield new FirefoxDriver((FirefoxOptions) options);
            }
            case "safari" -> new SafariDriver();
            default -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver((ChromeOptions) options);
            }
        };
    }

    private static URL toUrl(String url) {
        try {
            // URI.toURL() replaces the deprecated URL(String) constructor,
            // which performs no validation and is deprecated since Java 20.
            return URI.create(url).toURL();
        } catch (IllegalArgumentException | MalformedURLException e) {
            throw new IllegalArgumentException("Invalid SELENIUM_REMOTE_URL: " + url, e);
        }
    }

    public static void quitDriver() {
        WebDriver driver = driverThread.get();
        if (driver != null) {
            log.info("Quitting session");
            driver.quit();
            driverThread.remove();
        }
    }
}
