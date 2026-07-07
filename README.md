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

---

## Test Architecture

### Why Selenium + TestNG?
Selenium 4's W3C-compliant WebDriver API covers enterprise environments where Chromium-only tools are insufficient. TestNG's `@DataProvider`, parallel listeners, and `testng.xml` suite orchestration reflect real-world enterprise QA setups at scale.

### Design Decisions
| Decision | Choice | Rationale |
|----------|--------|-----------|
| ThreadLocal driver | `DriverFactory` | Each TestNG thread gets its own driver instance; enables parallel test execution without race conditions |
| PageFactory | `BasePage` constructor | `@FindBy` annotations keep selectors close to the page class; lazy element resolution avoids stale references |
| Screenshot on failure | `ScreenshotListener` | Captures the exact failure state; attached automatically by TestNG listener, no per-test boilerplate |
| Suite XML | `testng.xml` | Explicit test ordering and parallel configuration in one file; CI passes `-DsuiteXmlFile` to override |

### Test Pyramid
```
        ┌────────────────────┐
        │  UI Tests (Selenium)│  ← 4 test classes, cross-browser
        ├────────────────────┤
        │  Page Layer         │  ← 4 POMs with explicit waits
        └────────────────────┘
```

### Adding a New Page
1. Create `XxxPage.java` extending `BasePage` using `@FindBy` annotations
2. Create `XxxTest.java` extending `BaseTest`
3. Register the test class in `src/test/resources/testng.xml`

### Running with Docker + Selenium Grid
```bash
docker compose up -d selenium-hub chrome
docker compose run tests
```
