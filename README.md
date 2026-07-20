# Selenium Java Framework

![Selenium](https://img.shields.io/badge/Selenium-43B02A?style=flat-square&logo=selenium&logoColor=white)
![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![TestNG](https://img.shields.io/badge/TestNG-FF6C37?style=flat-square)
[![CI](https://github.com/damlapnar/selenium-java-framework/actions/workflows/selenium-tests.yml/badge.svg)](https://github.com/damlapnar/selenium-java-framework/actions/workflows/selenium-tests.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Cross-browser test automation framework built with Selenium WebDriver 4, Java 17, and TestNG against [saucedemo.com](https://www.saucedemo.com). Features thread-safe driver management, a Selenium Grid-ready `RemoteWebDriver` path, layered base tests for DRY setup, and full login/inventory/cart/checkout coverage.

## Features

- **Thread-Safe Driver** — `ThreadLocal<WebDriver>` for safe parallel runs
- **Page Object Model** — `@FindBy` annotations with a `BasePage` abstraction (JS-click helper for React-controlled elements that flake on native clicks)
- **Cross-Browser** — Chrome, Firefox, Safari via `WebDriverManager`, or remote via Selenium Grid
- **Layered Base Tests** — `BaseTest` → `AuthenticatedBaseTest` → `CartBaseTest`, so each test class only sets up the state it actually needs
- **Centralized Test Data** — `TestData` holds all saucedemo demo accounts, product names, and shipping fields
- **Screenshot on Failure** — `ScreenshotListener` captures a PNG for every failed test, and survives a browser session that's already gone
- **CI/CD** — GitHub Actions workflow running the full suite headless

## Project Structure

```
selenium-java-framework/
├── src/main/java/com/automation/
│   ├── config/
│   │   └── DriverFactory.java      # Thread-safe local + RemoteWebDriver management
│   ├── data/
│   │   └── TestData.java           # Users, products, shipping constants
│   ├── listeners/
│   │   └── ScreenshotListener.java # Failure screenshots (target/screenshots)
│   ├── pages/
│   │   ├── BasePage.java           # Common WebElement interactions, jsClick()
│   │   ├── LoginPage.java
│   │   ├── InventoryPage.java
│   │   ├── ProductPage.java        # Single-product detail view (available, not yet exercised by a test class)
│   │   ├── NavigationPage.java     # Burger menu: logout / reset app state (available, not yet exercised by a test class)
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   └── tests/
│       ├── BaseTest.java               # Driver setup/teardown per test
│       ├── AuthenticatedBaseTest.java   # + logs in as standard_user
│       ├── CartBaseTest.java            # + seeds a Backpack and lands on /cart
│       ├── LoginTest.java
│       ├── InventoryTest.java
│       ├── CartTest.java
│       └── CheckoutTest.java
└── src/test/resources/
    └── testng.xml                  # 4 suites: Login, Inventory, Cart, Checkout
```

`NavigationPage` and `ProductPage` are complete page objects without dedicated test classes yet — a natural next step for anyone extending this suite (logout/reset-state flows, single-product detail assertions).

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
```

---

## Test Architecture

### Why Selenium + TestNG?
Selenium 4's W3C-compliant WebDriver API covers enterprise environments where Chromium-only tools are insufficient. TestNG's listeners, parameterized suites, and `testng.xml` orchestration reflect real-world enterprise QA setups at scale.

### Design Decisions
| Decision | Choice | Rationale |
|----------|--------|-----------|
| ThreadLocal driver | `DriverFactory` | Each TestNG thread gets its own driver instance; enables parallel test execution without race conditions |
| Local or remote | `SELENIUM_REMOTE_URL` env var | If set, `DriverFactory` builds a `RemoteWebDriver` against it (e.g. a Selenium Grid hub); otherwise it launches a local driver via `WebDriverManager`. Same test code either way. |
| PageFactory | `BasePage` constructor | `@FindBy` annotations keep selectors close to the page class; lazy element resolution avoids stale references |
| JS click | `BasePage.jsClick()` | saucedemo's React-controlled buttons occasionally fail a native `WebElement.click()` (element intercepted/not yet interactable); dispatching the click via `JavascriptExecutor` is more reliable in headless CI |
| Layered base tests | `BaseTest` → `AuthenticatedBaseTest` → `CartBaseTest` | TestNG runs superclass `@BeforeMethod`s before the subclass's own, so each layer only adds the setup its tests actually need instead of repeating login/seeding per test class |
| Screenshot on failure | `ScreenshotListener` | Captures the exact failure state; attached automatically by TestNG listener, no per-test boilerplate. Catches `WebDriverException` as well as `IOException` — a session that's already gone (crashed/closed window) is logged, not left to crash the whole suite |
| Suite XML | `testng.xml` | Explicit test ordering in one file; CI can pass `-DsuiteXmlFile` to override |

### Test Pyramid
```
        ┌────────────────────┐
        │  UI Tests (Selenium)│  ← 4 test classes, 20 tests, cross-browser
        ├────────────────────┤
        │  Page Layer         │  ← 6 POMs with explicit waits
        └────────────────────┘
```

### Adding a New Page
1. Create `XxxPage.java` extending `BasePage` using `@FindBy` annotations
2. Create `XxxTest.java` extending `BaseTest` (or `AuthenticatedBaseTest`/`CartBaseTest` if it needs a logged-in session or a seeded cart)
3. Register the test class in `src/test/resources/testng.xml`

### Running with Docker + Selenium Grid
```bash
docker compose up -d selenium-hub chrome
docker compose run tests
```
The `tests` service sets `SELENIUM_REMOTE_URL` pointing at the hub; `DriverFactory` picks it up automatically and drives the `chrome` node instead of launching its own browser.
