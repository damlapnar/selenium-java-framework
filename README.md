# Selenium Java Framework

![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=flat-square&logo=selenium&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6C37?style=flat-square)
[![CI](https://github.com/damlapnar/selenium-java-framework/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/damlapnar/selenium-java-framework/actions/workflows/selenium-tests.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Cross-browser test automation framework built with Selenium WebDriver 4, Java 17, and TestNG. Features thread-safe driver management, data-driven testing, and parallel browser execution.

## Features

- **Thread-Safe Driver** — `ThreadLocal<WebDriver>` for safe parallel runs
- **Page Object Model** — `@FindBy` annotations with `BasePage` abstraction
- **Cross-Browser** — Chrome, Firefox, Safari via `WebDriverManager`
- **Data-Driven** — TestNG `@DataProvider` for parameterized tests
- **Parallel Execution** — configurable via `testng.xml`
- **CI/CD** — GitHub Actions matrix across browsers

## Project Structure

```
selenium-java-framework/
├── src/main/java/com/automation/
│   ├── config/
│   │   └── DriverFactory.java    # Thread-safe WebDriver management
│   ├── pages/
│   │   ├── BasePage.java         # Common WebElement interactions
│   │   └── LoginPage.java        # Login page POM
│   └── tests/
│       ├── BaseTest.java         # Setup/teardown
│       └── LoginTest.java        # Login test cases
└── src/test/resources/
    └── testng.xml                # Parallel suite config
```

## Prerequisites

- Java 17+
- Maven 3.8+

## Running Tests

```bash
# Default (Chrome, headed)
mvn test

# Specific browser
mvn test -Dbrowser=firefox

# Headless
mvn test -Dheadless=true

# Cross-browser parallel
mvn test -DsuiteFile=src/test/resources/testng.xml
```
